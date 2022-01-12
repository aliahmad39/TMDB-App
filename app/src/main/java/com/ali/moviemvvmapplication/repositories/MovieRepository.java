package com.ali.moviemvvmapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    // This class is Repository


    private static MovieRepository instance;

    private MovieApiClient movieApiClient;


    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }


    public MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPop() {
        return movieApiClient.getMoviesPopular();
    }


    // Calling the Method
    public void searchMovieApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query, pageNumber);
    }


    public void searchNextPage() {
        mPageNumber++;
        movieApiClient.searchMovieApi(mQuery , mPageNumber);
    }


    // Calling the Popular Method
    public void searchMoviePop(int pageNumber) {
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop( pageNumber);
    }


//    public void searchNextPage() {
//        mPageNumber++;
//        movieApiClient.searchMovieApi(mQuery , mPageNumber);
//    }



}
