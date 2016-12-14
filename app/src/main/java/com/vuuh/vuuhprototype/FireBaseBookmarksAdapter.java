package com.vuuh.vuuhprototype;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by alinbaltatescu on 12/14/16.
 */

public class FireBaseBookmarksAdapter extends FirebaseRecyclerAdapter<Bookmark, FireBaseBookmarksAdapter.ItemViewHolder> {


    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public FireBaseBookmarksAdapter(Class<Bookmark> modelClass, int modelLayout, Class<ItemViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
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
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView url;

        public ItemViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.bookmarkTitle);
            url = (TextView) itemView.findViewById(R.id.bookmarkURL);
        }
    }
}
