package com.example.umutkazan.worksheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private ListView favListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        favListView = (ListView) findViewById(R.id.fav_listview);

//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MovieInfo.movieList);
//        favListView.setAdapter(adapter);

        MovieListAdapter adapter = new MovieListAdapter(FavouritesActivity.this, R.layout.mylist, MovieInfo.movieList);
        favListView.setAdapter(adapter);
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MovieInfo infoPage = MovieInfo.movieList.get(i);
                Intent appInfo = new Intent(FavouritesActivity.this, MovieInfoActivity.class);
                appInfo.putExtra("MovieInfo", infoPage);
                startActivity(appInfo);
            }
        });
    }
}
