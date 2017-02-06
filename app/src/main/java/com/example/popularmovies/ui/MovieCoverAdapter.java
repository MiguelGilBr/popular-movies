package com.example.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.datamodel.SearchResult;
import com.example.popularmovies.popularmovies.R;
import com.squareup.picasso.Picasso;


public class MovieCoverAdapter extends RecyclerView.Adapter<MovieCoverAdapter.ViewHolder> {

    private static final String TAG = MovieCoverAdapter.class.getSimpleName();
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";

    private Context mContext;
    private SearchResult mSearchResult ;
    private IMovieCover mIMovieCover;


    public MovieCoverAdapter(SearchResult searchResult, Context context, IMovieCover iMovieCover) {
        mSearchResult = searchResult;
        mContext = context;
        mIMovieCover = iMovieCover;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Picasso.with(mContext).load(BASE_IMAGE_URL + mSearchResult.getResults().get(position).getPosterPath()).into(viewHolder.getImageView());
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIMovieCover.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearchResult.getResults().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        public ImageView getImageView() {
            return imageView;
        }


    }

    public interface IMovieCover {
        void onClick(int position);
    }
}