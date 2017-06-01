package com.example.praktikant.addressfinder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.praktikant.addressfinder.AppNavigation;
import com.example.praktikant.addressfinder.R;

public class MainActivity extends AppCompatActivity {

    /*AppCompatActivity overridden methods  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
    }

    /*Setup subviews*/

    private void setUpViews() {
        Button btFindAddress = (Button) findViewById(R.id.btFindAddress);
        final AppNavigation appNavigation = new AppNavigation(this);
        btFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appNavigation.startActivity(SearchActivity.class);
            }
        });
        Button btBookmarks = (Button) findViewById(R.id.btBookmarks);
        btBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              appNavigation.startActivity(BookmarksActivity.class);
            }
        });
    }
}
