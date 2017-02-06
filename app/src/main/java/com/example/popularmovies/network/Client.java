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

    public static final String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
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

    public static void getPopularMovies(Callback<SearchResult> callback) {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResult> callSearchResult = movies.getSearchResult("popular",API_KEY);
        callSearchResult.enqueue(callback);
    }
}




