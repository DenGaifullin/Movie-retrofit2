package com.dinislam.retrofit2rxjava2test.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String convertToString(List<Integer> genreIds){
        return new Gson().toJson(genreIds);
    }

    @TypeConverter
    public List<Integer> convertToList(String genreIds){
        Gson gson = new Gson();
        ArrayList arrayList = gson.fromJson(genreIds, ArrayList.class);
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (Object o : arrayList) {
            integerArrayList.add(gson.fromJson(o.toString(), Integer.class));
        }
        return integerArrayList;
    }
}