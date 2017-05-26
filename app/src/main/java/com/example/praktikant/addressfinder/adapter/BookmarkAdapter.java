package com.example.praktikant.addressfinder.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.activities.BookmarksActivity;
import com.example.praktikant.addressfinder.activities.MapsActivity;
import com.example.praktikant.addressfinder.model.Bookmark;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{

    /* Properties */
    private List<Bookmark> bookmarksList;
    private Context context;


    public BookmarkAdapter(List<Bookmark> bookmarksList, Context context) {
        this.bookmarksList = bookmarksList;
        this.context = context;
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAddress, tvCity, tvState, tvPostal;
        private ImageButton ibtDelete, ibtShowOnMap;

        public BookmarkViewHolder(View view){
            super(view);
            tvAddress = (TextView) view.findViewById(R.id.tvAddressListItem);
            tvCity = (TextView) view.findViewById(R.id.tvCityListItem);
            tvState = (TextView) view.findViewById(R.id.tvStateListItem);
            tvPostal = (TextView) view.findViewById(R.id.tvPostalListItem);
            ibtDelete = (ImageButton) view.findViewById(R.id.btDelete);
            ibtShowOnMap = (ImageButton) view.findViewById(R.id.btShowOnMap);
            ibtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ibtShowOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getString(R.string.keyIntentBookmark),bookmarksList.get(getLayoutPosition()));
                    bundle.putBoolean(context.getString(R.string.isFloatingButtonShown), true);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
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
        Bookmark bookmark = bookmarksList.get(position);
        holder.tvAddress.setText(bookmark.getAddress());
        holder.tvCity.setText(bookmark.getCity());
        holder.tvState.setText(bookmark.getState());
        holder.tvPostal.setText(bookmark.getPostal());
    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }


}
