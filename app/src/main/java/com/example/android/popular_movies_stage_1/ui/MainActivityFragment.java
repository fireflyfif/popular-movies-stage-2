package com.example.android.popular_movies_stage_1.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popular_movies_stage_1.MainActivity;
import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.utilities.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fifiv on 02/02/2018.
 */

public class MainActivityFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String SORT_BY_POPULAR = "popular";

    private static final String SORT_BY_TOP_RATED = "top_rated";

    private MoviesAdapter mMoviesAdapter;

    private RecyclerView mRecyclerGridView;

    private static boolean PREFERENCE_FLAG = false;



    // Mandatory empty constructor
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        mRecyclerGridView = rootView.findViewById(R.id.recycler_grid_view);

        List<Movies> moviesList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        // Set Span of the GridView for both vertical and horizontal orientation

        mRecyclerGridView.setLayoutManager(gridLayoutManager);
        mRecyclerGridView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(getActivity(), moviesList);

        mRecyclerGridView.setAdapter(mMoviesAdapter);

        setupSharedPreferences();

        return rootView;
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        loadMoviesFromPreferences();

        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadMoviesFromPreferences() {
        // Execute the network call on a separate background thread
        FetchMoviesTask task = new FetchMoviesTask(mMoviesAdapter);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // The preferences do not update the recycler view.
        // If I put a string key here, it will add to the url path and it will be wrong
        // TODO: Make the SharedPreferences update the movies
        String sortOrder = sharedPreferences.getString("", getString(R.string.pref_sort_by_popular));

        task.execute(sortOrder);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_by_key))) {
            loadMoviesFromPreferences();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMoviesFromPreferences();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
