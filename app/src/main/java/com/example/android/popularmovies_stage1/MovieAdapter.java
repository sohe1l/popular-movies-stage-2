package com.example.android.popularmovies_stage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies_stage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    final private MovieClickListener mOnMovieClickListener;

    private ArrayList<Movie> movies;


    MovieAdapter(ArrayList<Movie> movies, MovieClickListener listener){
        mOnMovieClickListener = listener;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view, context, mOnMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if(movies.size() > position){
            holder.bind(movies.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface MovieClickListener{
        void onMovieClick(int index);
    }

}
