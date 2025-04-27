package GameCode;

import java.io.*;
import java.nio.file.*;

public class SaveFileManager {

    private File[] saves = new File[3];

    public SaveFileManager() {
        for (int i = 0; i < 3; i++) {
            initializeFile(i);
        }
    }

    private void initializeFile(int i) {
        saves[i] = new File("src/SavedGames/" + i + ".txt");
        try {
            if (!saves[i].exists()) {
                saves[i].getParentFile().mkdirs();
                saves[i].createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(PlayerInfo player, int saveSlot) {
        File saveFile = saves[saveSlot];

        // Backup before saving
        backupSaveFile(saveFile.getPath());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(player.toSaveString());
            System.out.println("[SAVE] Successfully saved to slot " + saveSlot);
        } catch (IOException e) {
            System.err.println("[SAVE ERROR] Could not save file.");
            e.printStackTrace();
        }
    }

    public void loadSave(PlayerInfo player, int saveSlot) {
        File saveFile = saves[saveSlot];

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            if (sb.length() == 0) {
                // New file — don't overwrite player
                System.out.println("[LOAD] Empty save slot. Using fresh PlayerInfo.");
                return;
            }

            // Dummy template used for loading (replace later with better species matching)
            CritterTemplate dummyTemplate = new CritterTemplate(
                    "Species", 100, 60, 60,
                    70, 50, 30, 5, 10, 20,
                    10, 5, 2, "assets/bg.png", "assets/bg.png", 3,
                    "Balanced critter for testing"
            );

            player.fromSaveString(sb.toString(), dummyTemplate);

            System.out.println("[LOAD] Successfully loaded from slot " + saveSlot);

        } catch (IOException e) {
            System.err.println("[LOAD ERROR] Could not load save.");
            e.printStackTrace();
        }
    }

    public String softQuery(int slot) {
        File file = saves[slot];
        if (file.length() == 0) return "Empty Save Slot";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip version
            reader.readLine(); // Skip save file index
            String bucksLine = reader.readLine(); // Critter bucks line
            return "Critter Bucks: " + bucksLine.trim();
        } catch (IOException e) {
            return "Error reading save";
        }
    }

    private void backupSaveFile(String originalPath) {
        try {
            Path source = Paths.get(originalPath);
            Path backup = Paths.get(originalPath.replace(".txt", "_backup.txt"));

            Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[BACKUP] Backup created: " + backup.getFileName());
        } catch (IOException e) {
            System.err.println("[BACKUP ERROR] Could not create backup.");
            e.printStackTrace();
        }
    }
}




