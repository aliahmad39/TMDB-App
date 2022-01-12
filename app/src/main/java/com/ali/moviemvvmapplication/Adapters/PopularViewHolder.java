package com.ali.moviemvvmapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.moviemvvmapplication.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //  TextView title,releaseDate , duration;
    ImageView imageView2;
    RatingBar ratingBar2;

    OnMovieListener onMovieListener;

    public PopularViewHolder(@NonNull View itemView , OnMovieListener onMovieListener) {
        super(itemView);

//        title = itemView.findViewById(R.id.movie_title);
//        releaseDate = itemView.findViewById(R.id.movie_category);
//        duration = itemView.findViewById(R.id.movie_duration);



        imageView2 = itemView.findViewById(R.id.movie_img2);
        ratingBar2 = itemView.findViewById(R.id.rating_bar2);
        itemView.setOnClickListener(this);

        this.onMovieListener = onMovieListener;

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }


}
