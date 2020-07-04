package com.dinislam.retrofit2rxjava2test.api;

import com.dinislam.retrofit2rxjava2test.pojo.JSONResponse;
import com.dinislam.retrofit2rxjava2test.pojo.JSONResponseReviews;
import com.dinislam.retrofit2rxjava2test.pojo.JSONResponseTrailers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //https://api.themoviedb.org/3/movie/419704/reviews?api_key=92dbe6a858afaa7b3114964b2eadafda&language=en-US&page=1

    @GET("3/discover/movie?api_key=92dbe6a858afaa7b3114964b2eadafda")
    Observable<JSONResponse> getResponse(
                                         @Query("language") String lang,
                                         @Query("sort_by") String sortedBy,
                                         @Query("include_adult") String includeAdult,
                                         @Query("include_video") String includeVideo,
                                         @Query("page") String page,
                                         @Query("vote_count.gte") String gte
    );

    @GET("3/movie/{id}/videos?api_key=92dbe6a858afaa7b3114964b2eadafda")
    Observable<JSONResponseTrailers> getTrailersResponse(@Path("id") int groupId, @Query("language") String lang);

    @GET("3/movie/{id}/reviews?api_key=92dbe6a858afaa7b3114964b2eadafda")
    Observable<JSONResponseReviews> getCommentsResponse(@Path("id") String groupId, @Query("language") String lang, @Query("page") String page);
}
