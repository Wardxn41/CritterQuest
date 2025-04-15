package GameCode;

public class CritterFactory {
//Turtle
    public static CritterInfo createTurtle(String name) {
        CritterTemplate turtleTemplate = new CritterTemplate(
                "Turtle", 150,
                6, 6,              // slower hunger/thirst decay
                70, 40, 15,        // thresholds
                3, 5, 8,           // damage
                15, 20, 40, "images/background/tempBackground.png"         // time thresholds
        );
        return new CritterInfo(name, turtleTemplate);
    }
// Whale
    public static CritterInfo createWhale(String name) {
        CritterTemplate whaleTemplate = new CritterTemplate(
                "Whale", 120,
                5, 4,              // faster hunger/thirst decay
                70, 40, 15,
                5, 7, 10,
                10, 12, 20, "images/background/temp.jpg"
        );
        return new CritterInfo(name, whaleTemplate);
    }

    // Mushroom Man
    public static CritterInfo createMushroom_Man(String name) {
        CritterTemplate mushroomTemplate = new CritterTemplate(
                "Mushroom Man", 100,
                2, 9999,           // no thirst loss
                70, 40, 15,
                6, 9, 12,
                8, 10, 15, "images/background/temp.jpg"
        );
        return new CritterInfo(name, mushroomTemplate);
    }
//Bear
    public static CritterInfo createBear(String name) {
        CritterTemplate bearTemplate = new CritterTemplate(
                "Turtle", 150,
                6, 6,              // slower hunger/thirst decay
                70, 40, 15,        // thresholds
                3, 5, 8,           // damage
                15, 20, 40, "images/background/temp.jpg"         // time thresholds
        );
        return new CritterInfo(name, bearTemplate);
    }
//WOLF
    public static CritterInfo createWolf(String name) {
        CritterTemplate wolfTemplate = new CritterTemplate(
                "Turtle", 150,
                6, 6,              // slower hunger/thirst decay
                70, 40, 15,        // thresholds
                3, 5, 8,           // damage
                15, 20, 40, "images/background/temp.jpg"         // time thresholds
        );
        return new CritterInfo(name, wolfTemplate);
    }
}


