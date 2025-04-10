package GameCode;

public class CritterFactory {

    public static CritterInfo createTurtle(String name) {
        CritterTemplate turtleTemplate = new CritterTemplate(
                "Turtle", 150,
                6, 6,              // slower hunger/thirst decay
                70, 40, 15,        // thresholds
                3, 5, 8,           // damage
                15, 20, 40         // time thresholds
        );
        return new CritterInfo(name, turtleTemplate);
    }

    public static CritterInfo createRabbit(String name) {
        CritterTemplate rabbitTemplate = new CritterTemplate(
                "Rabbit", 80,
                3, 2,              // faster hunger/thirst decay
                70, 40, 15,
                5, 7, 10,
                10, 12, 20
        );
        return new CritterInfo(name, rabbitTemplate);
    }

    // You can add more:
    public static CritterInfo createFirePet(String name) {
        CritterTemplate fireTemplate = new CritterTemplate(
                "FirePet", 100,
                2, 9999,           // no thirst loss
                70, 40, 15,
                6, 9, 12,
                8, 10, 15
        );
        return new CritterInfo(name, fireTemplate);
    }
}

