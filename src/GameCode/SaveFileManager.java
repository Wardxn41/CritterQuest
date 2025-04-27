package GameCode;

import java.io.*;

public class SaveFileManager {

    private PlayerInfo playerInfo;
    private int saveFile;
    private File[] saves = new File[3];

    public SaveFileManager() {
        for (int i = 0; i < saves.length; i++) {
            loadFile(i);
        }
    }

    private void loadFile(int i) {
        saves[i] = new File("src/SavedGames/" + i + ".txt");

        try {
            if (!saves[i].exists()) {
                saves[i].getParentFile().mkdirs(); // create folder if needed
                saves[i].createNewFile();          // create empty file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSave(PlayerInfo player, int saveFile) {
        if (player == null) {
            player = new PlayerInfo(); // safe default
        }
        this.saveFile = saveFile;
        this.playerInfo = player;
        player.setSaveFile(saveFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(saves[saveFile]))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // Dummy template used when loading (you can improve this later)
            CritterTemplate dummyTemplate = new CritterTemplate(
                    "Species", 100, 60, 60,
                    70, 50, 30, 5, 10, 20,
                    10, 5, 2, "assets/bg.png", "assets/bg.png", 3, "Balanced critter for testing"
            );

            player.fromSaveString(sb.toString(), dummyTemplate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(PlayerInfo player, int saveFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saves[saveFile]))) {
            writer.write(player.toSaveString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String softQuery(int i) {
        File file = saves[i];
        if (file.length() == 0) {
            return "Empty Save Slot";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // SaveFile number
            String critterBucksLine = reader.readLine(); // CritterBucks
            return "Critter Bucks: " + critterBucksLine;
        } catch (IOException e) {
            return "Error Reading Save";
        }
    }
}




