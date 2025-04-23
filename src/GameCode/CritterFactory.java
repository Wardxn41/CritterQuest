package GameCode;

public class CritterFactory {

    // Turtle in crystals during the day
    public static CritterInfo createTurtle(String name) {
        CritterTemplate turtleTemplate = new CritterTemplate(
                "Turtle", 150,
                6, 6,
                70, 40, 15,
                3, 5, 8,
                15, 20, 40,
                "images/background/CrystalsDay.png"
        );
        return new CritterInfo(name, turtleTemplate);
    }

    // Whale in pond during the day
    public static CritterInfo createWhale(String name) {
        CritterTemplate whaleTemplate = new CritterTemplate(
                "Whale", 120,
                5, 4,
                70, 40, 15,
                5, 7, 10,
                10, 12, 20,
                "images/background/PondDay.png"
        );
        return new CritterInfo(name, whaleTemplate);
    }

    // Mushroom Man on trail during the day
    public static CritterInfo createMushroom_Man(String name) {
        CritterTemplate mushroomTemplate = new CritterTemplate(
                "MushroomMan", 100,
                2, 9999,
                70, 40, 15,
                6, 9, 12,
                8, 10, 15,
                "images/background/TrailDay.png"
        );
        return new CritterInfo(name, mushroomTemplate);
    }

    // Bear with antlers in forest during the day
    public static CritterInfo createBear(String name) {
        CritterTemplate bearTemplate = new CritterTemplate(
                "Bear", 150,
                6, 6,
                70, 40, 15,
                3, 5, 8,
                15, 20, 40,
                "images/background/ForestDay.png"
        );
        return new CritterInfo(name, bearTemplate);
    }

    // Cool Wolf in woods at night
    public static CritterInfo createWolf(String name) {
        CritterTemplate wolfTemplate = new CritterTemplate(
                "Wolf", 150,
                6, 6,
                70, 40, 15,
                3, 5, 8,
                15, 20, 40,
                "images/background/WoodsNight.png"
        );
        return new CritterInfo(name, wolfTemplate);
    }

}

