package com.example.android.popular_movies_stage_1.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.models.Movies;
import com.example.android.popular_movies_stage_1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifiv on 14/02/2018.
 */

public class MovieDetailActivityFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_DETAILS_KEY = "movie_parcel";
    @BindView(R.id.movie_poster_detail)
    ImageView mMoviePoster;
    @BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;
    @BindView(R.id.movie_original_title)
    TextView mMovieOriginalTitle;
    @BindView(R.id.movie_synopsis)
    TextView mMovieSynopsis;
    @BindView(R.id.movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.movie_vote)
    TextView mMovieRatingValue;
    @BindView(R.id.rating_bar)
    RatingBar mMovieRatingBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * Mandatory empty constructor for the fragment manager
     */
    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_activity_detail_movie, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        ButterKnife.bind(this, rootView);

        Intent receiveIntent = getActivity().getIntent();

        if (receiveIntent != null) {
            if (receiveIntent.hasExtra(MOVIE_DETAILS_KEY)) {
                Movies movies = receiveIntent.getParcelableExtra(MOVIE_DETAILS_KEY);

                populateUI(movies);
            }
        }

        return rootView;
    }

    /**
     * Method that populates all UI views with the current movie
     *
     * @param movies creates a movie object
     */
    public void populateUI(Movies movies) {

        // Display the current selected movie title on the Action Bar
        getActivity().setTitle(movies.getMovieTitle());

        // Set title to the current Movie
        collapsingToolbarLayout.setTitle(movies.getMovieTitle());

        // Set original to the current Movie
        mMovieOriginalTitle.setText(movies.getMovieOriginalTitle());

        // Display the poster image
        Picasso.with(mMoviePoster.getContext())
                .load(NetworkUtils.buildPosterPathUrl(movies.getPoster()))
                .placeholder(R.drawable.movie_poster)
                .into(mMoviePoster);

        // Display the second poster image background
        Picasso.with(mMovieBackdropImage.getContext())
                .load(NetworkUtils.buildPosterBackdropUrl(movies.getMovieBackdrop()))
                .placeholder(R.drawable.movie_poster)
                .into(mMovieBackdropImage);

        // Set synopsis to the current Movie
        mMovieSynopsis.setText(movies.getPlotSynopsis());

        // Set the average rating to the current Movie
        mMovieRatingValue.setText(String.valueOf(movies.getVoteAverage()));
        mMovieRatingBar.setRating((float) movies.getVoteAverage());

        // Set release date to the current Movie
        mMovieReleaseDate.setText(movies.getReleaseDate());
    }
}
