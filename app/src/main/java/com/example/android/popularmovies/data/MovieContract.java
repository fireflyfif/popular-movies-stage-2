package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the movie database
 *
 * Created by fifiv on 27/02/2018.
 */

public class MovieContract {

    /* The package name that serves as the authority, which is the name of the entire
    content provider. */
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";

    /* The base content URI */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /* This is the path for the "movies" directory */
    public static final String PATH_FAV_MOVIES = "movies";


    /**
     * Inner class that defines the table contents of the favorite movies table
     */
    public static final class FavMovieEntry implements BaseColumns {

        /**
         *  The base CONTENT_URI used to query the Movies table from the content provider
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAV_MOVIES)
                .build();

        /**
         * Name of the database table for favorite movies
         */
        public static final String TABLE_NAME = "movies";


        /* Movie ID for the favorite movies */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /* Movie poster path for the favorite movies */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /* Movie backdrop path for the favorite movies */
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";

        /* Movie title that stores the string for the favorite movies */
        public static final String COLUMN_MOVIE_TITLE = "title";

        /* Movie original title that stores the string for the favorite movies */
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "original_title";

        /* Movie synopsis that stores the overview for the favorite movies */
        public static final String COLUMN_MOVIE_SYNOPSIS = "synopsis";

        /* Movie rating that stores the average rating for the favorite movies */
        public static final String COLUMN_MOVIE_RATING = "rating";

        /* Movie release date for the favorite movies */
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";

        /* Movie genres of the favorite movies */
        //public static final String COLUMN_MOVIE_GENRES = "genres";

    }
}
