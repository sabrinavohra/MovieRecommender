import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.Images;

public class Movie {
    private String name;
    private int length;
    private int budget;
    private String genre;
    private String overview;
    private Images images;
    private double rating;

    public Movie(String name, int length, int budget, Genre genre, String overview, double rating, Images poster) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre.getName();
        this.overview = overview;
        this.images = poster;
        this.rating = rating;
    }

    public Movie(String name, int length, int budget, String genre, String overview) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre;
        this.overview = overview;
    }

    public Movie(int length, int budget, double rating, String genre) {
        this.length = length;
        this.budget = budget;
        this.genre = genre;
        this.rating = rating;
    }

    public Movie(String genre, String length, String rating) {
        this.genre = genre;
        this.length = Integer.parseInt(length);
        this.rating = Integer.parseInt(rating);
    }

    public String toString() {
        return "Title: " + name + "\nLength: " + length + "\nBudget: " + budget + "\nGenre: " + genre + "\nOverview:" + overview + "\nRating" + rating+ "\nPoster:" + images + "\n\n\n";
    }

    public String getName() {
        return this.name;
    }

    public int getLength() {
        return this.length;
    }

    public int getBudget() {
        return this.budget;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getOverview() {
        return this.overview;
    }

    public Images getImages()  {
        return this.images;
    }

    public double getRating() {
        return this.rating;
    }
}
