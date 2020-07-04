package com.dinislam.retrofit2rxjava2test.data;

import android.app.Application;
import android.util.Log;

import com.dinislam.retrofit2rxjava2test.api.ApiFactory;
import com.dinislam.retrofit2rxjava2test.api.Contract;
import com.dinislam.retrofit2rxjava2test.pojo.JSONResponse;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;
import com.dinislam.retrofit2rxjava2test.room.MovieDao;
import com.dinislam.retrofit2rxjava2test.room.MyViewModel;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyDataLoader {
    private Disposable disposable;
    private static int page = 1;
    private LoadDataAndDestroy loadDataAndDestroy;
    private MyViewModel myViewModel;

    public void loadData(boolean isItNewPages, String lang, String sortMethod, final boolean replace, String adult, String gte){
        if(isItNewPages)
            page++;
        if(replace)
            page = 1;

        ApiFactory factory = ApiFactory.getInstance();
        disposable = factory.getService().getResponse(lang, sortMethod, adult, Contract.PARAMS_INCLUDE_VIDEO, String.valueOf(page), gte)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONResponse>() {
                    @Override
                    public void accept(JSONResponse jsonResponse) throws Exception {
                        if(replace){
                            loadDataAndDestroy.setMoviesInAdapterWithReplace(jsonResponse.getMovies());
                            myViewModel.deleteAllMoviesFromDB();
                            myViewModel.addMovieList(jsonResponse.getMovies());
                        } else loadDataAndDestroy.setMoviesInAdapter (jsonResponse.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Den", Objects.requireNonNull(throwable.getMessage()) + "  " + throwable.getCause());

                        loadDataAndDestroy.setMoviesInAdapterWithReplace(myViewModel.getMovieList());
                    }
                });
    }

    public void myDispose(){
        disposable.dispose();
        page = 1;
    }

    public MyDataLoader(LoadDataAndDestroy loadDataAndDestroy, Application application) {
        this.loadDataAndDestroy = loadDataAndDestroy;
        myViewModel = MyViewModel.getInstance(application);
    }
}