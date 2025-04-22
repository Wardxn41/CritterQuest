package GameCode;

import java.io.*;

public class SaveFileManager {

    PlayerInfo playerInfo;
    int SaveFile;
    File[] saves = new File[3];

    SaveFileManager() {
        LoadFile(0);
        LoadFile(1);
        LoadFile(2);
    }

    // takes the index of save file, and tries to load that file and pass it to saves
    void LoadFile(int i) {

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

    // actually takes the File, turns it into a string and loads it into player info
    void LoadSave(PlayerInfo player, int SaveFile) {
        if(player==null) {player=new PlayerInfo();}
        this.SaveFile = SaveFile;
        player.setSaveFile(SaveFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(saves[SaveFile]))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // For now, we assume one hardcoded template per species
            // Replace with a real lookup in your implementation
            CritterTemplate dummyTemplate = new CritterTemplate(
                    "Species", 100, 60, 60,
                    70, 50, 30, 5, 10, 20,
                    10, 5, 2, "assets/bg.png", "assets/bg.png"
            );

            player.fromSaveString(sb.toString(), dummyTemplate);
            this.playerInfo = player;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deconstructs player into a string and saves it to the save file
    void SaveToFile(PlayerInfo player, int SaveFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saves[SaveFile]))) {
            writer.write(player.toSaveString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // for the file screen to show the presence of and currency of that save’s player info statistics
    String softQuery(int i) {
        File file = saves[i];
        if (file.length() == 0) return "Empty Save Slot";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // SaveFile
            String critterBucksLine = reader.readLine(); // critterBucks
            return "Critter Bucks: " + critterBucksLine;
        } catch (IOException e) {
            return "Error Reading Save";
        }
    }
}


