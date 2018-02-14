package com.example.android.popular_movies_stage_1.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popular_movies_stage_1.Movies;
import com.example.android.popular_movies_stage_1.R;
import com.example.android.popular_movies_stage_1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by fifiv on 31/01/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private Context mContext;
    private List<Movies> mMoviesList;

    private static final String MOVIE_DETAILS_KEY = "movie_parcel";


    /**
     * Constructor for MoviesAdapter
     *
     * @param context  Get the context of the activity
     * @param movies   List of movies
     */
    public MoviesAdapter(Context context, List<Movies> movies) {
        mContext = context;
        mMoviesList = movies;
    }


    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public final ImageView moviePosterThumbnail;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);

            moviePosterThumbnail = itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            Movies currentMovie = mMoviesList.get(getAdapterPosition());
            intent.putExtra(MOVIE_DETAILS_KEY, currentMovie);
            mContext.startActivity(intent);
        }
    }


    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate
                (R.layout.movie_item, parent, false);

        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {

        Movies moviePoster = mMoviesList.get(position);
        String moviePosterPath = moviePoster.getPoster();

        Picasso.with(mContext)
                // Not sure if this will work
                // .load(moviePosterPath)
                .load(NetworkUtils.buildPosterPathUrl(moviePosterPath))
                // Call resize before centerCrop, so that it won't throw an exception
                .resize(250, 400)
                .centerCrop()
                .placeholder(R.drawable.movie_poster)
                .into(holder.moviePosterThumbnail);
    }

    @Override
    public int getItemCount() {
        if (mMoviesList == null) {
            return 0;
        }
        return mMoviesList.size();
    }

    /**
     * This method is used to set the movies data on a MoviesAdapter it there is one.
     *
     * @param movieData The new movie data to be displayed
     */
    public void setMovieData(List<Movies> movieData) {
        mMoviesList = movieData;
        notifyDataSetChanged();
    }
}
