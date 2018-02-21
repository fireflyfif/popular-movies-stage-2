package com.example.android.popular_movies_stage_1.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popular_movies_stage_1.BuildConfig;
import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.ui.MainActivityFragment;
import com.example.android.popular_movies_stage_1.ui.MoviesAdapter;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

/**
 * Created by fifiv on 09/02/2018.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movies>> {

    private MoviesAdapter mAdapter;
    private WeakReference<MainActivityFragment> appReference;
    private ProgressBar mProgressBar;


    /**
     * Constructor for FetchMoviesTask Class
     *
     * @param moviesAdapter
     * @param context
     */
    public FetchMoviesTask(MoviesAdapter moviesAdapter, MainActivityFragment context,
                           ProgressBar progressBar) {
        mAdapter = moviesAdapter;
        appReference = new WeakReference<>(context);
        mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        MainActivityFragment activityFragment = appReference.get();
//        if (activityFragment == null || activityFragment.getActivity().isFinishing())
//            return;
//
//        ProgressBar loadingIndicator = activityFragment
//                .getActivity().findViewById(R.id.loading_indicator);

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movies> doInBackground(String... urls) {
        // Do not perform the request if there are no URLs, or the first URL is null
        if (urls.length < 1 || urls[0] == null) {
            return null;
        }

        // What this line does?
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

        mProgressBar.setVisibility(View.INVISIBLE);

        if (movies != null) {
            mAdapter.setMovieData(movies);
        }
        super.onPostExecute(movies);
    }
}
