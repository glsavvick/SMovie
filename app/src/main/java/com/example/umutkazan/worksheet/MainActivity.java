package com.example.umutkazan.worksheet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private EditText editText;
    private Button searchButton;
    private TextView pageNumber;
    private ListView listView;
    // private String[] movArray = new String[20];
    private int totalPages=-1;
    private SearchMovie search = new SearchMovie();
    private ArrayList<MovieInfo> infoArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.editText_search);
        searchButton = (Button) findViewById(R.id.button_search);
        listView = (ListView) findViewById(R.id.listView_movies);
        pageNumber = (TextView) findViewById((R.id.page_number));
        pageNumber.setText("");

    }

    public void onNextPageRequest(View view)
    {
        if(search.getPageNum() < totalPages)
        {
            search.incPageNum();
            pageNumber.setText(search.getPageNum() + "");
        }
        new RetrieveFeedTask().execute();
    }

    public void onPrevPageRequest(View view)
    {
        if(search.getPageNum() > 1)
        {
            search.decPageNum();
            pageNumber.setText(search.getPageNum() + "");
        }
        new RetrieveFeedTask().execute();
    }

    public void onClickRequest(View view)
    {
        search.setSearchText(editText.getText().toString());
        pageNumber.setText("1");
        new RetrieveFeedTask().execute();
    }

    private void handleAdapterAndStuff()
    {
        MovieListAdapter adapter = new MovieListAdapter(MainActivity.this, R.layout.mylist, infoArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MovieInfo infoPage = infoArray.get(i);
                Intent appInfo = new Intent(MainActivity.this, MovieInfoActivity.class);
                appInfo.putExtra("MovieInfo", infoPage);
                startActivity(appInfo);
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, HashMap<Integer, String>> {

        // private Exception exception;

        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            textView.setText("");
        }

        protected HashMap<Integer, String> doInBackground(Void... urls)
        {
            JSONObject response = search.getMovieNamesFromSearch();
            if(response == null)
            {
                System.err.println("HATAAAAAAAAA");
                System.exit(-1);
            }

            try
            {
                if(totalPages == -1)
                    totalPages = response.getInt("total_pages");
            }
            catch (JSONException e)
            {
                System.out.println("NE OLUYOR");
            }

		    HashMap<Integer, String> map = getMovieNames(response);
            return map;

        }

        protected void onPostExecute(HashMap<Integer, String> map)
        {
            String message = "";
            if(map == null)
            {
                message = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", message);
            Iterator itr = null;
            if (map != null) {
                itr = map.entrySet().iterator();
            }
            // int i = 0;
            while(itr.hasNext())
            {
                Map.Entry pair = (Map.Entry)itr.next();
                textView.append(pair.getValue() + "\n");
                // movArray[i++] = pair.getValue().toString();
            }
            handleAdapterAndStuff();

        }
    }

    public HashMap<Integer, String> getMovieNames(JSONObject object)
    {
        HashMap<Integer, String> movie = new HashMap<>();
        infoArray.clear();
        try
        {
            JSONArray movieArr = object.getJSONArray("results");
            for (int i = 0; i < movieArr.length(); i++)
            {
                MovieInfo movieInfo = new MovieInfo();
                JSONObject tempObj = movieArr.getJSONObject(i);

                int id = tempObj.getInt("id");
                String title = tempObj.getString("title");
                String posterPath = tempObj.getString("poster_path");
                String releaseDate = tempObj.getString("release_date");
                String rating = tempObj.getString("vote_average");

                movie.put(id, title);

                movieInfo.setId(id);
                movieInfo.setTitle(title);
                movieInfo.setPosterPath(posterPath);
                movieInfo.setVoteAverage(rating);
                movieInfo.setVoteCount(tempObj.getInt("vote_count"));
                movieInfo.setOverview(tempObj.getString("overview"));
                movieInfo.setReleaseDate(releaseDate);
                infoArray.add(movieInfo);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            System.err.println("ERROR ERROR ERROR ERROR ERROR");
        }
        return movie;
    }
}
