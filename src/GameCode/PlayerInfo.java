package GameCode;

public class PlayerInfo {

    private int SaveFile;
    private CritterInfo critter;
    private double critterBucks;
    private boolean[] unlocks;
    private boolean[] upgrades;

    public PlayerInfo() {

        this.SaveFile = 0;
        this.critter = new CritterInfo();
        this.critterBucks = 0.0;
        this.unlocks = new boolean[10];  // adjust size as needed
        this.upgrades = new boolean[10]; // adjust size as needed

        //unlock the first critter by default
        this.unlocks[0] = true;

    }

    public int getSaveFile() {
        return SaveFile;
    }

    public void setSaveFile(int saveFile) {
        this.SaveFile = saveFile;
    }

    public CritterInfo getCritter() {
        return critter;
    }

    public void setCritter(CritterInfo critter) {
        this.critter = critter;
    }

    public double getCritterBucks() {
        return critterBucks;
    }

    public void setCritterBucks(double critterBucks) {
        this.critterBucks = critterBucks;
    }

    public boolean[] getUnlocks() {
        return unlocks;
    }

    public void setUnlocks(boolean[] unlocks) {
        this.unlocks = unlocks;
    }

    public boolean[] getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(boolean[] upgrades) {
        this.upgrades = upgrades;
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SaveFile).append("\n");
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

        if(lines.length!=1) {

            this.SaveFile = Integer.parseInt(lines[0]);
            this.critterBucks = Double.parseDouble(lines[1]);

            String[] critterLine = lines[2].split(",");
            CritterInfo loadedCritter = new CritterInfo(critterLine[0], template);
            this.critter = loadedCritter;

            // Critter state (optional to reapply manually if needed)
            // skipped reapplying health/hunger/thirst because they're not modifiable through setters

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


