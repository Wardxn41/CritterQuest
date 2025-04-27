package GameCode;

public class RandomEvent {
    private String name;
    private String description;
    private Runnable effect;

    public RandomEvent(String name, String description, Runnable effect) {
        this.name = name;
        this.description = description;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void trigger() {
        effect.run();
    }
}

