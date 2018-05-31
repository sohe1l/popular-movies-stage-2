package com.example.android.popularmovies_stage1.utilities;


import com.example.android.popularmovies_stage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtilities {

    public static ArrayList<Movie> parseMovies(String json){

        ArrayList<Movie> movies = new ArrayList<>();

        try{
            JSONObject jo = new JSONObject(json);

            JSONArray jsonArray = jo.getJSONArray("results");

            for(int i=0; i<jsonArray.length(); i++){
                Movie movie = buildMovie(jsonArray.getJSONObject(i));
                movies.add(movie);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return movies;

    }

    private static Movie buildMovie(JSONObject jo){

        try{
            Movie movie = new Movie();

            movie.setVote_count( jo.optInt("vote_count"));

            movie.setId(jo.optInt("id"));

            movie.setVideo(jo.optBoolean("video"));
            movie.setVote_average(jo.optDouble("vote_average"));
            movie.setTitle(jo.optString("title"));
            movie.setPopularity(jo.optDouble("popularity"));
            movie.setPoster_path(jo.optString("poster_path"));
            movie.setOriginal_language(jo.optString("original_language"));

            movie.setOriginal_title(jo.optString("original_title"));

            // movie.setGenre_ids();

            movie.setBackdrop_path(jo.optString("backdrop_path"));
            movie.setAdult(jo.optBoolean("adult"));

            movie.setOverview(jo.optString("overview"));
            movie.setRelease_date(jo.optString("release_date"));

            return movie;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
