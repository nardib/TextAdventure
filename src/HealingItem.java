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
     * @param image image of the healing item given as a path
     * @param weight weight of the healing item
     * @param currentRoom current room of the healing item
     * @param healthPoints health points that the healing item heals
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
     * Method to check if two healing items are equal
     * 
     * @param obj object to compare
     * @return true if the two healing items are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!(obj instanceof HealingItem))
            return false;
        HealingItem item = (HealingItem) obj;
        return super.equals(obj) && item.HEALING_POINTS == this.HEALING_POINTS;
    }
}