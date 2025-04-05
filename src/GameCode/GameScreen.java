package GameCode;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1;
    private JLabel label = new JLabel("This is where the main gameplay loop is where you got a creature and such");

    public GameScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900,900);

        button1 = new JButton("Back to pregame screen");

        button1.addActionListener(e -> manager.setIndex(4));

        add(label);
        add(button1);

        button1.setAlignmentX(10);
        button1.setAlignmentY(10);
        button1.setBackground(Color.red);

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
