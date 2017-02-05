package com.example.popularmovies.network;

import com.example.popularmovies.datamodel.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IMovies {
    //@Headers("Content-Type: application/json; charset=utf-8")
    @GET
    Call<SearchResult> getSearchResult(@Url String url, @Query("api_key") String apiKey);
}
