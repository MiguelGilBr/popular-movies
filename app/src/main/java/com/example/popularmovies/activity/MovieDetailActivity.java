package com.example.popularmovies.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.Video;
import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.datamodel.searchResult.SearchResultReview;
import com.example.popularmovies.datamodel.searchResult.SearchResultVideo;
import com.example.popularmovies.network.Client;
import com.example.popularmovies.popularmovies.R;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    private int mMoviePosition;
    private SearchResultMovie mSearchResultMovie;

    //UI
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView tvTrailerTitle;
    private LinearLayout llVideos;


    //NETWORK CALLBACKS
    private Callback<SearchResultReview> reviewCallback = new Callback<SearchResultReview>() {        @Override
        public void onResponse(Call<SearchResultReview> call, Response<SearchResultReview> response) {
            if (response.isSuccessful()) {
                Log.i(TAG, "Respuesta asincrona correcta Review");
            } else {
                Log.i(TAG,response.message());
                //TODO: poner mensajico de clave invalida
                int statusCode = response.code();
                ResponseBody errorBody = response.errorBody();
            }
        }

        @Override
        public void onFailure(Call<SearchResultReview> call, Throwable t) {
            Log.i(TAG, "Respuesta asincrona fallo journey: " + t.getMessage());
        }
    };
    private Callback<SearchResultVideo> videoCallback = new Callback<SearchResultVideo>() {
        @Override
        public void onResponse(Call<SearchResultVideo> call, Response<SearchResultVideo> response) {
            if (response.isSuccessful()) {
                Log.i(TAG, "Respuesta asincrona correcta Review");
                loadVideos(response.body());
            } else {
                Log.i(TAG,response.message());
            }
        }

        @Override
        public void onFailure(Call<SearchResultVideo> call, Throwable t) {
            Log.i(TAG, "Respuesta asincrona fallo journey: " + t.getMessage());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        mMoviePosition = intent.getIntExtra(MainActivity.POSITION_KEY,0);
        mSearchResultMovie = DataModel.getInstance().getSearchResultMovie();

        initUI();

        collapsingToolbar.setTitle(mSearchResultMovie.getResults().get(mMoviePosition).getTitle());

        loadData();

        Client.getMovieReviews(reviewCallback, mSearchResultMovie.getResults().get(mMoviePosition).getId().toString());
        Client.getMovieVideos(videoCallback,  mSearchResultMovie.getResults().get(mMoviePosition).getId().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tvTrailerTitle = (TextView)  findViewById(R.id.title_trailer);
        llVideos = (LinearLayout) findViewById(R.id.ll_videos);
    }

    //Movie Video
    private void loadVideos(SearchResultVideo videos) {
        if (videos.getResults() != null) {
            llVideos.setVisibility(View.VISIBLE);
            tvTrailerTitle.setVisibility(View.VISIBLE);
            for (Video video : videos.getResults()) {
                if (video.getSite() != null && video.getSite().equalsIgnoreCase("YouTube") && video.getType() != null && video.getType().equalsIgnoreCase("Trailer")) {
                    attachVideo(video);
                }
            }
        }
    }

    private void attachVideo(final Video video) {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
                getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        img.setLayoutParams(llp);

        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.with(this).load(Client.YOUTUBE_PIC_URL + video.getKey() + "/0.jpg")
                .placeholder(R.drawable.ic_video_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer(video.getKey());
            }
        });
        llVideos.addView(img);
    }

    private void playTrailer(String videoID) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID));
        startActivity(i);
    }

    private void loadData() {
        //Backdrop
        final ImageView backdropImageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(Client.BASE_IMAGE_URL + mSearchResultMovie.getResults()
                .get(mMoviePosition).getPosterPath())
                .into(backdropImageView);

        //Movie Data
        final TextView yearTextView = (TextView) findViewById(R.id.tv_year);
        final TextView overviewTextView = (TextView) findViewById(R.id.tv_overview);
        yearTextView.setText(mSearchResultMovie.getResults().get(mMoviePosition).getReleaseDate().split("-")[0]);
        overviewTextView.setText(mSearchResultMovie.getResults().get(mMoviePosition).getOverview());
    }
}
