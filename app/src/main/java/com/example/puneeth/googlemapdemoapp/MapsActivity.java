package com.example.puneeth.googlemapdemoapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Bundle b;
    private GoogleMap mMap;
    UiSettings uiSettings;
    double lat,lon;
    Geocoder geocoder=new Geocoder(this, Locale.getDefault());
    String location="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        b=getIntent().getExtras();
        lat=Double.parseDouble(b.getString("lat"));
        lon=Double.parseDouble(b.getString("lon"));
        Log.d("2","in maps lat: "+lat+" lon: "+lon);
        try {
            List<Address> addressList=geocoder.getFromLocation(lat,lon,1);
            location+=(addressList.get(0).getAddressLine(0)+", "+addressList.get(0).getAddressLine(1)+", "+addressList.get(0).getAddressLine(2));
            Log.d("2","address: "+location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings=mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
