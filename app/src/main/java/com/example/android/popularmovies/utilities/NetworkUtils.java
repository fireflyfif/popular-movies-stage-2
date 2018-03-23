package com.example.android.popularmovies.utilities;

import android.util.Log;

/**
 * Utility class that holds constants with base urls and methods that build string urls
 *
 * Created by fifiv on 06/02/2018.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();


    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String FILE_SIZE = "w342";

    private static final String FILE_SIZE_BIGGER = "w500";

    public static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/";

    public static final String MOVIE_PATH = "movie";

    public static final String API_KEY_PARAM = "api_key";

    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/";
    public static final String YOUTUBE_WATCH_PARAM = "watch?v=";

    public static final String YOUTUBE_IMG_URL = "http://img.youtube.com/vi/";

    //Full-sized image of the YouTube thumbnail
    public static final String YOUTUBE_JPG_PARAM = "/0.jpg";


    /**
     * Method that builds the poster URL
     * The poster path looks like this:
     * http://image.tmdb.org/t/p/w342//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
     *
     * @param posterPath Is a unique path key for every movie
     * @return String of this URL
     */
    public static String buildPosterPathUrl(String posterPath) {

        String posterPathUrlString = IMAGES_BASE_URL + FILE_SIZE + posterPath;
        Log.v(TAG, "Built URI for Poster: " + posterPathUrlString);

        return posterPathUrlString;
    }

    /**
     * Method that builds the path to the movie backdrop image URL (second image of the movie)
     *
     * @param backdropPath Is a unique path key for every movie
     * @return String of this URL
     */
    public static String buildPosterBackdropUrl(String backdropPath) {
        String backdropUrlString = IMAGES_BASE_URL + FILE_SIZE_BIGGER + backdropPath;
        Log.v(TAG, "Built URI for Backdrop: " + backdropUrlString);

        return backdropUrlString;
    }

    /**
     * Method that builds the path to the YouTube trailer URL
     * example: https://www.youtube.com/watch?v=enLIWGK9cC8
     *
     * @param videoKey Unique key for every movie
     * @return String of this URL
     */
    public static String buildYouTubeTrailerUrl(String videoKey) {
        String youTubeTrailerUrl = YOUTUBE_BASE_URL + YOUTUBE_WATCH_PARAM + videoKey;
        Log.v(TAG, "Build URI for trailer: " + youTubeTrailerUrl);

        return youTubeTrailerUrl;
    }

    /**
     * Method that builds the path to the YouTube thumbnail for every movie
     * example: https://img.youtube.com/vi/8ndhidEmUbI/0.jpg
     *
     * @param videoKey Unique key for every movie
     * @return String of this URL
     */
    public static String buildYouTubeThumbnailUrl(String videoKey) {

        String videoThumbnailUrl = YOUTUBE_IMG_URL + videoKey + YOUTUBE_JPG_PARAM;
        Log.v(TAG, "Built URI for video thumbnail: " + videoThumbnailUrl);

        return videoThumbnailUrl;
    }
}

