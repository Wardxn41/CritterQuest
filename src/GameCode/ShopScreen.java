package GameCode;

import javax.swing.*;
import java.awt.*;

public class ShopScreen extends WindowPanel implements ScreenInterface {

    private JButton button;
    private JButton[] upgrades = new JButton[10];
    private JLabel label = new JLabel();

    public ShopScreen() {

        if(manager!=null) {
            label = new JLabel("el Shoppo - " + manager.getPlayerInfo().getCritterBucks() + "$");
        }

    }

    @Override
    public void calculateVisuals() {

        if(manager!=null) {
            label = new JLabel("el Shoppo - " + manager.getPlayerInfo().getCritterBucks() + "$");
        }

        clearPanel();
        frame.setSize(900,900);

        button = new JButton("Back");
        button.addActionListener(e -> manager.setIndex(4));

        int i = 0;
        for (JButton upgrade : upgrades) {

            upgrades[i] = new JButton("Upgrade " + (i + 1));
            int finalI = i;

            upgrades[i].addActionListener(e -> {
                manager.getPlayerInfo().buy(finalI);
                calculateVisuals();
            });


            i++;
        }

        add(label);
        add(button);
        button.setBackground(Color.red);

        for (JButton upgrade : upgrades) {
            add(upgrade);
        }


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