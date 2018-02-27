package com.example.android.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fifiv on 01/02/2018.
 */

public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    private String mMovieId;
    /**
     * Movie Title
     */
    private String mMovieTitle;
    /**
     * Movie Title
     */
    private String mMovieOriginalTitle;
    /**
     * Movie Release date
     */
    private String mReleaseDate;
    /**
     * Movie Poster
     */
    private String mMoviePoster;
    /**
     * Movie Second Image Poster
     */
    private String mMovieBackdrop;
    /**
     * Vote Average
     */
    private double mVoteAverage;
    /**
     * Plot Synopsis
     */
    private String mPlotSynopsis;

    /**
     * Create new Movies object
     *
     * @param movieId       is the Id of the movie
     * @param movieTitle    is the title of the movie
     * @param releaseDate   is the release date of the movie
     * @param moviePoster   is the poster of the movie
     * @param movieBackdrop is the second movie image of the movie
     * @param voteAverage   is the average vote for the movie
     * @param plotSynopsis  is the description of the movie
     */
    public Movies(String movieId, String movieTitle, String movieOriginalTitle, String releaseDate,
                  String moviePoster, String movieBackdrop, double voteAverage, String plotSynopsis) {

        mMovieId = movieId;
        mMovieTitle = movieTitle;
        mMovieOriginalTitle = movieOriginalTitle;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mMovieBackdrop = movieBackdrop;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }

    protected Movies(Parcel in) {
        mMovieId = in.readString();
        mMovieTitle = in.readString();
        mMovieOriginalTitle = in.readString();
        mReleaseDate = in.readString();
        mMoviePoster = in.readString();
        mMovieBackdrop = in.readString();
        mVoteAverage = in.readDouble();
        mPlotSynopsis = in.readString();
    }

    /**
     * Get the Movie's Id
     */
    public String getMovieId() {
        return mMovieId;
    }

    /**
     * Get the Movie's Title
     */
    public String getMovieTitle() {
        return mMovieTitle;
    }

    /**
     * Get the Movie's Title
     */
    public String getMovieOriginalTitle() {
        return mMovieOriginalTitle;
    }

    /**
     * Get the Movie's Release date
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Get the Movie's Poster
     */
    public String getPoster() {
        return mMoviePoster;
    }

    /**
     * Get the Movie's Second Image
     */
    public String getMovieBackdrop() {
        return mMovieBackdrop;
    }

    /**
     * Get the Vote Average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Get the Plot Synopsis
     */
    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMovieId);
        parcel.writeString(mMovieTitle);
        parcel.writeString(mMovieOriginalTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mMoviePoster);
        parcel.writeString(mMovieBackdrop);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mPlotSynopsis);
    }
}
