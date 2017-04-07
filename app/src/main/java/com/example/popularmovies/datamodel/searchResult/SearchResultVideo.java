package com.example.popularmovies.datamodel.searchResult;

import com.example.popularmovies.datamodel.Video;
import com.example.popularmovies.datamodel.base.SearchResultBase;

import java.util.List;

public class SearchResultVideo extends SearchResultBase<Video> {
    public SearchResultVideo(List<Video> results) {
        super(results);
    }
}
