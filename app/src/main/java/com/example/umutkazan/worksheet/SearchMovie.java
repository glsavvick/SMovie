package com.example.umutkazan.worksheet;

/**
 * Created by Umut Kazan on 13.08.2017.
 */


 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.net.HttpURLConnection;
 import java.net.URL;

 import org.json.JSONException;
 import org.json.JSONObject;
 import org.json.JSONTokener;

public class SearchMovie {

    final private static String API_KEY = "b43ca88b0be44ba6a03ffe8484ed0d0c";
    private static String query = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=";
    private static String pageString = "&page=";

    private static String API_FULL_KEY  = "?api_key=b43ca88b0be44ba6a03ffe8484ed0d0c";
    private static String movieDetails = "https://api.themoviedb.org/3/movie/";

    private static String response;
    private int pageNum = 1;

    private String searchText;



    public JSONObject getMovieNamesFromSearch()
    {
        try
        {
            response = null;
            URL url = new URL(query + getSearchText() + pageString + getPageNum());
            // System.out.println(url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString();

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e)
        {
            // Log.e("ERROR", e.getMessage(), e);
        }
        try {
            return (JSONObject) new JSONTokener(response).nextValue();
        }
        catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getMovieDetailsFromId(String id)
    {
        try
        {
            response = null;
            URL url = new URL(movieDetails + id + API_FULL_KEY);
            System.out.println(url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString();

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e)
        {
            // Log.e("ERROR", e.getMessage(), e);
        }
        try {
            return (JSONObject) new JSONTokener(response).nextValue();
        }
        catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getRecommendedMoviesFromID(String id)
    {
        String url1 = "https://api.themoviedb.org/3/movie/";
        String url2 = "/recommendations?api_key=b43ca88b0be44ba6a03ffe8484ed0d0c&page=1";
        try
        {
            response = null;
            URL url = new URL(url1 + id + url2);
            System.out.println(url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString();

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e)
        {
            // Log.e("ERROR", e.getMessage(), e);
        }
        try {
            return (JSONObject) new JSONTokener(response).nextValue();
        }
        catch (JSONException e) {
            return null;
        }
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        String newSearchText = searchText.replace(" ", "+");
        this.searchText = newSearchText.trim();
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public void incPageNum()
    {
        this.pageNum++;
    }

    public void decPageNum()
    {
        this.pageNum--;
    }

}

