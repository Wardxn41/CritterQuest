package GameCode;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class GameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1;
    private JLabel label = new JLabel("This is where the main gameplay loop is where you got a creature and such");
    private CritterStatsPanel statsPanel;
    private CritterInfo critter;
    private Image backgroundImage;
    private Image characterImage;
    private JLabel moneyLabel;
    private Timer incomeTimer;
    private int happyTimer = 0;
    private int lastRewardedAge = 0;
    private int lastBirthdayAge = 0;

    private void updateMoneyHUD() {
        moneyLabel.setText("Critter Bucks: $" + manager.getPlayerInfo().getCritterBucks());
    }
    public GameScreen() {
        //this.critter = critter;
       // this.statsPanel = new CritterStatsPanel(critter);
       // this.label = new JLabel("this is the main gameplay loop");
    }

    private String determineMood() {
        if (!critter.isAlive()) {
            return "Dead";
        }

        int health = critter.getHealth();
        int hunger = critter.getHunger();
        int thirst = critter.getThirst();

        if (health > 80 && hunger > 80 && thirst > 80) {
            return "Happy";
        } else if (hunger < 30 && thirst < 30) {
            return "Starving & Dehydrated";
        } else if (hunger < 30) {
            return "Hungry";
        } else if (thirst < 30) {
            return "Thirsty";
        } else if (health < 30) {
            return "Sick";
        } else {
            return "Content";
        }
    }
    private void checkPassiveIncome() {
        PlayerInfo player = manager.getPlayerInfo();

        // 1. Happiness Bonus every 30 seconds
        String mood = determineMood();
        if (mood.equals("Happy")) {
            happyTimer++;
            if (happyTimer >= 30) {
                player.setCritterBucks(player.getCritterBucks() + 1);
                updateMoneyHUD();
                happyTimer = 0;
                System.out.println("Gained $1 for keeping the critter happy!");
            }
        } else {
            happyTimer = 0; // Reset if not happy
        }

        // 2. Birthday Celebration (every 2 minutes = 1 year)
        int displayAge = critter.getDisplayAge();
        if (displayAge > lastBirthdayAge) {
            // New year reached!
            lastBirthdayAge = displayAge;

            // Reward CritterBucks for birthday
            player.setCritterBucks(player.getCritterBucks() + 20);
            updateMoneyHUD();

            // Show Birthday Popup
            JOptionPane.showMessageDialog(this,
                    "🎉 Happy Birthday, " + critter.getName() + "! 🎂\nYou earned 20 Critter Bucks!",
                    "Birthday Celebration!",
                    JOptionPane.INFORMATION_MESSAGE
            );

            System.out.println(critter.getName() + " had a birthday! 🎂");
        }

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

        // Create Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left Panel (Back button)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> calculateRewards());
        leftPanel.add(backButton);

        // Center Panel (Money HUD)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        moneyLabel = new JLabel("Critter Bucks: $" + manager.getPlayerInfo().getCritterBucks());
        moneyLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        moneyLabel.setForeground(Color.YELLOW);
        centerPanel.add(moneyLabel);

        // Right Panel (Feed, Drink, Heal, Shop buttons)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton actionButton1 = new JButton("Feed");
        JButton actionButton2 = new JButton("Drink");
        JButton actionButton3 = new JButton("Heal");
        JButton shopButton = new JButton("Shop");
        rightPanel.add(actionButton1);
        rightPanel.add(actionButton2);
        rightPanel.add(actionButton3);
        rightPanel.add(shopButton);

        // Assemble top panel
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Left Container for Stats Panel
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setOpaque(false);
        leftContainer.add(statsPanel, BorderLayout.SOUTH);
        add(leftContainer, BorderLayout.WEST);
        // Start Passive Income Timer
        incomeTimer = new Timer(true);
        incomeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (critter != null) {
                        critter.tickUpdate(System.currentTimeMillis()); // Update critter needs
                        statsPanel.updateStats(); // Refresh health/hunger/thirst/mood display
                        checkPassiveIncome();
                    }
                });
            }
        }, 0, 1000); // every 1 second


        // Button Actions with Money Costs
        actionButton1.addActionListener(e -> {
            if (manager.getPlayerInfo().buy(10)) { // Feed cost 10
                feedCritter();
                updateMoneyHUD();
                System.out.println("Fed the critter!");
            } else {
                System.out.println("Not enough Critter Bucks to feed!");
            }
        });

        actionButton2.addActionListener(e -> {
            if (manager.getPlayerInfo().buy(8)) { // Drink cost 8
                giveWaterToCritter();
                updateMoneyHUD();
                System.out.println("Gave the critter water!");
            } else {
                System.out.println("Not enough Critter Bucks to drink!");
            }
        });

        actionButton3.addActionListener(e -> {
            if (manager.getPlayerInfo().buy(15)) { // Heal cost 15
                healCritter();
                updateMoneyHUD();
                System.out.println("Healed the critter!");
            } else {
                System.out.println("Not enough Critter Bucks to heal!");
            }
        });

        shopButton.addActionListener(e -> {
            manager.setIndex(7); // Assuming your ShopScreen is screen 5
        });

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
        info.setCritterBucks((int) (info.getCritterBucks()+11.6));
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
