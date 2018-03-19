package com.example.android.popularmovies.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapters.MoviesAdapter;
import com.example.android.popularmovies.data.MovieContract.FavMovieEntry;
import com.example.android.popularmovies.models.MovieDbResponse;
import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.utilities.MainApplication;
import com.example.android.popularmovies.utilities.MovieDbApiManager;
import com.example.android.popularmovies.utilities.MovieDbInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by fifiv on 02/02/2018.
 */

public class MainActivityFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        MoviesAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = "MainActivityFragment";

    private static final String SAVE_STATE_MOVIE_KEY = "save_state_movie";
    private static final String SAVE_STATE_FAV_KEY = "save_state_fav";
    private static final String SAVE_STATE_IS_FAV = "is_favorite";

    private static final String MOVIE_DETAILS_KEY = "movie_parcel";

    private static final int ID_FAV_MOVIES_LOADER = 1;

    @BindView(R.id.recycler_grid_view)
    RecyclerView mRecyclerGridView;
    @BindView(R.id.loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.error_message_display)
    TextView mErrorMessage;

    private MoviesAdapter mMoviesAdapter;

    private List<Movies> mMoviesList;

    private Cursor mFavMoviesData;

    private Movies mMovies;

    private boolean mIsFavMovie;


    // Mandatory empty constructor
    public MainActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(LOG_TAG, "onCreate called Now!");

        if (savedInstanceState == null || !savedInstanceState.containsKey(SAVE_STATE_MOVIE_KEY)
                || !savedInstanceState.containsKey(SAVE_STATE_FAV_KEY)) {
            mMovies = new Movies();
            mMoviesList = new ArrayList<>();

        } else {
            mMovies = savedInstanceState.getParcelable(SAVE_STATE_MOVIE_KEY);
            mMoviesList = savedInstanceState.getParcelableArrayList(SAVE_STATE_FAV_KEY);
            //mIsFavMovie = savedInstanceState.getBoolean(SAVE_STATE_IS_FAV);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        ButterKnife.bind(this, rootView);

        mMoviesList = new ArrayList<>();

        mLoadingIndicator.setVisibility(View.VISIBLE);

        // Create new instance of GridLayoutManager and set the second argument -
        // columnSpan to have 2 for vertical and 3 for landscape mode
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.movie_list_columns));

        mRecyclerGridView.setLayoutManager(gridLayoutManager);

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);

        loadMoviesFromPreferences();

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVE_STATE_MOVIE_KEY, mMovies);

        //outState.putParcelableArrayList(SAVE_STATE_FAV_KEY, mMoviesList);
        //outState.putBoolean(SAVE_STATE_IS_FAV, mIsFavMovie);

        super.onSaveInstanceState(outState);
    }


    /**
     * Method that shows error message when there is a problem fetching the data or
     * there is no internet connection
     */
    private void showErrorMessage() {
        // Hide the currently visible data
        mRecyclerGridView.setVisibility(View.INVISIBLE);

        // Show the error message
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "onPause is called!" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "onStop is called!" );
    }

    private void loadMoviesFromPreferences() {
        // Get preferences according to user's choice
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        String sortOrderKey = getString(R.string.pref_sort_by_key);
        String sortOrderDefault = getString(R.string.pref_sort_by_popular);
        String sortOrder = sharedPreferences.getString(sortOrderKey, sortOrderDefault);

        if (sortOrder.equals(getString(R.string.pref_sort_by_favorites))) {
            // Load data from the database
            Log.v(LOG_TAG, "The user selected Favorites: " + sortOrder);
            loadFavoriteMovies();

        } else {
            Log.v(LOG_TAG, "The user selected " + sortOrder);
            loadMoviesFromNetwork(sortOrder);
        }
    }

    private void loadMoviesFromNetwork(String sortOrder) {

        // Print the URL to the Logs
//        HttpUrl urlPrint = MainApplication.apiManager.getMovies(sortOrder, BuildConfig.API_KEY).request().url();
//        Log.d(LOG_TAG, "Print URL: " + urlPrint.toString());

        // Perform network call asynchronously
        MainApplication.apiManager.getMovies(sortOrder, BuildConfig.API_KEY,
                new Callback<MovieDbResponse>() {

            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {

                if (response.isSuccessful()) {
                    // Hide the Progress Bar
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    mMoviesList = response.body().getResults();

                    Log.d(LOG_TAG, "Number of movies: " + mMoviesList.size());

                    if (mMoviesAdapter == null) {
                        mMoviesAdapter = new MoviesAdapter(getActivity(), mMoviesList,
                                MainActivityFragment.this);
                        mRecyclerGridView.setAdapter(mMoviesAdapter);
                        mRecyclerGridView.setHasFixedSize(true);
                    } else {
                        mMoviesAdapter.setMovieData(mMoviesList);
                        mMoviesAdapter.notifyDataSetChanged();
                    }

                    int statusCode = response.code();
                    Log.d(LOG_TAG, "Response code: " + statusCode);

                } else {

                    // Hide the Progress Bar
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    int statusCode = response.code();
                    Log.d(LOG_TAG, "Response code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable t) {
                // Hide the Progress Bar
                mLoadingIndicator.setVisibility(View.INVISIBLE);

                // Show the error message for no Connectivity
                showErrorMessage();
                Log.e(LOG_TAG, t.toString());
            }
        });
    }


    private void loadFavoriteMovies() {

        // Check if the Adapter is null, if so - load data from the Loader,
        // if not - set the data from the Movie Adapter
        if (mMoviesAdapter == null) {
            mMoviesAdapter = new MoviesAdapter(getContext(), mMoviesList, this);
            mRecyclerGridView.setAdapter(mMoviesAdapter);
            mRecyclerGridView.setHasFixedSize(true);
            getActivity().getSupportLoaderManager().initLoader(ID_FAV_MOVIES_LOADER,
                    null, this);
        }
        else {
            mMoviesAdapter.setMovieData(mMoviesList);
            mMoviesAdapter.notifyDataSetChanged();
        }

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


    /**
     * Method that handles responses to clicks from the grid of movie posters
     *
     * @param movies Creates an object of Movies
     */
    @Override
    public void onClick(Movies movies) {
        mMovies = movies;
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(MOVIE_DETAILS_KEY, movies);
        getContext().startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        switch (id) {

            case ID_FAV_MOVIES_LOADER:

                // Define a projection that specifies the columns from the movies table
                String[] projection = {
                        FavMovieEntry.COLUMN_MOVIE_ID,
                        FavMovieEntry.COLUMN_MOVIE_TITLE,
                        FavMovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE,
                        FavMovieEntry.COLUMN_POSTER_PATH,
                        FavMovieEntry.COLUMN_BACKDROP_PATH,
                        FavMovieEntry.COLUMN_MOVIE_RATING,
                        FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                        FavMovieEntry.COLUMN_MOVIE_SYNOPSIS
                };

                // Execute the ContentProvider's query method on a background thread
                return new CursorLoader(getContext(),
                        FavMovieEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param cursor The cursor generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        Log.d(LOG_TAG, "onLoadFinished is called");

        if ((cursor == null) || (cursor.getCount() <= 0)) {
            return;
        }

        mMoviesList = new ArrayList<>();

        // The Movie Cursor is assigned to the new one
        mFavMoviesData = cursor;

        // Make sure the Cursor starts from the beginning when the device rotates
        // just moveToFirst() will ignore the first row in the db
        cursor.moveToPosition(-1);

        // Loop through each row in the Cursor and add a new Movies
        while (cursor.moveToNext()) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            mMoviesList.add(new Movies(
                    cursor.getInt(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_ID)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_BACKDROP_PATH)),
                    cursor.getDouble(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_RATING)),
                    cursor.getString(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_SYNOPSIS))
            ));
        }

        mMoviesAdapter.setMovieData(mMoviesList);
        mMoviesAdapter.notifyDataSetChanged();

        mMoviesAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset is called");

        mMoviesAdapter.swapCursor(null);
    }
}
