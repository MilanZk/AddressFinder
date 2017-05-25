package com.example.praktikant.addressfinder.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
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

import java.sql.SQLException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /*Properties*/

    private AlertDialog dialog;
    private GoogleMap mMap;
    private LatLng addressLatLng;
    private String address, city, state, postal;
    private ORMDatabaseHelper databaseHelper;
    private Boolean clicked = false;

    /*FragmentActivity Override*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getIntentData();
        FloatingActionButton flbtBookmark = (FloatingActionButton) findViewById(R.id.flbtBookmark);
        flbtBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setAddress(address);
                    bookmark.setCity(city);
                    bookmark.setState(state);
                    bookmark.setPostal(postal);
                    try {
                        getDatabaseHelper().getBookmarkDao().create(bookmark);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    clicked = true;
                    Toast.makeText(MapsActivity.this, "Bookmark added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Bookmark already added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        addressLatLng = bundle.getParcelable("latlng");
        address = bundle.getString("address");
        city = bundle.getString("city");
        state = bundle.getString("state");
        postal = bundle.getString("postal");
    }
    /*Interface method*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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

    public ORMDatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMDatabaseHelper.class);
        }
        return databaseHelper;
    }
}