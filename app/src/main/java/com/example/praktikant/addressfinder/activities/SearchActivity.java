package com.example.praktikant.addressfinder.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.SearchResult;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.net.LocationService;
import com.example.praktikant.addressfinder.net.model.Candidate;
import com.example.praktikant.addressfinder.net.model.ResponseData;

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
    private Boolean showBookmarkButton;
    private Boolean showBookmarkAlreadyExist;
    private BookmarkManager bookmarkManager;


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

    /*Setup subviews*/

    private void initComponents() {
        bookmarkManager = new BookmarkManager(SearchActivity.this);
        progressBarSearch=(ProgressBar) findViewById(R.id.progressBarSearching);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        etPostal = (EditText) findViewById(R.id.etPostal);
        btSearch = (Button) findViewById(R.id.btSearch);
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
                    if (bookmarkManager.getBookmark(bookmark)!=null){
                        Bookmark bookmarkFromDatabase = bookmarkManager.getBookmark(bookmark);
                        showBookmarkButton=false;
                        showBookmarkAlreadyExist = true;
                        appNavigation(bookmarkFromDatabase);
                    }else {
                        showBookmarkButton = true;
                        showBookmarkAlreadyExist = false;
                        getResponse(bookmark);
                    }}
            }
        });
    }
    private boolean validateInputFields(){
        boolean fieldIsNotEmpty= true;
        if(TextUtils.isEmpty(etAddress.getText().toString().trim())){
            etAddress.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty=false;
        }
        if(TextUtils.isEmpty(etCity.getText().toString().trim())){
            etCity.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty=false;
        }
        if(TextUtils.isEmpty(etState.getText().toString().trim())){
            etState.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty=false;
        }
        if(TextUtils.isEmpty(etPostal.getText().toString().trim())){
            etPostal.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty=false;
        }
        return fieldIsNotEmpty;
    }
    private void showAlertDialogNoResults(){
        AlertDialog dialog = new AlertDialog.Builder(SearchActivity.this).setMessage(getString(R.string.noResults))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        etAddress.getText().clear();
                        etCity.getText().clear();
                        etState.getText().clear();
                        etPostal.getText().clear();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBarSearch.setVisibility(View.INVISIBLE);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBarSearch.setVisibility(View.INVISIBLE);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBarSearch.setVisibility(View.INVISIBLE);
                    }
                }).create();
        dialog.show();
    }
    /*Data*/

    private void getResponse(final Bookmark bookmark) {
        Call<ResponseData> call= LocationService.apiInterface().createResponse(bookmark.getAddress(),bookmark.getCity(),bookmark.getState(),bookmark.getPostal(), Bookmark.DATA_TYPE_JSON);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ResponseData responseData = response.body();
                List<Candidate> candidateList = responseData.getCandidates();
                    if (candidateList.size()!=0) {
                        bookmark.setLatitude(SearchResult.getBestCandidate(candidateList, bookmark.getAddress()).getLocation().getY());
                        bookmark.setLongitude(SearchResult.getBestCandidate(candidateList, bookmark.getAddress()).getLocation().getX());
                        appNavigation(bookmark);
                    }
                    else {
                        showAlertDialogNoResults();
                    }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(SearchActivity.this, R.string.checkYourConnection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Navigation and passing data*/

    private void appNavigation(Bookmark bookmark) {
        Intent intent = new Intent(SearchActivity.this,MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.isFloatingButtonShown), showBookmarkButton);
        bundle.putBoolean(getString(R.string.isSnackBarShown), showBookmarkAlreadyExist);
        bundle.putSerializable(getString(R.string.keyIntentBookmark),bookmark);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
