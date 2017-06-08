package com.example.praktikant.addressfinder.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.exceptions.AddressFinderException;
import com.example.praktikant.addressfinder.helpers.CandidateHelper;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.navigation.AppNavigation;
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
        setUpProgressBar(false);
    }

    /*Setup subviews*/

    private void initComponents() {
        progressBarSearch = (ProgressBar) findViewById(R.id.progressBarSearching);
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
                    getBookmarkValuesFromInputFields();
                    setUpProgressBar(true);
                    getBookmarkData();
                }
            }
        });
    }

    private void setUpProgressBar(boolean visible) {
        if (visible) {
            progressBarSearch.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBarSearch.setVisibility(View.INVISIBLE);
        }

    }

    private void getBookmarkValuesFromInputFields() {
        bookmark = new Bookmark();
        bookmark.setAddress(etAddress.getText().toString());
        bookmark.setCity(etCity.getText().toString());
        bookmark.setState(etState.getText().toString());
        bookmark.setPostal(etPostal.getText().toString());
    }

    private boolean validateInputFields() {
        boolean fieldIsNotEmpty = true;
        if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            etAddress.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            etCity.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(etState.getText().toString().trim())) {
            etState.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty = false;
        }
        if (TextUtils.isEmpty(etPostal.getText().toString().trim())) {
            etPostal.setError(getString(R.string.emptyEditTextField));
            fieldIsNotEmpty = false;
        }
        return fieldIsNotEmpty;
    }

    private void showAlertDialogNoResults() {
        AlertDialog dialog = new AlertDialog.Builder(SearchActivity.this)
                .setMessage(getString(R.string.noResults))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setUpProgressBar(false);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setUpProgressBar(false);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        setUpProgressBar(false);
                    }
                }).create();
        dialog.show();
    }

    /*Data*/

    private void getBookmarkFromWebService(final Bookmark bookmark) {
        Call<ResponseData> call = LocationService.apiInterface().createResponse(
                bookmark.getAddress(), bookmark.getCity(), bookmark.getState(), bookmark.getPostal(),
                Bookmark.DATA_TYPE_JSON);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ResponseData responseData = response.body();
                List<Candidate> candidateList = responseData.getCandidates();
                if (candidateList != null && candidateList.size() > 0) {
                    bookmark.setLatitude(CandidateHelper.getBestCandidate(candidateList,
                            bookmark).getLocation().getY());
                    bookmark.setLongitude(CandidateHelper.getBestCandidate(candidateList,
                            bookmark).getLocation().getX());
                    AppNavigation.startMapActivity(SearchActivity.this, showBookmarkButton,
                            showBookmarkAlreadyExist, bookmark);
                } else {
                    showAlertDialogNoResults();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(SearchActivity.this, R.string.checkYourConnection,
                        Toast.LENGTH_SHORT).show();
                setUpProgressBar(false);
            }
        });
    }

    private void getBookmarkData() {
        if (bookmarkManager == null) {
            bookmarkManager = new BookmarkManager(SearchActivity.this);
        }
        Bookmark bookmarkFromDatabase = null;
        try {
            bookmarkFromDatabase = bookmarkManager.getBookmark(bookmark);
        } catch (AddressFinderException e) {
            Toast.makeText(SearchActivity.this,
                    getString(R.string.problemGettingData),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (bookmarkFromDatabase != null) {
            showBookmarkButton = false;
            showBookmarkAlreadyExist = true;
            AppNavigation.startMapActivity(this, false, true, bookmarkFromDatabase);
        } else {
            showBookmarkButton = true;
            showBookmarkAlreadyExist = false;
            getBookmarkFromWebService(bookmark);
        }
    }
}
