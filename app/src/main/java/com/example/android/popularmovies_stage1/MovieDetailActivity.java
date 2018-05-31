package com.example.android.popularmovies_stage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies_stage1.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        /*  TODO for stage two use ButterKnife package
                http://jakewharton.github.io/butterknife/
                https://code.tutsplus.com/tutorials/quick-tip-using-butter-knife-to-inject-views-on-android--cms-23542
         */

        ImageView imgv_poster = (ImageView) findViewById(R.id.imgv_detail_poster);
        ImageView imgv_backdrop = (ImageView) findViewById(R.id.imgv_detail_backdrop);
        TextView tv_movie_title = (TextView) findViewById(R.id.movie_title_tv);
        TextView tv_release_date = (TextView) findViewById(R.id.movie_release_date_tv);
        TextView tv_vote_average = (TextView) findViewById(R.id.movie_vote_average_tv);
        TextView tv_overview = (TextView) findViewById(R.id.movie_overview_tv);


        Intent creatingIntent = getIntent();

        if(creatingIntent.hasExtra(getString(R.string.movie_detail_intent_key))){
            Movie movie = creatingIntent.getParcelableExtra(getString(R.string.movie_detail_intent_key));

            try{

                tv_movie_title.setText( movie.getTitle() );

                tv_release_date.setText( movie.getRelease_date() );
                tv_vote_average.setText( String.valueOf( movie.getVote_average()) );
                tv_overview.setText( movie.getOverview() );

                Picasso.with(this).load( Movie.POSTER_BASE_URL + movie.getPoster_path())
                        .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(imgv_poster);
                Picasso.with(this).load( Movie.BACKDROP_BASE_URL + movie.getBackdrop_path())
                        .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(imgv_backdrop);

                getSupportActionBar().setTitle(movie.getTitle());

            }catch(NullPointerException e){
                e.printStackTrace();
            }



        }


    }
}
