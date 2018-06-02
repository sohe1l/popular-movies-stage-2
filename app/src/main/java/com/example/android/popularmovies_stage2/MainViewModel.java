package com.example.android.popularmovies_stage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmovies_stage2.database.AppDatabase;
import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.utilities.JsonUtilities;
import com.example.android.popularmovies_stage2.utilities.LoadJsonAsync;
import com.example.android.popularmovies_stage2.utilities.NetworkUtilities;

import java.util.List;

public class MainViewModel extends AndroidViewModel
        implements LoadJsonAsync.StringAsyncResponse
{





    private List<Movie> topRatedMovies;
    private List<Movie> popularMovies;
    private LiveData<List<Movie>> favoriteMovies;


    public List<Movie> getTopRatedMovies() {

        if (topRatedMovies == null) {
            loadTopRatedMovies();
        }
        return topRatedMovies;
    }

    public List<Movie> getPopularMovies() {
        if(popularMovies == null){
            loadPopularMovies();
        }
        return popularMovies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if(favoriteMovies == null){
            loadFavoriteMovies();
        }
        return favoriteMovies;
    }




    private AppDatabase mDb;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(this.getApplication());
        //refreshMovies(R.string.pref_sort_favorites_key);
    }


    private void refreshMovies(int type){
        if(type == R.string.pref_sort_favorites_key){
            Log.wtf("WTF", "refreshing movies from view model");
            //movies = mDb.taskDao().getAll();

        }else{
            Log.wtf("WTF", "ELSE refreshing movies from view model");

            /*
            new LoadJsonAsync(this).execute(
                    NetworkUtilities.getUrl(sortOrder, context)
            );
            */
        }

    }

    private void loadTopRatedMovies(){
        new LoadJsonAsync(this).execute(
                NetworkUtilities.getTopRatedUrl()
        );
    }

    private void loadPopularMovies(){
        new LoadJsonAsync(this).execute(
                NetworkUtilities.getPopularUrl()
        );
    }

    private void loadFavoriteMovies(){
        favoriteMovies = mDb.taskDao().getAll();
    }

    @Override
    public void asyncProcessFinish(String res) {
        topRatedMovies = JsonUtilities.parseMovies(res);
        popularMovies = JsonUtilities.parseMovies(res);
    }






}
