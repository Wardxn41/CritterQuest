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

        button1 = new JButton("Go to Screen 2");
        button2 = new JButton("Go to Screen 3");
        button3 = new JButton("Go to Screen 4");
        button4 = new JButton("Exit");

        button1.addActionListener(e -> manager.setIndex(0));
        button2.addActionListener(e -> manager.setIndex(1));
        button3.addActionListener(e -> manager.setIndex(2));
        button4.addActionListener(e -> System.exit(0));

        add(label);
        add(button1);
        add(button2);
        add(button3);
        add(button4);


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

