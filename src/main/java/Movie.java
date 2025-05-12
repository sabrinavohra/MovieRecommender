import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.image.Artwork;
import java.util.List;

public class Movie {
    private String name;
    private int length;
    private int budget;
    private String genre;
    private String overview;
    private List<Artwork> images;
    private double rating;

    // Constructor used for information given from API
    public Movie(String name, int length, int budget, Genre genre, String overview, double rating, List<Artwork> poster) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre.getName();
        this.overview = overview;
        this.images = poster;
        this.rating = rating;
    }

    // Constructor used for creating test / backup movie
    public Movie(String name, int length, int budget, String genre, String overview) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre;
        this.overview = overview;
    }

    // Constructor used for information inputted by user
    public Movie(String genre, String length, String rating) {
        this.genre = genre;
        this.length = Integer.parseInt(length);
        this.rating = Integer.parseInt(rating);
    }

    // Used for debugging to make sure all information is being returned
    public String toString() {
        return "Title: " + name + "\nLength: " + length + "\nBudget: " + budget + "\nGenre: " + genre + "\nOverview:" + overview + "\nRating" + rating+ "\nPoster:" + images + "\n\n\n";
    }

    // Methods return class variables

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

    public List<Artwork> getImages()  {
        return this.images;
    }

    public double getRating() {
        return this.rating;
    }

    public String getOverview() {
        return this.overview;
    }
}
