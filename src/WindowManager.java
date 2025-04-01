public class WindowManager {
    //this one should be the one that actually handels changes to the current window, with what screen it switches to
    // controlled by each screen, what screen goes to what screen is controlled by each scene.
    //to be clear this one only communicates to each screen that they are now to show themselves in the window


    // for example say you are on the title screen which is screen id = 0 and is default, and you got the options button
    //start button and or another button. the title screen when any of these buttens are pushed tells the window manager
    //what index it should go to next controlled by each button, so if you clicked on start you would switch to id=1
    //and the window manager will prompt the title screen to draw its visuals on screen.

    //this solution makes it so that information of how a screen is shown is soly bound to each screen and so are where
    //each screen can take you making the whole screen section modular


}
