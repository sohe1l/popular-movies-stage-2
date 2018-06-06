package com.example.android.popularmovies_stage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.popularmovies_stage2.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final ImageView imageView;
    private final Context context;
    final private MovieAdapter.MovieClickListener mOnMovieClickListener;


    MovieViewHolder(View itemView, Context context, MovieAdapter.MovieClickListener clickListener) {
        super(itemView);

        this.context = context;
        mOnMovieClickListener = clickListener;

        imageView = (ImageView) itemView.findViewById(R.id.imgv_movie);

        itemView.setOnClickListener(this);
    }

    void bind(Movie movie) {
        Picasso.with(context).load(Movie.POSTER_BASE_URL + movie.getPoster_path())
                .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        int clickedPos = getAdapterPosition();
        mOnMovieClickListener.onMovieClick(clickedPos);
    }
}