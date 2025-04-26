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

        // Load background and character images
        try {
            String path = critter.getTemplate().getBackgroundPath();
            backgroundImage = new ImageIcon(path).getImage();
        } catch (Exception e) {
            System.err.println("Could not load critter background image.");
            e.printStackTrace();
        }
        try {
            String charPath = critter.getTemplate().getCharacterPath();
            characterImage = new ImageIcon(charPath).getImage();
        } catch (Exception e) {
            System.err.println("Could not load critter character image.");
            e.printStackTrace();
        }

        // Create Top Panel with three subpanels
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left panel (back button)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> calculateRewards());
        leftPanel.add(backButton);

        // Center panel (one button)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton actionButton1 = new JButton("Feed");
        centerPanel.add(actionButton1);

        // Right panel (two buttons)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton actionButton2 = new JButton("Drink");
        JButton actionButton3 = new JButton("Heal");
        rightPanel.add(actionButton2);
        rightPanel.add(actionButton3);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        // Create a container panel for the left side
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setOpaque(false); // transparent if you want

// Add the statsPanel to the bottom of the container
        leftContainer.add(statsPanel, BorderLayout.SOUTH);

// Add the whole container to the left side
        add(leftContainer, BorderLayout.WEST);


        revalidate();
        repaint();
    }
    private void healCritter() {
        if (critter != null && critter.isAlive()) {
            critter.updateHealth(20); // Heal 20 points
            System.out.println(critter.getName() + " was healed!");
            statsPanel.updateStats();
        }
    }

    private void feedCritter() {
        if (critter != null && critter.isAlive()) {
            int currentHunger = critter.getHunger();
            int newHunger = Math.min(100, currentHunger + 25); // Cap at 100
            critter.setHunger(newHunger);
            System.out.println(critter.getName() + " was fed!");
            statsPanel.updateStats();
        }
    }

    private void giveWaterToCritter() {
        if (critter != null && critter.isAlive()) {
            int currentThirst = critter.getThirst();
            int newThirst = Math.min(100, currentThirst + 25); // Cap at 100
            critter.setThirst(newThirst);
            System.out.println(critter.getName() + " drank water!");
            statsPanel.updateStats();
        }
    }

    //basically just the you are quiting your creature and getting your critter bucks for it
    private void calculateRewards() {

        PlayerInfo info = manager.getPlayerInfo();
        //info.setCritterBucks(info.getCritterBucks()+info.getCritter().getAge()); getAge doesent exist for some reason
        info.setCritterBucks(info.getCritterBucks()+11.6);
        manager.setIndex(4);

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
