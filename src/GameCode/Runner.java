package GameCode;
import java.util.List;
import GameCode.CritterInfo;
import GameCode.CritterFactory;
public class Runner {
    public static void main(String[] args) {
        //CritterInfo critter = CritterFactory.createTurtle("Shelly");
        //creates an instance of windowManager and fills it with the screens it manages
        WindowManager windowManager = new WindowManager(List.of(

                new TitleScreen(), new FileScreen(), new OptionsScreen(),
                //id = 0           //id = 1          //id = 2
                new CreditsScreen(), new PreGameScreen(), new GameScreen(), new CritterSelectScreen(),
                //id = 3           //id = 4          //id = 5               //id = 6?
                new ShopScreen()
                //id = 7           //id =           //id =
        ));
    }

}
