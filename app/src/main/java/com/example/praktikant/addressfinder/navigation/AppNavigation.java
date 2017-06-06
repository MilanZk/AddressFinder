package com.example.praktikant.addressfinder.navigation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.praktikant.addressfinder.activities.BookmarksActivity;
import com.example.praktikant.addressfinder.activities.MapsActivity;
import com.example.praktikant.addressfinder.activities.SearchActivity;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.example.praktikant.addressfinder.utils.Constants;

public class AppNavigation {


    /*Application Navigation public methods*/


    public static void startBookmarksActivity(Context context) {
        startActivity(BookmarksActivity.class, context);
    }

    public static void startSearchActivity(Context context) {
        startActivity(SearchActivity.class, context);

    }

    private static void startActivity(Class activity, Context context) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static void startMapActivity(Context context, boolean showBookmarkButton,
                                        boolean showBookmarkAlreadyExist, Bookmark bookmark) {
        Intent intent = new Intent(context, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.SHOW_BOOKMARK_BUTTON_KEY, showBookmarkButton);
        bundle.putBoolean(Constants.SHOW_ALREADY_EXISTS_BOOKMARK_KEY, showBookmarkAlreadyExist);
        bundle.putSerializable(Constants.BOOKMARK_KEY, bookmark);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
