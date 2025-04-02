package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//miles note 4/1 : this is the temp version so we should convert it to the idea shown through the window manager class


public class TitleScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("You are in title screen");

    public TitleScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(300,300);

        frame.setTitle("Options Screen");

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