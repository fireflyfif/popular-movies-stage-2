package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Reviews;
import com.example.android.popularmovies.models.Videos;

import java.util.List;

/**
 * Created by fifiv on 16/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Reviews> mReviewList;

    private int mExpandedPosition = -1;

    @Override
    public void onClick(View view) {
        ReviewViewHolder holder = (ReviewViewHolder) view.getTag();
        //String string = String.valueOf(mReviewList.get(holder.getPosition()));

        // Check for an expanded view, collapse if you find one
        if (mExpandedPosition >= 0) {
            int prev = mExpandedPosition;
            notifyItemChanged(prev);
        }

        // Set the current position to expanded
        mExpandedPosition = holder.getPosition();
        notifyItemChanged(mExpandedPosition);


    }

    //private final ReviewsAdapterOnClickHandler mClickHandler;

    public interface ReviewsAdapterOnClickHandler {
        void onClick(Reviews reviews);
    }

    public ReviewAdapter(Context context, List<Reviews> reviewsList) {
        mContext = context;
        mReviewList = reviewsList;
        //mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.review_item, parent, false);

        ReviewViewHolder holder = new ReviewViewHolder(view);

        holder.itemView.setOnClickListener(ReviewAdapter.this);
        holder.itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, final int position) {

        Reviews reviews = mReviewList.get(position);

        holder.mReviewContent.setText(reviews.getContent());
        holder.mReviewAuthor.setText(reviews.getAuthor());

        /*if (position == mExpandedPosition) {
            holder.expandableLayout.setVisibility(View.VISIBLE);
        } else {
            holder.expandableLayout.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        if (mReviewList == null) {
            return 0;
        }

        Log.d("DetailActivity", "Size of ReviewList: " + mReviewList.size());
        return mReviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        LinearLayout expandableLayout;
        final TextView mReviewAuthor;
        final TextView mReviewContent;
        final ImageView mArrow;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            expandableLayout = itemView.findViewById(R.id.review_item_layout);
            mReviewAuthor = itemView.findViewById(R.id.review_author);
            mReviewContent = itemView.findViewById(R.id.review_content);
            mArrow = itemView.findViewById(R.id.collapsing_arrow);
        }
    }

    public void setReviewData(List<Reviews> reviewData) {
        mReviewList = reviewData;
        notifyDataSetChanged();
    }
}
