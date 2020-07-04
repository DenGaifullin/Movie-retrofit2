package com.dinislam.retrofit2rxjava2test.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Ignore;
import androidx.room.Room;

import com.dinislam.retrofit2rxjava2test.pojo.Favorite;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyViewModel extends AndroidViewModel {
    private static MovieDatabase movieDatabase;
    private static MyViewModel myViewModel;

    private MyViewModel(@NonNull Application application) {
        super(application);
        movieDatabase = Room.databaseBuilder(application,
                MovieDatabase.class,
                "database")
//                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized MyViewModel getInstance(Application application) {
        if(myViewModel == null)
            myViewModel = new MyViewModel(application);
        return myViewModel;
    }

    // Add Movies
    public void addMovieList(List<Movie> movies){
        try {
            new AddMovieList().execute(movies).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class AddMovieList extends AsyncTask<List<Movie>, Void, Void> {
        @Override
        protected Void doInBackground(List<Movie>... lists) {
            movieDatabase.getMovieDao().addAllMovies(lists[0]);
            return null;
        }
    }

    // Delete all Movies from db
    public void deleteAllMoviesFromDB(){
        try {
            new DeleteAllMoviesFromDB().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class DeleteAllMoviesFromDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            movieDatabase.getMovieDao().deleteAllMovies();
            return null;
        }
    }

    // Get Movie list
    public List<Movie> getMovieList(){
        List<Movie> movies = new ArrayList<>();
        try {
            movies.addAll( new GetMovieList().execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }
    private static class GetMovieList extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return movieDatabase.getMovieDao().getAllMoviesFromDB();
        }
    }

    // Favorite
    // Add Favorite
    public void addFavoriteMovie(Favorite favorite){
        try {
            new AddFavoriteMovie().execute(favorite).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class AddFavoriteMovie extends AsyncTask<Favorite, Void, Void> {
        @Override
        protected Void doInBackground(Favorite... movie) {
            movieDatabase.getMovieDao().addFavoriteMovie(movie[0]);
            return null;
        }
    }

    // Get Favorite Movie list
    public List<Favorite> getFavoriteMovieList(){
        List<Favorite> movies = new ArrayList<>();
        try {
            movies.addAll( new GetFavoriteMovieList().execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }
    private static class GetFavoriteMovieList extends AsyncTask<Void, Void, List<Favorite>> {

        @Override
        protected List<Favorite> doInBackground(Void... voids) {
            return movieDatabase.getMovieDao().getFavoriteMovieList();
        }
    }

    // delete FavoriteMovie
    public void deleteFavoriteMovieById(Favorite favorite){
        try {
            new DeleteFavoriteMovie().execute(favorite).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static class DeleteFavoriteMovie extends AsyncTask<Favorite, Void, Void> {
        @Override
        protected Void doInBackground(Favorite... favorite) {
            movieDatabase.getMovieDao().deleteFavoriteMovie(favorite[0].getId());
            return null;
        }
    }

    // get Favorite movie by id
    public Favorite getFavoriteMovieById(int id){
        try {
            return new GetFavoriteMovie().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetFavoriteMovie extends AsyncTask<Integer, Void, Favorite> {
        @Override
        protected Favorite doInBackground(Integer... integers) {
            return movieDatabase.getMovieDao().getFavoriteMovie(integers[0]);
        }
    }
}