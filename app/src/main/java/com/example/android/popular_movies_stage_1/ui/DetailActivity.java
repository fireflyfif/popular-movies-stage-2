package com.example.android.popular_movies_stage_1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.models.Movies;
import com.example.android.popular_movies_stage_1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE_DETAILS_KEY = "movie_parcel";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receiveIntent = this.getIntent();

        if (receiveIntent != null) {
            if (receiveIntent.hasExtra(MOVIE_DETAILS_KEY)) {
                Movies movies = receiveIntent.getParcelableExtra(MOVIE_DETAILS_KEY);

                // Display the current selected movie title on the Action Bar
                getSupportActionBar().setTitle(movies.getMovieTitle());

                populateUI(movies);
            }
        }
    }

    /**
     * Method that populates the backdrop image with the current movie poster
     *
     * @param movies creates a movie object
     */
    public void populateUI(Movies movies) {
        // Set title to the current Movie
        collapsingToolbarLayout.setTitle(movies.getMovieTitle());

        // Display the second poster image background
        Picasso.with(mMovieBackdropImage.getContext())
                .load(NetworkUtils.buildPosterBackdropUrl(movies.getMovieBackdrop()))
                .placeholder(R.drawable.movie_poster)
                .into(mMovieBackdropImage);
    }
}
