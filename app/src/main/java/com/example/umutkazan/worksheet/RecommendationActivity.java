package com.example.umutkazan.worksheet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendationActivity extends AppCompatActivity {

    private ListView recommListView;
    private ArrayList<MovieInfo> recommMovies = new ArrayList<>();
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recommListView = (ListView) findViewById(R.id.recomm_listview);

        final Intent passedIntent = getIntent();
        final String id = passedIntent.getExtras().getString("id");

        ID = id;
        new RetrieveFeedTask().execute();

//        recommListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "Fuck you!", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject>
    {

        private Exception exception;

        protected void onPreExecute() {

        }

        protected JSONObject doInBackground(Void... urls) {
            JSONObject jsonMovie = SearchMovie.getRecommendedMoviesFromID(ID);
            return jsonMovie;
        }

        protected void onPostExecute(JSONObject jsonMovie) {
            String message = "";
            if (jsonMovie == null) {
                message = "THERE WAS AN ERROR";
            }
            getRecMovieNames(jsonMovie);
            handleAdapterAndStuff();
            Log.i("INFO", message);


        }
    }

    private void getRecMovieNames(JSONObject jsonRecs)
    {
        try
        {
            JSONArray movieArr = jsonRecs.getJSONArray("results");
            for (int i = 0; i < movieArr.length(); i++)
            {
                MovieInfo movieInf = new MovieInfo();
                JSONObject tempObj = movieArr.getJSONObject(i);
                String id = tempObj.getInt("id") + "";
                String title = tempObj.getString("title");
                String posterPath = tempObj.getString("poster_path");
                String releaseDate = tempObj.getString("release_date");
                String rating = tempObj.getString("vote_average");

                movieInf.setVoteCount(tempObj.getInt("vote_count"));
                movieInf.setOverview(tempObj.getString("overview"));
                movieInf.setId(Integer.valueOf(id));
                movieInf.setTitle(title);
                movieInf.setPosterPath(posterPath);
                movieInf.setReleaseDate(releaseDate);
                movieInf.setVoteAverage(rating);
                recommMovies.add(movieInf);


            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void handleAdapterAndStuff()
    {

        MovieListAdapter adapter = new MovieListAdapter(RecommendationActivity.this, R.layout.mylist, recommMovies);
        recommListView.setAdapter(adapter);
        recommListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MovieInfo infoPage = recommMovies.get(i);
                Intent appInfo = new Intent(RecommendationActivity.this, MovieInfoActivity.class);
                appInfo.putExtra("MovieInfo", infoPage);
                startActivity(appInfo);
            }
        });
    }
}
