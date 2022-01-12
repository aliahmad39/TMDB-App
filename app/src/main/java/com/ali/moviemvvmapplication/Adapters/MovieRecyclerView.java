package com.ali.moviemvvmapplication.Adapters;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.moviemvvmapplication.R;
import com.ali.moviemvvmapplication.models.MovieModel;
import com.ali.moviemvvmapplication.utils.Credentials;
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;




    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if(viewType == DISPLAY_SEARCH){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);

            return new MovieViewHolder(view , onMovieListener);
        }else{

           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout,parent,false);

            return new PopularViewHolder(view , onMovieListener);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType == DISPLAY_SEARCH){

            // vote average is over 10  and rating bar is over 5 star so: divide by 2
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(i).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mMovies.get(i).getPoster_path())
                    .into(   ((MovieViewHolder)holder).imageView);

        }else{
            // vote average is over 10  and rating bar is over 5 star so: divide by 2
            ((PopularViewHolder)holder).ratingBar2.setRating((mMovies.get(i).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+mMovies.get(i).getPoster_path())
                    .into(   ((PopularViewHolder)holder).imageView2);


        }


    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR){
            return DISPLAY_POP;
        }else{
            return DISPLAY_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int pos) {
        if(mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(pos);
            }
        }
        return null;
    }


}
