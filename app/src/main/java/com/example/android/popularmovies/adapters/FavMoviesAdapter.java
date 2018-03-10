package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Movies;

import java.util.List;

/**
 * Created by fifiv on 10/03/2018.
 */

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavMoviesViewHolder> {

    // Class variables for the Cursor that holds favorite movies data and the Context
    private Context mContext;
    private Cursor mCursor;
    private List<Movies> mMoviesList;

    /**
     * Constructor for the FavMoviesAdapter that initializes the Context
     *
     * @param context the current Context
     */
    public FavMoviesAdapter(Context context) {
        mContext = context;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView
     *
     * @return A new FavMoviesViewHolder that holds the view for each task
     */
    @Override
    public FavMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_item, parent, false);

        return new FavMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavMoviesAdapter.FavMoviesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class FavMoviesViewHolder extends RecyclerView.ViewHolder {

        public FavMoviesViewHolder(View itemView) {
            super(itemView);
        }
    }

}
