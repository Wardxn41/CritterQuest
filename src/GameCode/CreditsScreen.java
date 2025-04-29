package GameCode;

import javax.swing.*;
import java.awt.*;

public class CreditsScreen extends WindowPanel implements ScreenInterface {

    private JButton button;
    private JLabel titleLabel;
    private JLabel name1Label, work1Label;
    private JLabel name2Label, work2Label;
    private JLabel name3Label, work3Label;

    public CreditsScreen() {}

    @Override
    public void calculateVisuals() {

        clearPanel();
        frame.setSize(800, 600);

        setLayout(new BorderLayout());

        //top panel for title
        JPanel topPanel = new JPanel();
        titleLabel = new JLabel("Credits Screen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        //center panel for names and work descriptions
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // padding around edges

        //person 1
        name1Label = new JLabel("Henry Ward");
        name1Label.setFont(new Font("Arial", Font.BOLD, 22));
        String credits1 = "Main Gameplay Loop";
        work1Label = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;"+credits1+"</html>");

        //person 2
        name2Label = new JLabel("Hannah Hayden");
        name2Label.setFont(new Font("Arial", Font.BOLD, 22));
        String credits2 = "Visuals and GUI";
        work2Label = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;"+credits2+"</html>");

        //person 3
        name3Label = new JLabel("Miles Glover");
        name3Label.setFont(new Font("Arial", Font.BOLD, 22));
        String credits3 = "Back-end and File Management";
        work3Label = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;"+credits3+"</html>");

        //add all labels
        centerPanel.add(name1Label);
        centerPanel.add(work1Label);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // spacing

        centerPanel.add(name2Label);
        centerPanel.add(work2Label);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        centerPanel.add(name3Label);
        centerPanel.add(work3Label);

        //bottom panel for back button
        JPanel bottomPanel = new JPanel();
        button = new JButton("Back");
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(manager.getBackButtonColor());
        button.addActionListener(e -> manager.setIndex(0));
        bottomPanel.add(button);

        //add everything to main layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
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
