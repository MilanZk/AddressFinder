package com.example.praktikant.addressfinder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.praktikant.addressfinder.AppNavigation;
import com.example.praktikant.addressfinder.R;

public class MainActivity extends AppCompatActivity {

    /*Properties*/

    private Button btFindAddress, btBookmarks;
    private AppNavigation appNavigation;

    /*AppCompatActivity overridden methods  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setUpViews();
    }

    private void initComponents() {
        btBookmarks = (Button) findViewById(R.id.btBookmarks);
        btFindAddress = (Button) findViewById(R.id.btFindAddress);
    }

    /*Setup subviews*/

    private void setUpViews() {
        appNavigation = new AppNavigation(this);
        btFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appNavigation.startActivity(SearchActivity.class);
            }
        });

        btBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appNavigation.startActivity(BookmarksActivity.class);
            }
        });
    }
}
