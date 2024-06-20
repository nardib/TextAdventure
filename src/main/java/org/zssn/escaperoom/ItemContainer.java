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
    public ItemContainer (String name, String image, int currentRoom, Item[] items, boolean locked) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.locked = locked;

        ArrayIndexCount = items.length;
        this.items = new Item[ArrayIndexCount];
        for (int i = 0; i < ArrayIndexCount; i++) {
            this.items[i] = items[i];
        }
        setUsingMessage();
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

    /**
     * Method to set the using message of the container
     */
    public void setUsingMessage() {
        usingMessage = "In this container you find: \n";
        for (int i = 0; i < ArrayIndexCount; i++)
            usingMessage += "· " + items[i].getName() + "\n";
        usingMessage += "If you want to pick an item from the container, type 'use " + this.getName() + " <item_name>'";
    }

    /**
     * Method to get the element at index i of the container
     */
    public Item getItem(int i) {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        if (i < 0 || i >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        return items[i];
    }

    /**
     * Method to get the number of items in the container
     */
    public int getItemsLength() {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        return ArrayIndexCount;
    }

    /**
     * Method to check if the container is locked
     */
    public boolean isLocked() {
        return locked;
    }
}