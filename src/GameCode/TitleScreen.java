package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//miles note 4/1 : this is the temp version so we should convert it to the idea shown through the window manager class


public class TitleScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("You are in title screen");
    private BufferedImage backgroundImage;
    public TitleScreen() {
        try {
            backgroundImage = ImageIO.read(new File("images/TitleBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(800, 600); // Match image size or desired resolution
        frame.setTitle("CritterQuest");

        // Style your buttons and position them manually
        button1 = new JButton("Start Game");
        button2 = new JButton("Options");
        button3 = new JButton("Credits");
        button4 = new JButton("Exit");

        // Example positions
        button1.setBounds(300, 300, 200, 40);
        button2.setBounds(300, 350, 200, 40);
        button3.setBounds(300, 400, 200, 40);
        button4.setBounds(300, 450, 200, 40);

        button1.addActionListener(e -> manager.setIndex(0));
        button2.addActionListener(e -> manager.setIndex(1));
        button3.addActionListener(e -> manager.setIndex(2));
        button4.addActionListener(e -> System.exit(0));

        add(button1);
        add(button2);
        add(button3);
        add(button4);
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

}