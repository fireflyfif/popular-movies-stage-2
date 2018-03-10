package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.models.MovieDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fifiv on 07/03/2018.
 */

public interface MovieDbInterface {


    @GET("movie/{preference}")
    Call<MovieDbResponse> getMovies(@Path("preference") String preference,
                                    @Query("api_key") String ApiKey);


}
