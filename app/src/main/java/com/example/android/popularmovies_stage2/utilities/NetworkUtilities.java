package com.example.android.popularmovies_stage2.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies_stage2.BuildConfig;
import com.example.android.popularmovies_stage2.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {

    private static final String YOUTUBE_VIDEO_PATH = "https://www.youtube.com/watch";
    private static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private static final String TAG = NetworkUtilities.class.getSimpleName();
    private static final String API_TOP_RATED_PATH = "movie/top_rated/";
    private static final String API_REVIEWS_PATH = "movie/{id}/reviews";
    private static final String API_VIDEOS_PATH = "movie/{id}/videos";
    private static final String API_POPULAR_PATH = "movie/popular/";
    private static final String YOUTUBE_VIDEO_PARAM = "v";
    private static final String API_PARAM = "api_key";
    private static final int TIMEOUT = 5000;


    public static URL getUrl(String sort_order_key, Context context) {
        if (sort_order_key.equals(context.getString(R.string.pref_sort_popular_key)))
            return NetworkUtilities.getPopularUrl();

        else if (sort_order_key.equals(context.getString(R.string.pref_sort_top_rated_key)))
            return NetworkUtilities.getTopRatedUrl();

        else
            return null;
    }

    public static URL getPopularUrl() {
        Uri uri = Uri.parse(API_BASE_URL).buildUpon()
                .appendEncodedPath(API_POPULAR_PATH)
                .appendQueryParameter(API_PARAM, getApiKey())
                .build();

        return buildURLFromURI(uri);
    }

    public static URL getTopRatedUrl() {
        Uri uri = Uri.parse(API_BASE_URL).buildUpon()
                .appendEncodedPath(API_TOP_RATED_PATH)
                .appendQueryParameter(API_PARAM, getApiKey())
                .build();

        return buildURLFromURI(uri);
    }


    public static URL getVideosUrl(int movie_id) {
        Uri uri = Uri.parse(API_BASE_URL).buildUpon()
                .appendEncodedPath(API_VIDEOS_PATH.replace("{id}", String.valueOf(movie_id)))
                .appendQueryParameter(API_PARAM, getApiKey())
                .build();

        return buildURLFromURI(uri);
    }


    public static URL getReviewsUrl(int movie_id) {
        Uri uri = Uri.parse(API_BASE_URL).buildUpon()
                .appendEncodedPath(API_REVIEWS_PATH.replace("{id}", String.valueOf(movie_id)))
                .appendQueryParameter(API_PARAM, getApiKey())
                .build();

        return buildURLFromURI(uri);
    }


    public static Uri getYoutubeUri(String video_id) {
        return Uri.parse(YOUTUBE_VIDEO_PATH).buildUpon()
                .appendQueryParameter(YOUTUBE_VIDEO_PARAM, video_id)
                .build();
    }


    // ref: sunshine app
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setConnectTimeout(TIMEOUT);
        urlConnection.setReadTimeout(TIMEOUT);

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }


    /*
    Return the API key from the gradle.properties
    Add MoviedbAPIKey = "...." in  gradle.properties
     */
    private static String getApiKey() {
        return BuildConfig.MoviedbAPIKey;
    }


    private static URL buildURLFromURI(Uri uri) {
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
