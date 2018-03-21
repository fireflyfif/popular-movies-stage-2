package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Videos;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fifiv on 15/03/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<Videos> mVideosList;

    final private VideoAdapterOnClickHandler mClickHandler;

    public interface VideoAdapterOnClickHandler {
        void onClick(Videos videos);
    }

    public VideoAdapter(Context context, List<Videos> movieTrailers, VideoAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mVideosList = movieTrailers;
        mClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate
                (R.layout.video_item, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        Videos videos = mVideosList.get(position);
        String videoThumbnailKey = videos.getKey();

        String videoThumbnailUrl = NetworkUtils.buildYouTubeThumbnailUrl(videoThumbnailKey);
        Log.d("DetailActivity", "Video Thumbnail Url: " + videoThumbnailUrl);

        Picasso.with(mContext)
                .load(videoThumbnailUrl)
                .placeholder(R.drawable.movie_poster)
                .error(R.drawable.movie_poster)
                .into(holder.mVideoThumbnail);

        holder.mVideoTitle.setText(videos.getName());
    }

    @Override
    public int getItemCount() {
        if (mVideosList == null) {
            return 0;
        }
        Log.d("DetailActivity", "Size of VideoList: " + mVideosList.size());
        return mVideosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mVideoThumbnail;
        private TextView mVideoTitle;

        private VideoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mVideoThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
            mVideoTitle = itemView.findViewById(R.id.trailer_title);
        }

        @Override
        public void onClick(View view) {
            Videos currentVideos = mVideosList.get(getAdapterPosition());
            mClickHandler.onClick(currentVideos);

        }
    }

    public void setVideoData(List<Videos> videoData) {
        mVideosList = videoData;
        notifyDataSetChanged();
    }
}
