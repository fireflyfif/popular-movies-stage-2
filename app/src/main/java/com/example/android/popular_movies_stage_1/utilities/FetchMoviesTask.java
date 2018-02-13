package com.example.android.popular_movies_stage_1.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popular_movies_stage_1.BuildConfig;
import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.ui.MoviesAdapter;

import java.net.URL;
import java.util.List;

/**
 * Created by fifiv on 09/02/2018.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movies>> {

    private MoviesAdapter mAdapter;
    private Context mContext;

    public FetchMoviesTask(MoviesAdapter moviesAdapter) {
        mAdapter = moviesAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movies> doInBackground(String... urls) {
        // Do not perform the request if there are no URLs, or the first URL is null
        if (urls.length < 1 || urls[0] == null) {
            return null;
        }

        // ? What does this line mean?
        String sortOrder = urls[0];

        URL moviesRequestUrl = NetworkUtils.buildUrl(BuildConfig.API_KEY, sortOrder);

        try {
            String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl
                    (moviesRequestUrl);

            List<Movies> jsonMovieList = TheMovieDbJsonUtils.getMoviesFromJson(jsonMoviesResponse);

            return jsonMovieList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movies> movies) {

        if (movies != null) {
            mAdapter.setMovieData(movies);
        }
        super.onPostExecute(movies);
    }
}
