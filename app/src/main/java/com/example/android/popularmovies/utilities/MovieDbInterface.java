package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.models.MovieDbResponse;
import com.example.android.popularmovies.models.ReviewsDbResponse;
import com.example.android.popularmovies.models.VideosDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The MovieDb Interface with the endpoints for each call
 *
 * Created by fifiv on 07/03/2018.
 */

public interface MovieDbInterface {


    @GET("movie/{preference}")
    Call<MovieDbResponse> getMovies(@Path("preference") String preference,
                                    @Query("api_key") String ApiKey);

    @GET("movie/{id}/videos")
    Call<VideosDbResponse> getVideos(@Path("id") String movieId,
                                     @Query("api_key") String ApiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsDbResponse> getReviews(@Path("id") String movieId,
                                       @Query("api_key") String ApiKey);

}
