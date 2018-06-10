package com.example.android.popularmovies_stage2.database;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;
import android.util.Log;

import java.util.Arrays;

public class Converter {

    private final static String LOG_TAG = "Converter";

    @TypeConverter
    public static String toString(int[] array) {
        return Arrays.toString(array);
    }

    @TypeConverter
    public static int[] toIntArray(String stringArray) {
        if(stringArray.equals("null")){
            return new int[0];
        }else{
            String[] stringArr = stringArray
                    .replace("[","")
                    .replace("]","")
                    .split(",");

            int[] intArr = new int[stringArr.length];
            for(int i=0; i<stringArr.length; i++){
                try{
                    int num = Integer.parseInt(stringArr[i]);
                    intArr[i] = num;
                }catch(Exception e){
                    Log.d(LOG_TAG,"Error while parsing int");
                }
            }
            return intArr;
        }
    }

}
