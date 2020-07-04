package com.dinislam.retrofit2rxjava2test.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponseTrailers {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

}

