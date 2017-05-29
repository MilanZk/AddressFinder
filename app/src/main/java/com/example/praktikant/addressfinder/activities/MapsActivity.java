package com.example.praktikant.addressfinder.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.db.DatabaseRequest;
import com.example.praktikant.addressfinder.db.ORMDatabaseHelper;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.Serializable;
import java.sql.SQLException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /*Properties*/

    private Bookmark bookmark;
    private GoogleMap mMap;
    private DatabaseRequest databaseRequest;
    private Boolean isFloatingButtonShown;
    private FloatingActionButton flacbtBookmark;

    /*FragmentActivity overridden methods*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        showTheMap();
        initComponents();
        getIntentData();
        setUpFloatingActionButton();
    }
    private void initComponents() {
        flacbtBookmark= (FloatingActionButton) findViewById(R.id.flbtBookmark);
        databaseRequest = new DatabaseRequest(MapsActivity.this);
    }

    private void setUpFloatingActionButton() {
        if (!isFloatingButtonShown){
            flacbtBookmark.setVisibility(View.INVISIBLE);
        }
        flacbtBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    flacbtBookmark.setVisibility(View.INVISIBLE);
                    databaseRequest.createBookmark(bookmark);
                    flacbtBookmark.setVisibility(View.INVISIBLE);
        }
        });
    }
    private void showTheMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        bookmark = (Bookmark) bundle.getSerializable(getString(R.string.keyIntentBookmark));
        isFloatingButtonShown = bundle.getBoolean(getString(R.string.isFloatingButtonShown));
    }

    /*Interface method*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng addressLatLng = new LatLng( bookmark.getLatitude(),bookmark.getLongitude());
        mMap.addMarker(new MarkerOptions().position(addressLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(addressLatLng).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }


}