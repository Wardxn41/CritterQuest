package GameCode;
import javax.swing.*;
import java.awt.*;
public class BackgroundPanel extends JPanel {
    private Image background;

    public BackgroundPanel(String imagePath, LayoutManager layout) {
        super(layout);
        try {
            background = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + imagePath);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

