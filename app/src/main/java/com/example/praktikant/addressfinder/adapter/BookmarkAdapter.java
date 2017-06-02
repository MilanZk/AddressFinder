package com.example.praktikant.addressfinder.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praktikant.addressfinder.AddressFinderException;
import com.example.praktikant.addressfinder.AppNavigation;
import com.example.praktikant.addressfinder.R;
import com.example.praktikant.addressfinder.activities.MapsActivity;
import com.example.praktikant.addressfinder.db.BookmarkManager;
import com.example.praktikant.addressfinder.model.Bookmark;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    /* Properties */

    private final List<Bookmark> bookmarks;
    private final Context context;

    public BookmarkAdapter(List<Bookmark> bookmarksList, Context context) {
        this.bookmarks = bookmarksList;
        this.context = context;
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item_address, parent, false);
        return new BookmarkViewHolder(itemView);
    }

    /*RecyclerView.Adapter overridden methods*/

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        holder.tvAddress.setText(context.getString(R.string.tvAddress) + " " + bookmark.getAddress());
        holder.tvCity.setText(context.getString(R.string.tvCity) + " " + bookmark.getCity());
        holder.tvState.setText(context.getString(R.string.tvState) + " " + bookmark.getState());
        holder.tvPostal.setText(context.getString(R.string.tvPostal) + " " + bookmark.getPostal());
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {

        /*Properties*/

        private TextView tvAddress, tvCity, tvState, tvPostal;
        private ImageButton ibtDelete, ibtShowOnMap;
        private BookmarkManager bookmarkManager;

        public BookmarkViewHolder(View view) {
            super(view);
            initComponents(view);
            setUpViews();
        }

        /*Setup subviews*/
        private void setUpViews() {
            ibtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
            final AppNavigation appNavigation = new AppNavigation(context);
            ibtShowOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appNavigation.startMapActivity(MapsActivity.class, false, false,
                            bookmarks.get(getLayoutPosition()));
                }
            });
        }

        private void initComponents(View view) {
            tvAddress = (TextView) view.findViewById(R.id.tvAddressListItem);
            tvCity = (TextView) view.findViewById(R.id.tvCityListItem);
            tvState = (TextView) view.findViewById(R.id.tvStateListItem);
            tvPostal = (TextView) view.findViewById(R.id.tvPostalListItem);
            ibtDelete = (ImageButton) view.findViewById(R.id.btDelete);
            ibtShowOnMap = (ImageButton) view.findViewById(R.id.btShowOnMap);
        }

        /*Data*/

        private void deleteBookmarkFromDatabaseAndAdapter() {
            if (bookmarkManager == null) {
                bookmarkManager = new BookmarkManager(context);
            }
            try {
                bookmarkManager.deleteBookmark(bookmarks.get(getLayoutPosition()));
            } catch (AddressFinderException e) {
                Toast.makeText(context, context.getString(R.string.problemDeletingData),
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            bookmarks.remove(getLayoutPosition());
            notifyItemRemoved(getLayoutPosition());
        }

        private void showAlertDialog() {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.deleteBookmark))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteBookmarkFromDatabaseAndAdapter();
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


}
