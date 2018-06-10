package com.example.android.popularmovies_stage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies_stage2.database.AppDatabase;
import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.utilities.JsonUtilities;
import com.example.android.popularmovies_stage2.utilities.LoadJsonAsync;
import com.example.android.popularmovies_stage2.utilities.NetworkUtilities;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final AppDatabase mDb;
    private MutableLiveData<List<Movie>> topRatedMovies;
    private MutableLiveData<List<Movie>> popularMovies;
    private LiveData<List<Movie>> favoriteMovies;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mDb = AppDatabase.getInstance(this.getApplication().getApplicationContext());
    }

    public LiveData<List<Movie>> getTopRatedMovies() {

        if (topRatedMovies == null) {
            topRatedMovies = new MutableLiveData<>();
            loadTopRatedMovies();
        }
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        if (popularMovies == null) {
            popularMovies = new MutableLiveData<>();
            loadPopularMovies();
        }
        return popularMovies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if (favoriteMovies == null) {
            loadFavoriteMovies();
        }
        return favoriteMovies;
    }

    private void loadTopRatedMovies() {
        new LoadJsonAsync(new LoadJsonAsync.StringAsyncResponse() {
            public void asyncProcessFinish(String res) {
                topRatedMovies.setValue(JsonUtilities.parseMovies(res));
            }
        }).execute(
                NetworkUtilities.getTopRatedUrl()
        );
    }

    private void loadPopularMovies() {
        new LoadJsonAsync(new LoadJsonAsync.StringAsyncResponse() {
            public void asyncProcessFinish(String res) {
                popularMovies.setValue(JsonUtilities.parseMovies(res));
            }
        }).execute(
                NetworkUtilities.getPopularUrl()
        );
    }

    private void loadFavoriteMovies() {
        favoriteMovies = mDb.taskDao().getAll();
    }

}
