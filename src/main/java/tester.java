import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class tester {
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static Movie test;
    private static ArrayList<Integer> movieIDs;

    public static void main(String[] args) throws TmdbException, IOException, InterruptedException {
        movieIDs = new ArrayList<>();
        test = new Movie("Sabrina", 90, 0, "Romantic Comedy", "good movie" );
        Scanner input = new Scanner(System.in);
        // Must fix load to have movies in movieList
        loadIDs();
        System.out.println("How long would you like your movie to be?");
        int len = input.nextInt();
        System.out.println("What should the budget be?");
        int budg = input.nextInt();
        System.out.println("What genre?");
        input.nextLine();
        String gen = input.nextLine();
        System.out.println("What is a movie you've previously enjoyed?");
        String prev = input.nextLine();
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
        // Use get similar method?
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
        MovieDb movie = tmdbMovies.getDetails(5353, "en-US");
        // How to figure out range of movie id values?
//        for(int i = 0; i < 2147483647; i++) {
//            MovieDb current = tmdbMovies.getDetails(i, "en-US", MovieAppendToResponse.IMAGES);
//            Movie movie2 = new Movie(current.getTitle(), current.getRuntime(), current.getBudget(), current.getGenres().getFirst(), current.getOverview(), current.getImages());
//            movieList.add(movie2);
//            System.out.println(movie2);
//        }
    }

    public static void loadIDs() throws IOException, InterruptedException {
        TmdbApi tmdbApi = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A");
        TmdbMovies tmdbMovies = tmdbApi.getMovies();
        //MovieDb movie = tmdbMovies.getDetails(5353, "en-US");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        String toUse = ",\"id\":";
        String[] list = response.body().split(toUse);
        String[] ids = new String[list.length];
        for(int i = 0; i < list.length; i++) {
            ids[i] = list[i].substring(0, 6);
            System.out.println(ids[i]);
        }
        // Need to convert to Integer to call getDetails()
        for(int i = 0; i < list.length; i++) {
            tmdbMovies.getDetails(list[i]);
        }
    }
}
