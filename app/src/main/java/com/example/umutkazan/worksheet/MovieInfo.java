package com.example.umutkazan.worksheet;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Umut Kazan on 7.08.2017.
 */

public class MovieInfo implements Parcelable
{
    private boolean isAdult;
    private String backdropPath;
    private String belongsToCollection; // ?
    private int budget;
    private ArrayList<String> genres;
    private String homepage;
    private int id;
    private String imdbID;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private HashMap<Integer, String> productionCompanies;
    private HashMap<String, String> productionCountries;
    private String releaseDate;
    private int revenue;
    private int runtime; // ?
    private HashMap<String, String> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean isVideo;
    private String voteAverage;
    private int voteCount;

    public static List<MovieInfo> movieList = new ArrayList<>();
    public static List<String> favMovIds = new ArrayList<>();

    private JSONObject object;

    public MovieInfo(){}

    public MovieInfo(JSONObject object)
    {
        spokenLanguages = new HashMap<>();
        productionCompanies = new HashMap<>();
        productionCountries = new HashMap<>();
        this.object = object;
    }

    public MovieInfo(String releaseDate, String title, String posterPath, String rating, int id)
    {
        setPosterPath(posterPath);
        setTitle(title);
        setReleaseDate(releaseDate);
        setVoteAverage(rating);
        setId(id);
    }

    public MovieInfo(Parcel in)
    {

        this.id = Integer.valueOf(in.readString());
        this.title = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.voteCount = Integer.valueOf(in.readString());
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(String belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public HashMap<Integer, String> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(HashMap<Integer, String> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void addProductionCompanies(int id, String name)
    {
        productionCompanies.put(id, name);
    }

    public HashMap<String, String> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(HashMap<String, String> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public HashMap<String, String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(HashMap<String, String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "isAdult=" + isAdult +
                ", backdropPath='" + backdropPath + '\'' +
                ", belongsToCollection='" + belongsToCollection + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", homepage=" + homepage +
                ", id=" + id +
                ", imdbID='" + imdbID + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", productionCompanies=" + productionCompanies +
                ", productionCountries=" + productionCountries +
                ", releaseDate=" + releaseDate +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spokenLanguages=" + spokenLanguages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", isVideo=" + isVideo +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId() + "");
        parcel.writeString(getTitle());
        parcel.writeString(getPosterPath());
        parcel.writeString(getVoteAverage());
        parcel.writeString(getVoteCount() + "");
        parcel.writeString(getOverview());
        parcel.writeString(getReleaseDate());
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {

        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
