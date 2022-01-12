package com.ali.moviemvvmapplication.viewModels;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.repositories.MovieRepository;
import com.ali.moviemvvmapplication.utils.Credentials;

import java.util.List;

public class MovieListViewModel extends ViewModel {


    private MovieRepository movieRepository;


    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPopular() {
        return movieRepository.getPop();
    }

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }


    // Calling Method in view-model
    public void searchMovieApi(String query , int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }


    // Calling Popular Method in view-model
    public void searchMoviePop(int pageNumber){
        Credentials.POPULAR = true;
        movieRepository.searchMoviePop(pageNumber);
    }


    public void searchNextPage() {
        movieRepository.searchNextPage();
    }


}
