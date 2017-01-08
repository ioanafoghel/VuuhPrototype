package com.vuuh.vuuhprototype;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by ioanafoghel on 12/14/16.
 */

public class FireBaseBookmarksAdapter extends FirebaseRecyclerAdapter<Bookmark, FireBaseBookmarksAdapter.ItemViewHolder> {

    private Context context;
    private Activity mainActivity;

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public FireBaseBookmarksAdapter(Class<Bookmark> modelClass, int modelLayout, Class<ItemViewHolder> viewHolderClass, Query ref, Context context, Activity mainActivity) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.mainActivity = mainActivity;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The first two arguments correspond to the mLayout and mModelClass given to the constructor of
     * this class. The third argument is the item's position in the list.
     * <p>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param viewHolder The view to populate
     * @param model      The object containing the data used to populate the view
     * @param position   The position in the list of the view being populated
     */
    @Override
    protected void populateViewHolder(ItemViewHolder viewHolder, Bookmark model, int position) {

        viewHolder.title.setText(model.getName());
        viewHolder.url.setText(model.getUrl());
        viewHolder.bookmark = model;
        viewHolder.bindOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomTab();
            }
        });
    }

    private void openCustomTab() {

        String url = "http://www.vuuh.dk/cheap-monday-tight-jeans-58592/";

        int color = this.context.getResources().getColor(R.color.colorPrimary);
        int secondaryColor = this.context.getResources().getColor(R.color.vuuh_accent);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(color);
        intentBuilder.setSecondaryToolbarColor(secondaryColor);

        //Generally you do not want to decode bitmaps in the UI thread. Decoding it in the
        //UI thread to keep the example short.
        String actionLabel = this.context.getResources().getString(R.string.label_action);
        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                android.R.drawable.ic_menu_save);
        PendingIntent pendingIntent = createPendingIntent(ActionBroadcastReceiver.ACTION_ACTION_BUTTON);
        intentBuilder.setActionButton(icon, actionLabel, pendingIntent, true);
        intentBuilder.setStartAnimations(this.context, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(this.context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabActivityHelper.openCustomTab(mainActivity
                , intentBuilder.build(), Uri.parse(url), new WebviewFallback());
    }

    private PendingIntent createPendingIntent(int actionSourceId) {
        Intent actionIntent = new Intent(
                this.context, ActionBroadcastReceiver.class);
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSourceId);
        return PendingIntent.getBroadcast(
                this.context, actionSourceId, actionIntent, 0);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        public TextView title;
        public TextView url;
        public Bookmark bookmark;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;

            title = (TextView) itemView.findViewById(R.id.bookmarkTitle);
            url = (TextView) itemView.findViewById(R.id.bookmarkURL);
        }

        public void bindOnClickListener(View.OnClickListener onClickListener) {

            if (rootView != null) {
                rootView.setOnClickListener(onClickListener);
            }
        }
    }
}
