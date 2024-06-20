package org.zssn.escaperoom;

enum LockType {
    KEY,
    COMBINATION,
    NONE
}
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
     * Lock of the container
     */
    public final LockType lock;

    /**
     * ID of the lock
     */
    private int ID;

    
    /**
     * Constructor for the CluesItem class
     */
    public ItemContainer (String name, String image, int currentRoom, Item[] items, LockType lock, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);

        ArrayIndexCount = items.length;
        this.items = new Item[ArrayIndexCount];
        for (int i = 0; i < ArrayIndexCount; i++) {
            this.items[i] = items[i];
        }
        setUsingMessage();
        this.lock = lock;
        locked = false;
        if (lock != LockType.NONE)
            locked = true;
        this.ID = ID;
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
     * Method to unlock the container
     */
    public void unlock(Object obj) {
        switch (lock) {
            case KEY:
                if (obj instanceof Key) {
                    Key key = (Key) obj;
                    if (key.ID == this.ID)
                        locked = false;
                }
            break;
            case COMBINATION:
                if (obj instanceof Integer) {
                    int combination = (Integer) obj;
                    if (combination == this.ID)
                        locked = false;
                }
            break;
        case NONE:
            locked = false;
        break;
        default:
            throw new IllegalArgumentException("Invalid lock type");
        }
        throw new IllegalArgumentException("Invalid input");
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

    /**
     * Method to get the lock type of the container
     */
    public LockType getLockType() {
        return lock;
    }


    /**
     * Method to get the ID of the lock
     */
    public int getID() {
        return ID;
    }
}