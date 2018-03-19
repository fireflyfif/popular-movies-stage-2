package com.example.android.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fifiv on 11/03/2018.
 */

public class ReviewsDbResponse {

    @SerializedName("id")
    @Expose
    private Integer mId;

    @SerializedName("page")
    @Expose
    private Integer mPage;

    @SerializedName("results")
    @Expose
    private List<Reviews> mResults = null;

    @SerializedName("total_pages")
    @Expose
    private Integer mTotalPages;

    @SerializedName("total_results")
    @Expose
    private Integer mTotalResults;


    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getPage() {
        return mPage;
    }

    public void setPage(Integer page) {
        mPage = page;
    }

    public List<Reviews> getResults() {
        return mResults;
    }

    public void setResults(List<Reviews> results) {
        mResults = results;
    }

    public Integer getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Integer totalPages) {
        mTotalPages = totalPages;
    }

    public Integer getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Integer totalResults) {
        mTotalResults = totalResults;
    }
}
