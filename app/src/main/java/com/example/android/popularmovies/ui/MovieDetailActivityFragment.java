package com.example.android.popularmovies.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.adapters.VideoAdapter;
import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.models.Reviews;
import com.example.android.popularmovies.models.ReviewsDbResponse;
import com.example.android.popularmovies.models.Videos;
import com.example.android.popularmovies.models.VideosDbResponse;
import com.example.android.popularmovies.utilities.MainApplication;
import com.example.android.popularmovies.utilities.MovieDbApiManager;
import com.example.android.popularmovies.utilities.MovieDbInterface;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

/**
 * Created by fifiv on 14/02/2018.
 */

public class MovieDetailActivityFragment extends Fragment implements VideoAdapter.VideoAdapterOnClickHandler {


    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_DETAILS_KEY = "movie_parcel";
    private static final String SAVE_STATE_IS_FAV = "is_favorite";

    private boolean mIsFavMovie;

    private List<Videos> mVideoList;
    private VideoAdapter mVidAdapter;

    private List<Reviews> mReviewList;
    private ReviewAdapter mReviewAdapter;

    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.movie_poster_detail)
    ImageView mMoviePoster;
    /*@BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;*/
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
    @BindView(R.id.genres)
    TextView mMovieGenres;

    // Trailer Views
    @BindView(R.id.video_cardView)
    View videoView;
    @BindView(R.id.loading_indicator_trailers)
    ProgressBar mTrailerPB;
    @BindView(R.id.movie_trailer_rv)
    RecyclerView mTrailerRecyclerView;
    @BindView(R.id.error_message_videos)
    TextView mErrorMessageTrailers;


    // Review Views
    @BindView(R.id.review_cardView)
    View reviewView;
    @BindView(R.id.loading_indicator_review)
    ProgressBar mReviewPB;
    @BindView(R.id.movie_review_rv)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.error_message_review)
    TextView mErrorMessage;

    /**
     * Mandatory empty constructor for the fragment manager
     */
    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIsFavMovie = savedInstanceState.getBoolean(SAVE_STATE_IS_FAV);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_STATE_IS_FAV, mIsFavMovie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_activity_detail_movie, container, false);

        ButterKnife.bind(this, rootView);

        // Set the Trailers in Recycler View
        mVideoList = new ArrayList<>();

        // Set the Reviews in Recycler View
        mReviewList = new ArrayList<>();

        mTrailerPB.setVisibility(View.VISIBLE);
        mReviewPB.setVisibility(View.VISIBLE);


        Intent receiveIntent = getActivity().getIntent();

        if (receiveIntent != null) {

            if (receiveIntent.hasExtra(MOVIE_DETAILS_KEY)) {
                Movies movies = receiveIntent.getParcelableExtra(MOVIE_DETAILS_KEY);
                String movieId = String.valueOf(movies.getMovieId());

                populateUI(movies);
                loadTrailers(movieId);
                loadReviews(movieId);
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
        //collapsingToolbarLayout.setTitle(movies.getMovieTitle());

        // Set original to the current Movie
        mMovieOriginalTitle.setText(movies.getMovieOriginalTitle());

        String movieIdString = movies.getPoster();

        String posterPathUrlString = NetworkUtils.buildPosterPathUrl(movieIdString);
        Log.d(LOG_TAG, "Poster Url: " + posterPathUrlString);

        // Display the poster image
        Picasso.with(mMoviePoster.getContext())
                .load(posterPathUrlString)
                .placeholder(R.drawable.movie_poster)
                .error(R.drawable.movie_poster)
                .into(mMoviePoster);

        // Set synopsis to the current Movie
        mMovieSynopsis.setText(movies.getPlotSynopsis());

        // Set the average rating to the current Movie
        mMovieRatingValue.setText(valueOf(movies.getVoteAverage()));
        mMovieRatingBar.setRating((float) movies.getVoteAverage());

        // Set release date to the current Movie
        mMovieReleaseDate.setText(movies.getReleaseDate());

        // Set genres to the current movie
        /*if (mIsFavMovie) {
            mMovieGenres.setText("Some genres here");
        } else {
            mMovieGenres.setText(movies.getMovieGenres(movies.getGenreIds()));
        }*/

    }

    private void loadTrailers(String movieId) {

        // Print the URL to the Logs
//        HttpUrl urlPrint = movieDbInterface.getMovies(movieId, BuildConfig.API_KEY).request().url();
//        Log.d(LOG_TAG, "Print URL: " + urlPrint.toString());

        MainApplication.apiManager.getTrailers(movieId, BuildConfig.API_KEY,
                new Callback<VideosDbResponse>() {

                    @Override
                    public void onResponse(Call<VideosDbResponse> call, Response<VideosDbResponse> response) {
                        if (response.isSuccessful()) {
                            // Hide the Progress Bar
                            mTrailerPB.setVisibility(View.INVISIBLE);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                    LinearLayoutManager.HORIZONTAL, false);
                            mTrailerRecyclerView.setLayoutManager(layoutManager);

                            // Display the trailers
                            mVideoList = response.body().getResults();
                            Log.d(LOG_TAG, "Number of trailers: " + mVideoList.size());

                            if (mVideoList.size() == 0) {
                                mErrorMessageTrailers.setText("No Trailers for this movie");
                                mErrorMessageTrailers.setVisibility(View.VISIBLE);
                            }

                            if (mVidAdapter == null) {
                                mVidAdapter = new VideoAdapter(getActivity(), mVideoList,
                                        MovieDetailActivityFragment.this);
                                mTrailerRecyclerView.setAdapter(mVidAdapter);
                                mTrailerRecyclerView.setHasFixedSize(true);
                            } else {
                                mVidAdapter.setVideoData(mVideoList);
                                mVidAdapter.notifyDataSetChanged();
                            }

                            int statusCode = response.code();
                            Log.d(LOG_TAG, "Response code of Trailers: " + statusCode);

                        } else {
                            // Hide the Progress Bar
                            mTrailerPB.setVisibility(View.INVISIBLE);

                            int statusCode = response.code();
                            Log.d(LOG_TAG, "Response code of Trailers: " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<VideosDbResponse> call, Throwable t) {
                        // Hide the Progress Bar
                        mTrailerPB.setVisibility(View.INVISIBLE);

                        // Show error message for no connectivity
                        showErrorMessage();
                        Log.e(LOG_TAG, t.toString());
                    }
                });
    }

    @Override
    public void onClick(Videos videos) {

        String trailerUrl = NetworkUtils.YOUTUBE_BASE_URL + NetworkUtils.YOUTUBE_WATCH_PARAM +
                videos.getKey();

        Intent videoIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(trailerUrl));

        if (videoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(videoIntent);
        }
    }

    private void loadReviews(String movieId) {

        MainApplication.apiManager.getReviews(movieId, BuildConfig.API_KEY,
                new Callback<ReviewsDbResponse>() {

                    @Override
                    public void onResponse(Call<ReviewsDbResponse> call, Response<ReviewsDbResponse> response) {

                        if (response.isSuccessful()) {
                            // Hide the Progress Bar
                            mReviewPB.setVisibility(View.INVISIBLE);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                    LinearLayoutManager.HORIZONTAL, false);
                            mReviewRecyclerView.setLayoutManager(layoutManager);

                            // Display the trailers
                            mReviewList = response.body().getResults();
                            Log.d(LOG_TAG, "Number of reviews: " + mReviewList.size());

                            if (mReviewList.size() == 0) {
                                mErrorMessage.setText("No Reviews for this movie");
                                mErrorMessage.setVisibility(View.VISIBLE);
                            }

                            if (mReviewAdapter == null) {
                                mReviewAdapter = new ReviewAdapter(getActivity(), mReviewList);
                                mReviewRecyclerView.setAdapter(mReviewAdapter);
                                mReviewRecyclerView.setHasFixedSize(true);
                            } else {
                                mReviewAdapter.setReviewData(mReviewList);
                                mReviewAdapter.notifyDataSetChanged();
                            }

                            int statusCode = response.code();
                            Log.d(LOG_TAG, "Response code of Reviews: " + statusCode);

                        } else {
                            // Hide the Progress Bar
                            mReviewPB.setVisibility(View.INVISIBLE);

                            int statusCode = response.code();
                            Log.d(LOG_TAG, "Response code of Reviews: " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewsDbResponse> call, Throwable t) {
                        // Hide the Progress Bar
                        mReviewPB.setVisibility(View.INVISIBLE);

                        // Show error message for no connectivity
                        showErrorMessage();
                        Log.e(LOG_TAG, t.toString());
                    }
                });
    }


    /**
     * Method that shows error message when there is a problem fetching the data or
     * there is no internet connection
     */
    private void showErrorMessage() {
        // Hide the currently visible data
        mTrailerRecyclerView.setVisibility(View.INVISIBLE);

        // Show the error message
        mErrorMessage.setVisibility(View.VISIBLE);
    }
}
