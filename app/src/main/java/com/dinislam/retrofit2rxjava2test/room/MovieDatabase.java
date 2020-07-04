package com.dinislam.retrofit2rxjava2test.room;

import androidx.room.Database;

import com.dinislam.retrofit2rxjava2test.pojo.Favorite;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;

@Database(entities = {Movie.class, Favorite.class}, version = 3, exportSchema = false)
public abstract class MovieDatabase extends androidx.room.RoomDatabase {
    public abstract MovieDao getMovieDao();
}
