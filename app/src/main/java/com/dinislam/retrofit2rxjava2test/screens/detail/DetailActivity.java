package com.dinislam.retrofit2rxjava2test.screens.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinislam.retrofit2rxjava2test.R;
import com.dinislam.retrofit2rxjava2test.adapters.CommentsAdapter;
import com.dinislam.retrofit2rxjava2test.adapters.FavoriteMovieAdapter;
import com.dinislam.retrofit2rxjava2test.adapters.MovieAdapter;
import com.dinislam.retrofit2rxjava2test.adapters.TrailersAdapter;
import com.dinislam.retrofit2rxjava2test.data.LoaderForDetailActivity;
import com.dinislam.retrofit2rxjava2test.pojo.Comment;
import com.dinislam.retrofit2rxjava2test.pojo.Favorite;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;
import com.dinislam.retrofit2rxjava2test.pojo.Trailer;
import com.dinislam.retrofit2rxjava2test.room.MyViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.text_view_title_overview)
    TextView titleOverview;
    @BindView(R.id.textView_origin_title_overview) TextView originalTitleOverview;
    @BindView(R.id.textView_rating_overview) TextView ratingOverview;
    @BindView(R.id.textView_release_date_overview) TextView releaseDate;
    @BindView(R.id.text_view_description_overview) TextView descriptionOverview;
    @BindView(R.id.imageView)
    ImageView imageViewOncreate;
    private LoaderForDetailActivity loader;
    private MyViewModel myViewModel;
    private Favorite favorite;
    private Movie movie;

    private boolean isFavoriteActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detale);
        ButterKnife.bind(this);
        myViewModel = MyViewModel.getInstance(getApplication());
        Objects.requireNonNull(getSupportActionBar()).hide();

//        getIntent().getExtras();
        if(getIntent().getBooleanExtra("isFavorite", false)){
            isFavoriteActivity = true;
            movie = (Movie) FavoriteMovieAdapter.getList().get(getIntent().getIntExtra("ID", 0));
        } else {
            movie = (Movie) MovieAdapter.getMovies().get(getIntent().getIntExtra("ID", 0));
            isFavoriteActivity = false;
        }

        favorite = new Favorite(movie);

        Favorite savedMovie = myViewModel.getFavoriteMovieById(movie.getId());
        if(savedMovie != null){
            imageViewOncreate.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_favorite_));
        }

        titleOverview.setText(movie.getTitle());
        originalTitleOverview.setText(movie.getOriginalTitle());
        ratingOverview.setText(Double.toString(movie.getVoteAverage()));
        releaseDate.setText(movie.getReleaseDate());
        descriptionOverview.setText(movie.getOverview());

        String imgPath = String.format("https://image.tmdb.org/t/p/w500%s", movie.getPosterPath());

        imgPath = imgPath.replace("w500", "original");
        Log.i("Den", imgPath);
        ImageView poster = findViewById(R.id.image_view_movie_poster_detail_activity);
        Picasso.get ()
                .load(imgPath)
                .placeholder(R.drawable.ic_place_holder)
                .into(poster);

        // ЗАГРУЗКА ТРЕЙЛЕРОВ
        final List<Trailer> list = new ArrayList<>();
        final TrailersAdapter adapter = new TrailersAdapter(list);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewForTrailers);
        adapter.setTrailerClickListener(new TrailersAdapter.TrailerClickListener() {
            @Override
            public void onClick(String key) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://www.youtube.com/watch?v=%s", key)));
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loader = new LoaderForDetailActivity();
        loader.loadTrailers(movie, adapter);

        // Загрузка комментариев
        List<Comment> comments = new ArrayList<>();
        final CommentsAdapter commentsAdapter = new CommentsAdapter(comments);

        RecyclerView commentsRecyclerView = findViewById(R.id.recyclerViewForComments);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentsAdapter);
        commentsRecyclerView.setNestedScrollingEnabled(false);
        loader.loadComments(movie, commentsAdapter, 1);
        loader.loadComments(movie, commentsAdapter, 2);

    }

    public void onClick(View view) throws ExecutionException, InterruptedException {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        Drawable.ConstantState currentState = drawable.getConstantState();
        Drawable.ConstantState state = getResources().getDrawable(R.drawable.ic_delete_favorite_).getConstantState();

        if(currentState !=null && currentState.equals(state)){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_favorite_));
            myViewModel.addFavoriteMovie(favorite);
            Toast.makeText(this, "Добавлено в избранное ", Toast.LENGTH_SHORT).show();
        } else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete_favorite_));
            myViewModel.deleteFavoriteMovieById(favorite);
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();

            if (isFavoriteActivity) {
                FavoriteMovieAdapter.setList(myViewModel.getFavoriteMovieList());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loader.terminateLoading();
    }
}

