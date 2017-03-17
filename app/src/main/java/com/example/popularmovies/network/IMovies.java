package com.example.popularmovies.network;

import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.datamodel.searchResult.SearchResultReview;
import com.example.popularmovies.datamodel.searchResult.SearchResultVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IMovies {
    //@Headers("Content-Type: application/json; charset=utf-8")
    @GET
    Call<SearchResultMovie> getMovies(@Url String url, @Query("api_key") String apiKey);

    @GET
    Call<SearchResultReview> getMovieReviews(@Url String url, @Query("api_key") String apiKey);

    @GET
    Call<SearchResultVideo> getMovieVideos(@Url String url, @Query("api_key") String apiKey);
}
