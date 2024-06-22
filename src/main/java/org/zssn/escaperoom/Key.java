package org.zssn.escaperoom;

import javax.swing.ImageIcon;

/**
 * Key class is a subclass of Item. It is used to unlock doors in the game.
 */
public class Key extends Item {
    /**
     * Id of the key to match the correct lock
     */
    public final int ID;

    /**
     * The weight of the key is 1
     */
    public final static int WEIGHT = 1;

    /**
     * The key is pickable
     */
    public final static boolean PICKABLE = true;

    /**
     * Constructor for the Key
     * 
     * @param name name of the key
     * @param id id of the key
     * @param image image of the key given as a name of the file in the src/main/resources folder
     * @param currentRoom current room of the key
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public Key(String name, int id, String image, int currentRoom)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = id;
        usingMessage = "To use the key, face the wall with the item container to unlock and use the item with 'use <itemContainer>'";
    }

    /**
     * Constructor for the Key
     * 
     * @param name name of the key
     * @param id id of the key
     * @param image image of the key given as an ImageIcon object
     * @param currentRoom current room of the key
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public Key(String name, int id, ImageIcon image, int currentRoom)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = id;
        usingMessage = "To use the key, face the wall with the item container to unlock and use the item with 'use <itemContainer>'";
    }

    /**
     * Clone the key
     * 
     * @return a new key with the same attributes
     */
    @Override
    public Key clone()
    {
        return new Key(name, ID, icon, currentRoom);
    }

    /**
     * Check if two keys are equal
     * 
     * @param other the key to compare
     * @return true if the two keys are equal, false otherwise
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Key || other == null))
            return false;
        Key k = (Key) other;
        return this.name.equals(k.name) && this.ID == k.ID && this.currentRoom == k.currentRoom && this.usingMessage.equals(k.usingMessage) && this.icon.equals(k.icon);
    }
}