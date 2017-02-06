package com.example.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.datamodel.SearchResult;
import com.example.popularmovies.popularmovies.R;
import com.squareup.picasso.Picasso;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";

    private Context mContext;
    private SearchResult mSearchResult ;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG,"CLICK OK");
                }
            });
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public CustomAdapter(SearchResult searchResult, Context context) {
        mSearchResult = searchResult;
        mContext = context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        Picasso.with(mContext).load(BASE_IMAGE_URL + mSearchResult.getResults().get(position).getPosterPath()).into(viewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        return mSearchResult.getResults().size();
    }
}
