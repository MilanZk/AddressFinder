package com.example.praktikant.addressfinder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.SearchResult;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.net.LocationService;
import com.example.praktikant.addressfinder.net.model.Candidate;
import com.example.praktikant.addressfinder.net.model.ResponseData;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    /* Properties */

    private Bookmark bookmark;
    private Button btSearch;
    private EditText etAddress, etCity, etState, etPostal;
    private ProgressBar progressBarSearch;


    /* AppCompatActivity overridden methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initComponents();
        setUpViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBarSearch.setVisibility(View.INVISIBLE);
    }

    private void setUpViews() {
        progressBarSearch.setVisibility(View.INVISIBLE);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputFields()) {
                    bookmark = new Bookmark();
                    bookmark.setAddress(etAddress.getText().toString());
                    bookmark.setCity(etCity.getText().toString());
                    bookmark.setState(etState.getText().toString());
                    bookmark.setPostal(etPostal.getText().toString());
                    progressBarSearch.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    getResponse(bookmark);
                }
            }
        });
    }
    private boolean validateInputFields(){
        int fieldIsEmpty= 0;
        if(TextUtils.isEmpty(etAddress.getText().toString().trim())){
            etAddress.setError(getString(R.string.emptyEditTextField));
            fieldIsEmpty++;
        }
        if(TextUtils.isEmpty(etCity.getText().toString().trim())){
            etCity.setError(getString(R.string.emptyEditTextField));
            fieldIsEmpty++;
        }
        if(TextUtils.isEmpty(etState.getText().toString().trim())){
            etState.setError(getString(R.string.emptyEditTextField));
            fieldIsEmpty++;
        }
        if(TextUtils.isEmpty(etPostal.getText().toString().trim())){
            etPostal.setError(getString(R.string.emptyEditTextField));
            fieldIsEmpty++;
        }
        return fieldIsEmpty == 0;
    }

    private void getResponse(final Bookmark bookmark) {
        Call<ResponseData> call= LocationService.apiInterface().createResponse(bookmark.getAddress(),bookmark.getCity(),bookmark.getState(),bookmark.getPostal(),"pjson");
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
               ResponseData responseData = response.body();
                    List<Candidate> candidateList = responseData.getCandidates();
                    bookmark.setLatitude(SearchResult.getBestCandidate(candidateList,bookmark.getAddress()).getLocation().getY());
                    bookmark.setLongitude(SearchResult.getBestCandidate(candidateList,bookmark.getAddress()).getLocation().getX());
                    appNavigation();

            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void appNavigation() {
        Intent intent = new Intent(SearchActivity.this,MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.keyIntentBookmark),bookmark);
        bundle.putBoolean(getString(R.string.isFloatingButtonShown), false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initComponents() {
        progressBarSearch=(ProgressBar) findViewById(R.id.progressBarSearching);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        etPostal = (EditText) findViewById(R.id.etPostal);
        btSearch = (Button) findViewById(R.id.btSearch);
    }

}
