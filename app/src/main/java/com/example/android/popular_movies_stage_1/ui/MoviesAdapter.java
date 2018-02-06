package com.example.android.popular_movies_stage_1.ui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by fifiv on 31/01/2018.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {

    private Context mContext;
    private List<Movies> mMoviesList;


    private static final String MOVIES_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String FILE_SIZE = "w185";
    private static final String TEMP_POSTER_PATH = "/coss7RgL0NH6g4fC2s5atvf3dFO.jpg";

    /**
     * Constructor of the adapter class
     *
     * @param context
     * @param moviesList
     */
    public MoviesAdapter(Context context, List<Movies> moviesList) {

        super(context, 0, moviesList);
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

        Movies movies = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

//            movieImage = new ImageView(mContext);
//            movieImage.setAdjustViewBounds(true);
//            movieImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        ImageView movieImage = convertView.findViewById(R.id.movie_image);
        movieImage.setImageResource(movies.getIntPoster());
        //String url = MOVIES_BASE_URL + IMAGE_SIZE + movies.getPoster();
//            String url = MOVIES_BASE_URL + FILE_SIZE + TEMP_POSTER_PATH;
//            Picasso.with(mContext)
//                    .load(url)
//                    .centerCrop()
//                    .into(movieImage);


        return movieImage;
    }
}
