package org.zssn.escaperoom;

import javax.swing.ImageIcon;
/**
 * HealingItem class is a subclass of Item class. It represents a healing item that can be used to heal the player.
 */
public class HealingItem extends Item {
    /**
     * Points that the healing item heals
     */
    public final int HEALING_POINTS;

    /**
     * The item is pickable
     */
    public final static boolean PICKABLE = true;
    
    /** 
     * Constructor for the HealingItem
     * 
     * @param name name of the healing item
     * @param image image of the healing item given as a name of the file in the src/main/resources folder
     * @param weight weight of the healing item
     * @param currentRoom current room of the healing item
     * @param healthPoints health points that the healing item heals
     * 
     * @throws IllegalArgumentException if health points are less than 0
     */
    public HealingItem(String name, String image, int weight, int currentRoom, int healthPoints)
    {
        super(name, image, weight, currentRoom, PICKABLE);
        if (healthPoints < 0)
        {
            throw new IllegalArgumentException("Health points must be greater than or equal to 0");
        }
        this.HEALING_POINTS = healthPoints;
        usingMessage = "You have used " + name + " and gained " + healthPoints + " health points";
    }

    /** 
     * Constructor for the HealingItem
     * 
     * @param name name of the healing item
     * @param image image of the healing item given as ImageIcon object
     * @param weight weight of the healing item
     * @param currentRoom current room of the healing item
     * @param healthPoints health points that the healing item heals
     * 
     * @throws IllegalArgumentException if health points are less than 0
     */
    public HealingItem(String name, ImageIcon image, int weight, int currentRoom, int healthPoints)
    {
        super(name, image, weight, currentRoom, PICKABLE);
        if (healthPoints < 0)
        {
            throw new IllegalArgumentException("Health points must be greater than or equal to 0");
        }
        this.HEALING_POINTS = healthPoints;
        usingMessage = "You have used " + name + " and gained " + healthPoints + " health points";
    }

    /** 
     * Clone the healing item
     * 
     * @return a new healing item with the same attributes
     */
    @Override
    public HealingItem clone()
    {
        return new HealingItem(name, icon, WEIGHT, currentRoom, HEALING_POINTS);
    }
}