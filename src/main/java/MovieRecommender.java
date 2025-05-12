import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.image.Artwork;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class MovieRecommender {
    private static final ArrayList<Movie> movieList = new ArrayList<>();
    private static Movie test;

    // Connects classes and runs algorithm
    public static void main() throws TmdbException, IOException, InterruptedException {
        // Creates tester movie in case code fails
        test = new Movie("Sabrina", 113, 58000000, "Romantic Comedy", "Sabrina Fairchild (Julia Ormond) " +
                "is a chauffeur's daughter who grew up with the wealthy Larrabee family. She always had unreciprocated " +
                "feelings for David (Greg Kinnear), the family's younger son and playboy. But after returning from Paris," +
                " Sabrina has become a glamorous woman who gets David's attention. His older, work-minded brother Linus " +
                "(Harrison Ford) thinks their courtship is bad for the family business and tries to break them up -- but " +
                "then he starts to fall for her too.");
        MovieView2 view2 = new MovieView2();
        view2.repaint();
        // Loads all movies
        loadIDs();
    }

    // Finds the three most similar movies to the user's inputted information
    public static ArrayList<Movie> closest (Movie a) {
        ArrayList<Movie> topMatches = new ArrayList<>(movieList);
        // Compares edit distances of movies
        topMatches.sort((m1, m2) -> Integer.compare(
                editDistance(a, m2),
                editDistance(a, m1)
        ));

        ArrayList<Movie> result = new ArrayList<>();
        for (Movie m : topMatches) {
            if (!result.contains(m)) {
                result.add(m);
            }
            if (result.size() == 3) break;
        }

        while (result.size() < 3) {
            result.add(test);
        }
        // Returns ArrayList of the three most similar movies after comparing
        return result;
    }

    // Edit distance method decides how similar movie is
    public static int editDistance(Movie a, Movie b) {
        int total = 0;
        // Genre has most ability to change edit distance, budget has least, and length has little more than budget
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

    // Loads the movies IDs into the ArrayList and into the Movie class to save them
    public static void loadIDs() throws IOException, InterruptedException, TmdbException {
        // Code inputted from Tmdb API
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
            // Splices String to find all IDs
            String toUse = ",\"id\":";
            String[] list = response.body().split(toUse);
            String[] ids = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                ids[i] = list[i].substring(0, 7);
            }

            // Ensures that String is right amount of characters and only numbers
            ArrayList<String> convert = new ArrayList<>();
            for (String s : ids) {
                convert.add(s);
                for (int j = 0; j < s.length(); j++) {
                    char c = s.charAt(j);
                    if (!Character.isDigit(c)) {
                        convert.remove(s);
                    }
                }
            }

            // Converts ArrayList into Array
            String[] r = new String[convert.size()];
            for (int i = 0; i < convert.size(); i++) {
                r[i] = convert.get(i);
            }

            // Saves all the movies into the Movie class
            for (int i = 0; i < r.length; i++) {
                MovieDb mov = tmdbMovies.getDetails(Integer.parseInt(r[i]), "en-US", MovieAppendToResponse.IMAGES);
                List<Artwork> images = mov.getImages().getPosters();
                Movie n = new Movie(mov.getTitle(), mov.getRuntime(), mov.getBudget(), mov.getGenres().getFirst(), mov.getOverview(), mov.getPopularity(), images);
                movieList.add(n);
            }
        }
    }
}