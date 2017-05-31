package com.example.praktikant.addressfinder.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.activities.MapsActivity;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.model.Bookmark;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{

    /* Properties */

    private List<Bookmark> bookmarks;
    private Context context;


    public BookmarkAdapter(List<Bookmark> bookmarksList, Context context) {
        this.bookmarks = bookmarksList;
        this.context = context;
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAddress, tvCity, tvState, tvPostal;
        private ImageButton ibtDelete, ibtShowOnMap;

        public BookmarkViewHolder(View view){
            super(view);
            tvAddress = (TextView) view.findViewById(R.id.tvAddressListItem);
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvAddress.setSelected(true);
                }
            });
            tvCity = (TextView) view.findViewById(R.id.tvCityListItem);
            tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvCity.setSelected(true);
                }
            });
            tvState = (TextView) view.findViewById(R.id.tvStateListItem);
            tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvState.setSelected(true);
                }
            });
            tvPostal = (TextView) view.findViewById(R.id.tvPostalListItem);
            tvPostal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvPostal.setSelected(true);
                }
            });
            ibtDelete = (ImageButton) view.findViewById(R.id.btDelete);
            ibtShowOnMap = (ImageButton) view.findViewById(R.id.btShowOnMap);
            ibtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
            ibtShowOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getString(R.string.keyIntentBookmark),bookmarks.get(getLayoutPosition()));
                    bundle.putBoolean(context.getString(R.string.isFloatingButtonShown), false);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        private void showAlertDialog() {
            AlertDialog dialog = new AlertDialog.Builder(context).setMessage(context.getString(R.string.deleteBookmark))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BookmarkManager bookmarkManager = new BookmarkManager(context);
                            bookmarkManager.deleteBookmark(bookmarks.get(getLayoutPosition()));
                            bookmarks.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            dialog.dismiss();
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

    /*RecyclerView.Adapter overridden methods*/

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_address, parent, false);
        return new BookmarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        holder.tvAddress.setText(bookmark.getAddress());
        holder.tvCity.setText(bookmark.getCity());
        holder.tvState.setText(bookmark.getState());
        holder.tvPostal.setText(bookmark.getPostal());
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


}
