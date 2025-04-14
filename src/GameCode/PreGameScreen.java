package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");
    //private JComboBox<String> speciesDropdown;
    private JTextField nameField;
    private Image backgroundImage;

    private String selectedSpecies = "Turtle";
    private JPanel creatureSelectorPanel;
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
        setLayout(new BorderLayout());

        Font uiFont = new Font("SansSerif", Font.BOLD, 16);
        Font largeFont = new Font("SansSerif", Font.BOLD, 24);

        // 🔹 Back Button Panel (Top-Left)
        button1 = new JButton("⮌");
        button1.setFont(uiFont);
        button1.setPreferredSize(new Dimension(60, 60));
        button1.addActionListener(e -> manager.setIndex(1));

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(button1);
        topLeftPanel.setOpaque(false);
        add(topLeftPanel, BorderLayout.NORTH);

        // 🔹 Name Input
        nameField = new JTextField(15);
        nameField.setFont(uiFont);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setOpaque(false);

        // 🔹 Creature Selector Panel
        String[] speciesOptions = {"Turtle", "Bear", "Whale", "Wolf", "Mushroom Man"};
        creatureSelectorPanel = new JPanel();
        creatureSelectorPanel.setLayout(new BoxLayout(creatureSelectorPanel, BoxLayout.X_AXIS));
        creatureSelectorPanel.setOpaque(false);

        for (String species : speciesOptions) {
            JButton speciesButton = new JButton(species);
            speciesButton.setFont(largeFont);
            speciesButton.setPreferredSize(new Dimension(200, 80));
            speciesButton.addActionListener(e -> {
                selectedSpecies = species;
                System.out.println("Selected species: " + selectedSpecies);
            });
            creatureSelectorPanel.add(Box.createHorizontalStrut(15));
            creatureSelectorPanel.add(speciesButton);
        }

        JScrollPane scrollPane = new JScrollPane(creatureSelectorPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(700, 120));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Select Species"));

        // 🔹 Play Button
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

        // 🔹 Center Layout with GridBag to center vertically and horizontally
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(scrollPane, gbc);

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
