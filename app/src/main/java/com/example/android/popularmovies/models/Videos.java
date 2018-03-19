package com.example.android.popularmovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fifiv on 11/03/2018.
 */

public class Videos {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;

    @SerializedName("key")
    @Expose
    private String mKey;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("site")
    @Expose
    private String mSite;

    @SerializedName("size")
    @Expose
    private Integer mSize;

    @SerializedName("type")
    @Expose
    private String mType;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public Integer getSize() {
        return mSize;
    }

    public void setSize(Integer size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
