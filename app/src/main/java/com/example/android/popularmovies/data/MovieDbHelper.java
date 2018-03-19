package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.MovieContract.FavMovieEntry;


/**
 * Manages a local database for favorite movies data
 *
 * Created by fifiv on 28/02/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    /**
     * The name of the database
     */
    public static final String DATABASE_NAME = "movies.db";

    /**
     * The version of the database.
     * Every time the schema is changed, the number of the version must be incremented.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for the MovieDbHelper class
     *
     * @param context creates the context object
     */
    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called when the database is created for the first time.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*
         * This String contains a simple SQL statement that will create a table that will
         * cache the favorite movies of the user
         */
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + FavMovieEntry.TABLE_NAME + " (" +

                FavMovieEntry._ID                           + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                FavMovieEntry.COLUMN_MOVIE_ID               + " INTEGER NOT NULL, " +

                FavMovieEntry.COLUMN_MOVIE_TITLE            + " TEXT NOT NULL, " +

                FavMovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE   + " TEXT NOT NULL, " +

                FavMovieEntry.COLUMN_POSTER_PATH            + " TEXT NOT NULL, " +

                FavMovieEntry.COLUMN_BACKDROP_PATH          + " TEXT NOT NULL, " +

                FavMovieEntry.COLUMN_MOVIE_RATING           + " REAL, " +

                FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE     + " TEXT NOT NULL, " +

                FavMovieEntry.COLUMN_MOVIE_SYNOPSIS         + " TEXT NOT NULL, " +


                " UNIQUE (" + FavMovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE" + ");";

        /*
         *  Execute the SQL with the execSQL method of the SQLite database object.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
