package com.example.android.popularmovies_stage2.utilities;


import com.example.android.popularmovies_stage2.model.Movie;
import com.example.android.popularmovies_stage2.model.Review;
import com.example.android.popularmovies_stage2.model.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtilities {

    public static ArrayList<Movie> parseMovies(String json) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(json);

            JSONArray jsonArray = jo.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                Movie movie = buildMovie(jsonArray.getJSONObject(i));
                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    private static Movie buildMovie(JSONObject jo) {

        try {
            Movie movie = new Movie();
            movie.setVote_count(jo.optInt("vote_count"));
            movie.setId(jo.optInt("id"));
            movie.setVideo(jo.optBoolean("video"));
            movie.setVote_average(jo.optDouble("vote_average"));
            movie.setTitle(jo.optString("title"));
            movie.setPopularity(jo.optDouble("popularity"));
            movie.setPoster_path(jo.optString("poster_path"));
            movie.setOriginal_language(jo.optString("original_language"));
            movie.setOriginal_title(jo.optString("original_title"));
            movie.setBackdrop_path(jo.optString("backdrop_path"));
            movie.setAdult(jo.optBoolean("adult"));
            movie.setOverview(jo.optString("overview"));
            movie.setRelease_date(jo.optString("release_date"));
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<Video> parseVideos(String json) {

        ArrayList<Video> videos = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(json);
            JSONArray jsonArray = jo.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                Video video = buildVideo(jsonArray.getJSONObject(i));
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videos;
    }

    private static Video buildVideo(JSONObject jo) {
        try {
            return new Video(
                    jo.optString("id"),
                    jo.optString("key"),
                    jo.optString("name"),
                    jo.optString("site"),
                    jo.optInt("size"),
                    jo.optString("type")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<Review> parseReviews(String json) {

        ArrayList<Review> reviews = new ArrayList<>();

        try {
            JSONObject jo = new JSONObject(json);

            JSONArray jsonArray = jo.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = buildReview(jsonArray.getJSONObject(i));
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private static Review buildReview(JSONObject jo) {
        try {
            return new Review(
                    jo.optString("id"),
                    jo.optString("url"),
                    jo.optString("author"),
                    jo.optString("content")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
