import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MovieRecommenderView extends JFrame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 825;
    private static final int WINDOW_POSITION = 200;
    private static JButton b1;
    private static JButton b2;
    private static JButton b3;
    ActionListener a;


    public MovieRecommenderView () {
        ImageIcon img = new ImageIcon("src/main/resources/ImageIcon1.png");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocation(WINDOW_POSITION, WINDOW_POSITION);
        // Sets title of window
        this.setTitle("Movie Recommender!");
        // Makes Game exit when window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Allows user to see screen
        this.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        img = new ImageIcon("src/main/resources/ImageIcon1.png");
        System.out.println(JButton.getDefaultLocale());
        b1 = new JButton("Disable middle button", img);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");
        b2 = new JButton("Middle button", img);
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button", img);
        //Use the default text position of CENTER, TRAILING (RIGHT).
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("enable");
        b3.setEnabled(false);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(a);
        b3.addActionListener(a);

        b1.setToolTipText("Click this button to disable "
                + "the middle button.");
        b2.setToolTipText("This middle button does nothing "
                + "when you click it.");
        b3.setToolTipText("Click this button to enable the "
                + "middle button.");
    }
    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            b2.setEnabled(false);
            b1.setEnabled(false);
            b3.setEnabled(true);
        } else {
            b2.setEnabled(true);
            b1.setEnabled(true);
            b3.setEnabled(false);
        }
    }
    
    public static void main() {
        MovieRecommenderView newView = new MovieRecommenderView();
    }
}
