package com.motomecha.app.bike_module;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
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

public class BikeMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    Location mLocation;
    String slat,slng;
    double latitude, longitude;
    private static final String TAG = BikeMapActivity.class.getSimpleName();
    SupportMapFragment mapFrag;
GoogleMap Mmap;
    LocationManager locationManager;
    boolean GpsStatus;
    Context context;
    ImageButton bookingmap,callingmap;
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
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MY</font> <font color=#ffffff>Map</font>";
        tv.setText(Html.fromHtml(text));
        context = getApplicationContext();
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        slat=user.get("klati");
        slng=user.get("klongi");
        bookingmap=(ImageButton) findViewById(R.id.booknow_map);
        callingmap=(ImageButton) findViewById(R.id.call_now_map);
         autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
//                .build();
        AutocompleteFilter filter =
                new AutocompleteFilter.Builder().setCountry("IN").build();
        autocompleteFragment.setFilter(filter);

        autocompleteFragment.setHint("Search your Location");
bookingmap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(BikeMapActivity.this,ConfirmBooking.class);
        startActivity(intent);
    }
});

        if (slat!=null||slng!=null) {
            latitude=Double.parseDouble(slat);
            longitude=Double.parseDouble(slng);
        } else {
            latitude=12.903293;
            longitude=80.196510;
        }
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFrag.getMapAsync(this);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                laln=place.getLatLng();
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
           // map.animateCamera(CameraUpdateFactory.zoomIn());
           Mmap.animateCamera(CameraUpdateFactory.zoomTo(12.5f), 2000, null);
           Circle circle = Mmap.addCircle(new CircleOptions().center(laln).radius(5000).strokeColor(Color.BLUE).strokeWidth(2.0f));
           Mmap.setMaxZoomPreference(14.5f);
           Mmap.setMinZoomPreference(6.5f);
           Mmap.addMarker(new MarkerOptions().position(new LatLng(13.031522,80.201531)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2)));
       }
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
