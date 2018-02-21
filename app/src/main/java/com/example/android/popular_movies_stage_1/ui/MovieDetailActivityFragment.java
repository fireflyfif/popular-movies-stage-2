package com.example.android.popular_movies_stage_1.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by fifiv on 14/02/2018.
 */

public class MovieDetailActivityFragment extends Fragment {

    private ImageView mMoviePoster;
    private ImageView mMovieBackdropImage;
    private TextView mMovieSynopsis;
    private TextView mMovieReleaseDate;
    private TextView mMovieRatingValue;
    private RatingBar mMovieRatingBar;

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_DETAILS_KEY = "movie_parcel";

    /**
     * Mandatory empty constructor for the fragment manager
     */
    public MovieDetailActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_activity_detail_movie, container, false);

        mMoviePoster = rootView.findViewById(R.id.movie_poster_detail);
        mMovieBackdropImage = rootView.findViewById(R.id.movie_backdrop_image);
        mMovieSynopsis = rootView.findViewById(R.id.movie_synopsis);
        mMovieReleaseDate = rootView.findViewById(R.id.movie_release_date);
        mMovieRatingValue = rootView.findViewById(R.id.movie_vote);
        mMovieRatingBar = rootView.findViewById(R.id.rating_bar);

        Intent receiveIntent = getActivity().getIntent();

        if (receiveIntent != null) {
            if (receiveIntent.hasExtra(MOVIE_DETAILS_KEY)){
                Movies movies = receiveIntent.getParcelableExtra(MOVIE_DETAILS_KEY);

                populateUI(movies);
            }
        }

        return rootView;
    }

    /**
     *
     * @param movies
     */
    public void populateUI(Movies movies) {

        // Display the current selected movie title on the Action Bar
        getActivity().setTitle(movies.getMovieTitle());

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

        mMovieSynopsis.setText(movies.getPlotSynopsis());

        mMovieRatingValue.setText(String.valueOf(movies.getVoteAverage() + getString(R.string.vote_range)));

        mMovieRatingBar.setRating((float) movies.getVoteAverage());

        mMovieReleaseDate.setText(movies.getReleaseDate());

    }
}
