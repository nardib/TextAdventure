import java.lang.Math;

public class Enemy {
    /*
     * state variables
     */
    private String name;
    private Gender gender;
    private Room currentRoom; 
    private int damage = 1; //number of HP decreased to the player every 1.5 seconds
    private boolean presence; //is the enemy in the map?
    private boolean[] aveilable = new boolean[4];

    //aveilable[0] = currentRoom.crossableNorth;
    //aveilable[1] = currentRoom.crossableNorth;
    //aveilable[2] = currentRoom.crossableNorth;
    //aveilable[3] = currentRoom.crossableNorth;
    /*public void move() 
    {
        int i = 4%(int)(Math.random()*4);
        //if(aveilable[i]==true)
            
    }*/
}
