package com.example.android.popularmovies.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.FavMovieEntry;
import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE_DETAILS_KEY = "movie_parcel";

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static Movies sMovie;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;
    @BindView(R.id.fab_button)
    FloatingActionButton mFabButton;


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
                sMovie = receiveIntent.getParcelableExtra(MOVIE_DETAILS_KEY);

                // Display the current selected movie title on the Action Bar
                getSupportActionBar().setTitle(sMovie.getMovieTitle());

                populateUI(sMovie);
            }
        }

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
                Toast.makeText(DetailActivity.this, "Added to your favorites",
                        Toast.LENGTH_SHORT).show();
            }
        });
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

    public void addToFavorites() {

        // Create new content values object
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavMovieEntry.COLUMN_MOVIE_ID, sMovie.getMovieId());
        contentValues.put(FavMovieEntry.COLUMN_MOVIE_TITLE, sMovie.getMovieTitle());
        contentValues.put(FavMovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, sMovie.getMovieOriginalTitle());
        contentValues.put(FavMovieEntry.COLUMN_POSTER_PATH, sMovie.getPoster());
        contentValues.put(FavMovieEntry.COLUMN_BACKDROP_PATH, sMovie.getMovieBackdrop());
        contentValues.put(FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE, sMovie.getReleaseDate());
        contentValues.put(FavMovieEntry.COLUMN_MOVIE_RATING, sMovie.getVoteAverage());
        contentValues.put(FavMovieEntry.COLUMN_MOVIE_SYNOPSIS, sMovie.getPlotSynopsis());

        Uri uri;

        try {
           uri = getContentResolver().insert(FavMovieEntry.CONTENT_URI,
                    contentValues);
        } catch (IllegalArgumentException e) {
            uri = null;
            Log.v(LOG_TAG, e.toString());
        }

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
        }

        //finish();
    }
}
