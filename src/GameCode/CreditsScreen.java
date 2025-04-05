package GameCode;

import javax.swing.*;
import java.awt.*;

public class CreditsScreen extends WindowPanel implements ScreenInterface {

    private JButton button;
    private JLabel label = new JLabel("Credits are cool when implemented");

    public CreditsScreen() {}

    @Override
    public void calculateVisuals() {

        clearPanel();
        frame.setSize(900,900);

        button = new JButton("Back");
        button.addActionListener(e -> manager.setIndex(0));

        add(button);
        button.setAlignmentX(10);
        button.setAlignmentY(10);
        button.setBackground(Color.red);
        add(label);


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