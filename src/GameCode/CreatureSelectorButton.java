package GameCode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreatureSelectorButton extends JToggleButton {

    private final String speciesName;
    private final Color normalColor = Color.LIGHT_GRAY;
    private final Color selectedColor = new Color(173, 216, 230); // Light blue
    private final JLabel iconLabel;
    private final JLabel nameLabel;
    private boolean isLocked = false;
    private Image padlockIcon;

    public CreatureSelectorButton(String speciesName, int width, int height, ActionListener onSelect) {
        super();
        this.speciesName = speciesName;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width + 20, height + 40));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setBackground(normalColor);
        setOpaque(true);

        // Load image
        iconLabel = new JLabel();
        try {
            //Image img = new ImageIcon("images/creatures/" + speciesName.replace(" ", "_") + ".png").getImage();
            Image img = new ImageIcon("images/creatures/temp.jpg").getImage();
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("Could not load image for species: " + speciesName);
            iconLabel.setText("No Image");
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            padlockIcon = new ImageIcon("images/icons/lock.png").getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            System.err.println("Padlock image missing!");
        }
        // Name label
        nameLabel = new JLabel(speciesName);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        add(iconLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);
        setToolTipText(speciesName);
        addActionListener(onSelect);

        // Custom selection effect
        getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (getModel().isSelected()) {
                    setBackground(selectedColor);
                    setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                } else {
                    setBackground(normalColor);
                    setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                }
            }
        });
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
        setEnabled(!locked);
        setBackground(locked ? Color.DARK_GRAY : Color.LIGHT_GRAY);
        setToolTipText(locked ? "Locked — master the previous critter!" : speciesName);
        repaint(); // Repaint to trigger lock drawing
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isLocked && padlockIcon != null) {
            int padX = getWidth() - 40;
            int padY = 10;
            g.drawImage(padlockIcon, padX, padY, this);
        }
    }

}
