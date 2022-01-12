package com.ali.moviemvvmapplication.utils;

import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // search for movies
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );



    // search with id
    //https://api.themoviedb.org/3/movie/550?api_key=26b8e2bc29145f4b92afb7dd82287966
    @GET("3/movie/{movie_id}")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );


    // Get Popular Movie
    //https://api.themoviedb.org/3/movie/550?api_key=26b8e2bc29145f4b92afb7dd82287966
    @GET("3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
