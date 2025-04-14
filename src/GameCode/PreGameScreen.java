package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");
    private JTextField nameField;
    private Image backgroundImage;

    private String selectedSpecies = "Turtle";
    private int currentIndex = 0;

    private final String[] speciesOptions = {"Turtle", "Bear", "Whale", "Wolf", "Mushroom Man"};
    private final boolean[] unlocked = {true, false, false, false, false};

    private CardLayout cardLayout;
    private JPanel cardHolderPanel;

    public PreGameScreen() {
        try {
            backgroundImage = new ImageIcon("images/PreGameBackground.png").getImage();
        } catch (Exception e) {
            System.err.println("Could not load background image");
            e.printStackTrace();
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900, 900);
        setLayout(new BorderLayout());

        Font uiFont = new Font("SansSerif", Font.BOLD, 16);
        Font largeFont = new Font("SansSerif", Font.BOLD, 24);

        // Back Button
        button1 = new JButton("⮌");
        button1.setFont(uiFont);
        button1.setPreferredSize(new Dimension(60, 60));
        button1.addActionListener(e -> manager.setIndex(1));

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(button1);
        topLeftPanel.setOpaque(false);
        add(topLeftPanel, BorderLayout.NORTH);

        // Name input
        nameField = new JTextField(15);
        nameField.setFont(uiFont);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setOpaque(false);

        // Card-based Creature Selector
        cardLayout = new CardLayout();
        cardHolderPanel = new JPanel(cardLayout);
        cardHolderPanel.setOpaque(false);
        cardHolderPanel.setPreferredSize(new Dimension(220, 180));

        for (int i = 0; i < speciesOptions.length; i++) {
            String species = speciesOptions[i];
            boolean isUnlocked = unlocked[i];

            CreatureSelectorButton btn = new CreatureSelectorButton(species, 100, 100, e -> {
                if (isUnlocked) {
                    selectedSpecies = ((CreatureSelectorButton) e.getSource()).getSpeciesName();
                    System.out.println("Selected species: " + selectedSpecies);
                }
            });

            btn.setLocked(!isUnlocked);

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            wrapper.setOpaque(false);
            wrapper.add(btn);
            cardHolderPanel.add(wrapper, species);
        }

        // Navigation Buttons
        JButton leftButton = new JButton("←");
        leftButton.setFont(largeFont);
        leftButton.addActionListener(e -> {
            currentIndex = (currentIndex - 1 + speciesOptions.length) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
        });

        JButton rightButton = new JButton("→");
        rightButton.setFont(largeFont);
        rightButton.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
        });

        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setOpaque(false);
        navPanel.add(leftButton, BorderLayout.WEST);
        navPanel.add(cardHolderPanel, BorderLayout.CENTER);
        navPanel.add(rightButton, BorderLayout.EAST);

        // Play Button
        button2 = new JButton("Play!");
        button2.setFont(uiFont);
        button2.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Critter";

            CritterInfo newCritter;
            switch (selectedSpecies) {
                case "Whale": newCritter = CritterFactory.createWhale(name); break;
                case "Mushroom Man": newCritter = CritterFactory.createMushroom_Man(name); break;
                case "Bear": newCritter = CritterFactory.createBear(name); break;
                case "Turtle": newCritter = CritterFactory.createTurtle(name); break;
                case "Wolf": newCritter = CritterFactory.createWolf(name); break;
                default: return;
            }

            GameData.activeCritter = newCritter;
            manager.setIndex(5);
        });

        // Center Layout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(navPanel, gbc);

        gbc.gridy++;
        centerPanel.add(namePanel, gbc);

        gbc.gridy++;
        centerPanel.add(button2, gbc);

        add(centerPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
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

