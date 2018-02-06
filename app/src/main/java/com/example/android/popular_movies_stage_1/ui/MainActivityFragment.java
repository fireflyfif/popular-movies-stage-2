package com.example.android.popular_movies_stage_1.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by fifiv on 02/02/2018.
 */

public class MainActivityFragment extends Fragment {

    private MoviesAdapter mMoviesAdapter;

    Movies[] tempMoviesList = {
            new Movies(R.drawable.movie_poster),
            new Movies(R.drawable.movie_poster),
            new Movies(R.drawable.movie_poster),
            new Movies(R.drawable.movie_poster),
            new Movies(R.drawable.movie_poster),
            new Movies(R.drawable.movie_poster),
    };

    // Mandatory empty constructor
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        GridView gridView = rootView.findViewById(R.id.thumbnail_grid_view);

        List<Movies> mMoviesList = new ArrayList<>();

        mMoviesAdapter = new MoviesAdapter(getContext(), Arrays.asList(tempMoviesList));
        gridView.setAdapter(mMoviesAdapter);

        return rootView;
    }
}
