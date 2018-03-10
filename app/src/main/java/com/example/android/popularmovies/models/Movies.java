package com.example.android.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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

    /**
     * Movie ID
     */
    @SerializedName("id")
    @Expose
    private String mMovieId;

    /**
     * Movie Title
     */
    @SerializedName("title")
    @Expose
    private String mMovieTitle;

    /**
     * Movie Original Title
     */
    @SerializedName("original_title")
    @Expose
    private String mMovieOriginalTitle;

    /**
     * Movie Release date
     */
    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;

    /**
     * Movie Poster
     */
    @SerializedName("poster_path")
    @Expose
    private String mMoviePoster;

    /**
     * Movie Second Image Poster
     */
    @SerializedName("backdrop_path")
    @Expose
    private String mMovieBackdrop;

    /**
     * Vote Average
     */
    @SerializedName("vote_average")
    @Expose
    private double mVoteAverage;

    /**
     * Plot Synopsis
     */
    @SerializedName("overview")
    @Expose
    private String mPlotSynopsis;

    /**
     * Genre IDs
     */
    @SerializedName("genre_ids")
    @Expose
    private int[] mMovieGenreIds;

    // The List with Genre names and Ids is taken from the The Movie DB API:
    // http://api.themoviedb.org/3/genre/movie/list?api_key=####
    private static final String[] GENRE_NAMES = new String[]{
            "Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
            "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery",
            "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"
    };

    private static final List<Integer> GENRE_IDS = Arrays.asList(
            28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648,
            10749, 878, 10770, 53, 10752, 37
    );

    private String mRating;

    /**
     * Create empty Movies constructor
     *
     */
    public Movies() {
    }

    // Constructor that is used for the Database
    // TODO: Add the Genres
    public Movies(String movieId, String movieTitle, String movieOriginalTitle, String releaseDate,
                  String moviePoster, String movieBackdrop, String voteAverage, String plotSynopsis) {

        mMovieId = movieId;
        mMovieTitle = movieTitle;
        mMovieOriginalTitle = movieOriginalTitle;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mMovieBackdrop = movieBackdrop;
        mRating = voteAverage;
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
        //mMovieGenreIds = in.createIntArray();
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

    /**
     * Get the List with Genres for a single Movie
     */
    public int[] getGenreIds() {
        return mMovieGenreIds;
    }

    // May not needed
    public void setGenreIds(int[] genreIds) {
        mMovieGenreIds = genreIds;
    }

    public String getMovieGenres(int[] genreIds) {
        List<String> genres = new ArrayList<>();

        String movieGenereString;

        return null;
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
        //parcel.writeIntArray(mMovieGenreIds);
    }
}
