package com.example.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.SearchResult;
import com.example.popularmovies.fragment.RecyclerViewFragment;
import com.example.popularmovies.network.Client;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.util.InternetUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements Callback<SearchResult> {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String FRAGMENT_TAG = "RECYCLER_FRAGMENT";
    public static final String POSITION_KEY = "POSITION";

    private RecyclerViewFragment recyclerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        getFirstData();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getFirstData() {
        if (InternetUtils.isInternetConnected(mContext)) {
            if (DataModel.getInstance().getSearchResult() == null) {
                showLoadingDialog();
                Client.getPopularMovies(this);
            } else {
                updateFragment();
            }
        } else {
            Snackbar.make(findViewById(R.id.root_view), R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }
    private void refreshData() {
        if (InternetUtils.isInternetConnected(mContext)) {
            DataModel.getInstance().resetData();
            showLoadingDialog();
            Client.getTopMovies(this);

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
            recyclerFragment.refreshAdapter(DataModel.getInstance().getSearchResult());
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
            refreshData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //CALLBACKS
    @Override
    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
        hideLoadingDialog();
        if (response.isSuccessful()) {
            SearchResult searchResult = response.body();
            Log.i(TAG, "Respuesta asincrona correcta Movie");
            DataModel.getInstance().setSearchResult(searchResult);
            updateFragment();
        } else {
            Log.i(TAG,response.message());
            int statusCode = response.code();
            ResponseBody errorBody = response.errorBody();
        }
    }
    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        hideLoadingDialog();
        Log.i(TAG, "Respuesta asincrona fallo journey: " + t.getMessage());
    }
}