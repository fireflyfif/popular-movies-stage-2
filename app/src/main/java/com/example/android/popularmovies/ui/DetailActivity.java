package com.example.android.popularmovies.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.FavMovieEntry;
import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.models.Videos;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private static final int FAV_TAG = 0;
    private static final int NOT_FAV_TAG = 1;

    private static final String MOVIE_DETAILS_KEY = "movie_parcel";
    private static final String SAVE_STATE_IS_FAV = "is_favorite";

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static Movies sMovie;
    public Videos mVideos;
    private String mMovieId;

    private Uri mCurrentMovieUri;

    private boolean mIsFavMovie;

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
                mMovieId = String.valueOf(sMovie.getMovieId());

                // Display the current selected movie title on the Action Bar
                getSupportActionBar().setTitle(sMovie.getMovieTitle());

                populateUI(sMovie);
            }
        }

        if (savedInstanceState != null) {
            mIsFavMovie = savedInstanceState.getBoolean(SAVE_STATE_IS_FAV);
        }

        // Set icons of the FAB button
        setFabIcons();

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFavButton(view);
            }
        });
    }

    private void clickFavButton(View view) {
        int tagValue = (int) view.getTag();

        switch (tagValue) {
            case (FAV_TAG):
                removeFromFavorites();
                mFabButton.setTag(NOT_FAV_TAG);
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));

                Toast.makeText(DetailActivity.this, "Removed from favorites",
                        Toast.LENGTH_SHORT).show();
                break;
            case (NOT_FAV_TAG):
                addToFavorites();
                mFabButton.setTag(FAV_TAG);
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));

                Toast.makeText(DetailActivity.this, "Added to favorites",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                mFabButton.setTag(NOT_FAV_TAG);
                mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_STATE_IS_FAV, mIsFavMovie);
    }

    /**
     * Method that populates the backdrop image with the current movie poster
     *
     * @param movies creates a movie object
     */
    public void populateUI(Movies movies) {
        // Set title to the current Movie
        collapsingToolbarLayout.setTitle(movies.getMovieTitle());

        String backdropIdString = movies.getMovieBackdrop();
        String backdropUrlString = NetworkUtils.buildPosterBackdropUrl(backdropIdString);

        // Display the second poster image background
        Picasso.with(mMovieBackdropImage.getContext())
                .load(backdropUrlString)
                .placeholder(R.drawable.movie_poster)
                .error(R.drawable.movie_poster)
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

        try {
           mCurrentMovieUri = getContentResolver().insert(FavMovieEntry.CONTENT_URI,
                    contentValues);
        } catch (IllegalArgumentException e) {
            mCurrentMovieUri = null;
            Log.v(LOG_TAG, e.toString());
        }

        if (mCurrentMovieUri != null) {
            isAddedToFavorites();
        }

        //finish();
    }

    private void removeFromFavorites() {
        int rowsDeleted;

        if (mCurrentMovieUri != null) {
            rowsDeleted = getContentResolver().delete(
                    mCurrentMovieUri,
                    null,
                    null);
        }
    }

    private boolean isAddedToFavorites() {
        boolean isFavorite = false;

        String[] projection = {FavMovieEntry.COLUMN_MOVIE_ID};
        String selection = FavMovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[]{
                String.valueOf(sMovie.getMovieId())};

        Cursor cursor = this.getContentResolver().query(
                FavMovieEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                isFavorite = true;
                long currentIndex = cursor.getLong(cursor.getColumnIndex(FavMovieEntry.COLUMN_MOVIE_ID));
                mCurrentMovieUri = ContentUris.withAppendedId(FavMovieEntry.CONTENT_URI, currentIndex);
            } else {
                isFavorite = false;
                //setFabIcons();
                mCurrentMovieUri = null;
            }
            cursor.close();
        }

        return isFavorite;
    }

    private void setFabIcons() {

        if (isAddedToFavorites()) {
            mFabButton.setTag(FAV_TAG);
            mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        } else {
            mFabButton.setTag(NOT_FAV_TAG);
            mFabButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_share:
                Intent shareIntent = createShareMovieIntent();
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareMovieIntent() {

        // TODO: Create an Intent to share the trailer URL
        // Hard-coded video key to the movie Spirited Away
        String trailerSpiritedAwayUrl = "ByXuk9QqQkk";

        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, "Check out this movie trailer: ");

        String trailerUrl = NetworkUtils.buildYouTubeTrailerUrl(trailerSpiritedAwayUrl);
        shareIntent.putExtra(Intent.EXTRA_TEXT, trailerUrl);

        Log.d(LOG_TAG, "Trailer URL: " + trailerUrl);

        return shareIntent;
    }
}
