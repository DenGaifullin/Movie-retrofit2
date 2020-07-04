package com.dinislam.retrofit2rxjava2test.data;

import com.dinislam.retrofit2rxjava2test.pojo.Movie;

import java.util.List;

public interface LoadDataAndDestroy{
    void destroyActivity();
    void setMoviesInAdapter(List<Movie> list);
    void setMoviesInAdapterWithReplace(List<Movie> list);
}