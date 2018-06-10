package com.example.android.popularmovies_stage2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.utilities.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements RecyclerItemClickListener{

    private final String SORT_ORDER_KEY = "sort_order";
    private SharedPreferences sharedPreferences;
    private List<Movie> movies;
    private Toolbar toolbar;

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        refreshMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected = item.getItemId();

        switch (selected) {
            case R.id.action_sort_popular:
                setSortOrder(getString(R.string.pref_sort_popular_key));
                refreshMovies();
                break;

            case R.id.action_sort_top_rated:
                setSortOrder(getString(R.string.pref_sort_top_rated_key));
                refreshMovies();
                break;

            case R.id.action_favorites:
                setSortOrder(getString(R.string.pref_sort_favorites_key));
                refreshMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getSortOrder() {
        return sharedPreferences.getString(SORT_ORDER_KEY, getString(R.string.pref_sort_default_key));
    }

    private void setSortOrder(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_ORDER_KEY, key);
        editor.apply();
    }

    private void updateMovies() {

        MovieAdapter movieAdapter = new MovieAdapter(movies, this);

        LayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 3);
        }
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(movieAdapter);
    }


    private void refreshMovies() {
        String sortOrder = getSortOrder();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (sortOrder.equals(getString(R.string.pref_sort_favorites_key))) {
            toolbar.setSubtitle(getString(R.string.pref_favorites_label));

            viewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> resMovies) {
                    movies = resMovies;
                    updateMovies();
                }
            });

        } else {
            if (sortOrder.equals(getString(R.string.pref_sort_popular_key))) {
                toolbar.setSubtitle(getString(R.string.pref_sort_popular_label));

                viewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> resMovies) {

                        movies = resMovies;
                        updateMovies();
                    }
                });

            } else if (sortOrder.equals(getString(R.string.pref_sort_top_rated_key))) {
                toolbar.setSubtitle(getString(R.string.pref_sort_top_rated_label));

                viewModel.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> resMovies) {

                        movies = resMovies;
                        updateMovies();
                    }
                });
            }

        }
    }


    @Override
    public void onRecyclerItemClicked(int index) {
        Intent movieDetail = new Intent(this, MovieDetailActivity.class);
        movieDetail.putExtra(getString(R.string.movie_detail_intent_key), movies.get(index));
        startActivity(movieDetail);
    }
}
