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
     * Default constructor for the HiderItem class
     */
    public HiderItem() {super();}

    /**
     * Constructor for the HiderItem class
     * 
     * @param name name of the hider item
     * @param image image of the hider item
     * @param currentRoom current room of the hider item
     * @param hiddenItem item hidden by the hider item
     */
    public HiderItem (String name, String image, int currentRoom, Item hiddenItem) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.hiddenItem = hiddenItem;
        this.usingMessage = this.name + " was hiding a " + hiddenItem.getName();
        //when created, the item is hiding an element
        hiding = true;
    }

    /**
     * Method to get the hiding state of the item
     * 
     * @return true if the item is hiding, false otherwise
     */
    public boolean isHiding() {
        return hiding;
    }

    /**
     * Method to get the hidden item
     * 
     * @return the hidden item
     */
    public Item getHiddenItem() {
        return hiddenItem;
    }

    /**
     * Method to set the hidden item
     * 
     * @param hiddenItem the hidden item
     */
    public void setHiddenItem(Item hiddenItem) {
        this.hiddenItem = hiddenItem;
    }

    /**
     * Method to set the hiding state of the item
     * 
     * @param hiding the hiding state of the item
     */
    public void setHiding(boolean hiding) {
        this.hiding = hiding;
    }

    /**
     * Method to reveal the hidden item
     * 
     * @return the hidden item
     */
    public Item reveal() {
        hiding = false;
        return hiddenItem;
    }

    /**
     * Clone the hider item
     * 
     * @return a new hider item with the same attributes
     */
    @Override
    public HiderItem clone() {
        HiderItem h = new HiderItem(name, icon, currentRoom, hiddenItem.clone());
        h.hiding = hiding;
        return h;
    }

    /**
     * Check if two hider items are equal
     * 
     * @param other the hider item to compare
     * @return true if the two hider items are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof HiderItem || other == null))
            return false;
        HiderItem h = (HiderItem) other;
        return this.name.equals(h.name) && this.currentRoom == h.currentRoom && this.hiding == h.hiding && this.hiddenItem.equals(h.hiddenItem);
    }
}