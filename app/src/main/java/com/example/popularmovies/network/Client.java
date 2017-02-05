package com.example.popularmovies.network;

import android.util.Log;

import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String TAG = Client.class.getSimpleName();

    public static final String API_BASE_URL = "http://api.themoviedb.org/3/Movie/";
    public static final String API_KEY = "99b2bc6e311bc17202dd2e9a640921ba";

    //STATIC
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
                                                .baseUrl(API_BASE_URL)
                                                .addConverterFactory(GsonConverterFactory.create());

    private static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    //CONSTRUCTOR
    private Client() {}

    public static void getPopularMovies() {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResult> callSearchResult = movies.getSearchResult("popular",API_KEY);
        callSearchResult.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    SearchResult searchResult = response.body();
                    Log.i(TAG, "Respuesta asincrona correcta Movie");
                    DataModel.getInstance().setSearchResult(searchResult);

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
        });
    }
}




