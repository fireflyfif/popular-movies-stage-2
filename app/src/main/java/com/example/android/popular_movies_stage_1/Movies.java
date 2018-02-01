package com.example.android.popular_movies_stage_1;

/**
 * Created by fifiv on 01/02/2018.
 */

public class Movies {

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
    private String mVoteAverage;

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
    public Movies(String movieTitle, String releaseDate, String moviePoster,
                  String voteAverage, String plotSynopsis) {

        mMovieTitle = movieTitle;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
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
    public String getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Get the Plot Synopsis
     */
    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }
}
