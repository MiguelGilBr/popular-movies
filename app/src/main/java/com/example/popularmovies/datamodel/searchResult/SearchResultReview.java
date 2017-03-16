package com.example.popularmovies.datamodel.searchResult;

import com.example.popularmovies.datamodel.Movie;
import com.example.popularmovies.datamodel.base.SearchResultBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultReview extends SearchResultBase<Movie> {
    @SerializedName("id")
    @Expose
    private Integer id;
}
