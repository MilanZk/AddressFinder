package com.example.praktikant.addressfinder.db;

import android.content.Context;

import com.example.praktikant.addressfinder.model.Bookmark;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {

    /*Properties*/

    private ORMDatabaseHelper ormDatabaseHelper;

    public BookmarkManager(Context context) {
        ormDatabaseHelper = OpenHelperManager.getHelper(context, ORMDatabaseHelper.class);
    }

    /*BookmarkManager public methods*/

    public void createBookmark(Bookmark bookmark){
        try {
            ormDatabaseHelper.getBookmarkDao().create(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Bookmark> getAllBookmarks(){
        List<Bookmark> bookmarkList = new ArrayList<>();
        try {
            bookmarkList = ormDatabaseHelper.getBookmarkDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return bookmarkList;
    }
    public void deleteBookmark(Bookmark bookmark){
        try {
            ormDatabaseHelper.getBookmarkDao().delete(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Bookmark getBookmark(Bookmark bookmark) {
        List<Bookmark> bookmarks = new ArrayList<>();
        try {
            bookmarks = ormDatabaseHelper.getBookmarkDao().queryBuilder().where().like(Bookmark.FIELD_NAME_ADDRESS, bookmark.getAddress())
                    .and().like(Bookmark.FIELD_NAME_CITY, bookmark.getCity()).and().like(Bookmark.FIELD_NAME_STATE, bookmark.getState())
                    .and().like(Bookmark.FIELD_NAME_POSTAL, bookmark.getPostal()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (bookmarks.size()!=0){
            return bookmarks.get(0);
        }
        else return null;
    }
}
