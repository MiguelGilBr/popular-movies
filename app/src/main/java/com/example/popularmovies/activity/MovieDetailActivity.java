package com.example.popularmovies.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularmovies.application.PopularMoviesApplication;
import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.Movie;
import com.example.popularmovies.datamodel.MovieDao;
import com.example.popularmovies.datamodel.Review;
import com.example.popularmovies.datamodel.ReviewDao;
import com.example.popularmovies.datamodel.Video;
import com.example.popularmovies.datamodel.VideoDao;
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
    private MainActivity.DetailType detailType;

    private MovieDao movieDao;
    private ReviewDao reviewDao;
    private VideoDao videoDao;

    private Movie movie;
    private Boolean isFavourite = false;

    //UI
    private FloatingActionButton favouriteStar;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView tvTrailerTitle;
    private LinearLayout llVideos, llReviews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        initDAOs();
        initMovieData();
        initUI();
        loadData();
        loadExtraData();
    }

    private void initDAOs() {
        movieDao = ((PopularMoviesApplication) getApplication()).getDaoSession().getMovieDao();
        reviewDao = ((PopularMoviesApplication) getApplication()).getDaoSession().getReviewDao();
        videoDao = ((PopularMoviesApplication) getApplication()).getDaoSession().getVideoDao();
    }
    private void initMovieData() {
        Intent intent = getIntent();
        detailType = MainActivity.DetailType.values()[intent.getIntExtra(MainActivity.TYPE_KEY,-1)];
        switch (detailType) {
            case POPULAR:
                movie = DataModel.getInstance().getSearchResultPopularMovie().getResults().get(intent.getIntExtra(MainActivity.POSITION_KEY,0));
                break;
            case TOP:
                movie = DataModel.getInstance().getSearchResultTopMovie().getResults().get(intent.getIntExtra(MainActivity.POSITION_KEY,0));
                break;
            case FAVOURITE:
                movie = movieDao.queryBuilder().list().get(intent.getIntExtra(MainActivity.POSITION_KEY,0));
                break;
        }
        if (movieDao.load(movie.getId()) != null) {
            isFavourite = true;
        }
    }
    private void initUI() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        llReviews = (LinearLayout) findViewById(R.id.ll_reviews);
        tvTrailerTitle = (TextView)  findViewById(R.id.title_trailer);
        llVideos = (LinearLayout) findViewById(R.id.ll_videos);
        favouriteStar = (FloatingActionButton) findViewById(R.id.favourite_star);

        updateFavouriteIcon();
        collapsingToolbar.setTitle(movie.getTitle());
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

    //Movie Videos
    private void loadVideos(SearchResultVideo videos) {
        if (videos.getResults() != null) {
            llVideos.setVisibility(View.VISIBLE);
            tvTrailerTitle.setVisibility(View.VISIBLE);
            videoDao = ((PopularMoviesApplication) getApplication()).getDaoSession().getVideoDao();
            for (Video video : videos.getResults()) {
                video.setMovieId(movie.getId());
                videoDao.insertOrReplace(video);
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

    //Movie Review
    private void loadReviews(SearchResultReview reviewResults) {
        if (reviewResults.getResults() != null) {
            llReviews.setVisibility(View.VISIBLE);
            for (Review review : reviewResults.getResults()) {
                attachReview(review);
                review.setMovieId(movie.getId());
                reviewDao.insertOrReplace(review);
            }
        }
    }
    private void attachReview(Review review) {
        CardView card = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15);
        card.setLayoutParams(params);
        card.setContentPadding(15, 15, 15, 15);

        TextView tvAuthor = new TextView(this);
        tvAuthor.setLayoutParams(params);
        tvAuthor.setText(review.getAuthor());
        tvAuthor.setTypeface(null, Typeface.BOLD);
        llReviews.addView(tvAuthor);

        TextView tvContent = new TextView(this);
        tvContent.setLayoutParams(params);
        tvContent.setText(review.getContent());
        card.addView(tvContent);

        llReviews.addView(card);
    }

    //DATA
    private void loadData() {
        //Backdrop
        final ImageView backdropImageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(Client.BASE_IMAGE_URL + movie.getPosterPath())
                .into(backdropImageView);

        //Movie Data
        final TextView yearTextView = (TextView) findViewById(R.id.tv_year);
        final TextView overviewTextView = (TextView) findViewById(R.id.tv_overview);
        yearTextView.setText(movie.getReleaseDate().split("-")[0]);
        overviewTextView.setText(movie.getOverview());

        //Favourite Star
        favouriteStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavourite();
            }
        });
    }
    private void loadExtraData() {
        Client.getMovieReviews(reviewCallback, String.valueOf(movie.getId()));
        Client.getMovieVideos(videoCallback,  String.valueOf(movie.getId()));
    }
    private Callback<SearchResultReview> reviewCallback = new Callback<SearchResultReview>() {        @Override
    public void onResponse(Call<SearchResultReview> call, Response<SearchResultReview> response) {
        if (response.isSuccessful()) {
            loadReviews(response.body());
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

    //FAVOURITES
    private void toggleFavourite(){
        isFavourite=!isFavourite;
        if (isFavourite) {
            movieDao.insertOrReplace(movie);
        } else {
            movieDao.delete(movie);
        }
        updateFavouriteIcon();
    }
    private void updateFavouriteIcon() {
        if (isFavourite) {
            favouriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
        } else {
            favouriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
        }
    }
}
