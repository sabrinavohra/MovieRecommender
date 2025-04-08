import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.Images;

public class Movie {
    private String name;
    private double length;
    private int budget;
    private Genre genre;
    private String overview;
    private Images poster;

    public Movie(String name, double length, int budget, Genre genre, String overview, Images poster) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre;
        this.overview = overview;
        this.poster = poster;
    }

    public String toString() {
        return "Title: " + name + "\nLength: " + length + "\nBudget: " + budget + "\nGenre: " + genre + "\nOverview:" + overview + "\nPoster: " + poster + "\n\n\n";
    }

}
