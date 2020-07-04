package com.dinislam.retrofit2rxjava2test.screens.favoriteMovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.dinislam.retrofit2rxjava2test.R;
import com.dinislam.retrofit2rxjava2test.adapters.FavoriteMovieAdapter;
import com.dinislam.retrofit2rxjava2test.pojo.Favorite;
import com.dinislam.retrofit2rxjava2test.room.MyViewModel;
import com.dinislam.retrofit2rxjava2test.screens.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        List<Favorite> list = null;
        list = MyViewModel.getInstance(getApplication()).getFavoriteMovieList();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewForFavoriteMovies);
        FavoriteMovieAdapter adapter = new FavoriteMovieAdapter(list);
        adapter.setFavoriteAdepterOnClickListener(new FavoriteMovieAdapter.FavoriteAdepterOnClickListener() {
            @Override
            public void onClickListener(int position) {
                startActivity(new Intent(FavoriteMovieActivity.this, DetailActivity.class)
                        .putExtra("ID", position)
                        .putExtra("isFavorite", true));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        FavoriteMovieAdapter.setList(MyViewModel.getInstance(getApplication()).getFavoriteMovieList());
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        Log.i("Dend", "" +width);
        return Math.max((width / 200) > 3 ? width / 200 - 1 : width / 200, 2);
    }
}
