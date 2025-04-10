import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;

import java.util.ArrayList;
import java.util.Scanner;

public class tester {
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static Movie test;

    public static void main(String[] args) throws TmdbException {
        test = new Movie("Sabrina", 90, 0, "Romantic Comedy", "good movie" );
        Scanner input = new Scanner(System.in);
        load();
        System.out.println("How long would you like your movie to be?");
        int len = input.nextInt();
        System.out.println("What should the budget be?");
        int budg = input.nextInt();
        System.out.println("What genre?");
        input.nextLine();
        String gen = input.nextLine();
        Movie user = new Movie(len, budg, gen);
        Movie theClosest = closest(user);
        System.out.println(theClosest);
    }

    public static Movie closest (Movie a) {
        Movie current = null;
        int highest = 0;
        for(int i = 0; i < movieList.size(); i++) {
            int c = editDistance(a, movieList.get(i));
            if(c > highest) {
                current = movieList.get(i);
            }
        }
        if(current == null) {
            return test;
        }
        return current;
    }

    public static int editDistance(Movie a, Movie b) {
        int total = 0;
        if(Math.abs(a.getLength() - b.getLength()) <= 15) {
            total++;
        }
        if(Math.abs(a.getBudget() - b.getBudget()) <= 100) {
            total++;
        }
        if(a.getGenre().equals(b.getGenre())) {
            total++;
        }
        return total;
    }

    public static void load() throws TmdbException {
        TmdbApi tmdbApi = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A");
        TmdbMovies tmdbMovies = tmdbApi.getMovies();
        //MovieDb movie = tmdbMovies.getDetails(5353, "en-US");
        for(int i = 5000; i < 10000; i++) {
            MovieDb current = tmdbMovies.getDetails(i, "en-US", MovieAppendToResponse.IMAGES);
            Movie movie2 = new Movie(current.getTitle(), current.getRuntime(), current.getBudget(), current.getGenres().getFirst(), current.getOverview(), current.getImages());
            movieList.add(movie2);
        }
    }
}
