package org.zssn.escaperoom;

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
     * Default constructor for the ClueItem class
     */
    public ClueItem() {super();}

    /**
     * Constructor for the CluesItem class
     * 
     * @param name name of the item
     * @param usingMessage message to show when the item is used
     */
    public ClueItem(String name, String usingMessage) {
        super(name, WEIGHT, PICKABLE);
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
        return new ClueItem(name, usingMessage);
    }

    /**
     * Check if two ClueItem are equal
     * 
     * @param other the item to compare
     * @return true if the two items are equal, false otherwise
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof ClueItem || other == null))
            return false;
        ClueItem i = (ClueItem) other;
        return this.name.equals(i.name) && this.usingMessage.equals(i.usingMessage);
    }
}
