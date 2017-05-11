package com.motomecha.app.bike_module;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.motomecha.app.Global_classes.GlobalUrlInit;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.motomecha.app.R.id.map;

public class BikeMapActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    private ProgressDialog dialog;
    ListView bike_modules_list;

    Location mLocation;
    String slat, slng,myplace,servetype,vehicletype,name,email,mobile_number,kaddress,myurl,vehicleno;
    double latitud, longitud;
    private static final String TAG = BikeMapActivity.class.getSimpleName();
    SupportMapFragment mapFrag;
    GoogleMap Mmap;
    Context context;
    ImageButton bookingmap, callingmap,chatingmap;
    LatLng laln;
    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        setTitle("");
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>SERVICE</font> <font color=#ffffff>PROVIDERS</font>";
        tv.setText(Html.fromHtml(text));
        context = this;
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        name=user.get("name");
        email=user.get("email");
        mobile_number=user.get("pnum");
        kaddress=user.get("kaddre");

        HotlineConfig hotlineConfig = new HotlineConfig("a77f9cdb-24d2-441a-a950-c6a2ee3a97da", "79e50529-54c3-4bd3-a6ff-add1bcfafbb8");
        hotlineConfig.setVoiceMessagingEnabled(true);
        hotlineConfig.setCameraCaptureEnabled(true);
        hotlineConfig.setPictureMessagingEnabled(true);
        Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

        //Update user information
        HotlineUser user1 = Hotline.getInstance(getApplicationContext()).getUser();
        user1.setName(name).setEmail(email).setPhone("+91", mobile_number);;
        Hotline.getInstance(getApplicationContext()).updateUser(user1);
        bike_modules_list=(ListView) findViewById(R.id.bike_module_list);
        bookingmap = (ImageButton) findViewById(R.id.booknow_map);
        callingmap = (ImageButton) findViewById(R.id.call_now_map);
        chatingmap = (ImageButton) findViewById(R.id.chat_map);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
//                .build();
        AutocompleteFilter filter =
                new AutocompleteFilter.Builder().setCountry("IN").build();
        autocompleteFragment.setFilter(filter);
        servetype = getIntent().getStringExtra("servicetype");
        vehicletype = getIntent().getStringExtra("vehicletype");
        vehicleno = getIntent().getStringExtra("vechicleno");
        autocompleteFragment.setHint("AREA OR PINCODE");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                laln = place.getLatLng();
                handlenewlocation(laln);
            }

            @Override
            public void onError(Status status) {

                Log.i(TAG, "An error occurred: " + status);
            }

        });

        callingmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone ="+919840297628";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
            }
        });
        chatingmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hotline.showConversations(BikeMapActivity.this);
            }
        });
        slat = user.get("klati");
        slng = user.get("klongi");
        if (slat.equals("null") || slng.equals("null")) {
            latitud = 12.8711020;
            longitud = 80.2226490;
        } else {
            latitud = Double.parseDouble(slat);
            longitud = Double.parseDouble(slng);
        }
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFrag.getMapAsync(this);

        if (vehicletype.contains(" ")) {
            String[] separated = vehicletype.split(" ");
            String vehicletype1 = separated[0];
            String vehicletype2 = separated[1];
            vehicletype = vehicletype1 + "%20" + vehicletype2;
        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap=googleMap;
        LatLng locateme = new LatLng(latitud, longitud);
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        Mmap.setMapStyle(style);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Mmap.setMyLocationEnabled(true);
        Mmap.addMarker(new MarkerOptions().position(locateme).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
        Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(locateme,6.5f));
        // map.animateCamera(CameraUpdateFactory.zoomIn());
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
        Circle circle = Mmap.addCircle(new CircleOptions().center(locateme).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
        Mmap.setMaxZoomPreference(14.5f);
          Mmap.setMinZoomPreference(6.5f);
  //      Mmap.addMarker(new MarkerOptions().position(new LatLng(12.978424,80.219333)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
    //    Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
        myurl= GlobalUrlInit.BIKE_MERCHANLIST+"?slat="+latitud+"&slng="+longitud+"&serve_type="+servetype+"&vehicletype="+vehicletype;
        new JSONTask().execute(myurl);

    }

   public void handlenewlocation(final LatLng laln)
   {
                   Mmap.clear();
                   MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(BikeMapActivity.this, R.raw.style_json);
                   Mmap.setMapStyle(style);
                   Mmap.addMarker(new MarkerOptions().position(laln).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
                   Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(laln,6.5f));
                   // map.animateCamera(CameraUpdateFactory.zoomIn());
                   Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
                   Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
                   Mmap.setMaxZoomPreference(14.5f);
                   Mmap.setMinZoomPreference(6.5f);
                   latitud=laln.latitude;
                   longitud=laln.longitude;
                   myurl= GlobalUrlInit.BIKE_MERCHANLIST+"?slat="+latitud+"&slng="+longitud+"&serve_type="+servetype+"&vehicletype="+vehicletype;
                   new JSONTask().execute(myurl);



   }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Mmap.clear();
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                BikeMapActivity.this, R.raw.style_json);
        Mmap.setMapStyle(style);
        Mmap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
        Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(laln,6.5f));
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
        Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
        Mmap.setMaxZoomPreference(14.5f);
        Mmap.setMinZoomPreference(6.5f);
      //  Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));

    }
    public class MovieAdapter extends ArrayAdapter {

        private List<bikemerchantlist> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<bikemerchantlist> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;
            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                holder.display_na=(TextView) convertView.findViewById(R.id.textView10);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            final bikemerchantlist categorieslist= movieModelList.get(position);
            holder.display_na.setText(categorieslist.getDisplay_name());
            latitud=Double.parseDouble(categorieslist.getLatitu());
            longitud=Double.parseDouble(categorieslist.getLongitu());
            Mmap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)).title(categorieslist.getDisplay_name()));
            return convertView;
        }

        class ViewHolder{

            private TextView display_na;
        }



    }

    public class JSONTask extends AsyncTask<String,String, List<bikemerchantlist>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        dialog.show();

        }

        @Override
        protected List<bikemerchantlist> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<bikemerchantlist> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
