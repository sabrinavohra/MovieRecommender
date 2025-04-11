import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.Images;

public class Movie {
    private String name;
    private int length;
    private int budget;
    private String genre;
    private String overview;
    private Images poster;

    public Movie(String name, int length, int budget, Genre genre, String overview, Images poster) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre.getName();
        this.overview = overview;
        this.poster = poster;
    }

    public Movie(String name, int length, int budget, String genre, String overview) {
        this.name = name;
        this.length = length;
        this.budget = budget;
        this.genre = genre;
        this.overview = overview;
    }

    public Movie(int length, int budget, String genre) {
        this.length = length;
        this.budget = budget;
        this.genre = genre;
    }

    public String toString() {
        return "Title: " + name + "\nLength: " + length + "\nBudget: " + budget + "\nGenre: " + genre + "\nOverview:" + overview + "\nPoster: " + poster + "\n\n\n";
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

    public Images getPoster()  {
        return this.poster;
    }
}
