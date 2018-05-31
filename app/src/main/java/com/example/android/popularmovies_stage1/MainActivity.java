package com.example.android.popularmovies_stage1;

import com.example.android.popularmovies_stage1.utilities.NetworkUtilities;
import com.example.android.popularmovies_stage1.utilities.JsonUtilities;
import com.example.android.popularmovies_stage1.utilities.LoadJsonAsync;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.example.android.popularmovies_stage1.model.Movie;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.Menu;
import java.util.Arrays;




public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieClickListener, LoadJsonAsync.StringAsyncResponse {

    private final String SORT_ORDER_KEY = "sort_order";

    private SharedPreferences sharedPreferences;

    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        The actual key value is saved to shared preferences instead of the index
        In this way if order of options are changed or new items are added the saved preferences will not be affected
     */
    private String getSortOrder() {
        return sharedPreferences.getString(SORT_ORDER_KEY, getString(R.string.pref_sort_default_key));
    }

    private void setSortOrder(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_ORDER_KEY, key);
        editor.apply();
    }



    @Override
    public void onMovieClick(int index) {
        Intent movieDetail = new Intent(this, MovieDetailActivity.class);
        movieDetail.putExtra(getString(R.string.movie_detail_intent_key), movies.get(index));
        startActivity(movieDetail);
    }



    private void updateMovies(){

        MovieAdapter movieAdapter = new MovieAdapter(movies, this);

        LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
             layoutManager = new GridLayoutManager(this, 2);
        } else{
             layoutManager = new GridLayoutManager(this, 3);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);

    }


    private void refreshMovies(){
        new LoadJsonAsync(this).execute(
                NetworkUtilities.getUrl(getSortOrder(), this)
        );
    }

    @Override
    public void asyncProcessFinish(String res) {
        movies = JsonUtilities.parseMovies(res);
        updateMovies();
    }
}
