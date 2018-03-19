package com.example.android.popularmovies.utilities;

import android.app.Application;

/**
 * Created by fifiv on 16/03/2018.
 *
 * This Class will guarantee that the Retrofit instance is created only once
 */

public class MainApplication extends Application {

    public static MovieDbApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        apiManager = MovieDbApiManager.getInstance();
    }
}
