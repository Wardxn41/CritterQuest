package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitleScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4, button5;
    private JLabel label = new JLabel("You are in title screen");
    private BufferedImage backgroundImage;
    private BufferedImage titleImage;

    public TitleScreen() {
        try {
            backgroundImage = ImageIO.read(new File("images/background/TitleBackground.png"));
            titleImage = ImageIO.read(new File("images/icons/TitleScreenTitle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (titleImage != null) {
            int scaledWidth = titleImage.getWidth() / 2;   // adjust scale here
            int scaledHeight = titleImage.getHeight() / 2;

            int x = (getWidth() - scaledWidth) / 2; // center horizontally
            int y = 120; // vertical offset

            g2d.drawImage(titleImage, x, y, scaledWidth, scaledHeight, this);
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(800, 600); // Match image size or desired resolution
        frame.setTitle("CritterQuest");

        // Create buttons
        button1 = new JButton("Start Game");
        button2 = new JButton("Options");
        button3 = new JButton("Credits");
        button4 = new JButton("Exit");
        button5 = new JButton("Critter Select"); // New button

        // Position buttons
        button1.setBounds(300, 300, 200, 40);
        button2.setBounds(300, 350, 200, 40);
        button3.setBounds(300, 400, 200, 40);
        button4.setBounds(300, 450, 200, 40);
        button4.setBackground(manager.getBackButtonColor());
        button5.setBounds(300, 500, 200, 40); // New button

        // Button actions
        button1.addActionListener(e -> manager.setIndex(1));
        button2.addActionListener(e -> manager.setIndex(2));
        button3.addActionListener(e -> manager.setIndex(3));
        button4.addActionListener(e -> System.exit(0));
        button5.addActionListener(e -> manager.setIndex(6)); // Goes to CritterSelectScreen

        // Add buttons
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        //add(button5); supress adding this button

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
