package GameCode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**

 Custom toggle button for selecting critters.

 Displays an image, species name, and a lock overlay if the critter is locked.
 */
public class CreatureSelectorButton extends JToggleButton {

    private final String speciesName;
    private final Color normalColor = Color.LIGHT_GRAY;
    private final Color selectedColor = new Color(173, 216, 230); // Highlight when selected
    private final JLabel iconLabel; // Image holder
    private final JLabel nameLabel; // Species name label
    private boolean isLocked = false;
    private Image padlockIcon;

    /**

     Constructor for the selector button.

     @param speciesName species name (used for label and image lookup)

     @param width width of the image area

     @param height height of the image area

     @param onSelect event listener for when selected
     */
    public CreatureSelectorButton(String speciesName, int width, int height, ActionListener onSelect) {
        super();
        this.speciesName = speciesName;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width + 20, height + 40));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setBackground(normalColor);
        setOpaque(true);

        iconLabel = new JLabel();

// Attempt to load the creature image using the species name
        try {
            String imagePath = "images/creatures/" + speciesName.replace(" ", "") + ".png";
            System.out.println("Attempting to load image: " + imagePath);
            Image img = new ImageIcon(imagePath).getImage();
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
// If loading fails, fallback to a generic placeholder image
            System.err.println("Failed to load image for: " + speciesName + " → using temp.png");
            try {
                Image fallback = new ImageIcon("images/creatures/temp.png").getImage();
                Image scaled = fallback.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaled));
            } catch (Exception fallbackError) {
                System.err.println("Failed to load fallback image temp.png as well!");
                iconLabel.setText("No Image");
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

// Attempt to load a padlock image (optional)
        try {
            padlockIcon = new ImageIcon("images/icons/lock.png").getImage()
                    .getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            System.err.println("Padlock image missing!");
        }

// Name label shown below the image
        nameLabel = new JLabel(speciesName);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        add(iconLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);
        setToolTipText(speciesName);
        addActionListener(onSelect);

// Highlight the button when selected
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

    /**

     Get the name of the species this button represents.
     */
    public String getSpeciesName() {
        return speciesName;
    }

    /**

     Lock or unlock the button for selection.
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
        setEnabled(!locked);
        setBackground(locked ? Color.DARK_GRAY : Color.LIGHT_GRAY);
        setToolTipText(locked ? "Locked — master the previous critter!" : speciesName);
        repaint();
    }

    /**

     Draw the lock icon if this critter is locked.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isLocked && padlockIcon != null) {
            int padX = getWidth() - 40;
            int padY = 10;
            g.drawImage(padlockIcon, padX, padY, this);
        }
    }
}