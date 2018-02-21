package com.example.android.popular_movies_stage_1.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.popular_movies_stage_1.BuildConfig;
import com.example.android.popular_movies_stage_1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by fifiv on 06/02/2018.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String FILE_SIZE = "w342";

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3";

    private static final String TMDB_POSTER_PATH = "poster_path";

    private static final String MOVIE_PATH = "movie";

    private static final String POPULAR_PATH = "popular";

    private static final String TOP_RATED_PATH = "top_rated";

    private static final String API_KEY_PARAM = "api_key";


    // TODO HIDE API KEY before pushing to GitHub !!!

    private static String sortOrderPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String sortOrderKey = context.getString(R.string.pref_sort_by_key);
        String sortOrderDefault = context.getString(R.string.pref_sort_by_popular);

        return sharedPreferences.getString(sortOrderKey, sortOrderDefault);

    }

    // Write this code after I add SharedPreference
    public static URL buildUrl(String apiKey, String sortOrder) {

        Uri movieQueryUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(sortOrder)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL movieQueryUrl;
        try {
            movieQueryUrl = new URL(movieQueryUri.toString());

            Log.v(TAG, "Movie Query Url: " + movieQueryUrl);
            return movieQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // This method gives the right path to the Posters,
    // but when it is passed to the .load() method at @MoviesAdapter it does not show
    // the correct movie poster
    public static URL buildUrlForPosters(String posterPath) {
        Uri buildUri = Uri.parse(IMAGES_BASE_URL).buildUpon()
                .appendPath(FILE_SIZE)
                .appendPath(posterPath)
                .build();

        URL url;
        try {
            url = new URL(buildUri.toString());
            Log.v(TAG, "Built URI " + url);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildPosterPathUrl(String posterPath) {

        String posterPathUrlString = IMAGES_BASE_URL + FILE_SIZE + posterPath;
        Log.v(TAG, "Built URI for Poster: " + posterPathUrlString);

        return posterPathUrlString;
    }

    public static String buildPosterBackdropUrl(String backdropPath) {
        String backdropUrlString = IMAGES_BASE_URL + FILE_SIZE + backdropPath;
        Log.v(TAG, "Built URI for Backdrop: " + backdropUrlString);

        return backdropUrlString;
    }

    /**
     * this method returns the entire result from the HTTP response.
     *
     * @param url the URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network ad stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;

            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;

        } finally {
            urlConnection.disconnect();
        }
    }
}

