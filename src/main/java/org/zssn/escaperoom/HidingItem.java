package org.zssn.escaperoom;

/**
 * HidingItem class is a subclass of Item class. It represents a hiding item that can be used to hide the player from the enemy.
 */
public class HidingItem extends Item {

    /**
     * The weight of the hiding item is 11 because it can't be picked up
     */
    public final int WEIGHT = 11;

    /**
     * The hiding item is not pickable
     */
    public final static boolean PICKABLE = false;

    /**
     * Constructor for the HidingItem
     * 
     * @param name name of the hiding item
     * @param image image of the hiding item given as a name of the file in the src/main/resources folder
     * @param currentRoom current room of the hiding item
    */
    public HidingItem(String name, String image, int currentRoom)
    {
        super (name, image, currentRoom, currentRoom, false);
        usingMessage = "You are now hidden in the " + name + "\n The enemy now can't see you!\nEnter \"wait\" to wait for the enemy to change room or \"get out\" to exit from the chest.\"";
    }    
}