package com.ali.moviemvvmapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import com.ali.moviemvvmapplication.Adapters.MovieRecyclerView;
import com.ali.moviemvvmapplication.Adapters.OnMovieListener;
import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.request.Servicey;
import com.ali.moviemvvmapplication.response.MovieSearchResponse;
import com.ali.moviemvvmapplication.utils.Credentials;
import com.ali.moviemvvmapplication.utils.MovieApi;
import com.ali.moviemvvmapplication.viewModels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;


    private MovieListViewModel movieListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SearchView
        SetUpSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();
        movieListViewModel.searchMoviePop(1);

    }

    private void ObservePopularMovies() {
        movieListViewModel.getPopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                // Observing any data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        Log.v("Tag", "onChange : " + movieModel.getTitle());
                    }

                    movieRecyclerAdapter.setmMovies(movieModels);
                }
            }
        });
    }


    // Get data from searchView % Query Api to get Result
    private void SetUpSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Credentials.POPULAR = false;
            }
        });

    }


    private void ConfigureRecyclerView() {
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL , false));




        // Recycler View Pagination
        //  Loading next Page of Api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    // Here we display the next search result on the next page of api
                    movieListViewModel.searchNextPage();
                    //  Toast.makeText(MovieListActivity.this, "next called", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }


    // Observing Any data change
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                // Observing any data change
                if (movieModels != null) {
                    movieRecyclerAdapter.setmMovies(movieModels);
                }


            }
        });
    }


    @Override
    public void onMovieClick(int pos) {
        Toast.makeText(this, "Movie Click " + pos, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MovieDetilActivity.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(pos));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }


}