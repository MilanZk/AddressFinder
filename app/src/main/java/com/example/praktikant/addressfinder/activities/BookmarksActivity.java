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
import com.example.praktikant.addressfinder.db.ORMDatabaseHelper;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity{
    private ORMDatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private BookmarkAdapter mAdapter;
    private List<Bookmark> bookmarkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        try {
           bookmarkList=getDatabaseHelper().getBookmarkDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewBookmark);
        mAdapter = new BookmarkAdapter(bookmarkList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
    public ORMDatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMDatabaseHelper.class);
        }
        return databaseHelper;
    }
}
