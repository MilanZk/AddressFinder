package com.example.praktikant.addressfinder.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.example.praktikant.addressfinder.Constants;
import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /*Properties*/

    private Bookmark bookmark;
    private BookmarkManager bookmarkManager;
    private Boolean showBookmarkButton;
    private Boolean showBookmarkAlreadyExist=false;
    private FloatingActionButton floatingActionButtonBookmark;

    /*FragmentActivity overridden methods*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (savedInstanceState!=null){
            showBookmarkButton = savedInstanceState.getBoolean(Constants.SHOW_BOOKMARK_BUTTON_KEY);
        }
        showTheMap();
        initComponents();
        getIntentData(showBookmarkButton);
        setUpFloatingActionButtonBookmark();
        setUpSnackBarBookmarkAlreadyExist();
    }

    /*Setup subviews*/

    private void initComponents() {
        floatingActionButtonBookmark= (FloatingActionButton) findViewById(R.id.flbtBookmark);
        bookmarkManager = new BookmarkManager(MapsActivity.this);
    }
    private void setUpFloatingActionButtonBookmark() {
        if (!showBookmarkButton){
            floatingActionButtonBookmark.setVisibility(View.INVISIBLE);
        }
        floatingActionButtonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    floatingActionButtonBookmark.setVisibility(View.INVISIBLE);
                    bookmarkManager.createBookmark(bookmark);
                    showBookmarkButton=false;
            }});
    }
    private void setUpSnackBarBookmarkAlreadyExist() {
        if (showBookmarkAlreadyExist){
            View parentView =  findViewById(R.id.mapsLayout);
            Snackbar.make(parentView, getString(R.string.alreadySavedBookmark), Snackbar.LENGTH_LONG).show();
        }
    }
    private void showTheMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*Data*/

    private void getIntentData(Boolean bookmarkButttonWasShown) {
        Bundle bundle = getIntent().getExtras();
        bookmark = (Bookmark) bundle.getSerializable(Constants.BOOKMARK_KEY);
        if (bookmarkButttonWasShown == null) {
            showBookmarkButton = bundle.getBoolean(Constants.SHOW_BOOKMARK_BUTTON_KEY);
        }else showBookmarkButton=false;
        showBookmarkAlreadyExist = bundle.getBoolean(Constants.SHOW_ALREADY_EXISTS_BOOKMARK_KEY);
    }

    /*OnMapReadyCallback interface method*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng addressLatLng = new LatLng( bookmark.getLatitude(),bookmark.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(addressLatLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(addressLatLng).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(Constants.SHOW_BOOKMARK_BUTTON_KEY,false);
    }
}