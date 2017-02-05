package com.example.popularmovies.datamodel;

public class DataModel {

    private static DataModel instance;
    private SearchResult searchResult;

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    private DataModel() {
    }

    //GETTERS & SETTERs
    public SearchResult getSearchResult() {
        return searchResult;
    }
    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }
}
