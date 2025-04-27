package GameCode;

import javax.swing.*;
import java.awt.*;

public class ShopScreen extends WindowPanel implements ScreenInterface {

    private Image backgroundImage;
    private int returnScreen = 5; // Default back to GameScreen

    public ShopScreen() {
        try {
            backgroundImage = new ImageIcon("images/background/shopBackground.png").getImage();
        } catch (Exception e) {
            System.err.println("Could not load Shop background");
            e.printStackTrace();
        }
    }

    public void setReturnScreen(int index) {
        this.returnScreen = index;
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        setLayout(new BorderLayout());

        JPanel upgradeGrid = new JPanel(new GridBagLayout());
        upgradeGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40); // spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] upgradeTypes = {"Thirst", "Hunger", "Healing"};

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 4; row++) {
                JButton upgradeButton = new JButton("Level " + (row + 1)); // <<<<<<<<<< LEVEL X LABEL
                upgradeButton.setPreferredSize(new Dimension(140, 40));

                int upgradeCost = 20 + (row * 10);
                int finalCol = col;
                int finalRow = row;

                boolean purchased = finalRow < getCurrentLevel(upgradeTypes[finalCol]);
                upgradeButton.setEnabled(!purchased);
                if (purchased) {
                    upgradeButton.setBackground(Color.LIGHT_GRAY);
                }

                upgradeButton.addActionListener(e -> handleUpgradePurchase(upgradeTypes[finalCol], finalRow, upgradeCost));

                gbc.gridx = col;
                gbc.gridy = row;
                upgradeGrid.add(upgradeButton, gbc);
            }
        }

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setOpaque(false);
        centerWrapper.add(Box.createVerticalStrut(120));
        centerWrapper.add(upgradeGrid);

        add(centerWrapper, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Verdana", Font.BOLD, 18));
        backButton.addActionListener(e -> manager.setIndex(returnScreen));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }


    private int getCurrentLevel(String type) {
        PlayerInfo player = manager.getPlayerInfo();
        return switch (type) {
            case "Thirst" -> player.getThirstUpgradeLevel();
            case "Hunger" -> player.getHungerUpgradeLevel();
            case "Healing" -> player.getHealingUpgradeLevel();
            default -> 0;
        };
    }

    private void handleUpgradePurchase(String type, int level, int cost) {
        PlayerInfo player = manager.getPlayerInfo();

        int currentLevel = getCurrentLevel(type);

        if (level != currentLevel) {
            JOptionPane.showMessageDialog(this, "Buy upgrades in order!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!player.canAfford(cost)) {
            JOptionPane.showMessageDialog(this, "Not enough Critter Bucks!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean upgraded = false;
        switch (type) {
            case "Thirst" -> upgraded = player.upgradeThirst();
            case "Hunger" -> upgraded = player.upgradeHunger();
            case "Healing" -> upgraded = player.upgradeHealing();
        }

        if (upgraded) {
            player.setCritterBucks(player.getCritterBucks() - cost);
            JOptionPane.showMessageDialog(this, "Upgrade purchased!", "Success", JOptionPane.INFORMATION_MESSAGE);
            manager.saveInSlot(returnScreen, player.getSaveFile()); // Save after buying
        } else {
            JOptionPane.showMessageDialog(this, "Already at max level!", "Info", JOptionPane.WARNING_MESSAGE);
        }

        calculateVisuals(); // Refresh buttons
    }

    @Override
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



