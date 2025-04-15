package GameCode;

import javax.swing.*;
import java.awt.*;


public class OptionsScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("You are in options screen");

    public OptionsScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900,900);

        button1 = new JButton("Back");
        button2 = new JButton("option 1");
        button3 = new JButton("option 2");
        button4 = new JButton("option 3 (obviously they get replaced with actual options, whatever they may be (color schemes?))");

        button1.addActionListener(e -> manager.setIndex(0));
        button2.addActionListener(e -> manager.setIndex(2));
        button3.addActionListener(e -> manager.setIndex(2));
        button4.addActionListener(e -> manager.setIndex(2));

        add(label);
        add(button1);
        add(button2);
        add(button3);
        add(button4);

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
