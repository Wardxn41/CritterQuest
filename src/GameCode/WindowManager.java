package GameCode;

import java.util.List;


public class WindowManager {

    private int screenIndex = 0;
    private List<ScreenInterface> Screens;

    public WindowManager(List<ScreenInterface> Screens) {
        this.Screens = Screens;
        massSetManager();
        ShowVisuals();
    }

    public void ShowVisuals() {
        Screens.get(screenIndex).ShowVisuals();
    }

    public void setIndex(int index) {
        this.screenIndex = index;
        ShowVisuals();
    }

    public void massSetManager() {
        for (ScreenInterface Screen : Screens) {
            Screen.setManager(this);
        }
    }
}

