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
    private String characterPath;
    private String backgroundPath;
    public int difficultyRating; // 1 (easy) to 5 (very hard)
    public String description;   // Short blurb about the critter

    // Constructor
    public CritterTemplate(String speciesName, int maxHealth,
                           int hungerDecayRate, int thirstDecayRate,
                           int warningThreshold, int dangerThreshold, int criticalThreshold,
                           int warningDamage, int dangerDamage, int criticalDamage,
                           int warningTimer, int dangerTimer, int criticalTimer, String backgroundPath, String characterPath,
                           int difficultyRating, String description) {

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

        this.backgroundPath = backgroundPath;
        this.characterPath = characterPath;
        this.difficultyRating = difficultyRating;
        this.description = description;
    }

    public CritterTemplate() {
        this.speciesName = "";
        this.maxHealth = 0;

        this.hungerDecayRate = 0;
        this.thirstDecayRate = 0;

        this.warningThreshold = 0;
        this.dangerThreshold = 0;
        this.criticalThreshold = 0;

        this.warningDamage = 0;
        this.dangerDamage = 0;
        this.criticalDamage = 0;

        this.warningTimer = 0;
        this.dangerTimer = 0;
        this.criticalTimer = 0;

        this.backgroundPath = "";
    }


    public String getBackgroundPath() {
        return backgroundPath;
    }
    public String getSpeciesName() {
        return speciesName;
    }

    public String getCharacterPath(){ return characterPath; }



}



