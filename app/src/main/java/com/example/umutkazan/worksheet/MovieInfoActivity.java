package com.example.umutkazan.worksheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MovieInfoActivity extends AppCompatActivity {



    private static String IMG = "https://image.tmdb.org/t/p/w92/";
    private static String NOIMAGE = "http://www.freeiconspng.com/uploads/no-image-icon-15.png";

    private TextView titleTW;
    private TextView taglineTW;
    private TextView voteCountTW;
    private TextView voteAvgTW;
    private TextView releaseDateTW;
    private TextView homepageTW;
    private TextView imdbPageTW;
    private TextView overviewTW;
    private ImageView posterIW;
    private TextView genresTW;
    private Button recomButton;
    private Button favButton;

    private String ID;
    private String homepageG;
    private String imdbPageG;

    private MovieInfo movieForFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        titleTW = (TextView) findViewById(R.id.movie_name);
        taglineTW = (TextView) findViewById(R.id.movie_tagline);
        voteCountTW = (TextView) findViewById(R.id.movie_voters);
        voteAvgTW = (TextView) findViewById(R.id.movie_rating);
        releaseDateTW = (TextView) findViewById(R.id.movie_releaseDate);
        homepageTW = (TextView) findViewById(R.id.movie_homepage);
        imdbPageTW = (TextView) findViewById(R.id.movie_imdb);
        overviewTW = (TextView) findViewById(R.id.movie_overview);
        posterIW = (ImageView) findViewById(R.id.movie_poster);
        genresTW = (TextView) findViewById(R.id.movie_genres);
        recomButton = (Button) findViewById(R.id.button_recom);
        favButton = (Button) findViewById(R.id.add_to_fav);

        final Intent passedIntent = getIntent();
        final MovieInfo info = passedIntent.getParcelableExtra("MovieInfo");
        drawThePage(info);

        new RetrieveFeedTask().execute();
        homepageTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(!homepageTW.getText().toString().equals("Homepage Not Found!"))
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(homepageG)));
                }
                catch(Exception e)
                {
                    // no one cares
                }
            }
        });

        imdbPageTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(!imdbPageTW.getText().toString().equals("Imdb Page Not Found!"))
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(imdbPageG)));
                }
                catch(Exception e)
                {
                    // no one cares
                }
            }
        });

        recomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recomMovies = new Intent(MovieInfoActivity.this, RecommendationActivity.class);
                recomMovies.putExtra("id", ID);
                startActivity(recomMovies);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MovieInfo.favMovIds.contains(ID))
                    Toast.makeText(MovieInfoActivity.this, "Already faved", Toast.LENGTH_SHORT).show();
                else
                {
                    MovieInfo.favMovIds.add(movieForFav.getId() + "");
                    MovieInfo.movieList.add(movieForFav);
                }
            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();


        String fileName = "MyFile";
        String content = "hello world";

        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println(MovieInfoActivity.this.getFilesDir().getAbsolutePath());

//        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = mPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(MovieInfo.movieList);
//        prefsEditor.putString("MyObject", json);
//
//        String json2 = gson.toJson(MovieInfo.favMovIds);
//        prefsEditor.putString("MyObject2", json2);
//        prefsEditor.commit();
    }

    private void drawThePage(MovieInfo movie)
    {
        movieForFav = movie;
        ID = movie.getId() + "";
        titleTW.setText(movie.getTitle());
        taglineTW.setText("");
        voteCountTW.setText("Vote Count: " + movie.getVoteCount() + "");
        voteAvgTW.setText("Rating: " + movie.getVoteAverage());
        releaseDateTW.setText("Release Date: " + movie.getReleaseDate());
        homepageTW.setText("HomePage URL");
        imdbPageTW.setText("Imdb Page URL");

        if(movie.getOverview().equals("null"))
            overviewTW.setText("No Overview Found!");
        else
            overviewTW.setText(movie.getOverview());
        if(movie.getPosterPath() == null || movie.getPosterPath().equals("null"))
        {
            Picasso.with(getApplicationContext()).load(NOIMAGE).into(posterIW);
        }
        else
        {
            Picasso.with(getApplicationContext()).load(IMG + movie.getPosterPath()).into(posterIW);
        }
    }

    public void getNeccesaryInfos(JSONObject jsonMovie)
    {

        try
        {
            String tagline = jsonMovie.getString("tagline");
            String homePage = jsonMovie.getString("homepage");
            String ImdbPage = jsonMovie.getString("imdb_id");
            JSONArray genres = jsonMovie.getJSONArray("genres");
            String genresTr = getGenres(genres);

            if(tagline.equals("") || tagline == null || tagline.equals("null"))
            {
                taglineTW.setText("");
            }
            else
            {
                taglineTW.setText("\"" + tagline + "\"");
                // maybe change color
            }

            if(homePage.equals("") || homePage.equals("null") || homePage == null)
            {
                homepageTW.setText("Homepage Not Found!");
                homepageTW.setTextColor(Color.RED);
            }
            else
            {
                homepageTW.setText("Homepage");
                homepageG = homePage;
                // magical stuff!!
                homepageTW.setPaintFlags(homepageTW.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            }

            if(ImdbPage.equals("") || ImdbPage.equals("null") || ImdbPage == null)
            {
                imdbPageTW.setText("Imdb Page Not Found!");
                imdbPageTW.setTextColor(Color.RED);
            }
            else
            {
                imdbPageTW.setText("Imdb Page");
                imdbPageG = "http://www.imdb.com/title/" + ImdbPage;
                // text the magnificant!!
                imdbPageTW.setPaintFlags(imdbPageTW.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            }

            if(genresTr == null || genresTr.equals("") || genresTr.equals("null"))
            {
                genresTW.setText("");
            }
            else
            {
                genresTW.setText(genresTr);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private String getGenres(JSONArray jsonArray)
    {
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            for(int i=0; i < jsonArray.length(); i++)
            {
                JSONObject temp = jsonArray.getJSONObject(i);
                stringBuilder.append(temp.getString("name"));
                if(i != jsonArray.length() - 1)
                    stringBuilder.append(", ");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, JSONObject>
    {

        private Exception exception;

        protected void onPreExecute() {

        }

        protected JSONObject doInBackground(Void... urls) {
            JSONObject jsonMovie = SearchMovie.getMovieDetailsFromId(ID);
            return jsonMovie;
        }

        protected void onPostExecute(JSONObject jsonMovie) {
            String message = "";
            if (jsonMovie == null) {
                message = "THERE WAS AN ERROR";
            }
            getNeccesaryInfos(jsonMovie);
            Log.i("INFO", message);
        }
    }
}
