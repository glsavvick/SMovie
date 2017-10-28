package com.example.umutkazan.worksheet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Umut Kazan on 17.08.2017.
 */

public class MovieListAdapter extends ArrayAdapter<MovieInfo> {

    private static String IMG = "https://image.tmdb.org/t/p/w45/";
    private static String NOIMAGE = "http://www.freeiconspng.com/uploads/no-image-icon-15.png";

    private Context context;
    private List<MovieInfo> movieArr;

    public MovieListAdapter(Context context, int resource, List<MovieInfo> movieArr) {
        super(context, resource, movieArr);
        this.context = context;
        this.movieArr = movieArr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private class ViewHolder
    {
        ImageView image;
        TextView name;
        TextView year;
        TextView rating;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View myList = inflater.inflate(R.layout.mylist, parent, false);
        holder = new ViewHolder();

        holder.year = myList.findViewById(R.id.listView_year);
        holder.image = myList.findViewById(R.id.listView_movieImage);
        holder.name = myList.findViewById(R.id.listView_name);
        holder.rating = myList.findViewById(R.id.listView_rating);

        MovieInfo movie = movieArr.get(position);

        String release = movie.getReleaseDate();
        if(release == null || release.equals("null"))
            holder.year.setText("Release: Unknown");
        else
            holder.year.setText("Release: " + movie.getReleaseDate());
        if(movie.getPosterPath() == null || movie.getPosterPath().equals("null"))
        {
            Picasso.with(context).load(NOIMAGE).into(holder.image);
        }
        else
        {
            Picasso.with(context).load(IMG + movie.getPosterPath()).into(holder.image);
        }

        holder.name.setText(movie.getTitle());
        holder.rating.setText("TMDB Rating: " + movie.getVoteAverage());

        myList.setTag(holder);

        return myList;
    }
}
