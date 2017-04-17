package com.example.popularmovies.datamodel;

import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;

public class DataModel {

    private static DataModel instance;
    private SearchResultMovie searchResultPopularMovie = new SearchResultMovie();
    private SearchResultMovie searchResultTopMovie =  new SearchResultMovie();

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    private DataModel() {
    }

    //GETTERS & SETTERs
    public SearchResultMovie getSearchResultPopularMovie() {
        return searchResultPopularMovie;
    }
    public void setSearchResultPopularMovie(SearchResultMovie searchResultPopularMovie) {
        this.searchResultPopularMovie = searchResultPopularMovie;
    }
    public SearchResultMovie getSearchResultTopMovie() {
        return searchResultTopMovie;
    }
    public void setSearchResultTopMovie(SearchResultMovie searchResultTopMovie) {
        this.searchResultTopMovie = searchResultTopMovie;
    }
}
