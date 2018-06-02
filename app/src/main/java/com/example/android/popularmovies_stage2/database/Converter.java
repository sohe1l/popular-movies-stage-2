package com.example.android.popularmovies_stage2.database;

import android.arch.persistence.room.TypeConverter;

public class Converter {

    @TypeConverter
    public static String toString(int[] array){
        return "2,3,4";
    }

    @TypeConverter
    public static int[] toIntArray(String stringArray){
        int[] arr = {1,2,3,4};
        return arr;
    }

}
