package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");
    private JTextField nameField;
    private Image backgroundImage;

    private String selectedSpecies;
    private int currentIndex = 0;

    private final String[] speciesOptions = {"Turtle", "Bear", "Whale", "Wolf", "Mushroom Man"};
    private final boolean[] unlocked = {true, true, true, true, true};

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
        frame.setSize(900, 700);
        setLayout(new BorderLayout());

        Font uiFont = new Font("SansSerif", Font.BOLD, 16);
        Font largeFont = new Font("SansSerif", Font.BOLD, 24);

        // Name Input (Top)
        nameField = new JTextField(20);
        nameField.setFont(uiFont);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setOpaque(false);
        add(namePanel, BorderLayout.NORTH);

        // Creature Card Carousel (Center)
        cardLayout = new CardLayout();
        cardHolderPanel = new JPanel(cardLayout);
        cardHolderPanel.setOpaque(false);
        cardHolderPanel.setPreferredSize(new Dimension(320, 480)); // size of center creature

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

// Left/Right Carousel Controls
        JButton leftArrow = new JButton("←");
        leftArrow.setFont(new Font("SansSerif", Font.BOLD, 20));
        leftArrow.setPreferredSize(new Dimension(60, 60));
        leftArrow.addActionListener(e -> {
            currentIndex = (currentIndex - 1 + speciesOptions.length) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
        });

        JButton rightArrow = new JButton("→");
        rightArrow.setFont(new Font("SansSerif", Font.BOLD, 20));
        rightArrow.setPreferredSize(new Dimension(60, 60));
        rightArrow.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
        });

// Center layout with GridBagLayout for proper vertical centering
        JPanel centerCarouselPanel = new JPanel(new GridBagLayout());
        centerCarouselPanel.setOpaque(false);

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(10, 10, 10, 10);
        cc.gridy = 0;
        cc.fill = GridBagConstraints.NONE;

        cc.gridx = 0;
        centerCarouselPanel.add(leftArrow, cc);

        cc.gridx = 1;
        centerCarouselPanel.add(cardHolderPanel, cc);

        cc.gridx = 2;
        centerCarouselPanel.add(rightArrow, cc);

// Add to the main screen center
        add(centerCarouselPanel, BorderLayout.CENTER);


        // Bottom Buttons Panel: Back | Play | Shop
        JButton backButton = new JButton("⮌");
        backButton.setFont(uiFont);
        backButton.setPreferredSize(new Dimension(80, 40));
        backButton.addActionListener(e -> manager.saveInSlot(1,manager.getSaveIndex()));

        button2 = new JButton("Play");
        button2.setFont(uiFont);
        button2.setPreferredSize(new Dimension(120, 40));
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

        JButton shopButton = new JButton("Shop");
        shopButton.setFont(uiFont);
        shopButton.setPreferredSize(new Dimension(80, 40));
        shopButton.addActionListener(e -> {
            System.out.println("Shop button clicked (not implemented yet)");
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setPreferredSize(new Dimension(900, 70)); // Fix visibility
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);
        bottomPanel.add(button2);
        bottomPanel.add(shopButton);

        add(bottomPanel, BorderLayout.SOUTH);

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

