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
        int budget = 1000;
        Movie userMovie = new Movie(genre, lengthField.getText(), ratingField.getText());

        ArrayList<Movie> recommended = MovieRecommender.closest(userMovie);

        // Limit to 3 movies max to avoid layout issues
        int maxMovies = Math.min(3, recommended.size());

        // Create center panel with BoxLayout and spacing
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(Color.LIGHT_GRAY);

        centerPanel.add(Box.createHorizontalGlue());

        for (int i = 0; i < maxMovies; i++) {
            Movie movie = recommended.get(i);

            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.Y_AXIS));
            moviePanel.setBackground(Color.LIGHT_GRAY);
            moviePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            moviePanel.setMaximumSize(new Dimension(220, 450));

            // Poster
            try {
                if (movie.getImages() != null && !movie.getImages().isEmpty()) {
                    String posterPath = movie.getImages().getFirst().getFilePath();
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
            moviePanel.add(Box.createVerticalStrut(10));

            // Overview with line wrapping
            JTextArea overviewArea = new JTextArea("Overview:\n" + movie.getOverview());
            overviewArea.setLineWrap(true);
            overviewArea.setWrapStyleWord(true);
            overviewArea.setEditable(false);
            overviewArea.setOpaque(false);
            overviewArea.setMaximumSize(new Dimension(200, 100));
            overviewArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            overviewArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            moviePanel.add(overviewArea);

            centerPanel.add(moviePanel);
            if (i < maxMovies - 1) {
                centerPanel.add(Box.createHorizontalStrut(80)); // adds space between movie panels
            }

        }

        // Wrap centerPanel for vertical centering
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerWrapper.add(centerPanel, gbc);

        whitePanel.add(centerWrapper, BorderLayout.CENTER);

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

    public static void main(String[] args) {
        MovieView2 current = new MovieView2();
    }
}