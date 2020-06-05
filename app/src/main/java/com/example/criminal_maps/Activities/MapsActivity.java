package com.example.criminal_maps.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminal_maps.Classes.Crime;
import com.example.criminal_maps.Classes.DBHandler;
import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // TODO Fetch from server
    private static final String[] crimeTypes = {"murder", "robbery", "rape", "physical violence", "uncategorized"};
    private static final float[] crimeColors = {BitmapDescriptorFactory.HUE_RED , BitmapDescriptorFactory.HUE_MAGENTA ,
            BitmapDescriptorFactory.HUE_ORANGE , BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_VIOLET};

    private API api;

    public static final int ADD_CRIME = 1;

    private Crime[] crimes = null;

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    private Marker current_marker = null;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.context = mapFragment.getActivity();

        api = (API) getIntent().getSerializableExtra("API");

        Button btn_add_crime = (Button) findViewById(R.id.fragment_maps_add_crime);
        btn_add_crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_marker == null){
                    Toast.makeText( MapsActivity.this, getResources().getString(R.string.add_a_marker), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!API.isNetworkConnected(MapsActivity.this)){
                    Toast.makeText( MapsActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MapsActivity.this, CrimeActivity.class);
                intent.putExtra("LATITUDE", current_marker.getPosition().latitude);
                intent.putExtra("LONGITUDE", current_marker.getPosition().longitude);
                intent.putExtra("API", api);

                startActivity(intent);
            }
        });

        Button btn_logout = (Button) findViewById(R.id.fragment_maps_button_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.i(TAG, "mapsActivity");
               logout();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Thessaloniki, move the camera and zoom.
        LatLng thessaloniki = new LatLng(40.631111, 22.953334);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thessaloniki, 11f));
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.report_layout, null);

                // Getting the position from the marker
                String latLng = arg0.getSnippet();
                TextView title = (TextView) v.findViewById(R.id.report_textview_title);
                TextView snippet = (TextView) v.findViewById(R.id.report_textview_snippet);

                title.setText(arg0.getTitle());
                snippet.setText(arg0.getSnippet());

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        refresh();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Intent intent = new Intent(MapsActivity.this, CrimeActivity.class);
                intent.putExtra("LATITUDE", point.latitude);
                intent.putExtra("LONGITUDE", point.longitude);
                intent.putExtra("API", api);
                if (current_marker != null) {
                    current_marker.remove();
                }
                 current_marker = mMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory
                         .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


//
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        current_marker = null;
        if (mMap != null) {
            refresh();
        }
    }


    public void onRefreshClick(View view) {
        refresh();
    }


    private void refresh() {
        mMap.clear();
        DBHandler dbHandler = new DBHandler(this.context, null, null, 1);
        boolean isNetworkConnected = API.isNetworkConnected(this.context);
        if (isNetworkConnected){
            try {
                crimes = api.getCrimes();
                if (crimes == null) {
                    crimes = new Crime[]{};
                    Log.e(TAG, api.getError());
                }
            }catch (JSONException e) {
                crimes = new Crime[]{};
                e.printStackTrace();
            }
        }else{
            Toast.makeText( MapsActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Not network Connected");
        }


        // if no network was found the database will not get refreshed
        // will get the already fetched data
        if (crimes == null){
            crimes = dbHandler.getAllCrimes();

        }

        // If nothing was found return
        if (crimes  == null) {
            Toast.makeText( MapsActivity.this, getResources().getString(R.string.no_markers_locally) , Toast.LENGTH_LONG).show();
            Log.i(TAG, "No crimes were found");
            return;
        }

        for (Crime crime: crimes) {
            Log.i(TAG, crime.toString());
            if (isNetworkConnected) dbHandler.addCrime(crime);
            LatLng location = new LatLng(crime.getLatitude(), crime.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location)
                                                .title(crime.getName())
                                                .snippet("Category: "  + crimeTypes[crime.getType()-1] + "\n" +
                                                        "Description: " + crime.getReport() + "\n" +
                                                        "Date: " + crime.getDate() )
                                                .icon(BitmapDescriptorFactory.defaultMarker(crimeColors[crime.getType()-1])));
        }
    }


    private void logout(){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.deleteCredentials();
        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
        startActivity(intent);
        MapsActivity.this.finish();


    }
}
