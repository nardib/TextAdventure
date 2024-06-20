package org.zssn.escaperoom;

/**
 * ItemContainer class that contains other items used in the game
 * The items in the container can be removed, but can't be added back
 */
public class ItemContainer extends Item{
     /**
     * Weight of the item
     */
    public final static int WEIGHT = 11;

    /**
     * Pickable state of the item
     */
    public final static boolean PICKABLE = false;

    /**
     * Array of items in the container
     */
    private Item[] items;

    /**
     * Index of the last element of the array
     */
    private int ArrayIndexCount;

    /**
     * Tells if this hiding item is locked or not
     */
    private boolean locked;

    
    /**
     * Constructor for the CluesItem class
     */
    public ItemContainer (String name, String image, int currentRoom, String usingMessage, Item[] items, boolean locked) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.usingMessage = usingMessage;
        this.locked = locked;

        ArrayIndexCount = items.length;
        this.items = new Item[ArrayIndexCount];
        for (int i = 0; i < ArrayIndexCount; i++) {
            this.items[i] = items[i];
        }
    }

    /**
     * Method to remove an item to the container
     */
    public Item removeItem(int index) {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        Item removedItem = items[index];
        items[index] = null;
        //compact the elements of the array
        int j = 0;
        for (int i = 0; i < ArrayIndexCount; i++)
        {
            if (items[i] != null)
            {
                items[j] = items[i];
                j++;
            }
        }
        ArrayIndexCount--;
        return removedItem;
    }

    /**
     * Method to print the items in the container
     */
    public String printItems() {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        String out = "";
        for (int i = 0; i < ArrayIndexCount; i++)
            out += items[i].getName() + "\n";
        return out;
    }

    /**
     * Method to unlock the container
     */
    public void unlock() {
        locked = false;
    }
}