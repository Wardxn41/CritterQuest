package GameCode;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class CritterStatsPanel extends JPanel {
    private CritterInfo critter;
    private JLabel nameLabel;
    private JLabel statusLabel;
    private JLabel moodLabel;
    private JProgressBar healthBar;
    private JProgressBar hungerBar;
    private JProgressBar thirstBar;

    public CritterStatsPanel(CritterInfo critter) {
        this.critter = critter;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Critter Stats"));
        setOpaque(false);

        nameLabel = makeStyledLabel();
        statusLabel = makeStyledLabel();
        moodLabel = makeStyledLabel();

        healthBar = makeStyledBar(Color.RED, critter.getTemplate().maxHealth);
        hungerBar = makeStyledBar(Color.ORANGE, 100);
        thirstBar = makeStyledBar(Color.CYAN, 100);

        add(createRow(nameLabel));
        add(createRow(healthBar));
        add(createRow(hungerBar));
        add(createRow(thirstBar));
        add(createRow(statusLabel));
        add(createRow(moodLabel));

        updateStats();

        // Update every second
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    critter.tickUpdate(System.currentTimeMillis());
                    updateStats();

                });
            }
        }, 0, 1000);
    }

    private JPanel createRow(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(component);
        panel.setMaximumSize(new Dimension(300, 30)); // Make the row thinner
        return panel;
    }

    private JLabel makeStyledLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        return label;
    }

    private JProgressBar makeStyledBar(Color color, int maxValue) {
        JProgressBar bar = new JProgressBar(0, maxValue);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(Color.DARK_GRAY);
        bar.setFont(new Font("Verdana", Font.BOLD, 10));
        bar.setPreferredSize(new Dimension(200, 20)); // smaller and thinner
        return bar;
    }

    public void updateStats() {
        nameLabel.setText("Name: " + critter.getName());

        healthBar.setValue(critter.getHealth());
        healthBar.setString("Health: " + critter.getHealth() + "/" + critter.getTemplate().maxHealth);

        hungerBar.setValue(critter.getHunger());
        hungerBar.setString("Hunger: " + critter.getHunger() + "/100");

        thirstBar.setValue(critter.getThirst());
        thirstBar.setString("Thirst: " + critter.getThirst() + "/100");

        statusLabel.setText("Status: " + (critter.isAlive() ? "Alive" : "Dead"));
        moodLabel.setText("Mood: " + determineMood());
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
}

