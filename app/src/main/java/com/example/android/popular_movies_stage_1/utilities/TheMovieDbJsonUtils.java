package com.example.android.popular_movies_stage_1.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popular_movies_stage_1.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fifiv on 01/02/2018.
 */

public class TheMovieDbJsonUtils {

    private static final String LOG_TAG = TheMovieDbJsonUtils.class.getSimpleName();

    private static final String ERROR_CODE = "status_code";

    private static final String TMDB_RESULTS = "results";

    private static final String TMDB_ID = "id";

    private static final String TMDB_POSTER_PATH = "poster_path";

    private static final String TMDB_BACKDROP_PATH = "backdrop_path";

    private static final String TMDB_ORIGINAL_TITLE = "original_title";

    private static final String TMDB_OVERVIEW = "overview";

    private static final String TMDB_VOTE_AVERAGE = "vote_average";

    private static final String TMDB_RELEASE_DATE = "release_date";


    public static List<Movies> getMoviesFromJson(String moviesJsonString) {

        if (TextUtils.isEmpty(moviesJsonString)) {
            return null;
        }

        // Create an empty ArrayList
        List<Movies> moviesList = new ArrayList<>();

        try {

            JSONObject moviesJson = new JSONObject(moviesJsonString);

            // If there is an error fetching the data from the server
            if (moviesJson.has(ERROR_CODE)) {
                int errorCode = moviesJson.getInt(ERROR_CODE);
                Log.e(LOG_TAG, "Parse json error code: " + errorCode);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        return null;
                }
            }

            JSONArray resultsArray = moviesJson.getJSONArray(TMDB_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentMovie = resultsArray.getJSONObject(i);

                String movieId = currentMovie.getString(TMDB_ID);

                String posterPath = currentMovie.getString(TMDB_POSTER_PATH);

                String movieTitle = currentMovie.getString(TMDB_ORIGINAL_TITLE);

                String movieBackdropPath = currentMovie.getString(TMDB_BACKDROP_PATH);

                String plotSynopsis = currentMovie.getString(TMDB_OVERVIEW);

                double userRating = currentMovie.getDouble(TMDB_VOTE_AVERAGE);

                String releaseDate = currentMovie.getString(TMDB_RELEASE_DATE);

                Log.v(LOG_TAG, " PosterPath: " + posterPath + "title: " + movieTitle +
                        "user rating: " + userRating + "release date: " + releaseDate);

                Movies movies = new Movies(movieId, movieTitle, releaseDate, posterPath,
                        movieBackdropPath, userRating, plotSynopsis);
                moviesList.add(movies);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem parsing the movies JSON result", e);
        }

        return moviesList;
    }
}
