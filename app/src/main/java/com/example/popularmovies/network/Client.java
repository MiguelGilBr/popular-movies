package com.example.popularmovies.network;

import com.example.popularmovies.datamodel.SearchResult;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String TAG = Client.class.getSimpleName();

    public static final String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
    public static final String API_KEY = "XXX";

    public static final String ENDPOINT_POPULAR = "popular";
    public static final String ENDPOINT_TOP = "top_rated";

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
        getMovies(callback,ENDPOINT_POPULAR);
    }
    public static void getTopMovies(Callback<SearchResult> callback) {
        getMovies(callback,ENDPOINT_TOP);
    }

    private static void getMovies(Callback<SearchResult> callback, String endPoint) {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResult> callSearchResult = movies.getSearchResult(endPoint,API_KEY);
        callSearchResult.enqueue(callback);
    }
}




