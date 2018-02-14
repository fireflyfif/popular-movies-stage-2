package com.example.android.popular_movies_stage_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fifiv on 01/02/2018.
 */

public class Movies implements Parcelable{

    private String mMovieId;

    /**
     * Movie Title
     */
    private String mMovieTitle;

    /**
     * Movie Release date
     */
    private String mReleaseDate;

    /**
     * Movie Poster
     */
    private String mMoviePoster;

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
     * @param movieTitle   is the title of the movie
     * @param releaseDate  is the release date of the movie
     * @param moviePoster  is the poster of the movie
     * @param voteAverage  is the average vote for the movie
     * @param plotSynopsis is the description of the movie
     */
    public Movies(String movieId, String movieTitle, String releaseDate, String moviePoster,
                  double voteAverage, String plotSynopsis) {

        mMovieId = movieId;
        mMovieTitle = movieTitle;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }


    protected Movies(Parcel in) {
        mMovieId = in.readString();
        mMovieTitle = in.readString();
        mReleaseDate = in.readString();
        mMoviePoster = in.readString();
        mVoteAverage = in.readDouble();
        mPlotSynopsis = in.readString();
    }

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

    public String getMovieId() {
        return mMovieId;
    }

    /**
     * Get the Movie's Title
     */
    public String getMoviewTitle() {
        return mMovieTitle;
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
        parcel.writeString(mReleaseDate);
        parcel.writeString(mMoviePoster);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mPlotSynopsis);
    }
}
