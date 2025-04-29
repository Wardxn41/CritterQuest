package GameCode;

import javax.swing.*;
import java.awt.*;

public class OptionsScreen extends WindowPanel implements ScreenInterface {

    private JButton button1;
    private JLabel label = new JLabel("Options");
    private JColorChooser colorChooser;

    public OptionsScreen() {}

    @Override
    public void calculateVisuals() {

        clearPanel();
        frame.setSize(800, 600);

        //set layout
        setLayout(new BorderLayout());

        //top label
        JPanel topPanel = new JPanel();
        topPanel.add(label);

        //center panel that holds back button and color chooser
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        //back button panel (keeps back button small)
        JPanel backButtonPanel = new JPanel();
        button1 = new JButton("Back");
        button1.setPreferredSize(new Dimension(150, 40)); // keep size reasonable
        button1.addActionListener(e -> manager.setIndex(0));
        button1.setBackground(manager.getBackButtonColor());
        backButtonPanel.add(button1);

        //color chooser (embedded)
        colorChooser = new JColorChooser(manager.getBackButtonColor());
        colorChooser.getSelectionModel().addChangeListener(e -> {
            Color newColor = colorChooser.getColor();
            manager.setBackButtonColor(newColor);
            button1.setBackground(newColor); // update immediately
        });

        //add subpanels to center
        centerPanel.add(backButtonPanel, BorderLayout.NORTH);
        centerPanel.add(colorChooser, BorderLayout.CENTER);

        //add to main layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

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

