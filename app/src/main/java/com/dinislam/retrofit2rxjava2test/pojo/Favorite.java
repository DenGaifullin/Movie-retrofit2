package com.dinislam.retrofit2rxjava2test.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.dinislam.retrofit2rxjava2test.util.Converter;

@Entity(tableName = "favorite_movies")
@TypeConverters(value = Converter.class)
public class Favorite extends Movie {
    @Ignore
    public Favorite(Movie movie) {
        super(movie.getPopularity(), movie.getVoteCount(), movie.isVideo(), movie.getPosterPath(),
                movie.getId(), movie.isAdult(), movie.getBackdropPath(), movie.getOriginalLanguage(),
                movie.getOriginalTitle(), movie.getGenreIds(), movie.getTitle(), movie.getVoteAverage(),
                movie.getOverview(), movie.getReleaseDate());
    }

    public Favorite() {
    }
}
