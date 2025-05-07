import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieView2 extends JFrame {
    private JTextField genreField, lengthField, ratingField;
    private JPanel inputPanel, goPanel;
    private MovieRecommender back;

    public MovieView2() {
        back = new MovieRecommender();

        setTitle("Movie Recommender");
        setSize(1024, 825);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel with labels and text fields
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY); // for visibility
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
        JButton goButton = new JButton("GO");
        //goButton.setPreferredSize(new Dimension(250, 40));
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
        whitePanel.setBackground(Color.PINK);
        whitePanel.setLayout(new BorderLayout());

        // Top panel for text info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.PINK);
        Movie input = new Movie(genreField.getText(), lengthField.getText(), ratingField.getText());
        Movie a = MovieRecommender.closest(input);
        infoPanel.add(new JLabel(String.valueOf(a)));
        infoPanel.add(new JLabel("Genre: " + genreField.getText()));
        infoPanel.add(new JLabel("Length: " + lengthField.getText()));
        infoPanel.add(new JLabel("Rating: " + ratingField.getText()));
        whitePanel.add(infoPanel, BorderLayout.NORTH);

        // Center panel for movie posters and titles
        JPanel moviesPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        moviesPanel.setBackground(Color.PINK);

        // Example posters and labels
        addMoviePoster(moviesPanel, "poster1.jpg", "Movie 1");
        addMoviePoster(moviesPanel, "poster2.jpg", "Movie 2");
        addMoviePoster(moviesPanel, "poster3.jpg", "Movie 3");

        whitePanel.add(moviesPanel, BorderLayout.CENTER);

        add(whitePanel);
        revalidate();
        repaint();
    }

    // Helper method to add a poster and label
        private void addMoviePoster(JPanel panel, String imagePath, String title) {
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BorderLayout());
            moviePanel.setBackground(Color.WHITE);

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