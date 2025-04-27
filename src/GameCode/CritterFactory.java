package GameCode;

public class CritterFactory {

    // Turtle
    public static CritterInfo createTurtle(String name) {
        CritterTemplate turtleTemplate = new CritterTemplate(
                "Turtle", 200,
                8, 8, // Very slow hunger/thirst decay
                50, 30, 10, // thresholds
                5, 15, 25, // damage per threshold
                20, 30, 50, // timers
                "images/background/turtleBackground.png",
                "images/creatures/turtleCritter_transparent.png",1, // Easy
                "Tanky and slow. Easy to care for."
        );
        return new CritterInfo(name, turtleTemplate);
    }

    // Whale
    public static CritterInfo createWhale(String name) {
        CritterTemplate whaleTemplate = new CritterTemplate(
                "Whale", 150,
                5, 2, // Normal hunger, super fast thirst
                60, 30, 10, // thresholds
                15, 20, 30, // damage per threshold
                10, 20, 30, // timers
                "images/background/whaleBackground.png",
                "images/creatures/whaleCritter_transparent.png", 4,
                "Massive thirst drain. Needs constant hydration"
        );
        return new CritterInfo(name, whaleTemplate);
    }

    // Mushroom Man
    public static CritterInfo createMushroom_Man(String name) {
        CritterTemplate mushroomTemplate = new CritterTemplate(
                "Mushroom Man", 100,
                3, 9999, // Slow hunger, no thirst
                40, 25, 10, // thresholds
                10, 20, 25, // damage per threshold
                25, 35, 50, // timers
                "images/background/mushroomManBackground.png",
                "images/creatures/mushroomManCritter_transparent.png", 2,
                "Low maintenance but fragile if neglected"
        );
        return new CritterInfo(name, mushroomTemplate);
    }

    // Bear - Tough
    public static CritterInfo createBear(String name) {
        CritterTemplate bearTemplate = new CritterTemplate(
                "Bear", 100,
                6, 6, // Medium hunger/thirst decay
                55, 35, 15, // thresholds
                10, 15, 20, // damage per threshold
                15, 20, 30, // timers
                "images/background/bearBackground.png",
                "images/creatures/bearCritter_transparent.png", 3,
                "Tough but needs moderate attention to stay healthy"
        );
        return new CritterInfo(name, bearTemplate);
    }

    // Wolf
    public static CritterInfo createWolf(String name) {
        CritterTemplate wolfTemplate = new CritterTemplate(
                "Wolf", 100,
                3, 5, // Very fast hunger, normal thirst
                60, 35, 15, // thresholds
                10, 20, 30, // damage per threshold
                12, 18, 25, // timers
                "images/background/wolfBackground.png",
                "images/creatures/wolfCritter_transparent.png", 5,
                "Wild and fast metabolism. Needs constant feeding!"
        );
        return new CritterInfo(name, wolfTemplate);
    }
}



