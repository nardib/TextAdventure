package org.zssn.escaperoom;

/**
 * Item class for all the hider items in the game
 * An hider item hides another item in the room
 */
public class HiderItem extends Item{
    
    /**
     * Weight for the HiderItem
     */
    public final static int WEIGHT = 11;

    /**
     * Pickable state of the HiderItem
     */
    public final static boolean PICKABLE = false;

    /**
     * The item hidden by the hider item
     */
    private boolean hiding;

    /**
     * Item hidden by the hider item
     */
    private Item hiddenItem;

   /**
     * Constructor for the HiderItem class
     */
    public HiderItem (String name, String image, int currentRoom, Item hiddenItem) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.hiddenItem = hiddenItem;
        this.usingMessage = this.name + " is hiding a " + hiddenItem.getName();
        //when created, the item is hiding an element
        hiding = true;
    }

    /**
     * Method to get the hiding state of the item
     */
    public boolean isHiding() {
        return hiding;
    }

    /**
     * Method to reveal the hidden item
     */
    public Item reveal() {
        hiding = false;
        return hiddenItem;
    }
}