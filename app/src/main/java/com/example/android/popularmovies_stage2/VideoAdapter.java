package com.example.android.popularmovies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies_stage2.model.Video;
import com.example.android.popularmovies_stage2.utilities.RecyclerItemClickListener;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    final private RecyclerItemClickListener mClickListener;
    private List<Video> videos;

    VideoAdapter(List<Video> videos, RecyclerItemClickListener clickListener) {
        this.videos = videos;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.video_item, parent, false);

        return new VideoViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        if (videos.size() > position) {
            holder.bind(videos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (videos == null) {
            return 0;
        } else {
            return videos.size();
        }
    }
}
