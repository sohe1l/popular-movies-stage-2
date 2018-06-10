package com.example.android.popularmovies_stage2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies_stage2.model.Review;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView authorText;
    private final TextView reviewText;

    ReviewViewHolder(View itemView) {
        super(itemView);
        authorText = itemView.findViewById(R.id.tv_review_author);
        reviewText = itemView.findViewById(R.id.tv_review_body);
    }

    void bind(Review review) {
        authorText.setText(review.getAuthor());
        reviewText.setText(review.getContent());
    }


}
