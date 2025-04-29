package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverScreen extends WindowPanel implements ScreenInterface {

    private Image backgroundImage;

    public GameOverScreen() {
        try {
            backgroundImage = new ImageIcon("images/background/GameOverBackground.png").getImage();
        } catch (Exception e) {
            System.err.println("Could not load Game Over background");
            e.printStackTrace();
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setBackground(manager.getBackButtonColor());
        mainMenuButton.setFont(new Font("Verdana", Font.BOLD, 20));
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.addActionListener(e -> manager.setIndex(0)); // Assuming TitleScreen is index 0

        JButton creditsButton = new JButton("View Credits");
        creditsButton.setFont(new Font("Verdana", Font.BOLD, 20));
        creditsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsButton.addActionListener(e -> manager.setIndex(3)); // Let's say CreditsScreen is index 8

        add(Box.createVerticalStrut(100)); // space at top
        add(titleLabel);
        add(Box.createVerticalStrut(50));
        add(mainMenuButton);
        add(Box.createVerticalStrut(20));
        add(creditsButton);

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }
}

