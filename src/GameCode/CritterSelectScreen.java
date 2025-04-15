package GameCode;

import javax.swing.*;
import java.awt.*;

/**
 * Screen for selecting a starting critter.
 * Provides interactive buttons that let the user pick a species
 * which is then instantiated and stored globally for the game.
 */
public class CritterSelectScreen extends WindowPanel implements ScreenInterface {

    private JPanel buttonPanel;     // Container for critter buttons
    private JLabel titleLabel;      // Screen title
    private JButton backButton;     // Navigation back button

    // List of selectable species
    private final String[] speciesOptions = {"Turtle", "Whale", "Mushroom Man", "Bear", "Wolf"};
    // Placeholder: determine unlock state per species
    private final boolean[] unlocked = {true, true, false, false, false}; // Replace with real logic

    public CritterSelectScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900, 700);
        setLayout(new BorderLayout());

        // Title at the top
        titleLabel = new JLabel("Select Your Critter");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Center grid of critter buttons
        buttonPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < speciesOptions.length; i++) {
            String species = speciesOptions[i];
            boolean isUnlocked = unlocked[i];

            CreatureSelectorButton button = new CreatureSelectorButton(species, 100, 100, e -> {
                if (!isUnlocked) return; // Ignore clicks on locked critters
                CritterInfo critter = getCritterFromFactory(species, species);
                GameData.activeCritter = critter;
                manager.setIndex(6); // Go to GameScreen
            });

            button.setLocked(!isUnlocked);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Bottom: Back button
        backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.addActionListener(e -> manager.setIndex(0)); // Return to title

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    /**
     * Factory lookup based on species name.
     */
    private CritterInfo getCritterFromFactory(String species, String name) {
        switch (species) {
            case "Whale": return CritterFactory.createWhale(name);
            case "Mushroom Man": return CritterFactory.createMushroom_Man(name);
            case "Bear": return CritterFactory.createBear(name);
            case "Wolf": return CritterFactory.createWolf(name);
            default: return CritterFactory.createTurtle(name);
        }
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }
}