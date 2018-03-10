package com.example.android.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fifiv on 06/03/2018.
 */

public class MovieDbResponse {

    @SerializedName("page")
    @Expose
    private Integer mPage;

    @SerializedName("total_results")
    @Expose
    private Integer mTotalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer mTotalPages;

    @SerializedName("results")
    @Expose
    private List<Movies> mMovieResults;


    public Integer getPage() {
        return mPage;
    }

    public void setPage(Integer page) {
        mPage = page;
    }

    public Integer getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Integer totalResults) {
        mTotalResults = totalResults;
    }

    public Integer getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Integer totalPages) {
        mTotalPages = totalPages;
    }

    public List<Movies> getResults() {
        return mMovieResults;
    }

    public void setResults(List<Movies> results) {
        mMovieResults = results;
    }
}
