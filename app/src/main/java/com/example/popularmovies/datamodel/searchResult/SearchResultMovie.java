package com.example.popularmovies.datamodel.searchResult;

import com.example.popularmovies.datamodel.Movie;
import com.example.popularmovies.datamodel.base.SearchResultBase;
import java.util.List;

public class SearchResultMovie extends SearchResultBase<Movie> {
    public SearchResultMovie() {
        super();
    }
    public SearchResultMovie(List<Movie> results) {
        super(results);
    }
}