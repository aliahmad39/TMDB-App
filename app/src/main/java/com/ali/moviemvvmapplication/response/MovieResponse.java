package com.ali.moviemvvmapplication.response;

import com.ali.moviemvvmapplication.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {


    @SerializedName("result")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }





}
