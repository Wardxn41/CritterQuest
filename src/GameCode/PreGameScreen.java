package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2;
    private JTextField nameField;
    private Image backgroundImage;
    private int currentIndex = 0;
    private final String[] speciesOptions = {"Turtle", "Bear", "Whale", "Wolf", "Mushroom Man"};
    private final boolean[] unlocked = {true, true, true, true, true};
    private CardLayout cardLayout;
    private JPanel cardHolderPanel;
    private JPanel starPanel;
    private JLabel descriptionLabel;

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

        // --- Name Input Panel ---
        nameField = new JTextField(20);
        nameField.setFont(uiFont);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(uiFont);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setOpaque(false);

        // --- Creature Card Carousel ---
        cardLayout = new CardLayout();
        cardHolderPanel = new JPanel(cardLayout);
        cardHolderPanel.setOpaque(false);
        cardHolderPanel.setPreferredSize(new Dimension(320, 480));

        for (String species : speciesOptions) {
            JLabel imageLabel;
            try {
                String imagePath = switch (species) {
                    case "Turtle" -> "images/creatures/turtleCritter_transparent.png";
                    case "Whale" -> "images/creatures/whaleCritter_transparent.png";
                    case "Mushroom Man" -> "images/creatures/mushroomManCritter_transparent.png";
                    case "Bear" -> "images/creatures/bearCritter_transparent.png";
                    case "Wolf" -> "images/creatures/wolfCritter_transparent.png";
                    default -> "images/creatures/temp.jpg";
                };
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaled));
            } catch (Exception e) {
                imageLabel = new JLabel("Image not found");
                e.printStackTrace();
            }

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            wrapper.setOpaque(false);
            wrapper.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            wrapper.add(imageLabel);
            cardHolderPanel.add(wrapper, species);
        }

        // --- Carousel Navigation ---
        JButton leftArrow = new JButton("←");
        leftArrow.setFont(largeFont);
        leftArrow.setPreferredSize(new Dimension(60, 60));
        leftArrow.addActionListener(e -> {
            currentIndex = (currentIndex - 1 + speciesOptions.length) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
            updateCritterInfoDisplay();
        });

        JButton rightArrow = new JButton("→");
        rightArrow.setFont(largeFont);
        rightArrow.setPreferredSize(new Dimension(60, 60));
        rightArrow.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % speciesOptions.length;
            cardLayout.show(cardHolderPanel, speciesOptions[currentIndex]);
            updateCritterInfoDisplay();
        });

        JPanel centerCarouselPanel = new JPanel(new GridBagLayout());
        centerCarouselPanel.setOpaque(false);
        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(10, 10, 10, 10);
        cc.gridy = 0;
        cc.fill = GridBagConstraints.NONE;
        cc.anchor = GridBagConstraints.CENTER;
        cc.gridx = 0; centerCarouselPanel.add(leftArrow, cc);
        cc.gridx = 1; centerCarouselPanel.add(cardHolderPanel, cc);
        cc.gridx = 2; centerCarouselPanel.add(rightArrow, cc);

        // --- Critter Info (Stars + Description) ---
        starPanel = new JPanel();
        starPanel.setOpaque(false);
        starPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(starPanel); // << ADDED HERE
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descriptionLabel);

        // --- Combine Carousel + Info ---
        JPanel carouselAndInfoPanel = new JPanel();
        carouselAndInfoPanel.setLayout(new BoxLayout(carouselAndInfoPanel, BoxLayout.Y_AXIS));
        carouselAndInfoPanel.setOpaque(false);
        carouselAndInfoPanel.add(centerCarouselPanel);
        carouselAndInfoPanel.add(infoPanel);

        // --- Center Wrapper ---
        JPanel verticalCenterWrapper = new JPanel();
        verticalCenterWrapper.setLayout(new BoxLayout(verticalCenterWrapper, BoxLayout.Y_AXIS));
        verticalCenterWrapper.setOpaque(false);
        verticalCenterWrapper.add(Box.createVerticalStrut(30));
        verticalCenterWrapper.add(namePanel);
        verticalCenterWrapper.add(Box.createVerticalStrut(20));
        verticalCenterWrapper.add(carouselAndInfoPanel);

        add(verticalCenterWrapper, BorderLayout.CENTER);

        // --- Bottom Panel: Back | Play | Shop ---
        JButton backButton = new JButton("⮌");
        backButton.setFont(uiFont);
        backButton.setPreferredSize(new Dimension(80, 40));
        backButton.addActionListener(e -> manager.saveInSlot(1, manager.getSaveIndex()));

        button2 = new JButton("Play");
        button2.setFont(uiFont);
        button2.setPreferredSize(new Dimension(120, 40));
        button2.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) playerName = "Critter";

            String selectedSpecies = speciesOptions[currentIndex];

            CritterInfo newCritter = switch (selectedSpecies) {
                case "Whale" -> CritterFactory.createWhale(playerName);
                case "Mushroom Man" -> CritterFactory.createMushroom_Man(playerName);
                case "Bear" -> CritterFactory.createBear(playerName);
                case "Turtle" -> CritterFactory.createTurtle(playerName);
                case "Wolf" -> CritterFactory.createWolf(playerName);
                default -> null;
            };

            if (newCritter != null) {
                GameData.activeCritter = newCritter;
                manager.setIndex(5);
            }
        });

        JButton shopButton = new JButton("Shop - " + manager.getPlayerInfo().getCritterBucks() + "$");
        shopButton.setFont(uiFont);
        shopButton.setPreferredSize(new Dimension(160, 40));
        shopButton.addActionListener(e -> manager.setIndex(7));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setPreferredSize(new Dimension(900, 70));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);
        bottomPanel.add(button2);
        bottomPanel.add(shopButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Finish Up ---
        updateCritterInfoDisplay();
        revalidate();
        repaint();
    }

    private void updateCritterInfoDisplay() {
        CritterTemplate template = getSelectedCritterTemplate();
        if (template != null) {
            updateStarDisplay(template.difficultyRating);
            descriptionLabel.setText(template.description);
        }
    }

    private void updateStarDisplay(int rating) {
        starPanel.removeAll();

        try {
            ImageIcon filledIcon = new ImageIcon("images/icons/StarFilled.png");
            ImageIcon emptyIcon = new ImageIcon("images/icons/StarEmpty.png");

            // Resize the images to 24x24 pixels
            Image filledImage = filledIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            Image emptyImage = emptyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

            ImageIcon scaledFilled = new ImageIcon(filledImage);
            ImageIcon scaledEmpty = new ImageIcon(emptyImage);

            for (int i = 0; i < 5; i++) {
                JLabel star = new JLabel(i < rating ? scaledFilled : scaledEmpty);
                starPanel.add(star);
            }
        } catch (Exception e) {
            System.err.println("Could not load or resize star images.");
            e.printStackTrace();
        }

        starPanel.revalidate();
        starPanel.repaint();
    }


    private CritterTemplate getSelectedCritterTemplate() {
        if (speciesOptions != null && currentIndex >= 0 && currentIndex < speciesOptions.length) {
            String selectedSpecies = speciesOptions[currentIndex];
            return switch (selectedSpecies) {
                case "Turtle" -> CritterFactory.createTurtle("Temp").getTemplate();
                case "Whale" -> CritterFactory.createWhale("Temp").getTemplate();
                case "Mushroom Man" -> CritterFactory.createMushroom_Man("Temp").getTemplate();
                case "Bear" -> CritterFactory.createBear("Temp").getTemplate();
                case "Wolf" -> CritterFactory.createWolf("Temp").getTemplate();
                default -> null;
            };
        }
        return null;
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




