package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.models.MovieDbResponse;
import com.example.android.popularmovies.models.ReviewsDbResponse;
import com.example.android.popularmovies.models.VideosDbResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fifiv on 07/03/2018.
 *
 * Create Retrofit Instance inside the Manager so that the the Retrofit Instance
 * is created only once throughout the lifetime of the app
 *
 * Link to tutorial: http://codingsonata.com/retrofit-tutorial-android-part-1-introduction/
 */
public class MovieDbApiManager {

    private static Retrofit sRetrofit = null;
    private static MovieDbApiManager apiManager;
    private static MovieDbInterface movieDbInterface;

    private MovieDbApiManager() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtils.MOVIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        movieDbInterface = sRetrofit.create(MovieDbInterface.class);
    }

    public static MovieDbApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new MovieDbApiManager();
        }

        return apiManager;
    }

    public void getMovies(String sortOrder, String apiKey, Callback<MovieDbResponse> callback) {

        Call<MovieDbResponse> movieCall = movieDbInterface.getMovies(sortOrder, apiKey);
        movieCall.enqueue(callback);
    }

    public void getTrailers(String movieId, String apiKey, Callback<VideosDbResponse> callback) {

        Call<VideosDbResponse> videoCall = movieDbInterface.getVideos(movieId, apiKey);
        videoCall.enqueue(callback);
    }

    public void getReviews(String movieId, String apiKey, Callback<ReviewsDbResponse> callback) {

        Call<ReviewsDbResponse> reviewCall = movieDbInterface.getReviews(movieId, apiKey);
        reviewCall.enqueue(callback);
    }
}
