package com.example.android.popular_movies_stage_1.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

    private static final int SORT_ORDER_POPULAR_INT = 0;
    private static final int SORT_ORDER_TOP_RATED_INT = 1;

    private static final String SORT_BY_POPULAR = "popular";

    private static final String SORT_BY_TOP_RATED = "top_rated";

    private MoviesAdapter mMoviesAdapter;

    private RecyclerView mRecyclerGridView;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessage;




    // Mandatory empty constructor
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        mRecyclerGridView = rootView.findViewById(R.id.recycler_grid_view);
        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mErrorMessage = rootView.findViewById(R.id.error_message_display);

        List<Movies> moviesList = new ArrayList<>();

        // Create new instance of GridLayoutManager and set the second argument -
        // columnSpan to have 2 for vertical and 3 for landscape mode
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.movie_list_columns));

        mRecyclerGridView.setLayoutManager(gridLayoutManager);
        mRecyclerGridView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(getActivity(), moviesList);

        mRecyclerGridView.setAdapter(mMoviesAdapter);

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);

        // Check for Network Connection
        if (haveNetworkConnection()) {
            loadMoviesFromPreferences();
        } else {
            showErrorMessage();
        }

        return rootView;
    }

    /**
     * Check for Network Connection
     */
    private boolean haveNetworkConnection() {

        // Get reference to the ConnectivityManager to check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo;
        boolean haveNetConnection = false;
        // Get details on the currently active default data network
        if  (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            haveNetConnection = networkInfo.isConnected();
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
     *
     */
    private void setupSharedPreferences() {
//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(getActivity());

        // Execute the network call on a separate background thread
//        FetchMoviesTask task = new FetchMoviesTask(mMoviesAdapter, this, mLoadingIndicator);
//
//        int sortOrder = sharedPreferences.getInt("sort_by", SORT_ORDER_POPULAR_INT);
//        switch (sortOrder) {
//            case SORT_ORDER_POPULAR_INT:
//                task.execute(SORT_BY_POPULAR);
//                break;
//            case SORT_ORDER_TOP_RATED_INT:
//                task.execute(SORT_BY_TOP_RATED);
//                break;
//            default:
//                task.execute(SORT_BY_POPULAR);
//                break;
//        }

        loadMoviesFromPreferences();

        // Register the listener
        // sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadMoviesFromPreferences() {

        // There is something fishy here
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        // Execute the network call on a separate background thread
        FetchMoviesTask task = new FetchMoviesTask(mMoviesAdapter, this, mLoadingIndicator);

        // The preferences do not update the recycler view.
        // TODO: Make the SharedPreferences update the movies

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
            //setupSharedPreferences();
            loadMoviesFromPreferences();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Should I call this? If so, I need to add instance of sharedPreferences
        loadMoviesFromPreferences();
        //setupSharedPreferences();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
