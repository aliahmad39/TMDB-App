package com.ali.moviemvvmapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.moviemvvmapplication.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  //  TextView title,releaseDate , duration;
    ImageView imageView;
    RatingBar ratingBar;

    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView , OnMovieListener onMovieListener) {
        super(itemView);

//        title = itemView.findViewById(R.id.movie_title);
//        releaseDate = itemView.findViewById(R.id.movie_category);
//        duration = itemView.findViewById(R.id.movie_duration);



        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        itemView.setOnClickListener(this);

        this.onMovieListener = onMovieListener;

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
