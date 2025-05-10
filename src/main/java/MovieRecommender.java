import com.sun.net.httpserver.Request;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
//import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.movies.Images;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieRecommender {
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static Movie test;
    private static ArrayList<Integer> movieIDs;
    private static ArrayList<Movie> movieDetails;
    private static MovieRecommenderView view;
    private static MovieView2 view2;

    public static void main(String[] args) throws TmdbException, IOException, InterruptedException {
        //view = new MovieRecommenderView();
        view2 = new MovieView2();
        view2.repaint();
        movieIDs = new ArrayList<>();
        movieDetails = new ArrayList<>();
        test = new Movie("Sabrina", 90, 0, "Romantic Comedy", "good movie" );
        Scanner input = new Scanner(System.in);
        // Must fix load to have movies in movieList
        loadIDs();
        /* System.out.println("How long would you like your movie to be?");
        int len = input.nextInt();
        System.out.println("What should the budget be?");
        int budg = input.nextInt();
        System.out.println("What genre?");
        input.nextLine();
        String gen = input.nextLine();
        System.out.println("What is a movie you've previously enjoyed?");
        String prev = input.nextLine();
        Movie user = new Movie(len, budg, rating, gen);
        Movie theClosest = closest(user);
        System.out.println(theClosest); */
    }

    public static ArrayList<Movie> closest (Movie a) {
        ArrayList<Movie> topMatches = new ArrayList<>(movieList);

        // Sort movies by descending edit distance (most similar first)
        topMatches.sort((m1, m2) -> Integer.compare(
                editDistance(a, m2),
                editDistance(a, m1)
        ));

        // Collect the top 3 distinct movies
        ArrayList<Movie> result = new ArrayList<>();
        for (Movie m : topMatches) {
            if (!result.contains(m)) {
                result.add(m);
            }
            if (result.size() == 3) break;
        }

        // Fallback to test movie if not enough matches
        while (result.size() < 3) {
            result.add(test);
        }
        return result;
    }

    public static int editDistance(Movie a, Movie b) {
        // Use get similar method?
        // The further you are, the fewer points you get
        // Some categories worth more than less?
        // Research or creative?
            // Recommendation algorithms / matching algorithms
            // Have fields in a record and which saved record is the most similar?
        int total = 0;
        if(Math.abs(a.getLength() - b.getLength()) <= 15) {
            total+=2;
        }
        if(Math.abs(a.getBudget() - b.getBudget()) <= 100) {
            total++;
        }
        if(a.getGenre().equals(b.getGenre())) {
            total+=5;
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

    public static void loadIDs() throws IOException, InterruptedException, TmdbException {
        TmdbApi tmdbApi = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A");
        TmdbMovies tmdbMovies = tmdbApi.getMovies();
        for (int m = 0; m < 50; m++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=" + m +"&sort_by=popularity.desc"))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDkwNjk5ZGE1ODg4ZGI5MjE1ZGNmMGNhMjgwZDZmYiIsIm5iZiI6MTc0MzE5MjczMi42ODUsInN1YiI6IjY3ZTcwMjljMDkyNTI4NjJlYTc2N2U4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Q08Y593tZg-jbJ1WUG5Q5QV4Wc_NeCiZ4nM9x0JHb3A")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
            String toUse = ",\"id\":";
            String[] list = response.body().split(toUse);
            ArrayList<String> id = new ArrayList<>();
            String[] ids = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                ids[i] = list[i].substring(0, 7);
            }

            ArrayList<String> convert = new ArrayList<>();
            for (String s : ids) {
                //convert.add(null);
                convert.add(s);
                for (int j = 0; j < s.length(); j++) {
                    char c = s.charAt(j);
                    if (!Character.isDigit(c)) {
                        convert.remove(s);
                    }
                }
                //convert.remove(null);
            }

            String[] r = new String[convert.size()];
            for (int i = 0; i < convert.size(); i++) {
                r[i] = convert.get(i);
                //System.out.println(r[i]);
            }

            for (int i = 0; i < r.length; i++) {
                // Fix IDs
                MovieDb mov = tmdbMovies.getDetails(Integer.parseInt(r[i]), "en-US", MovieAppendToResponse.IMAGES);
                Images images = mov.getImages();
                Movie n = new Movie(mov.getTitle(), mov.getRuntime(), mov.getBudget(), mov.getGenres().getFirst(), mov.getOverview(), mov.getPopularity(), images);
                movieList.add(n);
                //System.out.println(n);
            }
        }
        System.out.println(movieList);
    }

}
