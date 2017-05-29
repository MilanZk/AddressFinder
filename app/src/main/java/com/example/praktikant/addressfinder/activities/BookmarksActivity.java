package com.example.praktikant.addressfinder.activities;

import android.content.DialogInterface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.adapter.BookmarkAdapter;
import com.example.praktikant.addressfinder.db.DatabaseRequest;
import com.example.praktikant.addressfinder.db.ORMDatabaseHelper;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity{

    /*Properties*/

    private List<Bookmark> bookmarkList;
    private RecyclerView recyclerView;

    /*AppCompatActivity overridden methods*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        initComponents();
        getAllBookmarks();
        setUpViews();
    }
    private void initComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewBookmark);
    }
    private void getAllBookmarks() {
        DatabaseRequest databaseRequest = new DatabaseRequest(BookmarksActivity.this);
        bookmarkList = databaseRequest.queryForAllBookmark();
    }
    private void setUpViews() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        BookmarkAdapter mAdapter = new BookmarkAdapter(bookmarkList, this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
