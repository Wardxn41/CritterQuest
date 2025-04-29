package GameCode;

public class PlayerInfo {

    private static final int CURRENT_SAVE_VERSION = 1;
    private int saveVersion;
    private int saveFile;
    private CritterInfo critter;
    private int critterBucks;
    private boolean[] unlocks;
    private boolean[] upgrades;

    private int hungerUpgradeLevel = 0;
    private int thirstUpgradeLevel = 0;
    private int healingUpgradeLevel = 0;

    public PlayerInfo() {
        this.saveVersion = CURRENT_SAVE_VERSION;
        this.saveFile = 0;
        this.critter = new CritterInfo();
        this.critterBucks = 100; // default
        this.unlocks = new boolean[10];
        this.upgrades = new boolean[10];
        this.unlocks[0] = true;
    }

    public int getSaveFile() { return saveFile; }
    public void setSaveFile(int saveFile) { this.saveFile = saveFile;}

    public CritterInfo getCritter() { return critter; }
    public void setCritter(CritterInfo critter) { this.critter = critter; }

    public int getCritterBucks() { return critterBucks; }
    public void setCritterBucks(int critterBucks) { this.critterBucks = Math.max(0, critterBucks); }

    public int getHungerUpgradeLevel() { return hungerUpgradeLevel; }
    public int getThirstUpgradeLevel() { return thirstUpgradeLevel; }
    public int getHealingUpgradeLevel() { return healingUpgradeLevel; }

    public boolean upgradeHunger() {
        if (hungerUpgradeLevel < 4) { hungerUpgradeLevel++; return true; }
        return false;
    }

    public boolean upgradeThirst() {
        if (thirstUpgradeLevel < 4) { thirstUpgradeLevel++; return true; }
        return false;
    }

    public boolean upgradeHealing() {
        if (healingUpgradeLevel < 4) { healingUpgradeLevel++; return true; }
        return false;
    }

    public boolean[] getUnlocks() { return unlocks; }
    public boolean[] getUpgrades() { return upgrades; }

    public boolean canAfford(int amount) { return critterBucks >= amount; }
    public boolean buy(int cost) {
        if (canAfford(cost)) {
            critterBucks -= cost;
            return true;
        }
        return false;
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CURRENT_SAVE_VERSION).append("\n");
        sb.append(saveFile).append("\n");
        sb.append(critterBucks).append("\n");

        sb.append(critter.getName()).append(",")
                .append(critter.getSpecies()).append(",")
                .append(critter.getHealth()).append(",")
                .append(critter.getHunger()).append(",")
                .append(critter.getThirst()).append(",")
                .append(critter.isAlive()).append("\n");

        for (boolean b : unlocks) sb.append(b).append(",");
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");

        for (boolean b : upgrades) sb.append(b).append(",");
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");

        sb.append(hungerUpgradeLevel).append(",");
        sb.append(thirstUpgradeLevel).append(",");
        sb.append(healingUpgradeLevel).append("\n");

        return sb.toString();
    }

    public void fromSaveString(String saveData, CritterTemplate template) {
        String[] lines = saveData.split("\n");

        if (lines.length < 7) {
            System.err.println("[SAVE ERROR] Incomplete save file.");
            return;
        }

        try {
            this.saveVersion = Integer.parseInt(lines[0]);
            this.saveFile = Integer.parseInt(lines[1]);
            this.critterBucks = (int) Double.parseDouble(lines[2]);

            String[] critterLine = lines[3].split(",");
            if (critterLine.length >= 2) {
                CritterInfo loadedCritter = new CritterInfo(critterLine[0], template);
                this.critter = loadedCritter;
            } else {
                System.err.println("[SAVE ERROR] Broken critter data, creating default critter.");
                this.critter = new CritterInfo();
            }

            String[] unlockSplit = lines[4].split(",");
            this.unlocks = new boolean[unlockSplit.length];
            for (int i = 0; i < unlockSplit.length; i++) {
                this.unlocks[i] = Boolean.parseBoolean(unlockSplit[i]);
            }

            String[] upgradeSplit = lines[5].split(",");
            this.upgrades = new boolean[upgradeSplit.length];
            for (int i = 0; i < upgradeSplit.length; i++) {
                this.upgrades[i] = Boolean.parseBoolean(upgradeSplit[i]);
            }

            String[] upgradeStats = lines[6].split(",");
            this.hungerUpgradeLevel = Integer.parseInt(upgradeStats[0]);
            this.thirstUpgradeLevel = Integer.parseInt(upgradeStats[1]);
            this.healingUpgradeLevel = Integer.parseInt(upgradeStats[2]);

            if (saveVersion < CURRENT_SAVE_VERSION) {
                upgradeSaveToCurrentVersion();
            }

        } catch (Exception e) {
            System.err.println("[SAVE ERROR] Failed to load save.");
            e.printStackTrace();
        }
    }

    private void upgradeSaveToCurrentVersion() {
        System.out.println("[SAVE UPGRADE] Save version upgraded from " + saveVersion + " to " + CURRENT_SAVE_VERSION);
        saveVersion = CURRENT_SAVE_VERSION;
    }
}





