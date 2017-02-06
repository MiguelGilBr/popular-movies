package com.example.popularmovies.activity;

import android.os.Bundle;
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
import com.example.popularmovies.ui.MovieCoverAdapter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements Callback<SearchResult> {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String POSITION_KEY = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Client.getPopularMovies(this);
    }


    private void updateFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        transaction.replace(R.id.mainFragment, fragment);
        transaction.commit();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             //       .setAction("Action", null).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //CALLBACKS
    @Override
    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
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
        Log.i(TAG, "Respuesta asincrona fallo journey: " + t.getMessage());
    }
}