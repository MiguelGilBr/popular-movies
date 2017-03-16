package com.example.popularmovies.datamodel;

import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;

public class DataModel {

    private static DataModel instance;
    private SearchResultMovie searchResultMovie;

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    private DataModel() {
    }

    public void resetData() {
        instance = null;
    }

    //GETTERS & SETTERs
    public SearchResultMovie getSearchResultMovie() {
        return searchResultMovie;
    }
    public void setSearchResultMovie(SearchResultMovie searchResultMovie) {
        this.searchResultMovie = searchResultMovie;
    }
}
