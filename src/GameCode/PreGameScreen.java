package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1,button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");

    public PreGameScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900,900);

        button1 = new JButton("Back to file select");
        button2 = new JButton("Go to main game play screen");

        button1.addActionListener(e -> manager.setIndex(1));
        button2.addActionListener(e -> manager.setIndex(5));

        add(label);
        add(button1);

        button1.setAlignmentX(10);
        button1.setAlignmentY(10);
        button1.setBackground(Color.red);

        add(button2);

        revalidate();
        repaint();
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

}
