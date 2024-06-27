package org.zssn.escaperoom;

/**
 * HealingItem class is a subclass of Item class. It represents a healing item that can be used to heal the player.
 */
public class HealingItem extends Item {
    /**
     * Points that the healing item heals
     */
    private int healingPoints;

    /**
     * The item is pickable
     */
    public final static boolean PICKABLE = true;
    
    /** 
     * Default constructor for the HealingItem
    */
    public HealingItem() {super();}

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
        this.healingPoints = healthPoints;
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
        return new HealingItem(name, icon, weight, currentRoom, healingPoints);
    }

    /** 
     * Check if two healing items are equal
     * 
     * @param other the healing item to compare
     * @return true if the two healing items are equal, false otherwise
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof HealingItem || other == null))
            return false;
        HealingItem i = (HealingItem) other;
        return this.name.equals(i.name) && this.currentRoom == i.currentRoom && this.healingPoints == i.healingPoints && this.usingMessage.equals(i.usingMessage) && this.icon.equals(i.icon);
    }

    /**
     * Get the healing points of the healing item
     * 
     * @return the healing points of the healing item
     */
    public int getHealingPoints() {
        return healingPoints;
    }

    /**
     * Set the healing points of the healing item
     * 
     * @param healingPoints healing points of the healing item
     * @throws IllegalArgumentException if the healing points are less than 0
     */
    public void setHealingPoints(int healingPoints) {
        if (healingPoints < 0)
            throw new IllegalArgumentException("Health points must be greater than or equal to 0");
        this.healingPoints = healingPoints;
    }
    
}