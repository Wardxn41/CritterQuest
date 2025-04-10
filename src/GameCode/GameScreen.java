package GameCode;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1;
    private JLabel label = new JLabel("This is where the main gameplay loop is where you got a creature and such");
    private CritterStatsPanel statsPanel;
    private CritterInfo critter;
    public GameScreen() {
        //this.critter = critter;
       // this.statsPanel = new CritterStatsPanel(critter);
       // this.label = new JLabel("this is the main gameplay loop");
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        setLayout(new BorderLayout());

        this.critter = GameData.activeCritter;
        this.statsPanel = new CritterStatsPanel(critter);

        JButton backButton = new JButton("Back to pregame screen");
        backButton.addActionListener(e -> manager.setIndex(4));
        backButton.setBackground(Color.RED);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.EAST);

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
