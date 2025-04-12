package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");
    private JComboBox<String> speciesDropdown;
    private JTextField nameField;
    private Image backgroundImage;

    public PreGameScreen() {
        try {
            backgroundImage = new ImageIcon("images/PreGameBackground.png").getImage(); // Adjust path if needed
        } catch (Exception e) {
            System.err.println("Could not load background image");
            e.printStackTrace();
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900, 900);

        Font uiFont = new Font("SansSerif", Font.BOLD, 16);

        // Buttons
        button1 = new JButton("Back to file select");
        button1.setFont(uiFont);
        button2 = new JButton("Go to main game play screen");
        button2.setFont(uiFont);

        // Species and name input
        speciesDropdown = new JComboBox<>(new String[]{
                "Turtle", "Bear", "Whale", "Wolf", "Mushroom Man"
        });
        speciesDropdown.setFont(uiFont);

        nameField = new JTextField(15);
        nameField.setFont(uiFont);

        // Button listener
        button2.addActionListener(e -> {
            String selectedSpecies = (String) speciesDropdown.getSelectedItem();
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Critter";

            CritterInfo newCritter;
            switch (selectedSpecies) {
                case "Whale":
                    newCritter = CritterFactory.createWhale(name);
                    break;
                case "Mushroom Man":
                    newCritter = CritterFactory.createMushroom_Man(name);
                    break;
                case "Bear":
                    newCritter = CritterFactory.createBear(name);
                    break;
                case "Turtle":
                    newCritter = CritterFactory.createTurtle(name);
                    break;
                case "Wolf":
                    newCritter = CritterFactory.createWolf(name);
                    break;
                default:
                    return;
            }

            GameData.activeCritter = newCritter;
            manager.setIndex(5); // Go to GameScreen
        });

        button1.addActionListener(e -> manager.setIndex(1));

        // Sub-panels
        JPanel speciesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        speciesPanel.add(new JLabel("Species:"));
        speciesPanel.add(speciesDropdown);
        speciesPanel.setOpaque(false);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setOpaque(false);

        JPanel critterSetupPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        critterSetupPanel.setBorder(BorderFactory.createTitledBorder("Create Your Critter"));
        critterSetupPanel.add(speciesPanel);
        critterSetupPanel.add(namePanel);
        critterSetupPanel.add(button2);
        critterSetupPanel.setOpaque(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(button1);
        bottomPanel.setOpaque(false);

        label.setFont(uiFont);
        label.setForeground(Color.WHITE);
        label.setOpaque(false);

        add(label, BorderLayout.NORTH);
        add(critterSetupPanel, BorderLayout.CENTER);
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
