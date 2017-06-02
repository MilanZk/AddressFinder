package com.example.praktikant.addressfinder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.praktikant.addressfinder.model.Bookmark;

public class AppNavigation {

    /*Properties*/

    private final Context context;

    /*Application Navigation public methods*/

    public AppNavigation(Context context) {
        this.context = context;
    }

    public void startActivity(Class activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public void startMapActivity(Class activity, boolean showBookmarkButton,
                                 boolean showBookmarkAlreadyExist, Bookmark bookmark) {
        Intent intent = new Intent(context, activity);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.SHOW_BOOKMARK_BUTTON_KEY, showBookmarkButton);
        bundle.putBoolean(Constants.SHOW_ALREADY_EXISTS_BOOKMARK_KEY, showBookmarkAlreadyExist);
        bundle.putSerializable(Constants.BOOKMARK_KEY, bookmark);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
