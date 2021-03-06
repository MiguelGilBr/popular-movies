package com.example.popularmovies.network;

import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.datamodel.searchResult.SearchResultReview;
import com.example.popularmovies.datamodel.searchResult.SearchResultVideo;

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
    public static final String YOUTUBE_PIC_URL = "https://img.youtube.com/vi/";

    public static final String ENDPOINT_POPULAR = "popular";
    public static final String ENDPOINT_TOP = "top_rated";
    public static final String ENDPOINT_REVIEW = "reviews";
    public static final String ENDPOINT_VIDEO = "videos";

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

    //GET MOVIES
    public static void getPopularMovies(Callback<SearchResultMovie> callback) {
        getMovies(callback,ENDPOINT_POPULAR);
    }
    public static void getTopMovies(Callback<SearchResultMovie> callback) {
        getMovies(callback,ENDPOINT_TOP);
    }
    private static void getMovies(Callback<SearchResultMovie> callback, String endPoint) {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResultMovie> callSearchResult = movies.getMovies(endPoint,API_KEY);
        callSearchResult.enqueue(callback);
    }
    //GET REVIEWS
    public static void getMovieReviews(Callback<SearchResultReview> callback, String movieId) {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResultReview> callSearchResult = movies.getMovieReviews(movieId + "/" + ENDPOINT_REVIEW,API_KEY);
        callSearchResult.enqueue(callback);
    }
    //GET VIDEOS
    public static void getMovieVideos(Callback<SearchResultVideo> callback, String movieId) {
        IMovies movies = Client.createService(IMovies.class);
        Call<SearchResultVideo> callSearchResult = movies.getMovieVideos(movieId + "/" + ENDPOINT_VIDEO,API_KEY);
        callSearchResult.enqueue(callback);
    }
}




