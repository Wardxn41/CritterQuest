package GameCode;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class CritterStatsPanel extends JPanel{
    private CritterInfo critter;
    private JLabel nameLabel;
    private JLabel healthLabel;
    private JLabel hungerLabel;
    private JLabel thirstLabel;
    private JLabel statusLabel;

    public CritterStatsPanel(CritterInfo critter) {
        this.critter = critter;
        setLayout(new GridLayout(0,1));
        setBorder(BorderFactory.createTitledBorder("Critter Stats"));

        nameLabel = new JLabel();
        healthLabel = new JLabel();
        hungerLabel = new JLabel();
        thirstLabel = new JLabel();
        statusLabel = new JLabel();

        add(nameLabel);
        add(healthLabel);
        add(hungerLabel);
        add(thirstLabel);
        add(statusLabel);
        upadateLabels();

        //update every second
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                SwingUtilities.invokeLater(() -> {
                    critter.tickUpdate(System.currentTimeMillis());
                    upadateLabels();
                });
            }
        }, 0, 1000);
    }
    private void upadateLabels(){
        nameLabel.setText("Name: " +critter.getName());
        healthLabel.setText("Health: " +critter.getHealth());
        hungerLabel.setText("Hunger: " +critter.getHunger());
        thirstLabel.setText("Thirst: " +critter.getThirst());
        statusLabel.setText("Status: " +(critter.isAlive() ? "Alive" : "Dead"));
    }

}
