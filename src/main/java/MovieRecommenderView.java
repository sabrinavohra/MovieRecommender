import javax.swing.*;
import java.awt.*;

public class MovieRecommenderView extends JFrame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 825;
    private static final int WINDOW_POSITION = 200;

    public MovieRecommenderView () {
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocation(WINDOW_POSITION, WINDOW_POSITION);
        // Sets title of window
        this.setTitle("Movie Recommender!");
        // Makes Game exit when window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Allows user to see screen
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
    }
}
