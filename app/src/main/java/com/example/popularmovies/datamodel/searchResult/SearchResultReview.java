package com.example.popularmovies.datamodel.searchResult;

import com.example.popularmovies.datamodel.Review;
import com.example.popularmovies.datamodel.base.SearchResultBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultReview extends SearchResultBase<Review> {
    @SerializedName("id")
    @Expose
    private Integer id;

    public SearchResultReview(List<Review> results) {
        super(results);
    }

    //GETTERS & SETTERs
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
