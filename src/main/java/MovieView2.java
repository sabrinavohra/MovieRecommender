import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MovieView2 extends JFrame {
    private JTextField genreField, lengthField, ratingField;
    private JPanel inputPanel, goPanel;
    private MovieRecommender back;
    private final Font a = new Font("Arial", Font.BOLD, 16);

    public MovieView2() {
        back = new MovieRecommender();

        setTitle("Movie Recommender");
        setSize(1024, 825);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel with labels and text fields
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;


        // Genre
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        genreField = new JTextField(20);
        inputPanel.add(genreField, gbc);

        // Length
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Length:"), gbc);
        gbc.gridx = 1;
        lengthField = new JTextField(20);
        inputPanel.add(lengthField, gbc);

        // Rating
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        ratingField = new JTextField(20);
        inputPanel.add(ratingField, gbc);

        // GO button
        goPanel = new JPanel();
        Font a = new Font("Arial", Font.BOLD, 16);
        JButton goButton = new JButton("GO!!");
        goButton.setFont(a);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genre = genreField.getText().trim();
                String length = lengthField.getText().trim();
                String rating = ratingField.getText().trim();

                if (genre.isEmpty() || length.isEmpty() || rating.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            MovieView2.this,
                            "Please fill in all the fields (Genre, Length, and Rating).",
                            "Missing Input",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    showNextScreen();
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 100;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        goButton.setPreferredSize(new Dimension(120, 60));
        goButton.setBackground(Color.GREEN);
        goButton.setForeground(Color.GREEN);
        inputPanel.add(goButton, gbc);
        goButton.setOpaque(true);

        add(inputPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showNextScreen() {
        getContentPane().removeAll();

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.LIGHT_GRAY);
        whitePanel.setLayout(new BorderLayout());

        // Create user movie from input fields
        int length = Integer.parseInt(lengthField.getText().trim());
        String genre = genreField.getText().trim();
        int budget = 1000; // Can change later to be based upon user input
        Movie userMovie = new Movie(genre, lengthField.getText(), ratingField.getText()); // assuming constructor matches

        ArrayList<Movie> recommended = MovieRecommender.closest(userMovie);

        // CENTER: Show all recommended movies
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        centerPanel.setBackground(Color.LIGHT_GRAY);

        for (Movie movie : recommended) {
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.Y_AXIS));
            moviePanel.setBackground(Color.LIGHT_GRAY);

            // Add poster
            try {
                if (movie.getImages() != null && !movie.getImages().getPosters().isEmpty()) {
                    String posterPath = movie.getImages().getPosters().get(0).getFilePath();
                    String fullUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
                    ImageIcon icon = new ImageIcon(new java.net.URL(fullUrl));
                    Image img = icon.getImage().getScaledInstance(180, 270, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    moviePanel.add(imageLabel);
                } else {
                    moviePanel.add(new JLabel("No poster available."));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                moviePanel.add(new JLabel("Error loading image."));
            }

            moviePanel.add(Box.createVerticalStrut(15));
            moviePanel.add(new JLabel("Title: " + movie.getName()));
            moviePanel.add(new JLabel("Genre: " + movie.getGenre()));
            moviePanel.add(new JLabel("Length: " + movie.getLength() + " min"));
            moviePanel.add(new JLabel("Budget: $" + movie.getBudget()));
            moviePanel.add(new JLabel("Rating: " + movie.getRating()));

            centerPanel.add(moviePanel);
        }

        whitePanel.add(centerPanel, BorderLayout.CENTER);

        // SOUTH: Replay button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        JButton replayButton = new JButton("REPLAY!");
        replayButton.setPreferredSize(new Dimension(120, 60));
        replayButton.setFont(a);
        replayButton.setBackground(Color.BLUE);
        replayButton.setForeground(Color.BLACK);

        replayButton.addActionListener(e -> {
            getContentPane().removeAll();
            genreField.setText("");
            lengthField.setText("");
            ratingField.setText("");
            add(inputPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        bottomPanel.add(replayButton);
        whitePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(whitePanel);
        revalidate();
        repaint();
    }


    // Helper method to add a poster and label
        private void addMoviePoster(JPanel panel, String imagePath, String title) {
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BorderLayout());
            moviePanel.setBackground(Color.LIGHT_GRAY);

            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
                moviePanel.add(imageLabel, BorderLayout.CENTER);
                moviePanel.add(titleLabel, BorderLayout.SOUTH);
            } catch (Exception e) {
                moviePanel.add(new JLabel("Image not found"), BorderLayout.CENTER);
            }

            panel.add(moviePanel);
        }

    public static void main(String[] args) {
        MovieView2 current = new MovieView2();
    }
}