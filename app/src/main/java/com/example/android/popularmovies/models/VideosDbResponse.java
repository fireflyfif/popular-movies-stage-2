package com.example.android.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fifiv on 11/03/2018.
 */

public class VideosDbResponse {

    @SerializedName("id")
    @Expose
    private Integer mId;

    @SerializedName("results")
    @Expose
    private List<Videos> mResults = null;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public List<Videos> getResults() {
        return mResults;
    }

    public void setResults(List<Videos> results) {
        mResults = results;
    }
}
