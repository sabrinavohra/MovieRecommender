import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;

import java.util.ArrayList;

public class tester {
    private static ArrayList<Movie> movieList = new ArrayList<Movie>();
    public static void main(String[] args) throws TmdbException {

        TmdbApi tmdbApi = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A");
        TmdbMovies tmdbMovies = tmdbApi.getMovies();
        MovieDb movie = tmdbMovies.getDetails(5353, "en-US");
        System.out.println(movie);
        for(int i = 5000; i < 10000; i++) {
            MovieDb current = tmdbMovies.getDetails(i, "en-US", MovieAppendToResponse.IMAGES);
            Movie movie2 = new Movie(current.getTitle(), current.getRuntime(), current.getBudget(), current.getGenres().getFirst(), current.getOverview(), current.getImages());
            movieList.add(movie2);
        }
        for(int i = 0; i < movieList.size(); i++) {
            System.out.println(movieList.get(i));
        }
    }
}
