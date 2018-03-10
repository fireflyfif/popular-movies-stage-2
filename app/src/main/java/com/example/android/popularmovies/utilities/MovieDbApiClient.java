package com.example.android.popularmovies.utilities;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fifiv on 07/03/2018.
 */

public class MovieDbApiClient {

    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() throws SecurityException {

        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtils.MOVIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
