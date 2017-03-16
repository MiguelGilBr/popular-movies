package com.example.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.network.Client;
import com.example.popularmovies.popularmovies.R;
import com.squareup.picasso.Picasso;


public class MovieCoverAdapter extends RecyclerView.Adapter<MovieCoverAdapter.ViewHolder> {

    private static final String TAG = MovieCoverAdapter.class.getSimpleName();

    private Context mContext;
    private SearchResultMovie mSearchResultMovie;
    private IMovieCover mIMovieCover;

    //CONSTRUCTOR
    public MovieCoverAdapter(SearchResultMovie searchResultMovie, Context context, IMovieCover iMovieCover) {
        mSearchResultMovie = searchResultMovie;
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

        Picasso.with(mContext).load(Client.BASE_IMAGE_URL + mSearchResultMovie.getResults().get(position).getPosterPath()).into(viewHolder.getImageView());
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIMovieCover.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearchResultMovie.getResults().size();
    }

    public void setmSearchResultMovie(SearchResultMovie mSearchResultMovie) {
        this.mSearchResultMovie = mSearchResultMovie;
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