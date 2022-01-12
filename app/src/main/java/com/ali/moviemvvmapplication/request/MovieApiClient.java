package com.ali.moviemvvmapplication.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ali.moviemvvmapplication.AppExecutors;
import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.response.MovieSearchResponse;
import com.ali.moviemvvmapplication.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // live data for search
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;


    // Making Global Runnable
    private RetrieveMovieRunnable retrieveMovieRunnable;


    // live data for Popular Movies
    private MutableLiveData<List<MovieModel>> mMoviesPopular;

    // Making Global Popular Runnable
    private RetrieveMovieRunnablePop retrieveMovieRunnablePopular;


    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }


    public MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPopular() {
        return mMoviesPopular;
    }


    // this method is call through Activity classes to search movie
    public void searchMovieApi(String query, int pageNumber) {

        if (retrieveMovieRunnable != null) {
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(query, pageNumber);


        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call

                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }




    // this method is call through Activity classes to get Popular movie
    public void searchMoviesPop(int pageNumber) {

        if (retrieveMovieRunnablePopular != null) {
            retrieveMovieRunnablePopular = null;
        }

        retrieveMovieRunnablePopular = new RetrieveMovieRunnablePop(pageNumber);


        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMovieRunnablePopular);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call

                myHandler2.cancel(true);

            }
        }, 5000, TimeUnit.MILLISECONDS);


    }







    // Retreive data from rest Api by runnable class
    // We hava 2 types of queries : the ID & search Queries
    private class RetrieveMovieRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMovieRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the Response Object
            try {
                Response response = getMovies(query, pageNumber).execute();


                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());

                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: Used for background thread
                        // setValue: not for background thread

                        mMovies.postValue(list);
                    } else {
                        // this is for posting previous + next page movies

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);

                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        // Search Method / query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }

    }



    // Retreive data from rest Api by runnable class
    // We hava 2 types of queries : the ID & search Queries
    private class RetrieveMovieRunnablePop implements Runnable {


        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMovieRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the Response Object
            try {
                Response response = getMoviesPop(pageNumber).execute();


                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());

                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: Used for background thread
                        // setValue: not for background thread

                        mMoviesPopular.postValue(list);
                    } else {
                        // this is for posting previous + next page movies

                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);

                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMoviesPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }
        }

        // Search Method / query
        private Call<MovieSearchResponse> getMoviesPop(int pageNumber) {
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }


        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }

    }

}
