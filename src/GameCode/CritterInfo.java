package GameCode;
import java.util.*;
public class CritterInfo {
    private String critterName;
    private int health;
    private int hunger;
    private int thirst;
    private int age;
    //time tracking
    private long lastUpdatedTime;

    //maximums
    double maxHealth;
    double maxHunger;
    double maxThirst;

    // status flags                            //will modify existence based on in what stage of life you are in
    double[] ageThreasholds;    //basically at what ages are what lifepoints of creature, so baby, adult, elderly etc
    boolean isAlive;            //is only false if creature dies as its health goes below zero
    boolean isCreated;          //is only false if you are between rounds
    String[] StatusEffects;     //basically when your triad of resources, health, hunger, and thirst get below certain
    double[] StatusThreshholds; //threshholds they give you statuses, that inform the play that things are wrong, like
    //dehydrated or severly dehydrated, or healthy, near death, moderately unhealthy ect.
    //satiated, hungry, starving, ravenous
    boolean isHungry;
    boolean isThirsty;
    boolean isMoody;

    public CritterInfo(String critterName) {
        this.critterName = critterName;
        this.age = 0;
        this.health = 100;
        this.hunger = 100;
        this.thirst = 100;
    }

    //getters
    public String getCritterName() {
        return critterName;
    }
    public int getAge() {
        return age;
    }
    public int getHealth() {
        return health;
    }
    public int getHunger() {
        return hunger;
    }
    public int getThirst() {
        return thirst;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public boolean isCreated() {
        return isCreated;
    }
     // health logic
    private void updateHealth(int change){
        health = Math.max(0, Math.min(100, health + change));
    }
    private void updateHunger(int change){
        hunger = Math.max(0, Math.min(100, hunger + change));
    }
    private void updateThirst(int change){
        thirst = Math.max(0, Math.min(100, thirst + change));
    }

    //core actions
    public void feed(int amount) {
        hunger = Math.max(0, hunger-amount);
        updateHealth(5);
        updateHunger(10);
    }

    public void drink(int amount) {
        thirst = Math.max(0, thirst-amount);
        updateHealth(5);
        updateThirst(10);

    }

    public void tickUpdate(long currentTime) {
        long elapsedTime = currentTime - lastUpdatedTime;
        int minutesPassed = (int) (elapsedTime / 60000);

        hunger = Math.max(0, hunger-minutesPassed);
        thirst = Math.max(0, thirst-minutesPassed);

        if (hunger <= 10 || thirst <= 10) {
            updateHealth(-5);
        }
        if (health <= 0) {
            isAlive = false;
        }
        lastUpdatedTime = currentTime;
        age += minutesPassed;
    }

    //debugging utility
    public String toString() {
        return String.format(
                "%s the %s (Age: %d)\nHealth: %d\nHunger: %d\nThirst: %d\nStatus: %s",
                critterName, age, health, hunger, thirst,
                isAlive ? "Alive" : "Deceased"
        );
    }
}