//                   String success=finalObject.getString("success");
//                    if (success.equals("TRUE"))
//                    {
                        bikemerchantlist categorieslist = gson.fromJson(finalObject.toString(), bikemerchantlist.class);
                        movieModelList.add(categorieslist);
  //                  }
//                   else
//                    {
//                        dialog.dismiss();
//                        new DroidDialog.Builder(context)
//                                .icon(R.drawable.msingletone_logo)
//                                .title("WE`LL BE THERE SOON")
//                                .content("WE AREN`T AVAILABLE IN YOUR LOCALITY YET")
//                                .cancelable(true, true)
//                                .negativeButton("EXIT", new DroidDialog.onNegativeListener() {
//                                    @Override
//                                    public void onNegative(Dialog dialog) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .positiveButton("CHAT WITH US !", new DroidDialog.onPositiveListener() {
//                                    @Override
//                                    public void onPositive(Dialog droidDialog) {
//                                        droidDialog.dismiss();
//                                        Hotline.showConversations(BikeMapActivity.this);
//
//                                    }
//
//                                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();
//                    }

                }

                return movieModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }

        @Override
        protected void onPostExecute(final List<bikemerchantlist> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList.size()>=1){
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_bike_merchant, movieModelList);
                bike_modules_list.setAdapter(adapter);
                bookingmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("GENERAL SERVICE")
                                .content("Our regular service offering intends to do away with the need of visiting a garage for your general maintenance needs. We perform maintenance tasks like oil change, air filter cleaning, spark plug cleaning, brake cleaning, chain adjustment, and valve clearance check at your doorstep. Keep your bike fit and road ready!\nWe would love to, but it’s not possible for us. For tasks like engine repair (engine making noise?), mag wheel repair, shocker repair, body repair, clutch plate replacement etc., we suggest you take the bike to an authorized service center. They have the right set of tools needed for the job and the quality won’t be compromised!")
                                .cancelable(true, true)
                                .positiveButton("BOOK NOW", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        Intent intent = new Intent(BikeMapActivity.this, ConfirmBooking.class);
                                        intent.putExtra("kaddress",kaddress);
                                        intent.putExtra("vechicletype","BIKE");
                                        intent.putExtra("vehicleno",vehicleno);
                                        startActivity(intent);
                                    }

                                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();

                    }
                });
            }
            else {
                new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("WE`LL BE THERE SOON")
                                .content("WE AREN`T AVAILABLE IN YOUR LOCALITY YET")
                                .cancelable(true, true)
                                .negativeButton("EXIT", new DroidDialog.onNegativeListener() {
                                    @Override
                                    public void onNegative(Dialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .positiveButton("CHAT WITH US !", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        Hotline.showConversations(BikeMapActivity.this);
                                    }
                                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).show();
                bookingmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("OOPS")
                                .content("NO SERVICE PROVIDER AVAILABLE IN THIS AREA")
                                .cancelable(true, true)
                                .positiveButton("BOOK NOW", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                    }

                                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();

                    }
                });
            }
        }
    }
}
