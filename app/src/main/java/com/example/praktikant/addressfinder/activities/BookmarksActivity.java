package com.example.praktikant.addressfinder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.adapter.BookmarkAdapter;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.exceptions.AddressFinderException;
import com.example.praktikant.addressfinder.model.Bookmark;

import java.util.Collections;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    /*Properties*/

    private List<Bookmark> bookmarks;
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

    /*Setup subviews*/

    private void initComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewBookmark);
    }

    private void setUpViews() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        BookmarkAdapter mAdapter = new BookmarkAdapter(bookmarks, this);
        Collections.reverse(bookmarks);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /*Data*/

    private void getAllBookmarks() {
        BookmarkManager bookmarkManager = new BookmarkManager(BookmarksActivity.this);
        try {
            bookmarks = bookmarkManager.getAllBookmarks();
        } catch (AddressFinderException e) {
            Toast.makeText(this, getString(R.string.problemGettingData),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
