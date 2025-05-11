import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.image.Artwork;
import info.movito.themoviedbapi.model.movies.Images;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import info.movito.themoviedbapi.tools.builders.discover.DiscoverMovieParamBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class MovieRecommender {
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static ArrayList<Integer> movieIDs;
    private static ArrayList<Movie> movieDetails;
    private static MovieView2 view2;
    private static Movie test;

    public static void main(String[] args) throws TmdbException, IOException, InterruptedException {
        test = new Movie("Sabrina", 113, 58000000, "Romantic Comedy", "Sabrina Fairchild (Julia Ormond) " +
                "is a chauffeur's daughter who grew up with the wealthy Larrabee family. She always had unreciprocated " +
                "feelings for David (Greg Kinnear), the family's younger son and playboy. But after returning from Paris," +
                " Sabrina has become a glamorous woman who gets David's attention. His older, work-minded brother Linus " +
                "(Harrison Ford) thinks their courtship is bad for the family business and tries to break them up -- but " +
                "then he starts to fall for her too.");
        view2 = new MovieView2();
        view2.repaint();
        movieIDs = new ArrayList<>();
        movieDetails = new ArrayList<>();
        loadIDs();
    }

    public static ArrayList<Movie> closest (Movie a) {
        ArrayList<Movie> topMatches = new ArrayList<>(movieList);
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
        return result;
    }

    public static int editDistance(Movie a, Movie b) {
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
            String toUse = ",\"id\":";
            String[] list = response.body().split(toUse);
            String[] ids = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                ids[i] = list[i].substring(0, 7);
            }

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

            String[] r = new String[convert.size()];
            for (int i = 0; i < convert.size(); i++) {
                r[i] = convert.get(i);
            }

            for (int i = 0; i < r.length; i++) {
                MovieDb mov = tmdbMovies.getDetails(Integer.parseInt(r[i]), "en-US", MovieAppendToResponse.IMAGES);
                List<Artwork> images = mov.getImages().getPosters();
                Movie n = new Movie(mov.getTitle(), mov.getRuntime(), mov.getBudget(), mov.getGenres().getFirst(), mov.getOverview(), mov.getPopularity(), images);
                movieList.add(n);
            }
        }
    }
}