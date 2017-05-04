package com.motomecha.app.bike_module;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
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
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import java.util.HashMap;

import static com.motomecha.app.R.id.map;

public class BikeMapActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {


    Location mLocation;
    String slat, slng,myplace,servetype,vehicletype;
    double latitude, longitude;
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
        autocompleteFragment.setHint("AREA OR PINCODE");
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
                                startActivity(intent);
                            }
                        }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();

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
        slat = user.get("klati");
        slng = user.get("klongi");
        if (slat.equals("null") || slng.equals("null")) {
            latitude = 12.8711020;
            longitude = 80.2226490;
        } else {
            latitude = Double.parseDouble(slat);
            longitude = Double.parseDouble(slng);
        }
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFrag.getMapAsync(this);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                laln = place.getLatLng();

                handlenewlocation(laln);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }

        });


    }

    @Override
    public void onMapReady(GoogleMap Mmap) {
        LatLng locateme = new LatLng(latitude, longitude);
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json);
        Mmap.setMapStyle(style);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
        Mmap.addMarker(new MarkerOptions().position(new LatLng(12.978424,80.219333)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
        Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));


    }

   public void handlenewlocation(final LatLng laln)
   {
       if (Mmap==null)
       {
           mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
           mapFrag.getMapAsync(new OnMapReadyCallback() {
               @Override
               public void onMapReady(GoogleMap googleMap) {
                   Mmap=googleMap;
                   Mmap.clear();
                   MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                           BikeMapActivity.this, R.raw.style_json);
                   Mmap.setMapStyle(style);
                   Mmap.addMarker(new MarkerOptions().position(laln).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
                   Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(laln,6.5f));
                   // map.animateCamera(CameraUpdateFactory.zoomIn());
                   Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
                   Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
                   Mmap.setMaxZoomPreference(14.5f);
                   Mmap.setMinZoomPreference(6.5f);
                   Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
               }


           });
       }
    else {
           Mmap.clear();
           MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                   BikeMapActivity.this, R.raw.style_json);
           Mmap.setMapStyle(style);
           Mmap.addMarker(new MarkerOptions().position(laln).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
           Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(laln,6.5f));
           Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
           Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
           Mmap.setMaxZoomPreference(14.5f);
           Mmap.setMinZoomPreference(6.5f);
           Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
       }
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
        // map.animateCamera(CameraUpdateFactory.zoomIn());
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
        Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
        Mmap.setMaxZoomPreference(14.5f);
        Mmap.setMinZoomPreference(6.5f);
        Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));

    }

//    @Override
//    public void onLocationChanged(Location location) {
//        latitude=location.getLatitude();
//        longitude=location.getLongitude();
//        LatLng locateme = new LatLng(latitude, longitude);
//        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
//                this, R.raw.style_json);
//        Mmap.setMapStyle(style);
//        Mmap.addMarker(new MarkerOptions().position(locateme).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_markf)));
//        Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(locateme,6.5f));
//        // map.animateCamera(CameraUpdateFactory.zoomIn());
//        Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
//        Circle circle = Mmap.addCircle(new CircleOptions().center(locateme).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
//        Mmap.setMaxZoomPreference(14.5f);
//        Mmap.setMinZoomPreference(6.5f);
//    }
}
