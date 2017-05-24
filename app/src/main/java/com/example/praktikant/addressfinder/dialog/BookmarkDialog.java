package com.example.praktikant.addressfinder.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.praktikant.addressfinder.R;

public class BookmarkDialog extends AlertDialog.Builder {
    public BookmarkDialog(Context context) {
        super(context);
        setTitle(R.string.bookmarkDialogTitle);
        setMessage(R.string.bookmarkDialogMessage);
        setCancelable(false);
        setPositiveButton(R.string.bookmarkDialogPositiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        setNegativeButton(R.string.bookmarkDialogNegativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }
    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
