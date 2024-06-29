package org.zssn.escaperoom;

import java.lang.Math;

/**
 * Enemy class. This class represents the enemy in the game. 
 * The enemy is a character that moves randomly in the map and can attack the player. 
 * The enemy has a name.
 */
public class Enemy extends Character{ //AKA ER CATTIVONE

    /**
     * Damage that the enemy does to the player
     */
    public final int DAMAGE = 1;
    
    /**
     * Let the enemy move in a random direction in the map given the room in which it is
     *  
     * @see Room
     * @param n room in which the enemy is
     * @return the room in which the enemy is after the move
     */
    public int move(Room n) {
        boolean[] rooms = new boolean[4]; //contiene i boleani N, S, E, W per sapere se la facciata ha la porta o meno
        rooms[0] = n.getCrossableNorth(); 
        rooms[1] = n.getCrossableSouth();
        rooms[2] = n.getCrossableEast();
        rooms[3] = n.getCrossableWest();

        while(true) {
            int i = (int) (Math.random()*4);  //genera un intero da 0 a 3 che sarà usato per vedere se la facciata è attraversabile o meno. Se non lo è riprova. L'ordine delle facciate è 0=N, 1=S, 2=E e 3=W;
            if(rooms[i]) {
                switch(i) { //se la stanza selezionata è attraversabile allora setta la nuova stanza
                    case 0: currentRoom += CROSS_NORTH;
                    return currentRoom;
                    case 1: currentRoom += CROSS_SOUTH;
                    return currentRoom;
                    case 2: currentRoom += CROSS_EAST;
                    return currentRoom;
                    case 3: currentRoom += CROSS_WEST;
                    return currentRoom;
                }
            }
        }
    }

    /**
     * Defualt constructor for the enemy
     */
    public Enemy() {
        super();
    }

    /**
     * Constructor for the enemy
     * 
     * @param n name of the enemy
     * @param g gender of the enemy given as a string ("m","f","n" or "male", "female", "neutral" are the valid inputs)
     * 
     * @throws IllegalArgumentException if the gender is not given in a valid format
     */
    public Enemy(String n, String g) {
        super(n, g, generateStartingPoint());
    }

    /**
     * Constructor for the enemy
     * 
     * @param n name of the enemy
     * @param g is the gender of the enemy given ad Gender type
     */
    public Enemy(String n,Gender g) {
        super(n, g, generateStartingPoint());
    }

    /**
     * Private function to generate a random numeber for the starting point between 1 and 9 except 5, which is the starting room of the player
     * 
     * @return the starting room of the enemy
     */
    private static int generateStartingPoint() {
        int room = (int) Math.round(Math.random()*8)+1;
        //if i get 5, i recursively call the function again
        if(room == 5)
            return generateStartingPoint();
        return room;
    }

    /**
     * Method to check equality between two enemies
     * 
     * @param other the object to compare
     * @return true if the two enemies are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Enemy || other == null))
            return false;
        Enemy e = (Enemy) other;
        return this.name.equals(e.name) && this.gender == e.gender && this.currentRoom == e.currentRoom;
    }

    /**
     * Method to clone this enemy (deep copy)
     * 
     * @return a new enemy
     */
    @Override
    public Enemy clone() {
        Enemy e = new Enemy(this.getName(), this.getGender());
        e.setCurrentRoom(this.getCurrentRoom());
        return e;
    }
}