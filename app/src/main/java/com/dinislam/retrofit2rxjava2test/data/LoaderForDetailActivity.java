package com.dinislam.retrofit2rxjava2test.data;

import android.util.Log;

import com.dinislam.retrofit2rxjava2test.adapters.CommentsAdapter;
import com.dinislam.retrofit2rxjava2test.adapters.TrailersAdapter;
import com.dinislam.retrofit2rxjava2test.api.ApiFactory;
import com.dinislam.retrofit2rxjava2test.api.ApiService;
import com.dinislam.retrofit2rxjava2test.pojo.Comment;
import com.dinislam.retrofit2rxjava2test.pojo.JSONResponseReviews;
import com.dinislam.retrofit2rxjava2test.pojo.JSONResponseTrailers;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;
import com.dinislam.retrofit2rxjava2test.pojo.Trailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoaderForDetailActivity  {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService service;

    public void loadTrailers(Movie movie, final TrailersAdapter adapter){
        List<Trailer> trailers = new ArrayList<>();
        Disposable disposable = service.getTrailersResponse(movie.getId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONResponseTrailers>() {
                    @Override
                    public void accept(JSONResponseTrailers trailers) throws Exception {
                        adapter. setTrailers(trailers.getTrailers());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Den", Objects.requireNonNull(throwable.getMessage()) + "  " + throwable.getCause());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadComments(Movie movie, final CommentsAdapter commentsAdapter, int page){
        Disposable disposable1 = service.getCommentsResponse(Integer.toString(movie.getId()), Locale.getDefault().getLanguage(), Integer.toString(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONResponseReviews>() {
                    @Override
                    public void accept(JSONResponseReviews responseReviews) throws Exception {
                        commentsAdapter.setComments(responseReviews.getComments());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("Den", Objects.requireNonNull(throwable.getMessage()) + "  " + throwable.getCause());
                    }
                });
        compositeDisposable.add(disposable1);
    }

    public void terminateLoading(){
        compositeDisposable.dispose();
    }

    public LoaderForDetailActivity() {
        service = ApiFactory.getInstance().getService();
    }
}
