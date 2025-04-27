package GameCode;
import java.nio.file.*;
public class PlayerInfo {

    // --- Save Version ---
    private static final int CURRENT_SAVE_VERSION = 1;
    private int saveVersion;

    // --- Player Data ---
    private int saveFile;
    private CritterInfo critter;
    private int critterBucks;
    private boolean[] unlocks;
    private boolean[] upgrades;
    private int saveSlotIndex; // (1, 2, or 3)


    // --- Constructor ---
    public PlayerInfo() {
        this.saveVersion = CURRENT_SAVE_VERSION;
        this.saveFile = 0;
        this.critter = new CritterInfo();
        this.critterBucks = 100; // Start player with 100 bucks
        this.unlocks = new boolean[10];
        this.upgrades = new boolean[10];
        this.unlocks[0] = true; // Unlock first critter by default
    }

    // --- Getters and Setters ---
    public int getSaveFile() { return saveFile; }
    public void setSaveFile(int saveFile) { this.saveFile = saveFile; }

    public CritterInfo getCritter() { return critter; }
    public void setCritter(CritterInfo critter) { this.critter = critter; }

    public int getCritterBucks() { return critterBucks; }
    public void setCritterBucks(int critterBucks) {
        this.critterBucks = Math.max(0, critterBucks);
    }

    public boolean[] getUnlocks() { return unlocks; }
    public boolean[] getUpgrades() { return upgrades; }

    // --- Buying Logic ---
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

    // --- Saving ---
    public String toSaveString() {
        StringBuilder sb = new StringBuilder();
        sb.append(saveVersion).append("\n");     // Save Version First
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

    // --- Loading ---
    public void fromSaveString(String saveData, CritterTemplate template) {
        String[] lines = saveData.split("\n");

        if (lines.length < 6) { // Minimal number of expected lines
            System.err.println("[SAVE ERROR] Incomplete save file.");
            return;
        }

        try {
            this.saveVersion = Integer.parseInt(lines[0]);
            this.saveFile = Integer.parseInt(lines[1]);
            this.critterBucks = (int) Double.parseDouble(lines[2]);

            // Critter Data
            String[] critterLine = lines[3].split(",");
            if (critterLine.length >= 2) {
                CritterInfo loadedCritter = new CritterInfo(critterLine[0], template);
                this.critter = loadedCritter;
            } else {
                System.err.println("[SAVE ERROR] Broken critter data. Creating default critter.");
                this.critter = new CritterInfo();
            }

            // Unlocks
            String[] unlockSplit = lines[4].split(",");
            this.unlocks = new boolean[unlockSplit.length];
            for (int i = 0; i < unlockSplit.length; i++) {
                this.unlocks[i] = Boolean.parseBoolean(unlockSplit[i]);
            }

            // Upgrades
            String[] upgradeSplit = lines[5].split(",");
            this.upgrades = new boolean[upgradeSplit.length];
            for (int i = 0; i < upgradeSplit.length; i++) {
                this.upgrades[i] = Boolean.parseBoolean(upgradeSplit[i]);
            }

            // ⚡ Handle Old Versions
            if (saveVersion < CURRENT_SAVE_VERSION) {
                upgradeSaveToCurrentVersion();
            }

        } catch (Exception e) {
            System.err.println("[SAVE ERROR] Failed to load save properly.");
            e.printStackTrace();
        }
    }

    // --- Future Upgrades ---
    private void upgradeSaveToCurrentVersion() {
        System.out.println("[SAVE UPGRADE] Upgrading save from version " + saveVersion + " to " + CURRENT_SAVE_VERSION);

        if (saveVersion == 1) {
            // EXAMPLE: Later you could initialize a "happiness" field or similar
            // critter.setHappiness(100);
        }

        saveVersion = CURRENT_SAVE_VERSION;
    }

    public static void backupSaveFile(String originalPath) {
        try {
            Path source = Paths.get(originalPath);
            Path backup = Paths.get(originalPath.replace(".txt", "_backup.txt"));

            Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[SAVE] Backup created: " + backup.getFileName());
        } catch (Exception e) {
            System.err.println("[SAVE] Backup failed.");
            e.printStackTrace();
        }
    }

    public int getSaveSlotIndex() {
        return saveSlotIndex;
    }
    public void setSaveSlotIndex(int saveSlotIndex) {
        this.saveSlotIndex = saveSlotIndex;
    }

}




