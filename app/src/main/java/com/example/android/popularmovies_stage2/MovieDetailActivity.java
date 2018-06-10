package com.example.android.popularmovies_stage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies_stage2.database.AppDatabase;
import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.model.Review;
import com.example.android.popularmovies_stage2.model.Video;
import com.example.android.popularmovies_stage2.utilities.NetworkUtilities;
import com.example.android.popularmovies_stage2.utilities.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements RecyclerItemClickListener {

    Boolean isFavorite = false;
    int star_on_res_id;
    int star_off_res_id;
    private AppDatabase mDb;
    private Movie movie;
    private List<Video> videos;
    private List<Review> reviews;

    @BindView(R.id.imgv_detail_poster) ImageView imgv_poster;
    @BindView(R.id.imgv_detail_backdrop) ImageView imgv_backdrop;
    @BindView(R.id.movie_title_tv) TextView tv_movie_title;

    @BindView(R.id.movie_release_date_tv) TextView tv_release_date;
    @BindView(R.id.movie_vote_average_tv) TextView tv_vote_average;
    @BindView(R.id.movie_overview_tv) TextView tv_overview;
    @BindView(R.id.tv_reviews_title) TextView tv_reviews_title;

    @BindView(R.id.fav_iv) ImageView imgv_fav;

    @BindView(R.id.rv_videos) RecyclerView rvVideos;
    @BindView(R.id.rv_reviews) RecyclerView rvReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(this);

        star_on_res_id = getResources().getIdentifier("android:drawable/btn_star_big_on", null, null);
        star_off_res_id = getResources().getIdentifier("android:drawable/btn_star_big_off", null, null);

        imgv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavClicked();
            }
        });

        Intent creatingIntent = getIntent();

        if (creatingIntent.hasExtra(getString(R.string.movie_detail_intent_key))) {
            movie = creatingIntent.getParcelableExtra(getString(R.string.movie_detail_intent_key));

            try {
                tv_movie_title.setText(movie.getTitle());

                tv_release_date.setText(movie.getRelease_date());
                tv_vote_average.setText(String.valueOf(movie.getVote_average()));
                tv_overview.setText(movie.getOverview());

                Picasso.with(this).load(Movie.POSTER_BASE_URL + movie.getPoster_path())
                        .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(imgv_poster);
                Picasso.with(this).load(Movie.BACKDROP_BASE_URL + movie.getBackdrop_path())
                        .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(imgv_backdrop);


                LiveData<Movie> movie_row = mDb.taskDao().getMovie(movie.getId());
                movie_row.observe(this, new Observer<Movie>() {
                    @Override
                    public void onChanged(@Nullable Movie movie) {
                        if (movie != null) {
                            isFavorite = true;
                            imgv_fav.setImageResource(star_on_res_id);
                        } else {
                            isFavorite = false;
                            imgv_fav.setImageResource(star_off_res_id);
                        }
                    }
                });

                //load videos
                MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

                viewModel.getVideos(movie.getId()).observe(this, new Observer<List<Video>>() {
                    @Override
                    public void onChanged(@Nullable List<Video> res) {
                        videos = res;
                        updateVideos();
                    }
                });

                //load reviews
                viewModel.getReviews(movie.getId()).observe(this, new Observer<List<Review>>() {
                    @Override
                    public void onChanged(@Nullable List<Review> res) {
                        reviews = res;
                        updateReviews();
                    }
                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateVideos() {
        VideoAdapter videoAdapter = new VideoAdapter(videos, this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        rvVideos.setLayoutManager(layoutManager);
        rvVideos.setHasFixedSize(true);
        rvVideos.setAdapter(videoAdapter);
    }

    private void updateReviews() {
        if(reviews == null || reviews.size() == 0){
            tv_reviews_title.setVisibility(View.INVISIBLE);
        }else{
            ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            rvReviews.setLayoutManager(layoutManager);
            rvReviews.setHasFixedSize(true);
            rvReviews.setAdapter(reviewAdapter);
        }
    }

    private void onFavClicked() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFavorite) {
                    mDb.taskDao().delete(movie);
                } else {
                    mDb.taskDao().insert(movie);
                }
            }
        });
    }

    @Override
    public void onRecyclerItemClicked(int index) {
        Video selectedVideo = videos.get(index);
        Uri youtubeUri = NetworkUtilities.getYoutubeUri(selectedVideo.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
