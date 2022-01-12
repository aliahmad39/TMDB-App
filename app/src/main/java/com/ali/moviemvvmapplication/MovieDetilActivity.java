package com.ali.moviemvvmapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ali.moviemvvmapplication.models.MovieModel;
import com.bumptech.glide.Glide;

public class MovieDetilActivity extends AppCompatActivity {

    private ImageView ivDetail;
    private TextView title , desc;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detil);

        ivDetail = findViewById(R.id.image_detail);
        title = findViewById(R.id.tv_title_detail);
        desc = findViewById(R.id.tv_desc_detail);
       ratingBar = findViewById(R.id.ratingBar);


       getDataFromIntent();


    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
           Log.v("Tag","incoming intent :"+movieModel.getTitle());
            title.setText(movieModel.getTitle());
            desc.setText(movieModel.getMovie_overview());
            ratingBar.setRating((movieModel.getVote_average())/2);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path())
                    .into(ivDetail);

        }
    }

    public void removeActivity(View view) {
        finish();
    }
}