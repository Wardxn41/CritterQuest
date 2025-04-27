package GameCode;

import javax.swing.*;
import java.awt.*;


public abstract class WindowPanel extends JPanel {

    protected static JFrame frame = new JFrame("Application");
    protected static JPanel panel = new JPanel(new CardLayout()); // Panel that holds all screens
    protected WindowManager manager;

    public WindowPanel() {
        if (frame.getContentPane().getComponentCount() == 0) {
            frame.add(panel);
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        panel.add(this, this.getClass().getSimpleName()); // Add screen to panel by name
    }

    public void setManager(WindowManager manager) {
        this.manager = manager;
    }

    protected void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

    public static void showPanel(String screenName) {
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, screenName);
    }
    public WindowManager getManager() {
        return manager;
    }


    public abstract void calculateVisuals();

    public void ShowVisuals() {
        calculateVisuals();
        showPanel(this.getClass().getSimpleName());
    }
}



