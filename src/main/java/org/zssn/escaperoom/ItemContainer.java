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
     * 
     * @param name name of the item
     * @param image image of the item
     * @param currentRoom current room of the item
     * @param items items in the container
     * @param lock lock of the container
     * @param ID ID of the lock
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
     * 
     * @param index index of the item to remove
     * @return the removed item
     * 
     * @throws IllegalArgumentException if the container is locked or the index is invalid
     */
    public Item removeItem(int index) {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        if (index < 0 || index >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
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
     * 
     * @param obj object to unlock the container (Key or combination)
     * 
     * @throws IllegalArgumentException if the container is already unlocked
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
     * 
     * @param i index of the item
     * @return the item at index i
     * 
     * @throws IllegalArgumentException if the container is locked or the index is invalid
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
     * 
     * @return the number of items in the container
     * 
     * @throws IllegalArgumentException if the container is locked
     */
    public int getItemsLength() {
        if (locked)
            throw new IllegalArgumentException("The container is locked");
        return ArrayIndexCount;
    }

    /**
     * Method to check if the container is locked
     * 
     * @return true if the container is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Method to get the lock type of the container
     * 
     * @return the lock type of the container
     */
    public LockType getLockType() {
        return lock;
    }

    /**
     * Method to get the ID of the lock
     * 
     * @return the ID of the lock
     */
    public int getID() {
        return ID;
    }
}