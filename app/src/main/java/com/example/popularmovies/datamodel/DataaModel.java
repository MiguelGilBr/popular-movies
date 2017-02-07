package com.example.popularmovies.datamodel;

public class DataaModel {

    private static DataaModel instance;
    private SearchResult searchResult;

    public static DataaModel getInstance() {
        if (instance == null) {
            instance = new DataaModel();
        }
        return instance;
    }

    private DataaModel() {
    }

    public void resetData() {
        instance = null;
    }

    //GETTERS & SETTERs
    public SearchResult getSearchResult() {
        return searchResult;
    }
    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }
}
