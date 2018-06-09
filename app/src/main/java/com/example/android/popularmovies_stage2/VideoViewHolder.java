package com.example.android.popularmovies_stage2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies_stage2.model.Video;
import com.example.android.popularmovies_stage2.utilities.RecyclerItemClickListener;

public class VideoViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

    private TextView videoText;
    final private RecyclerItemClickListener mClickListener;


    VideoViewHolder(View itemView, RecyclerItemClickListener clickListener) {
        super(itemView);
        videoText = itemView.findViewById(R.id.video_name);

        mClickListener = clickListener;

        itemView.setOnClickListener(this);
    }


    void bind(Video video){
        videoText.setText( video.getName() );
    }


    public void onClick(View v) {
        mClickListener.onRecyclerItemClicked( getAdapterPosition() );
    }
}
