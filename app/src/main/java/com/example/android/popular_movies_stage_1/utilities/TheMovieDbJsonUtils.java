package com.example.android.popular_movies_stage_1.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fifiv on 01/02/2018.
 */

public class TheMovieDbJsonUtils {

    public static String[] getSimpleStringFromJson(Context context, String moviesJsonString)
            throws JSONException {

        final String TMDB_OBJECT = "object";

        final String TMDB_RESULTS = "results";

        final String TMDB_POSTER_PATH = "poster_path";

        final String TMDB_ORIGINAL_TITLE = "original_title";

        final String TMDB_OVERVIEW = "overview";

        final String TMDB_VOTE_AVERAGE = "vote_average";

        final String TMDB_RELEASE_DATE = "release_date";

        String[] parseMoviesData = null;



        JSONObject moviesJson = new JSONObject(moviesJsonString);

        JSONObject moviesObject = moviesJson.getJSONObject(TMDB_OBJECT);

        if (moviesObject.has(TMDB_RESULTS)) {
            JSONArray resultsArray = moviesObject.getJSONArray(TMDB_RESULTS);

            parseMoviesData = new String[resultsArray.length()];

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentMovie = resultsArray.getJSONObject(i);

                String posterPath = currentMovie.getString(TMDB_POSTER_PATH);

                String movieTitle = currentMovie.getString(TMDB_ORIGINAL_TITLE);

                String plotSynopsis = currentMovie.getString(TMDB_OVERVIEW);

                String userRating = currentMovie.getString(TMDB_VOTE_AVERAGE);

                String releaseDate = currentMovie.getString(TMDB_RELEASE_DATE);


                parseMoviesData[i] = movieTitle + " - " + plotSynopsis +
                        " - " + userRating + " - " + releaseDate;
             }

        }
    return parseMoviesData;
    }
}
