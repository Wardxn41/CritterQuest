package GameCode;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;

public class FileScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("You are in file screen");
    private Image backgroundImage;
    public FileScreen() {
        try {
            backgroundImage = new ImageIcon("images/PreGameBackground.png").getImage(); // Same image as before
        } catch (Exception e) {
            System.err.println("Could not load background image");
            e.printStackTrace();
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(600, 600);

        Font uiFont = new Font("SansSerif", Font.BOLD, 14);

        button1 = new JButton("Back");
        button2 = new JButton("Save 1 (not implemented)");
        button3 = new JButton("Save 2");
        button4 = new JButton("Save 3");

        button1.setFont(uiFont);
        button2.setFont(uiFont);
        button3.setFont(uiFont);
        button4.setFont(uiFont);

        button1.setBackground(Color.RED);

        button1.addActionListener(e -> manager.setIndex(0));
        button2.addActionListener(e -> manager.setIndex(4));
        button3.addActionListener(e -> manager.setIndex(4));
        button4.addActionListener(e -> manager.setIndex(4));

        // Make UI elements transparent
        label.setOpaque(false);
        label.setForeground(Color.WHITE);
        label.setFont(uiFont);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(label);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(button1);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(button2);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(button3);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(button4);

        // Center everything
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 40));
        container.setOpaque(false);
        container.add(buttonPanel);

        add(container, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

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

