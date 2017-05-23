package com.example.praktikant.addressfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.model.Position;
import com.example.praktikant.addressfinder.net.MyService;
import com.example.praktikant.addressfinder.net.model.Candidate;
import com.example.praktikant.addressfinder.net.model.Location;
import com.example.praktikant.addressfinder.net.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    /* Properties */

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
                Position position = new Position();
                position.setAddress(etAddress.getText().toString());
                position.setCity(etCity.getText().toString());
                position.setState(etState.getText().toString());
                position.setPostal(etPostal.getText().toString());

                getResponse(position);

            }


        });
    }



    private void getResponse(Position position) {
        Call<ResponseData> call= MyService.apiInterface().createResponse(position.getAddress(),position.getCity(),position.getState(),position.getPostal(),"pjson");
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
               ResponseData responseData = response.body();
                List<Candidate> candidateList = responseData.getCandidates();
                for (Candidate c: candidateList
                     ) {
                    Toast.makeText(SearchActivity.this, c.getLocation().toString(), Toast.LENGTH_SHORT).show();
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
