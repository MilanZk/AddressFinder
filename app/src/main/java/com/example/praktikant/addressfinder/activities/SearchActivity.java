package com.example.praktikant.addressfinder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    /* AppCompatActivity override */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initComponents();


        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etAddress.getText().toString().trim())){
                    etAddress.setError("This field cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(etCity.getText())){
                    etCity.setError("This field cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(etState.getText())){
                    etState.setError("This field cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(etPostal.getText())){
                    etPostal.setError("This field cannot be empty");
                    return;
                }
                    bookmark = new Bookmark();
                    bookmark.setAddress(etAddress.getText().toString());
                    bookmark.setCity(etCity.getText().toString());
                    bookmark.setState(etState.getText().toString());
                    bookmark.setPostal(etPostal.getText().toString());
                    getResponse(bookmark);}
        });
    }
    private void getResponse(final Bookmark bookmark) {
        Call<ResponseData> call= LocationService.apiInterface().createResponse(bookmark.getAddress(),bookmark.getCity(),bookmark.getState(),bookmark.getPostal(),"pjson");
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
               ResponseData responseData = response.body();

                try {
                    List<Candidate> candidateList = responseData.getCandidates();
                    LatLng latLng = SearchResult.getLatLng(SearchResult.getCandidate(candidateList,bookmark.getAddress()));
                    bookmark.setLatitude(latLng.latitude);
                    bookmark.setLongitude(latLng.longitude);
                    Intent intent = new Intent(SearchActivity.this,MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("address", bookmark.getAddress());
                    bundle.putString("city", bookmark.getCity());
                    bundle.putString("state", bookmark.getState());
                    bundle.putString("postal", bookmark.getPostal());
                    bundle.putParcelable("latlng", latLng);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, "Web service is unavailable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        etPostal = (EditText) findViewById(R.id.etPostal);
        btSearch = (Button) findViewById(R.id.btSearch);
    }

}
