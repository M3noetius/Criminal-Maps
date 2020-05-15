package com.example.criminal_maps.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.criminal_maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button crimebtn = (Button)findViewById(R.id.crimebtn);
        crimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crimeIntent = new Intent(MapsActivity.this, CrimeActivity.class);
                startActivity(crimeIntent);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Thessaloniki, move the camera and zoom.
        LatLng thessaloniki = new LatLng(40.631111, 22.953334);
        mMap.addMarker(new MarkerOptions().position(thessaloniki).title("Thessaloniki"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thessaloniki, 12f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Intent intent = new Intent(MapsActivity.this, CrimeActivity.class);
                intent.putExtra("LATITUDE", point.latitude);
                intent.putExtra("LONGITUDE", point.longitude);
//                mMap.addMarker(new MarkerOptions().position(point));
                startActivity(intent);
            }
        });
    }

}
