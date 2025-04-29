package GameCode;

import javax.swing.*;
import java.util.*;

public class RandomEventManager {
    private List<RandomEvent> events = new ArrayList<>();
    private List<RandomEvent> legendaryEvents = new ArrayList<>();
    private Random random = new Random();
    private WindowPanel parentPanel;

    public RandomEventManager(WindowPanel parentPanel) {
        this.parentPanel = parentPanel;
        setupEvents();
    }

    private void setupEvents() {
        events.add(new RandomEvent(
                "Rainstorm!",
                "It's raining! Your critter's thirst is refilled.",
                () -> {
                    GameData.activeCritter.setThirst(100);
                }
        ));

        events.add(new RandomEvent(
                "Heatwave!",
                "A heatwave scorches the land! Thirst decays faster.",
                () -> {
                    GameData.activeCritter.getTemplate().thirstDecayRate /= 2;  // thirst decays faster temporarily
                    // You could set up a timer to reset it after a while if you want!
                }
        ));

        events.add(new RandomEvent(
                "Feast Day!",
                "There's a feast! Your critter's hunger is restored.",
                () -> {
                    GameData.activeCritter.setHunger(100);
                }
        ));

        events.add(new RandomEvent(
                "Poisonous Plant!",
                "Your critter ate something bad! Lost 20 health.",
                () -> {
                    GameData.activeCritter.updateHealth(-20);
                }
        ));

        events.add(new RandomEvent(
                "Deep Sleep",
                "Your critter takes a restful nap. Gaining health!",
                () -> {
                    GameData.activeCritter.updateHealth(10);
                }
        ));
    }

    private void setupLegendaryEvents() {
        legendaryEvents.add(new RandomEvent(
                "Meteor Shower!",
                "A meteor shower rains down! Your critter gains 50 Critter Bucks and feels inspired.",
                () -> {
                    PlayerInfo player = parentPanel.getManager().getPlayerInfo();
                    player.setCritterBucks(player.getCritterBucks() + 50);
                }
        ));

        legendaryEvents.add(new RandomEvent(
                "Magical Rain!",
                "Glowing rain restores your critter's health and hunger to full!",
                () -> {
                    GameData.activeCritter.setHealth(GameData.activeCritter.getTemplate().maxHealth);
                    GameData.activeCritter.setHunger(100);
                }
        ));

        legendaryEvents.add(new RandomEvent(
                "Eclipse!",
                "A mysterious eclipse appears... Nothing seems to happen... Or does it?",
                () -> {
                    // Maybe later add a hidden effect, like secret unlocks?
                }
        ));
    }

    public void tryTriggerRandomEvent() {
        System.out.println("Random event attempted");
        int chance = random.nextInt(100);
        if (chance < 20) { // 20% chance every check
            RandomEvent event = events.get(random.nextInt(events.size()));
            event.trigger();
            showEventPopup(event);
        }
        if (chance < 1) { // 1% chance to trigger legendary event
            RandomEvent event = legendaryEvents.get(random.nextInt(legendaryEvents.size()));
            event.trigger();
            showEventPopup(event);
        }
    }

    private void showEventPopup(RandomEvent event) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentPanel,
                    event.getDescription(),
                    event.getName(),
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}

