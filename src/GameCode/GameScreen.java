package GameCode;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class GameScreen extends WindowPanel implements ScreenInterface {

    private CritterInfo critter;
    private CritterStatsPanel statsPanel;
    private Image backgroundImage;
    private Image characterImage;
    private JLabel moneyLabel;
    private RandomEventManager randomEventManager;
    private Timer randomEventTimer;
    private JButton feedButton, drinkButton, healButton, shopButton;
    private Timer incomeTimer;
    private int happyTimer = 0;
    private int lastBirthdayAge = 0;
    private boolean hasHandledDeath = false;
    private FadeOverlay fadeOverlay;

    public GameScreen() {}

    private void updateMoneyHUD() {
        moneyLabel.setText("Critter Bucks: $" + manager.getPlayerInfo().getCritterBucks());
    }

    private String determineMood() {
        if (!critter.isAlive()) return "Dead";

        int health = critter.getHealth();
        int hunger = critter.getHunger();
        int thirst = critter.getThirst();

        if (health > 80 && hunger > 80 && thirst > 80) return "Happy";
        if (hunger < 30 && thirst < 30) return "Starving & Dehydrated";
        if (hunger < 30) return "Hungry";
        if (thirst < 30) return "Thirsty";
        if (health < 30) return "Sick";
        return "Content";
    }

    private void checkPassiveIncome() {
        PlayerInfo player = manager.getPlayerInfo();
        String mood = determineMood();

        if (mood.equals("Happy") || mood.equals("Content")) {
            happyTimer++;
            if (happyTimer >= 30) {
                player.setCritterBucks(player.getCritterBucks() + 1);
                updateMoneyHUD();
                happyTimer = 0;
                System.out.println("Gained $1 for keeping the critter happy!");
            }
        } else {
            happyTimer = 0;
        }

        int displayAge = critter.getDisplayAge();
        if (displayAge > lastBirthdayAge) {
            lastBirthdayAge = displayAge;
            player.setCritterBucks(player.getCritterBucks() + 20);
            updateMoneyHUD();

            JOptionPane.showMessageDialog(this,
                    "🎉 Happy Birthday, " + critter.getName() + "! 🎂\nYou earned 20 Critter Bucks!",
                    "Birthday Celebration!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void checkCritterDeath() {
        if (critter != null && !critter.isAlive() && !hasHandledDeath) {
            hasHandledDeath = true;
            stopIncomeTimer();

            feedButton.setEnabled(false);
            drinkButton.setEnabled(false);
            healButton.setEnabled(false);

            new Thread(() -> {
                try {
                    // Fade only to 75% opacity at first
                    for (int i = 0; i <= 75; i++) {
                        float opacity = i / 100f;
                        fadeOverlay.setOpacity(opacity);
                        Thread.sleep(10);
                    }

                    // Now on UI thread, show popup
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this,
                                critter.getName() + " has passed away... 💀",
                                "Critter Death",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // After clicking OK, finish fading to 100%
                        new Thread(() -> {
                            try {
                                for (int i = 76; i <= 100; i++) {
                                    float opacity = i / 100f;
                                    fadeOverlay.setOpacity(opacity);
                                    Thread.sleep(10);
                                }
                                SwingUtilities.invokeLater(() -> manager.setIndex(8));
                                fadeOverlay.setOpacity(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



    private void stopIncomeTimer() {
        if (incomeTimer != null) {
            incomeTimer.cancel();
            incomeTimer = null;
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        setLayout(new BorderLayout());
        fadeOverlay = new FadeOverlay();
        fadeOverlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getLayeredPane().add(fadeOverlay, JLayeredPane.DRAG_LAYER); // add on top

        this.hasHandledDeath = false;
        this.critter = GameData.activeCritter;
        randomEventManager = new RandomEventManager(this);
        this.statsPanel = new CritterStatsPanel(critter);

        // Load Images
        loadImages();

        // Top Panel (Back, Money, Actions)
        JPanel topPanel = buildTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Left Container for Stats
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setOpaque(false);
        leftContainer.add(statsPanel, BorderLayout.SOUTH);
        add(leftContainer, BorderLayout.WEST);

        // Start Passive Income Timer
        startIncomeTimer();

        revalidate();
        repaint();
    }

    private void loadImages() {
        try {
            String bgPath = critter.getTemplate().getBackgroundPath();
            backgroundImage = new ImageIcon(bgPath).getImage();

            String charPath = critter.getTemplate().getCharacterPath();
            characterImage = new ImageIcon(charPath).getImage();
        } catch (Exception e) {
            System.err.println("Could not load critter images.");
            e.printStackTrace();
        }
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Back Button
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> calculateRewards());
        leftPanel.add(backButton);

        // Money HUD
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        moneyLabel = new JLabel("Critter Bucks: $" + manager.getPlayerInfo().getCritterBucks());
        moneyLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        moneyLabel.setForeground(Color.BLACK);
        centerPanel.add(moneyLabel);

        // Action Buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        feedButton = new JButton("Feed");
        drinkButton = new JButton("Drink");
        healButton = new JButton("Heal");
        shopButton = new JButton("Shop");

        feedButton.addActionListener(e -> attemptAction(() -> feedCritter(), 10));
        drinkButton.addActionListener(e -> attemptAction(() -> giveWaterToCritter(), 8));
        healButton.addActionListener(e -> attemptAction(() -> healCritter(), 15));
        shopButton.addActionListener(e -> manager.setIndex(7)); // Shop screen assumed to be index 7

        rightPanel.add(feedButton);
        rightPanel.add(drinkButton);
        rightPanel.add(healButton);
        rightPanel.add(shopButton);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    private void attemptAction(Runnable action, int cost) {
        PlayerInfo player = manager.getPlayerInfo();
        if (player.getCritterBucks() >= cost) {
            player.setCritterBucks(player.getCritterBucks() - cost);
            action.run();
            updateMoneyHUD();
        } else {
            System.out.println("Not enough Critter Bucks!");
        }
    }

    private void startIncomeTimer() {
        incomeTimer = new Timer(true);
        incomeTimer.scheduleAtFixedRate(new TimerTask() {
            private int secondsPassed = 0;

            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (critter != null) {
                        critter.tickUpdate(System.currentTimeMillis());
                        statsPanel.updateStats();
                        checkPassiveIncome();
                        checkCritterDeath();

                        secondsPassed++;
                        if (secondsPassed % 30 == 0) { // every 30 seconds
                            randomEventManager.tryTriggerRandomEvent();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void healCritter() {
        if (critter != null && critter.isAlive()) {
            critter.updateHealth(20);
            System.out.println(critter.getName() + " was healed!");
            statsPanel.updateStats();
        }
    }

    private void feedCritter() {
        if (critter != null && critter.isAlive()) {
            critter.setHunger(Math.min(100, critter.getHunger() + 25));
            System.out.println(critter.getName() + " was fed!");
            statsPanel.updateStats();
        }
    }

    private void giveWaterToCritter() {
        if (critter != null && critter.isAlive()) {
            critter.setThirst(Math.min(100, critter.getThirst() + 25));
            System.out.println(critter.getName() + " drank water!");
            statsPanel.updateStats();
        }
    }

    private void calculateRewards() {
        PlayerInfo info = manager.getPlayerInfo();
        info.setCritterBucks((int) (info.getCritterBucks() + 10));
        manager.setIndex(4); // Go back to pregame
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (critter != null && characterImage != null) {
            int newWidth;
            int newHeight;

            if (critter.getSpecies().equals("Whale")) {
                newWidth = 600;
                newHeight = 500;
            } else {
                newWidth = 300;
                newHeight = 300;
            }

            int x = (getWidth() - newWidth) / 2;
            int y = getHeight() - newHeight - 50;

            g.drawImage(characterImage, x, y, newWidth, newHeight, this);
        }
    }



    @Override
    public void clearPanel() {
        removeAll();
        if (incomeTimer != null) {
            incomeTimer.cancel();
        }
        if (randomEventTimer != null) {
            randomEventTimer.cancel();
        }
        revalidate();
        repaint();
    }
}

