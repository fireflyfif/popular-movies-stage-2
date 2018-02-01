package com.example.android.popular_movies_stage_1.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popular_movies_stage_1.Movies;

import java.util.List;

/**
 * Created by fifiv on 31/01/2018.
 */

public class MoviesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movies> mMoviesList;


    /**
     * Constructor of the adapter class
     *
     * @param context
     * @param moviesList
     */
    public MoviesAdapter(Context context, List<Movies> moviesList) {
        mContext = context;
        mMoviesList = moviesList;
    }

    @Override
    public int getCount() {
        return mMoviesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Create a new Image View for each item references by the adapter
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView movieImage;

        if (convertView == null) {
            movieImage = new ImageView(mContext);
            movieImage.setAdjustViewBounds(true);
            movieImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            movieImage.setPadding(8,8,8,8);
        } else {
            movieImage = (ImageView) convertView;
        }

        return movieImage;
    }
}
