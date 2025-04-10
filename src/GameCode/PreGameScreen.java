package GameCode;

import javax.swing.*;
import java.awt.*;

public class PreGameScreen extends WindowPanel implements ScreenInterface {

    private JButton button1,button2;
    private JLabel label = new JLabel("This is where the player is between rounds, also is where the shop is");
    private JComboBox<String> speciesDropdown;
    private JTextField nameField;
    public PreGameScreen() {}

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900,900);

        button1 = new JButton("Back to file select");
        button2 = new JButton("Go to main game play screen");
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
        //button2.addActionListener(e -> manager.setIndex(5));
        speciesDropdown = new JComboBox<>(new String[] {
                "Turtle", "Bear", "Whale", "Wolf", "Mushroom_Man"
        });
        nameField = new JTextField(15);
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        inputPanel.add(new JLabel("Choose Species:"));
        inputPanel.add(speciesDropdown);
        inputPanel.add(new JLabel("Enter Critter Name:"));
        inputPanel.add(nameField);
        inputPanel.add(button2); // Start game button

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(button1); // Back button

        add(label, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    @Override
    public void clearPanel() {
        removeAll();
        revalidate();
        repaint();
    }

}
