package com.example.android.popularmovies_stage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies_stage2.model.Review;
import com.example.android.popularmovies_stage2.model.Video;
import com.example.android.popularmovies_stage2.utilities.JsonUtilities;
import com.example.android.popularmovies_stage2.utilities.LoadJsonAsync;
import com.example.android.popularmovies_stage2.utilities.NetworkUtilities;

import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {

    private MutableLiveData<List<Video>> videos;
    private MutableLiveData<List<Review>> reviews;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Video>> getVideos(int movie_id) {
        if (videos == null) {
            videos = new MutableLiveData<>();
            loadVideos(movie_id);
        }
        return videos;
    }

    private void loadVideos(int movie_id) {
        new LoadJsonAsync(new LoadJsonAsync.StringAsyncResponse() {
            public void asyncProcessFinish(String res) {
                videos.setValue(JsonUtilities.parseVideos(res));
            }
        }).execute(
                NetworkUtilities.getVideosUrl(movie_id)
        );
    }

    public LiveData<List<Review>> getReviews(int movie_id) {
        if (reviews == null) {
            reviews = new MutableLiveData<>();
            loadReviews(movie_id);
        }
        return reviews;
    }

    private void loadReviews(int movie_id) {
        new LoadJsonAsync(
                new LoadJsonAsync.StringAsyncResponse() {
                    @Override
                    public void asyncProcessFinish(String output) {
                        reviews.setValue(JsonUtilities.parseReviews(output));
                    }
                }
        ).execute(NetworkUtilities.getReviewsUrl(movie_id));
    }


}
