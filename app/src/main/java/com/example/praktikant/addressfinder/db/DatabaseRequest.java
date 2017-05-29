package com.example.praktikant.addressfinder.db;

import android.content.Context;

import com.example.praktikant.addressfinder.model.Bookmark;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRequest {
    private Context context;
    private ORMDatabaseHelper ormDatabaseHelper;

    public DatabaseRequest(Context context) {
        this.context = context;
        ormDatabaseHelper = OpenHelperManager.getHelper(context, ORMDatabaseHelper.class);
    }
    public void createBookmark(Bookmark bookmark){
        try {
            ormDatabaseHelper.getBookmarkDao().create(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Bookmark> queryForAllBookmark(){
        List<Bookmark> bookmarkList = new ArrayList<>();
        try {
            bookmarkList = ormDatabaseHelper.getBookmarkDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return bookmarkList;
    }
    public void delleteBookmark(Bookmark bookmark){
        try {
            ormDatabaseHelper.getBookmarkDao().delete(bookmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkIfRecordExistsInDatabase(String bookmarkAddress, String bookmarkCity, String bookmarkState, String bookmarkPostal) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        try {
            bookmarkList = ormDatabaseHelper.getBookmarkDao().queryBuilder().where().eq(Bookmark.FIELD_NAME_ADDRESS, bookmarkAddress)
                    .and().eq(Bookmark.FIELD_NAME_CITY, bookmarkCity).and().eq(Bookmark.FIELD_NAME_STATE, bookmarkState)
                    .and().eq(Bookmark.FIELD_NAME_POSTAL, bookmarkPostal).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            bookmarkList.get(0);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
