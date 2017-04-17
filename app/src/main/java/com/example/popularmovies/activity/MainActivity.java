package com.example.popularmovies.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.application.PopularMoviesApplication;
import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.Movie;
import com.example.popularmovies.datamodel.MovieDao;
import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.fragment.RecyclerViewFragment;
import com.example.popularmovies.network.Client;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.util.InternetUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String FRAGMENT_TAG = "RECYCLER_FRAGMENT";
    public static final String POSITION_KEY = "POSITION";

    public static final String TYPE_KEY = "TYPE";
    public enum DetailType  {
        POPULAR,
        TOP,
        FAVOURITE
    };
    private DetailType detailType = DetailType.POPULAR;

    private RecyclerViewFragment recyclerFragment;
    private Callback<SearchResultMovie> movieCallback = (new Callback<SearchResultMovie>() {
        @Override
        public void onResponse(Call<SearchResultMovie> call, Response<SearchResultMovie> response) {
            hideLoadingDialog();
            if (response.isSuccessful()) {
                SearchResultMovie searchResultMovie = response.body();
                Log.i(TAG, "Respuesta asincrona correcta Movie");
                setData(searchResultMovie);
            } else {
                Log.i(TAG,response.message());
                //TODO: poner mensajico de clave invalida
                int statusCode = response.code();
                ResponseBody errorBody = response.errorBody();
            }
        }
        @Override
        public void onFailure(Call<SearchResultMovie> call, Throwable t) {
            hideLoadingDialog();
            Log.i(TAG, "Respuesta asincrona fallo journey: " + t.getMessage());
        }
    });
    private void setData(SearchResultMovie searchResultMovie) {
        switch (detailType) {
            case POPULAR:
                DataModel.getInstance().setSearchResultPopularMovie(searchResultMovie);
                break;
            case TOP:
                DataModel.getInstance().setSearchResultTopMovie(searchResultMovie);
                break;
            default:
                break;
        }
        updateFragment(searchResultMovie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(TYPE_KEY,-1)> -1) {
                detailType = DetailType.values()[savedInstanceState.getInt(TYPE_KEY,-1)];
            }
        }

        initUI();
        initData();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TYPE_KEY, detailType.ordinal());
    }

    private void initData() {
        switch (detailType) {
            case POPULAR:
                getPopularData();
                break;
            case TOP:
                getTopData();
                break;
            case FAVOURITE:
                getFavouriteData();
                break;
        }
    }
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_popular:
                        getPopularData();
                        return true;
                    case R.id.navigation_top:
                        getTopData();
                        return true;
                    case R.id.navigation_favourites:
                        getFavouriteData();
                        return true;
                }
                return false;
            }
        });
    }

    private void getPopularData() {
        detailType = DetailType.POPULAR;
        if (InternetUtils.isInternetConnected(mContext)) {
            if (DataModel.getInstance().getSearchResultPopularMovie() == null) {
                showLoadingDialog();
                Client.getPopularMovies(movieCallback);
            } else {
                updateFragment(DataModel.getInstance().getSearchResultPopularMovie());
            }
        } else {
            Snackbar.make(findViewById(R.id.root_view), R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }
    private void getTopData() {
        detailType = DetailType.TOP;
        if (InternetUtils.isInternetConnected(mContext)) {
            if (DataModel.getInstance().getSearchResultTopMovie() == null) {
                showLoadingDialog();
                Client.getTopMovies(movieCallback);
            } else {
                updateFragment(DataModel.getInstance().getSearchResultTopMovie());
            }
        } else {
            Snackbar.make(findViewById(R.id.root_view), R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }
    private void getFavouriteData(){
        detailType = DetailType.FAVOURITE;
        MovieDao movieDao = ((PopularMoviesApplication) getApplication()).getDaoSession().getMovieDao();
        List<Movie> list = movieDao.queryBuilder().list();
        updateFragment(new SearchResultMovie(movieDao.queryBuilder().list()));
    }

    private void updateFragment(SearchResultMovie searchResultMovie) {
        recyclerFragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (recyclerFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            recyclerFragment = RecyclerViewFragment.newInstance();
            transaction.replace(R.id.root_view, recyclerFragment,FRAGMENT_TAG);
            transaction.commit();
            recyclerFragment.setCacheSearchResultMovie(searchResultMovie);
        } else {
            recyclerFragment.setCacheSearchResultMovie(searchResultMovie);
            recyclerFragment.refreshAdapter(searchResultMovie);
        }
    }

    //MENÚ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_retry) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }