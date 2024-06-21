package org.zssn.escaperoom;

import javax.swing.ImageIcon;
/**
 * Item class for all the clues items in the game
 * Each item has a clue necessary for completing the game
 * Clue objects can't be picked up like notes
 */
public class ClueItem extends Item{
    
    /**
     * Weight of the item
     */
    public final static int WEIGHT = 11;

    /**
     * Pickable state of the item
     */
    public final static boolean PICKABLE = false;

    /**
     * Constructor for the CluesItem class
     * 
     * @param name name of the item
     * @param image image of the item given as the name of the file in the src/main/resources folder
     * @param currentRoom current room of the item
     * @param usingMessage message to show when the item is used
     */
    public ClueItem(String name, String image, int currentRoom, String usingMessage) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.usingMessage = usingMessage;
    }

    /**
     * Constructor for the CluesItem class
     * 
     * @param name name of the item
     * @param image image of the item given as a ImageIcon object
     * @param currentRoom current room of the item
     * @param usingMessage message to show when the item is used
     */
    public ClueItem(String name, ImageIcon image, int currentRoom, String usingMessage) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.usingMessage = usingMessage;
    }

    /**
     * Clone the item
     * 
     * @return a new item with the same attributes
     */
    @Override
    public ClueItem clone()
    {
        return new ClueItem(name, icon, currentRoom, usingMessage);
    }
}
