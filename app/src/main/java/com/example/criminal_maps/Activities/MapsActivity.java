package com.example.criminal_maps.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.criminal_maps.Classes.Crime;
import com.example.criminal_maps.Classes.DBHandler;
import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private API api;

    public static final int ADD_CRIME = 1;

    private Crime[] crimes;

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        api = (API) getIntent().getSerializableExtra("API");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Thessaloniki, move the camera and zoom.
        LatLng thessaloniki = new LatLng(40.631111, 22.953334);
//        mMap.addMarker(new MarkerOptions().position(thessaloniki).title("Thessaloniki"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thessaloniki, 12f));

        refresh();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Intent intent = new Intent(MapsActivity.this, CrimeActivity.class);
                intent.putExtra("LATITUDE", point.latitude);
                intent.putExtra("LONGITUDE", point.longitude);
                intent.putExtra("API", api);
//                mMap.addMarker(new MarkerOptions().position(point));

                // We don't actually expect a result. We do however want to refresh the map when a new crime is added. This lets us do it at onActivityResult()
                startActivityForResult(intent, ADD_CRIME);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CRIME) {
            refresh();
        }
    }

    public void onRefreshClick(View view) {
        refresh();
    }

    private void refresh() {
        mMap.clear();
        try {
            crimes = api.getCrimes();
            if (crimes == null) {
                crimes = new Crime[]{};
                Log.e(TAG, api.getError());
            }
        }
        catch (JSONException e) {
            crimes = new Crime[]{};
            e.printStackTrace();
        }
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        // ArrayList<Crime> crimes = dbHandler.getAllCrimes();
        for (Crime crime: crimes) {
            dbHandler.addCrime(crime);
            LatLng location = new LatLng(crime.getLatitude(), crime.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(crime.getName()));
        }
    }

}
