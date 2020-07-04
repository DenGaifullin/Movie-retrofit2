package com.dinislam.retrofit2rxjava2test.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.dinislam.retrofit2rxjava2test.R;
import com.dinislam.retrofit2rxjava2test.adapters.MovieAdapter;
import com.dinislam.retrofit2rxjava2test.api.Contract;
import com.dinislam.retrofit2rxjava2test.data.LoadDataAndDestroy;
import com.dinislam.retrofit2rxjava2test.data.MyDataLoader;
import com.dinislam.retrofit2rxjava2test.pojo.Movie;
import com.dinislam.retrofit2rxjava2test.screens.detail.DetailActivity;
import com.dinislam.retrofit2rxjava2test.screens.favoriteMovie.FavoriteMovieActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoadDataAndDestroy {
    private MovieAdapter adapter;
    private Switch switche;
    private Switch switcheGte;
    private MyDataLoader dataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataLoader = new MyDataLoader(this, getApplication());
        //spinner
        final Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 : loadData(false, Contract.PARAMS_SORTED_BY_POPULARITY_DESC, true); break;
                    case 1 : loadData(false, Contract.PARAMS_SORTED_BY_MOST_POPULAR, true); break;
                    case 2 : loadData(false, Contract.PARAMS_SORTED_BY_RELEASE_DATE, true); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Switch ADULT
        switche = findViewById(R.id.switch1);
        switche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (spinner.getSelectedItemPosition()){
                    case 0 : loadData(false, Contract.PARAMS_SORTED_BY_POPULARITY_DESC, true); break;
                    case 1 : loadData(false, Contract.PARAMS_SORTED_BY_MOST_POPULAR, true); break;
                    case 2 : loadData(false, Contract.PARAMS_SORTED_BY_RELEASE_DATE, true); break;
                }
            }
        });
        // Switch GTE
        switcheGte = findViewById(R.id.switch2);
        switcheGte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (spinner.getSelectedItemPosition()){
                    case 0 : loadData(false, Contract.PARAMS_SORTED_BY_POPULARITY_DESC, true); break;
                    case 1 : loadData(false, Contract.PARAMS_SORTED_BY_MOST_POPULAR, true); break;
                    case 2 : loadData(false, Contract.PARAMS_SORTED_BY_RELEASE_DATE, true); break;
                }
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new MovieAdapter(new ArrayList<Movie>());

        loadData(false, Contract.PARAMS_SORTED_BY_POPULARITY_DESC, false);
        adapter.onReachEnd = new MovieAdapter.OnReachEnd() {
            @Override
            public void onReachEnd() {
//                if(!isLoadingData){
                    loadData(true, Contract.PARAMS_SORTED_BY_POPULARITY_DESC, false);
                    Log.i("Den", "reach end");
//                }
            }
        };
        MovieAdapter.onClickPoster = new MovieAdapter.OnClickPoster() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra ("ID", position);
                intent.putExtra("isFavorite", false);
                startActivity(intent);
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        recyclerView.setAdapter(adapter);


    }
    private void loadData(boolean isItNewPages, String sortMethod, boolean replace){
        Log.i("Den", sortMethod);
        String adult = (switche.isChecked()) ? "true" : "false";
        String gte = (switcheGte.isChecked()) ? "500" : "0";

        dataLoader.loadData(isItNewPages, Locale.getDefault().getLanguage(), sortMethod, replace, adult, gte);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoader.myDispose();
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        Log.i("Dend", "" +width);
        return Math.max((width / 200) > 3 ? width / 200 - 1 : width / 200, 2);
    }

    @Override
    public void destroyActivity() {

    }

    @Override
    public void setMoviesInAdapter(List<Movie> list) {
        adapter.addMovies(list);
    }

    @Override
    public void setMoviesInAdapterWithReplace(List<Movie> list) {
        adapter.replaceMovies(list);
    }


    // Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favourites : startActivity(new Intent(this, FavoriteMovieActivity.class)); break;
//            case R.id.spare :  break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
