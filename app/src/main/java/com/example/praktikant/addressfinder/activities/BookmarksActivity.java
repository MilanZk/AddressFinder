package com.example.praktikant.addressfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.adapter.BookmarkAdapter;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.model.Bookmark;

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

    /*Setup subviews*/

    private void initComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewBookmark);
    }
    private void setUpViews() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        BookmarkAdapter mAdapter = new BookmarkAdapter(bookmarkList, this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /*Data*/

    private void getAllBookmarks() {
        BookmarkManager bookmarkManager = new BookmarkManager(BookmarksActivity.this);
        bookmarkList = bookmarkManager.getAllBookmarks();
    }
}
