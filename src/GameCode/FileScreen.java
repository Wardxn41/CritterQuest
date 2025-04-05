package GameCode;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;

public class FileScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("You are in file screen");

    public FileScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(600,600);

        button1 = new JButton("Back");
        button2 = new JButton("Save 1 none of these are implented yet, they are just buttons");
        button3 = new JButton("Save 2");
        button4 = new JButton("Save 3");

        button1.addActionListener(e -> manager.setIndex(0));
        button2.addActionListener(e -> manager.setIndex(4));
        button3.addActionListener(e -> manager.setIndex(4));
        button4.addActionListener(e -> manager.setIndex(4));

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

