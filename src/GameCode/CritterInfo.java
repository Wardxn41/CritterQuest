package GameCode;

public class CritterInfo {

    double health;
    double hunger;
    double thirst;
    double maxHealth;
    double maxHunger;
    double maxThirst;
    double age;                 //will modify existence based on in what stage of life you are in
    double[] ageThreasholds;    //basically at what ages are what lifepoints of creature, so baby, adult, elderly etc
    boolean isAlive;            //is only false if creature dies as its health goes below zero
    boolean isCreated;          //is only false if you are between rounds
    String[] StatusEffects;     //basically when your triad of resources, health, hunger, and thirst get below certain
    double[] StatusThreshholds; //threshholds they give you statuses, that inform the play that things are wrong, like
                                //dehydrated or severly dehydrated, or healthy, near death, moderately unhealthy ect.
                                //satiated, hungry, starving, ravenous

    CritterInfo(){


    }

    //setters, getters, modifiers, and such go here for critter stuff

}
