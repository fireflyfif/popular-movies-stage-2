package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Reviews;

import java.util.List;

/**
 * Adapter that feeds the RecyclerView that displays Movie reviews
 * Created by fifiv on 16/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private List<Reviews> mReviewList;


    public ReviewAdapter(Context context, List<Reviews> reviewsList) {
        mContext = context;
        mReviewList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.review_item, parent, false);

        ReviewViewHolder holder = new ReviewViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, final int position) {

        final Reviews reviews = mReviewList.get(position);

        holder.mReviewContent.setText(reviews.getContent());
        holder.mReviewAuthor.setText(reviews.getAuthor());
        holder.mReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewUrl = reviews.getUrl();
                Uri webPage = Uri.parse(reviewUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            }
        });
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

        final CardView mCardView;
        final TextView mReviewAuthor;
        final TextView mReviewContent;
        final TextView mReadMore;


        public ReviewViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.review_cardView);
            mReviewAuthor = itemView.findViewById(R.id.review_author);
            mReviewContent = itemView.findViewById(R.id.review_content);
            mReadMore = itemView.findViewById(R.id.read_more_tv);
        }
    }

    public void setReviewData(List<Reviews> reviewData) {
        mReviewList = reviewData;
        notifyDataSetChanged();
    }
}
