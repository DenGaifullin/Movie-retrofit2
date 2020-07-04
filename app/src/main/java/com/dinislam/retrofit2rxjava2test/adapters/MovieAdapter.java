package com.dinislam.retrofit2rxjava2test.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinislam.retrofit2rxjava2test.R;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private static List<Movie> movies;
    public OnReachEnd onReachEnd;
    public static OnClickPoster onClickPoster;
    public interface OnReachEnd {
        void onReachEnd();
    }
    public interface OnClickPoster {
        void onClick(int position);
    }

    public MovieAdapter(List<Movie> movies) {
        MovieAdapter.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolder holder, int position) {
        Picasso.get()
                .load(String.format("https://image.tmdb.org/t/p/w500%s", movies.get(position).getPosterPath()))
                .placeholder(R.drawable.ic_place_holder)
                .into(holder.imageView);
        if(movies.size() - 4 == position){
            onReachEnd.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_moviePoster);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPoster.onClick(getAdapterPosition());
                }
            });
        }
    }

    public static List<Movie> getMovies() {
        return movies;
    }

    public void addMovies(List<Movie> movies) {
        MovieAdapter.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public void replaceMovies(List<Movie> movies) {
        MovieAdapter.movies.clear();
        MovieAdapter.movies.addAll(movies);
        notifyDataSetChanged();
    }
}
