import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieView2 extends JFrame {
    private JTextField genreField, lengthField, ratingField;
    private JPanel inputPanel, goPanel;

    public MovieView2() {
        setTitle("Movie Recommender");
        setSize(1024, 825);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel with labels and text fields
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY); // for visibility
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
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
        goButton.setPreferredSize(new Dimension(120, 40));
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextScreen();
            }
        });
        goPanel.add(goButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.CENTER);
        add(goPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showNextScreen() {
        getContentPane().removeAll();
        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        add(whitePanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieView2());
    }
}