package com.example.android.popularmovies_stage1.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class LoadJsonAsync extends AsyncTask<URL, Void, String> {



    public interface StringAsyncResponse {
        void asyncProcessFinish(String output);
    }

    private StringAsyncResponse delegate = null;

    public LoadJsonAsync(StringAsyncResponse delegate){
        this.delegate = delegate;
    }


    @Override
    protected String doInBackground(URL... urls) {
        String jsonResult = "";
        try{
            jsonResult = NetworkUtilities.getResponseFromHttpUrl(urls[0]);

        }catch(IOException e){
            e.printStackTrace();
        }
        return jsonResult;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.asyncProcessFinish(s);
    }





}
