package GameCode;

import java.util.List;

public class Runner {

    public static void main(String[] args) {


        //creates an instance of windowManager and fills it with the screens it manages
        WindowManager windowManager = new WindowManager(List.of(

                new TitleScreen(), new FileScreen(), new OptionsScreen()
                //id = 1           //id = 2          //id = 3

        ));

    }

}
