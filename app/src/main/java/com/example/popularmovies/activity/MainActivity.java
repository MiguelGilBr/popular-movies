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
import com.example.popularmovies.datamodel.searchResult.SearchResultReview;
import com.example.popularmovies.datamodel.searchResult.SearchResultVideo;
import com.example.popularmovies.fragment.RecyclerViewFragment;
import com.example.popularmovies.network.Client;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.util.InternetUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String FRAGMENT_TAG = "RECYCLER_FRAGMENT";
    public static final String POSITION_KEY = "POSITION";

    private RecyclerViewFragment recyclerFragment;

    private Callback<SearchResultMovie> movieCallback = (new Callback<SearchResultMovie>() {
        @Override
        public void onResponse(Call<SearchResultMovie> call, Response<SearchResultMovie> response) {
            hideLoadingDialog();
            if (response.isSuccessful()) {
                SearchResultMovie searchResultMovie = response.body();
                Log.i(TAG, "Respuesta asincrona correcta Movie");
                DataModel.getInstance().setSearchResultMovie(searchResultMovie);
                updateFragment();
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        getPopularData();
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
                        //TODO

                        return true;
                }
                return false;
            }
        });
    }

    private void getPopularData() {
        if (InternetUtils.isInternetConnected(mContext)) {
            DataModel.getInstance().resetData();
            if (DataModel.getInstance().getSearchResultMovie() == null) {
                showLoadingDialog();
                Client.getPopularMovies(movieCallback);
            } else {
                updateFragment();
            }
        } else {
            Snackbar.make(findViewById(R.id.root_view), R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }
    private void getTopData() {
        if (InternetUtils.isInternetConnected(mContext)) {
            DataModel.getInstance().resetData();
            showLoadingDialog();
            Client.getTopMovies(movieCallback);
        } else {
            Snackbar.make(findViewById(R.id.root_view), R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void updateFragment() {
        RecyclerViewFragment recyclerFragment = (RecyclerViewFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (recyclerFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            recyclerFragment = new RecyclerViewFragment();
            transaction.replace(R.id.root_view, recyclerFragment,FRAGMENT_TAG);
            transaction.commit();
        } else {
            recyclerFragment.refreshAdapter(DataModel.getInstance().getSearchResultMovie());
        }
    }

    //MENÚ
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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