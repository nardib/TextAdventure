package org.zssn.escaperoom;

/**
 * HidingItem class is a subclass of Item class. It represents a hiding item that can be used to hide the player from the enemy.
 */
public class HidingItem extends Item {

    /**
     * The weight of the hiding item is 11 because it can't be picked up
     */
    public final static int WEIGHT = 11;

    /**
     * The hiding item is not pickable
     */
    public final static boolean PICKABLE = false;

    /**
     * Default constructor for the HidingItem class
     */
    public HidingItem() {super();}

    /**
     * Constructor for the HidingItem
     * 
     * @param name name of the hiding item
    */
    public HidingItem(String name)
    {
        super (name, WEIGHT, false);
        usingMessage = "You are now hidden in the " + name + "\nThe enemy now can't see you!\nEnter \"wait\" to wait for the enemy to change room or \"unhide\" to exit from the chest.";
    }

    /**
     * Clone the hiding item
     * 
     * @return a new hiding item with the same attributes
     */
    @Override
    public HidingItem clone()
    {
        return new HidingItem(name);
    }

    /**
     * Check if two hiding items are equal
     * 
     * @param obj hiding item to compare
     * @return true if the hiding items are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof HidingItem || obj == null))
            return false;
        HidingItem other = (HidingItem) obj;
        return name.equals(other.name);
    }
}