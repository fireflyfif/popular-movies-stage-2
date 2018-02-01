package com.example.android.popular_movies_stage_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.android.popular_movies_stage_1.ui.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movies> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.thumbnail_grid_view);

        mMoviesList = new ArrayList<>();

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, mMoviesList);
        gridView.setAdapter(moviesAdapter);
    }
}
