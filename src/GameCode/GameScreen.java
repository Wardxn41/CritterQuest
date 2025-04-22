package GameCode;
import javax.swing.*;
import java.awt.*;

public class GameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1;
    private JLabel label = new JLabel("This is where the main gameplay loop is where you got a creature and such");
    private CritterStatsPanel statsPanel;
    private CritterInfo critter;
    private Image backgroundImage;
    private Image characterImage;

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

        // load background image from critter template
        try {
            String path = critter.getTemplate().getBackgroundPath();
            backgroundImage = new ImageIcon(path).getImage();
        } catch (Exception e) {
            System.err.println("Could not load critter background image.");
            e.printStackTrace();
        }
        // load character image from critter template
        try {
            String charPath = critter.getTemplate().getCharacterPath();
            characterImage = new ImageIcon(charPath).getImage();
        } catch (Exception e) {
            System.err.println("Could not load critter character image.");
            e.printStackTrace();
        }
        JButton backButton = new JButton("Back to pregame screen");
        backButton.addActionListener(e -> manager.setIndex(4));
        backButton.setBackground(Color.RED);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.EAST);

        CritterInfo critter = GameData.activeCritter;

        revalidate();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (characterImage != null) {
            // Example: center the character image
            int charWidth = characterImage.getWidth(this);
            int charHeight = characterImage.getHeight(this);
            int x = (getWidth() - charWidth) / 3;
            int y = (getHeight() - charHeight) / 3;
            g.drawImage(characterImage, x, y, this);
        }
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

}
