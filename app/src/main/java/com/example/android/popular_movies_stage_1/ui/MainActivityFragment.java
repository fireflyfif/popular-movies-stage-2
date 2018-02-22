package com.example.android.popular_movies_stage_1.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.utilities.FetchMoviesTask;

import java.util.ArrayList;


/**
 * Created by fifiv on 02/02/2018.
 */

public class MainActivityFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String LOG_TAG = "MainActivityFragment";

    private static final String SAVE_STATE_KEY = "save_state";

    private static final String RECYCLER_VIEW_STATE = "list_state";

    private Parcelable savedRecyclerViewState;

    private MoviesAdapter mMoviesAdapter;

    private RecyclerView mRecyclerGridView;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessage;

    // Declare the movie list as an ArrayList,
    // because Parcelable saves state of ArrayList, but not ListView
    private ArrayList<Movies> mMoviesList;


    // Mandatory empty constructor
    public MainActivityFragment() {}

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Log.v(LOG_TAG, "onViewStateRestored called Now!");
        if (savedInstanceState != null) {
            savedRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
            mRecyclerGridView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(LOG_TAG, "onCreate called Now!");

        if (savedInstanceState == null || !savedInstanceState.containsKey(SAVE_STATE_KEY)) {
            mMoviesList = new ArrayList<>();
        } else {
            mMoviesList = savedInstanceState.getParcelableArrayList(SAVE_STATE_KEY);
            savedRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        mRecyclerGridView = rootView.findViewById(R.id.recycler_grid_view);
        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mErrorMessage = rootView.findViewById(R.id.error_message_display);

        mMoviesList = new ArrayList<>();

        // Create new instance of GridLayoutManager and set the second argument -
        // columnSpan to have 2 for vertical and 3 for landscape mode
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.movie_list_columns));

        mRecyclerGridView.setLayoutManager(gridLayoutManager);
        mRecyclerGridView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(getActivity(), mMoviesList);

        mRecyclerGridView.setAdapter(mMoviesAdapter);

        // Call onRestoreInstanceState when the data has been reattached to the mRecyclerGridView
        mRecyclerGridView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);


        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);

        // Check for Network Connection
        if (haveNetworkConnection()) {
            Log.v(LOG_TAG, "There is an internet connection");
            loadMoviesFromPreferences();
        } else {
            Log.v(LOG_TAG, "There is NO internet connection");
            showErrorMessage();
        }

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_STATE_KEY, mMoviesList);

        // Put state of the position of the RecyclerView
        outState.putParcelable(RECYCLER_VIEW_STATE, mRecyclerGridView.getLayoutManager()
                .onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }


    /**
     * Check for Network Connection
     */
    private boolean haveNetworkConnection() {

        // Get reference to the ConnectivityManager to check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean haveNetConnection = false;
        // Get details on the currently active default data network
        if  (networkInfo != null && networkInfo.isConnected()) {
            haveNetConnection = true;
        }
        return haveNetConnection;
    }

    /**
     * Show error message when there is a problem fetching the data or
     * there is no internet connection
     */
    private void showErrorMessage() {
        // Hide the currently visible data
        mRecyclerGridView.setVisibility(View.INVISIBLE);

        // Show the error
        mErrorMessage.setVisibility(View.VISIBLE);
    }


    /**
     * Method for loading movies using user's preference sort order
     * for fetching movies asynchronously
     */
    private void loadMoviesFromPreferences() {
        // There is something fishy here
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        // Execute the network call on a separate background thread
        FetchMoviesTask task = new FetchMoviesTask(mMoviesAdapter, this, mLoadingIndicator);

        // Update the recycler view with the user's preferences.
        String sortOrderKey = getString(R.string.pref_sort_by_key);
        String sortOrderDefault = getString(R.string.pref_sort_by_popular);
        String sortOrder = sharedPreferences.getString(sortOrderKey, sortOrderDefault);

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
