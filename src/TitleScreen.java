import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//miles note 4/1 : this is the temp version so we should convert it to the idea shown through the window manager class


public class TitleScreen {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TitleScreen().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("CritterQuest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        // Create a panel to hold everything
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(135, 206, 250)); // Light blue background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("CritterQuest", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        playButton.setFocusPainted(false);
        playButton.setBackground(new Color(50, 205, 50)); // Green button
        playButton.setForeground(Color.WHITE);
        playButton.setBorderPainted(false);
        playButton.setPreferredSize(new Dimension(200, 60));

        // Action for play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Game Start Placeholder!"); // Placeholder action
            }
        });

        // Center the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
}
