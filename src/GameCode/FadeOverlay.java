package GameCode;

import javax.swing.*;
import java.awt.*;

public class FadeOverlay extends JPanel {
    private float opacity = 0f; // 0 = transparent, 1 = fully black

    public FadeOverlay() {
        setOpaque(false);
    }

    public void setOpacity(float value) {
        this.opacity = Math.min(1f, Math.max(0f, value)); // Clamp 0 to 1
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (opacity > 0f) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}
