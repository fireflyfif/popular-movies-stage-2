package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.models.Movies;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fifiv on 31/01/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    // Class variable for the Cursor that holds task data
    private Cursor mCursor;

    private Context mContext;
    private List<Movies> mMoviesList;

    final private MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movies movies);
    }


    /**
     * Constructor for MoviesAdapter
     *
     * @param context      Get the context of the activity
     * @param movies       List of movies
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MoviesAdapter(Context context, List<Movies> movies, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mMoviesList = movies;
        mClickHandler = clickHandler;
    }


    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public final ImageView moviePosterThumbnail;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

            moviePosterThumbnail = itemView.findViewById(R.id.movie_image);
        }

        @Override
        public void onClick(View view) {
            Movies currentMovie = mMoviesList.get(getAdapterPosition());
            mClickHandler.onClick(currentMovie);
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
                .load(NetworkUtils.buildPosterPathUrl(moviePosterPath))
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
     * This method is used to set the movies data on a MoviesAdapter if there is one.
     *
     * @param movieData The new movie data to be displayed
     */
    public void setMovieData(List<Movies> movieData) {
        mMoviesList = movieData;

        notifyDataSetChanged();
    }

    public Cursor swapCursor(Cursor cursor) {
        // Check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == cursor) {
            // If it's the same, nothing has changed
            return null;
        }

        // Assign new value to the old cursor, the same as:
        // mCursor = cursor
        Cursor temp = mCursor;
        this.mCursor = cursor;

        // Check if this is a valid cursor, then update the cursor
        if (cursor != null) {
            this.notifyDataSetChanged();
        }

        // Return the new cursor
        return temp;
    }
}
