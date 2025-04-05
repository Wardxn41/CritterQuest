package GameCode;

import java.io.File;

public class SaveFileManager {

    int SaveFile;

    SaveFileManager() {



    }

    //takes the index of save file, and tries to load that file and pass it to LoadSave
    File LoadFile(int i){

        return new File(i+".txt"); //man is this a temp

    }

    //actually takes the File, turns it into a big string and loads it into player
    boolean LoadSave(PlayerInfo player, int SaveFile) {

        return false;

    }

    //deconstructs player into a string, and saves it to the save file, or if no file exists, then creates file
    void SaveToFile(PlayerInfo player, int SaveFile) {



    }

    //for the file screen to show the presence of, time of, currency of, and whether it is in game or not
    //formatted into a string sent to filescreen to display
    String softQuery(int i){

        return "nothing for now";

    }

}
