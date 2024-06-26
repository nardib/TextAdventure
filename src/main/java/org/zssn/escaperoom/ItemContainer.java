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
     * Number of items in the container
     */
    private int ArrayIndexCount;

    /**
     * Tells if this hiding item is locked or not
     */
    private boolean locked;

    /**
     * Type of lock of the container
     */
    private LockType lock;

    /**
     * ID of the lock
     */
    private int ID;

    /**
     * Default constructor for the ItemContainer class
     */
    public ItemContainer() {super();}
    
    /**
     * Constructor for the CluesItem class
     * 
     * @param name name of the item
     * @param items items in the container
     * @param lock lock of the container
     * @param ID ID of the lock
     */
    public ItemContainer (String name, Item[] items, LockType lock, int ID) {
        super(name, WEIGHT, PICKABLE);

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
        this.setUsingMessage();
        return removedItem;
    }

    /**
     * Method to unlock the container with a key
     * 
     * @param k key to unlock the container
     * @return true if the container was succesfully unlocked, false instead
     * 
     * @throws IllegalArgumentException if this container can't be unlocked with a key
     */
    public boolean unlock(Key k) {
        if (lock == LockType.KEY){
            if (k.getID() == this.ID)
                locked = false;
            return !locked;
        }
        throw new IllegalArgumentException("This containter can't be unlocked with a key");
    }

    /**
     * Method to unlock the container with a numeric combination
     * 
     * @param comb numeric combination to unlock the container
     * @return true if the container was successfully unlocked, false instead
     * 
     * @throws IllegalArgumentException if this container can't be unlocked with a combination
     */
    public boolean unlock(int comb) {
        if (lock == LockType.COMBINATION) {
            if (comb == this.ID)
                locked = false;
            return !locked;
        }
        throw new IllegalArgumentException("This container can't be unlocked with a combination");
    }

    /**
     * Method to set the using message of the container based on the items in it
     */
    public void setUsingMessage() {
        if (ArrayIndexCount == 0){
            usingMessage = "There are no items in " + this.getName();
            return;
        }
        usingMessage = "In this container you find: \n";
        for (int i = 0; i < ArrayIndexCount; i++)
            usingMessage += "- " + items[i].getName() + "\n";
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
     * Method to get the items in the container
     * 
     * @return the items in the container
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * Method to get the number of items in the container
     * 
     * @return the number of items in the container
     * 
     * @throws IllegalArgumentException if the container is locked
     */
    public int getItemsLength() {
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

    /**
     * Method to set the lock of the container
     * 
     * @param lock lock of the container
     */
    public void setLockType(LockType lock) {
        this.lock = lock;
    }

    /**
     * Method to set the ID of the lock
     * 
     * @param ID ID of the lock
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Method to set the locked state of the container
     * 
     * @param locked locked state of the container
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Method to set the items in the container
     * 
     * @param items items in the container
     */
    public void setItems(Item[] items) {
        ArrayIndexCount = items.length;
        this.items = new Item[ArrayIndexCount];
        for (int i = 0; i < ArrayIndexCount; i++) {
            this.items[i] = items[i];
        }
    }

    /**
     * Method to set the number of items in the container
     * 
     * @param ArrayIndexCount number of items in the container
     */
    public void setItemsLength(int ArrayIndexCount) {
        this.ArrayIndexCount = ArrayIndexCount;
    }


    /**
     * Clone the item container
     * 
     * @return a new item container with the same attributes
     */
    @Override
    public ItemContainer clone() {
        if (ArrayIndexCount == 0) {
            ItemContainer ic = new ItemContainer(this.getName(), new Item[]{}, this.lock, this.ID);
            ic.locked = this.locked;
            return ic;
        }
        Item[] items = new Item[ArrayIndexCount];
        for (int i = 0; i < ArrayIndexCount; i++)
            items[i] = this.items[i].clone();
        ItemContainer ic = new ItemContainer(this.getName(), items, this.lock, this.ID);
        ic.locked = this.locked;
        return ic;
    }

    /**
     * Check if two item containers are equal
     * 
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemContainer || obj == null))
            return false;
        ItemContainer i = (ItemContainer) obj;
        i.setUsingMessage();
        this.setUsingMessage();
        if (i.ArrayIndexCount != this.ArrayIndexCount)
            return false;
        if (ArrayIndexCount == 0)
            return i.lock == this.lock && i.ID == this.ID && this.getName().equals(i.getName()) && this.usingMessage.equals(i.usingMessage) && this.locked == i.locked;
        for (int j = 0; j < ArrayIndexCount; j++)
            if (!i.items[j].equals(this.items[j]))
                return false;
        return i.lock == this.lock && i.ID == this.ID && this.getName().equals(i.getName()) && this.usingMessage.equals(i.usingMessage) && this.locked == i.locked;
    }
}