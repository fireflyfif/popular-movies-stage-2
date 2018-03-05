package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fifiv on 28/02/2018.
 */

public class MovieProvider extends ContentProvider {

    private static final String LOG_TAG = "MovieProvider";

    /*
     * Define integer constants for the directory of movies and a single movie
     */
    public static final int CODE_MOVIES = 100;
    public static final int CODE_MOVIES_WITH_ID = 101;

    /**
     * Static variable for the Uri matcher that is used by this content provider
     */
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * Creates the UriMatcher that will match each URI to the CODE_MOVIES and
     * CODE_MOVIES_WITH_ID constants.
     *
     * @return A UriMatcher that correctly matches the constants for CODE_MOVIES and
     * CODE_MOVIES_WITH_ID
     */
    public static UriMatcher buildUriMatcher() {
        // Create a new UriMatcher object to construct an empty matcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add matches with addURI (String authority, String path, int code)
        // Movie Directory
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAV_MOVIES, CODE_MOVIES);

        // Single movie
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAV_MOVIES + "/#",
                CODE_MOVIES_WITH_ID);

        return uriMatcher;
    }


    /*
     * Member variable for a MovieDbHelper that's initialized in the onCreate() method
     */
    private MovieDbHelper mMovieDbHelper;

    @Override
    public boolean onCreate() {

        // Initialize a MovieDbHelper on startup
        mMovieDbHelper = new MovieDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor returnCursor = null;

        switch (match) {
            // Query for the movie directory
            case CODE_MOVIES:
                returnCursor = db.query(MovieContract.FavMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            case CODE_MOVIES_WITH_ID:

                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri = null;

        switch (match) {
            case CODE_MOVIES:
                long id = db.insert(MovieContract.FavMovieEntry.TABLE_NAME, null, values);

                if (id > 0) {
                    // Success
                    returnUri = ContentUris.withAppendedId(MovieContract.FavMovieEntry.CONTENT_URI, id);

                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                Log.v(LOG_TAG, "Number of new rows inserted: " + uri.toString());
                break;
            case CODE_MOVIES_WITH_ID:

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
