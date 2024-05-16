import java.language.Math;

public class Enemy {
    /*
     * state variables
     */
    private String name;
    private Gender gender;
    private Room currentRoom; 
    private int damage = 1; //number of HP decreased to the player every 1.5 seconds
    private boolean presence; //is the enemy in the map?
    

    public void move() 
    {
        int i = 4%(Integer.parseInt(Math.random()*4));
        if()
    }
}
