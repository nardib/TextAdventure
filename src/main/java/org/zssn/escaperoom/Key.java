package org.zssn.escaperoom;

/**
 * Key class is a subclass of Item. It is used to unlock the item containers in the game
 */
public class Key extends Item {
    /**
     * Id of the key to match the correct lock
     */
    private int ID;

    /**
     * The weight of the key is 1
     */
    public final static int WEIGHT = 1;

    /**
     * The key is pickable
     */
    public final static boolean PICKABLE = true;

    /**
     * Default constructor for the Key
     */
    public Key() {super();}

    /**
     * Constructor for the Key
     * 
     * @param name name of the key
     * @param id id of the key
     * 
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public Key(String name, int id)
    {
        super(name, WEIGHT, PICKABLE);
        this.ID = id;
        usingMessage = "To use the key, face the wall with the item container to unlock and use the item with 'use <itemContainer>'";
    }

    /**
     * Get the ID of the key
     * 
     * @return the ID of the key
     */ 
    public int getID()
    {
        return ID;
    }

    /**
     * Set the ID of the key
     * 
     * @param id the ID of the key
     */
    public void setID(int id) {
        this.ID = id;
    }

    /**
     * Clone the key
     * 
     * @return a new key with the same attributes
     */
    @Override
    public Key clone()
    {
        return new Key(name, ID);
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
        return this.name.equals(k.name) && this.ID == k.ID && this.usingMessage.equals(k.usingMessage);
    }
}