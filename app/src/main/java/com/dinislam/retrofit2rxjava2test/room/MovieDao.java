package com.dinislam.retrofit2rxjava2test.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dinislam.retrofit2rxjava2test.pojo.Favorite;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    // Movies
    @Query("SELECT * FROM movies")
    List<Movie> getAllMoviesFromDB();

    @Insert
    void addAllMovies(List<Movie> movies);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    // Favorite Movies
    @Insert
    void addFavoriteMovie(Favorite favorite);

    @Query("SELECT * FROM favorite_movies")
    List<Favorite> getFavoriteMovieList();


    @Query("DELETE FROM favorite_movies where id = :id")
    void deleteFavoriteMovie(int id);

    @Query("SELECT * FROM favorite_movies where id = :id")
    Favorite getFavoriteMovie(int id);
}
