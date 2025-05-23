package GameCode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WindowManager {

    private int screenIndex = 0;
    private List<ScreenInterface> Screens;
    private PlayerInfo playerInfo;
    private SaveFileManager saveFileManager;
    private Color backButtonColor = Color.red;

    public WindowManager(List<ScreenInterface> Screens) {
        this.saveFileManager = new SaveFileManager();
        this.playerInfo = new PlayerInfo();
        this.Screens = Screens;
        massSetManager();
        ShowVisuals();
    }
    public ScreenInterface getScreen(int index) {
        return Screens.get(index);
    }

    public void ShowVisuals() {
        Screens.get(screenIndex).ShowVisuals();
    }

    public void setIndex(int index) {
        if (Screens.get(screenIndex) != null) {
            Screens.get(screenIndex).clearPanel();
        }

        // Now actually switch
        this.screenIndex = index;
        ShowVisuals();
    }

    public void saveInSlot(int index, int saveIndex) {

        playerInfo.setSaveFile(saveIndex);
        saveFileManager.saveToFile(playerInfo, saveIndex);
        this.screenIndex = index;
        ShowVisuals();

    }

    public void loadSaveSlot(int index, int saveIndex) {
        saveFileManager.loadSave(playerInfo, saveIndex);
        this.screenIndex = index;
        ShowVisuals();
    }

    public int getSaveIndex() {
        return playerInfo.getSaveFile();
    }

    public void massSetManager() {
        for (ScreenInterface screen : Screens) {
            screen.setManager(this);
        }
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    //setter and getter for back-button-color
    public void setBackButtonColor(Color backButtonColor) {this.backButtonColor=backButtonColor;}
    public Color getBackButtonColor() {return backButtonColor;}
}

