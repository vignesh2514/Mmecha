package com.motomecha.app.Global_classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.motomecha.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.motomecha.app.R.id.googleMap;

public class NearestPetrol extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    private static final String GOOGLE_API_KEY = "AIzaSyDvk_Tb5IabOrKIg5Z1cgEKfW5z_tQnBug";
    GoogleMap Mmap;
    SupportMapFragment supportMapFragment;
    private int PROXIMITY_RADIUS = 5000;
    private double latitu;
    private double longitu;
    private  String type="gas_station";
    String Url;
    private static final int REQUEST_INTERNET = 200;
    private boolean isEnableNetwork=false;
    private ListView listApps;
    private ProgressDialog dialog;
    Button btn;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    ConnectionDetector c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_petrol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        setTitle("");
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String text = "<font color=#ff1545>PETROL</font> <font color=#ffffff>BUNK</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NearestPetrol.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        dialog=new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...Please wait");

                supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(googleMap);
        supportMapFragment.getMapAsync(this);


    }

    @Override
    public void onLocationChanged(Location location) {
        latitu = location.getLatitude();
        longitu = location.getLongitude();
        LatLng latLng = new LatLng(latitu, longitu);
        Mmap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.nearest_petrol_pin)));
        Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(13));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap=googleMap;
        if (ContextCompat.checkSelfPermission(NearestPetrol.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(NearestPetrol.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(NearestPetrol.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_INTERNET);
            ActivityCompat.requestPermissions(NearestPetrol.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_INTERNET);
        }
        Mmap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Mmap.getUiSettings().setZoomControlsEnabled(true);
        if(bestProvider!=null){
            isEnableNetwork=true;
            if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
                Toast.makeText(this, "If you are using WiFi please Turn On GPS", Toast.LENGTH_LONG);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }
            List<String> matchingProviders = locationManager.getAllProviders();
            for (String provider: matchingProviders){
                mLastLocation = locationManager.getLastKnownLocation(provider);
                if (mLastLocation != null) {
                    onLocationChanged(mLastLocation);
                }
                Mmap.setMyLocationEnabled(true);
            }

            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        }
        else {
            Toast.makeText(this,"Turn On Your Network",Toast.LENGTH_LONG).show();
        }





        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitu + "," + longitu);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=" + type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[2];
        toPass[0] = Mmap;
        toPass[1] = googlePlacesUrl.toString();
        googlePlacesReadTask.execute(toPass);
        c = new ConnectionDetector(NearestPetrol.this);
        if (c.isConnect()) {

            Url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitu + "," + longitu + "&radius=" + PROXIMITY_RADIUS + "&types=" + type + "&sensor=true&key=" + GOOGLE_API_KEY + "";

        new JsonTask().execute(Url);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
    }


    public class JsonTask extends AsyncTask<String,String,List<PetrolList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<PetrolList> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String Line = "";
                while ((Line = reader.readLine()) != null) {
                    stringBuffer.append(Line);
                }

                String finalJson = stringBuffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<PetrolList> BunkList = new ArrayList<>();

                StringBuffer finalStringData = new StringBuffer();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    PetrolList application = new PetrolList();
                    application.setIcon(finalObject.getString("icon"));
                    application.setName(finalObject.getString("name"));

                    JSONObject nextObject = finalObject.getJSONObject("geometry");


                    JSONObject Locations = nextObject.getJSONObject("location");

                    application.setLn(Locations.getString("lng"));
                    application.setLa(Locations.getString("lat"));


                    BunkList.add(application);

                }

                return BunkList;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<PetrolList> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            c = new ConnectionDetector(NearestPetrol.this);
            if (c.isConnect()) {

                listApps = (ListView) findViewById(R.id.listViewParse);
                CustomAdapter customAdapter = new CustomAdapter(NearestPetrol.this, result);
                // Log.d("MapsActivity", String.valueOf(customAdapter));
                listApps.setAdapter(customAdapter);
            }

        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
        }

    }




    private class CustomAdapter  extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater;
        private List<PetrolList> petrolBunks;

        ImageView bunk_logo;
        public CustomAdapter(Activity activity, List<PetrolList> applications) {
            this.activity=activity;
            this.petrolBunks=applications;
        }

        @Override
        public int getCount() {
            return petrolBunks.size();
        }

        @Override
        public Object getItem(int position) {
            return petrolBunks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            /*final Holder mHolder;*/
            final double lat1,lon1,lat2,lon2;
            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_petrol_bunk, null);

            }
            bunk_logo = (ImageView) convertView.findViewById(R.id.bunk_logo);
            final TextView name=(TextView)convertView.findViewById(R.id.bunk_name);
            TextView distance=(TextView)convertView.findViewById(R.id.distance);
            LinearLayout aas=(LinearLayout)convertView.findViewById(R.id.aas);
            PetrolList application=petrolBunks.get(position);
            lat1=latitu;
            lon1=longitu;

            final Location loc1 = new Location("A");
            loc1.setLatitude(lat1);
            loc1.setLongitude(lon1);
            lat2= Double.parseDouble(application.getLa());
            lon2= Double.parseDouble(application.getLn());

            final Location loc2 = new Location("B");
            loc2.setLatitude(lat2);
            loc2.setLongitude(lon2);

            float distanceInMeters = loc1.distanceTo(loc2);
            float distanceIn=distanceInMeters/1000;
            distance.setText(new DecimalFormat("##.##").format(distanceIn)+" km away");

            name.setText(application.getName());
            String hel=application.getName();

            if(hel.contains("Total")||(hel.contains("total"))) {
                int resID = getResources().getIdentifier("total", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            else if(hel.contains("Bharat")||(hel.contains("Bp"))||(hel.contains("BP"))) {
                int resID = getResources().getIdentifier("bharat", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            else if(hel.contains("Indian")||(hel.contains("oil"))) {
                int resID = getResources().getIdentifier("indianoil", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            else if(hel.contains("HP")||(hel.contains("hp"))||(hel.contains("Hindustan"))) {
                int resID = getResources().getIdentifier("hp", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            else if(hel.contains("shell")||(hel.contains("Shell"))||(hel.contains("SHELL"))) {
                int resID = getResources().getIdentifier("shell", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            else
            {
                int resID = getResources().getIdentifier("petrol_bunk", "drawable", getPackageName());
                bunk_logo.setBackgroundResource(resID);
            }
            aas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //     Toast.makeText(parent.getContext(),"Position"+getItemId(position),Toast.LENGTH_LONG).show();
                    Mmap.clear();
                    LatLng origin=new LatLng(lat1,lon1);
                    LatLng dest=new LatLng(lat2,lon2);
                    Mmap.addMarker(new MarkerOptions().position(dest)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.nearest_petrol_pin))
                            .title(name.getText().toString()));
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            });
            return convertView;
        }
        private class DownloadTask extends AsyncTask<String, Void, String> {
            // Downloading data in non-ui thread
            @Override
            protected String doInBackground(String... url) {

                // For storing data from web service
                String data = "";

                try{
                    // Fetching the data from web service
                    data = downloadUrl(url[0]);
                }catch(Exception e){
                    Log.d("Background Task", e.toString());
                }
                return data;
            }

            // Executes in UI thread, after the execution of
            // doInBackground()
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ParserTask parserTask = new ParserTask();

                // Invokes the thread for parsing the JSON data
                parserTask.execute(result);
            }
        }
        /** A class to parse the Google Places in JSON format */
        private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

            // Parsing the data in non-ui thread
            @Override
            protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

                JSONObject jObject;
                List<List<HashMap<String, String>>> routes = null;

                try{
                    jObject = new JSONObject(jsonData[0]);
                    DirectionsJSONParser parser = new DirectionsJSONParser();

                    // Starts parsing data
                    routes = parser.parse(jObject);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return routes;
            }

            // Executes in UI thread, after the parsing process
            @Override
            protected void onPostExecute(List<List<HashMap<String, String>>> result) {
                ArrayList<LatLng> points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();

                // Traversing through all the routes
                for(int i=0;i<result.size();i++){
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for(int j=0;j<path.size();j++){
                        HashMap<String,String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(6);
                    lineOptions.color(Color.RED);
                }

                // Drawing polyline in the Google Map for the i-th route
                Mmap.addPolyline(lineOptions);

            }
        }
        @SuppressLint("LongLogTag")
        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while( ( line = br.readLine()) != null){
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
            }finally{
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

        private String getDirectionsUrl(LatLng origin, LatLng dest){

            // Origin of route
            String str_origin = "origin="+origin.latitude+","+origin.longitude;

            // Destination of route
            String str_dest = "destination="+dest.latitude+","+dest.longitude;

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = str_origin+"&"+str_dest+"&"+sensor;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

            return url;
        }

    }


}

