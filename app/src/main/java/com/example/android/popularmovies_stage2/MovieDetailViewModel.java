package com.example.android.popularmovies_stage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.model.Video;
import com.example.android.popularmovies_stage2.utilities.JsonUtilities;
import com.example.android.popularmovies_stage2.utilities.LoadJsonAsync;
import com.example.android.popularmovies_stage2.utilities.NetworkUtilities;

import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {

    private MutableLiveData<List<Video>> videos;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Video>> getVideos(int movie_id){
        if(videos == null){
            videos = new MutableLiveData<>();
            loadVideos(movie_id);
        }
        return videos;
    }

    private void loadVideos(int movie_id){
        new LoadJsonAsync(new LoadJsonAsync.StringAsyncResponse() {
            public void asyncProcessFinish(String res) {
                Log.wtf("MovieVidelViewModel", "Async Videos finished");
                videos.setValue(JsonUtilities.parseVideos(res));
            }
        }).execute(
                NetworkUtilities.getVideosUrl(movie_id)
        );
    }


}
