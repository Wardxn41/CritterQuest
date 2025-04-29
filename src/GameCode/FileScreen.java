package GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileScreen extends WindowPanel implements ScreenInterface {

    private JButton button1, button2, button3, button4;
    private JLabel label = new JLabel("Chose Save File:");
    private Image backgroundImage;

    public FileScreen() {
        try {
            backgroundImage = new ImageIcon("images/background/PreGameBackground.png").getImage();
        } catch (Exception e) {
            System.err.println("Could not load background image");
            e.printStackTrace();
        }
    }

    @Override
    public void calculateVisuals() {
        clearPanel();
        frame.setSize(900, 700); // Larger window for a grander layout

        // Bigger font for better visibility
        Font bigFont = new Font("SansSerif", Font.BOLD, 24);

        // Label (Title)
        label.setFont(bigFont);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setOpaque(false);

        // Button 1 - Back
        button1 = new JButton("← Back");
        styleButton(button1, bigFont, new Color(220, 20, 60), Color.WHITE, Color.DARK_GRAY);
        button1.setBackground(manager.getBackButtonColor());
        button1.addActionListener(e -> manager.setIndex(0));

        // Button 2 - Save Slot 1
        button2 = new JButton("💾 Save Slot 1");
        styleButton(button2, bigFont, new Color(70, 130, 180), Color.WHITE, Color.WHITE);
        addHoverEffect(button2, new Color(65, 105, 145));
        button2.addActionListener(e -> manager.loadSaveSlot(4,0));

        // Button 3 - Save Slot 2
        button3 = new JButton("🗃 Save Slot 2");
        styleButton(button3, bigFont, new Color(60, 179, 113), Color.BLACK, Color.BLACK);
        addHoverEffect(button3, new Color(46, 139, 87));
        button3.addActionListener(e -> manager.loadSaveSlot(4,1));

        // Button 4 - Save Slot 3
        button4 = new JButton("📂 Save Slot 3");
        styleButton(button4, bigFont, new Color(138, 43, 226), Color.WHITE, Color.WHITE);
        addHoverEffect(button4, new Color(123, 31, 200));
        button4.addActionListener(e -> manager.loadSaveSlot(4,2));

        // Button Panel - vertical layout and spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(label);
        buttonPanel.add(Box.createVerticalStrut(40));
        buttonPanel.add(centered(button2));
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(centered(button3));
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(centered(button4));
        buttonPanel.add(Box.createVerticalStrut(40));
        buttonPanel.add(centered(button1));
        buttonPanel.add(Box.createVerticalGlue());

        // Center the panel
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.add(buttonPanel, BorderLayout.CENTER);
        add(outer, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void styleButton(JButton button, Font font, Color bg, Color fg, Color border) {
        button.setFont(font);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(border, 3));
        button.setPreferredSize(new Dimension(400, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void addHoverEffect(JButton button, Color hoverColor) {
        Color originalColor = button.getBackground();
        button.setRolloverEnabled(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
    }

    private Component centered(JComponent comp) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new FlowLayout(FlowLayout.CENTER));
        wrap.add(comp);
        return wrap;
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


