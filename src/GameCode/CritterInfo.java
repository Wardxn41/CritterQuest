
package GameCode;
public class CritterInfo {
    private String name;
    private int health, hunger, thirst, age;
    private boolean isAlive;
    private long lastUpdatedTime;
    private CritterTemplate template;

    // Decay timers
    private int hungerDecayTimer, thirstDecayTimer;
    private int warningTimer, dangerTimer, criticalTimer;

    public CritterInfo(String name, CritterTemplate template) {
        this.name = name;
        this.template = template;

        this.health = template.maxHealth;
        this.hunger = 100;
        this.thirst = 100;
        this.isAlive = true;
        this.lastUpdatedTime = System.currentTimeMillis();
    }

    public void tickUpdate(long currentTime) {
        long elapsed = currentTime - lastUpdatedTime;
        int seconds = (int) (elapsed / 1000);
        if (seconds <= 0) return;

        // Resource decay
        hungerDecayTimer += seconds;
        thirstDecayTimer += seconds;

        if (hungerDecayTimer >= template.hungerDecayRate) {
            hunger = Math.max(0, hunger - 1);
            hungerDecayTimer = 0;
        }

        if (thirstDecayTimer >= template.thirstDecayRate) {
            thirst = Math.max(0, thirst - 1);
            thirstDecayTimer = 0;
        }

        // Health decay
        int need = Math.min(hunger, thirst);
        warningTimer += seconds;
        dangerTimer += seconds;
        criticalTimer += seconds;

        if (need <= template.criticalThreshold) {
            if (criticalTimer >= template.criticalTimer) {
                updateHealth(-template.criticalDamage);
                criticalTimer = 0;
            }
        } else if (need <= template.dangerThreshold) {
            if (dangerTimer >= template.dangerTimer) {
                updateHealth(-template.dangerDamage);
                dangerTimer = 0;
            }
        } else if (need <= template.warningThreshold) {
            if (warningTimer >= template.warningTimer) {
                updateHealth(-template.warningDamage);
                warningTimer = 0;
            }
        } else {
            warningTimer = dangerTimer = criticalTimer = 0;
        }

        if (health <= 0) {
            isAlive = false;
        }

        age += seconds;
        lastUpdatedTime = currentTime;
    }

    private void updateHealth(int change) {
        health = Math.max(0, Math.min(template.maxHealth, health + change));
    }

    // Getters for UI
    public int getHealth() { return health; }
    public int getHunger() { return hunger; }
    public int getThirst() { return thirst; }
    public boolean isAlive() { return isAlive; }
    public String getName() { return name; }
    public String getSpecies() { return template.speciesName; }

}




