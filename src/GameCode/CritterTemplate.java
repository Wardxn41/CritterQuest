package GameCode;

public class CritterTemplate {
    public String speciesName;
    public int maxHealth;

    public int hungerDecayRate;   // seconds per 1 hunger
    public int thirstDecayRate;   // seconds per 1 thirst

    public int warningThreshold;
    public int dangerThreshold;
    public int criticalThreshold;

    public int warningDamage;
    public int dangerDamage;
    public int criticalDamage;

    public int warningTimer;
    public int dangerTimer;
    public int criticalTimer;

    // Constructor
    public CritterTemplate(String speciesName, int maxHealth,
                           int hungerDecayRate, int thirstDecayRate,
                           int warningThreshold, int dangerThreshold, int criticalThreshold,
                           int warningDamage, int dangerDamage, int criticalDamage,
                           int warningTimer, int dangerTimer, int criticalTimer) {

        this.speciesName = speciesName;
        this.maxHealth = maxHealth;

        this.hungerDecayRate = hungerDecayRate;
        this.thirstDecayRate = thirstDecayRate;

        this.warningThreshold = warningThreshold;
        this.dangerThreshold = dangerThreshold;
        this.criticalThreshold = criticalThreshold;

        this.warningDamage = warningDamage;
        this.dangerDamage = dangerDamage;
        this.criticalDamage = criticalDamage;

        this.warningTimer = warningTimer;
        this.dangerTimer = dangerTimer;
        this.criticalTimer = criticalTimer;
    }
}

