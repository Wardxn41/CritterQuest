package GameCode;

public class PlayerInfo {
    private int saveFile;
    private CritterInfo critter;
    private int critterBucks;
    private boolean[] unlocks;
    private boolean[] upgrades;

    public PlayerInfo() {
        this.saveFile = 0;
        this.critter = new CritterInfo();
        this.critterBucks = 100; // Start with 100 bucks
        this.unlocks = new boolean[10]; // Can expand later
        this.upgrades = new boolean[10];

        this.unlocks[0] = true; // Unlock first critter
    }

    // Getters and Setters
    public int getSaveFile() { return saveFile; }
    public void setSaveFile(int saveFile) { this.saveFile = saveFile; }

    public CritterInfo getCritter() { return critter; }
    public void setCritter(CritterInfo critter) { this.critter = critter; }

    public int getCritterBucks() { return critterBucks; }
    public void setCritterBucks(int critterBucks) { this.critterBucks = Math.max(0, critterBucks); }

    public boolean[] getUnlocks() { return unlocks; }
    public boolean[] getUpgrades() { return upgrades; }

    // New, cleaner buy method
    public boolean canAfford(int amount) {
        return critterBucks >= amount;
    }

    public boolean buy(int cost) {
        if (canAfford(cost)) {
            critterBucks -= cost;
            return true;
        }
        return false;
    }

    // Save and Load Methods
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append(saveFile).append("\n");
        sb.append(critterBucks).append("\n");
        sb.append(critter.getName()).append(",")
                .append(critter.getSpecies()).append(",")
                .append(critter.getHealth()).append(",")
                .append(critter.getHunger()).append(",")
                .append(critter.getThirst()).append(",")
                .append(critter.isAlive()).append("\n");

        for (boolean b : unlocks) sb.append(b).append(",");
        sb.append("\n");
        for (boolean b : upgrades) sb.append(b).append(",");

        return sb.toString();
    }

    public void fromSaveString(String saveData, CritterTemplate template) {
        String[] lines = saveData.split("\n");
        if (lines.length > 1) {
            this.saveFile = Integer.parseInt(lines[0]);
            this.critterBucks = (int) Double.parseDouble(lines[1]);


            String[] critterLine = lines[2].split(",");
            CritterInfo loadedCritter = new CritterInfo(critterLine[0], template);
            this.critter = loadedCritter;

            String[] unlockSplit = lines[3].split(",");
            this.unlocks = new boolean[unlockSplit.length];
            for (int i = 0; i < unlockSplit.length; i++) {
                this.unlocks[i] = Boolean.parseBoolean(unlockSplit[i]);
            }

            String[] upgradeSplit = lines[4].split(",");
            this.upgrades = new boolean[upgradeSplit.length];
            for (int i = 0; i < upgradeSplit.length; i++) {
                this.upgrades[i] = Boolean.parseBoolean(upgradeSplit[i]);
            }
        }
    }
}



