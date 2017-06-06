package com.example.praktikant.addressfinder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.navigation.AppNavigation;

public class MainActivity extends AppCompatActivity {

    /*Properties*/

    private Button btFindAddress, btBookmarks;

    /*AppCompatActivity overridden methods  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setUpViews();
    }

    /*Setup subviews*/

    private void initComponents() {
        btBookmarks = (Button) findViewById(R.id.btBookmarks);
        btFindAddress = (Button) findViewById(R.id.btFindAddress);
    }

    private void setUpViews() {
        btFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppNavigation.startSearchActivity(MainActivity.this);
            }
        });
        btBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppNavigation.startBookmarksActivity(MainActivity.this);
            }
        });
    }
}
