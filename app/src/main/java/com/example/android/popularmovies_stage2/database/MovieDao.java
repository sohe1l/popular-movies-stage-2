package com.example.android.popularmovies_stage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popularmovies_stage2.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAll();

    @Insert
    void insert(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Movie movie);

    @Delete
    void delete(Movie movie);


//
//    @Update
//    void updateFavorite(int id, Boolean is_favorite);




}
