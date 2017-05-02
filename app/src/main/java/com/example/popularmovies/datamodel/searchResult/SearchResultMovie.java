package com.example.popularmovies.datamodel.searchResult;

import android.database.Cursor;

import com.example.popularmovies.datamodel.Movie;
import com.example.popularmovies.datamodel.base.SearchResultBase;

import java.util.ArrayList;
import java.util.List;

public class SearchResultMovie extends SearchResultBase<Movie> {
    public SearchResultMovie() {
        super();
    }

    public SearchResultMovie(List<Movie> results) {
        super(results);
    }

    public SearchResultMovie(Cursor cursor) {
        super();
        List<Movie> results = new ArrayList<Movie>();
        if (cursor.moveToFirst()) {
            do {
                results.add(new Movie(cursor));
            } while(cursor.moveToNext());
        }
        setResults(results);
    }
}