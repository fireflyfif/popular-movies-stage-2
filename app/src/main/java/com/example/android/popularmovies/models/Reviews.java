package com.example.android.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fifiv on 11/03/2018.
 */

public class Reviews {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("author")
    @Expose
    private String mAuthor;

    @SerializedName("content")
    @Expose
    private String mContent;

    @SerializedName("url")
    @Expose
    private String mUrl;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
