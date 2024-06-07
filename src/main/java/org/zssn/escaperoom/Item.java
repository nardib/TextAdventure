package org.zssn.escaperoom;

import javax.swing.ImageIcon;

/**
 * Class Item - Represents an item in the game. It has a name, an image, a weight, a current room, and a pickable state.
 */
public class Item {
    /**
     * Name of the Item
     */
    protected String name;

    /**
     * Icon of the Item
     */
    protected ImageIcon icon;

    /**
     * Weight of the Item
     */
    public final int WEIGHT;

    /**
     * Current room of the Item
     */
    protected int currentRoom;

    /**
     * Pickable state of the Item
     */
    public final boolean PICKABLE;

    /**
     * Message to show when the item is used
     */
    protected String usingMessage;

    /**
     * Constructor for the Item
     * 
     * @param name name of the item
     * @param image image of the item given as the name of the file in the src/main/resources folder
     * @param weight weight of the item
     * @param currentRoom current room of the item
     * @param pickable pickable state of the item
     * @throws IllegalArgumentException if the weight is less than 0 or the room is not between 0 and 9
     */
    public Item(String name, String image, int weight, int currentRoom, boolean pickable)
    {
        icon = new ImageIcon(getClass().getResource("/" + image));
        setName(name);
        setRoom(currentRoom);
        this.PICKABLE = pickable;

        if(weight >= 0)
            this.WEIGHT = weight;
        else
            throw new IllegalArgumentException("Weight must be greater than or equal to 0");
    }

    /**
     * Retrurns the icon of the image
     * 
     * @return icon of the image
     */
    public ImageIcon getIcon()
    {
        return icon;
    }

    /**
     * Returns the roomt of the object
     * 
     * @return room of the object as an integer (0 if the object is picked by the player)
     */
    public int getRoom()
    {
        return currentRoom;
    }
    
    /**
     * Returns the name of the object
     * 
     * @return name of the object
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the using message of the object
     * 
     * @return using message of the object
     */
    public String getUsingMessage() {
        return usingMessage;
    }

    /**
     * Set the current room (if 0, then it is picked by the player)
     * 
     * @param room room of the object given as an integer
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public void setRoom(int room)
    {
        if(room >= 0 && room <= 9) {
            currentRoom = room;
            return;
        }
        throw new IllegalArgumentException("Room must be between 0 and 9");
    }

    /**
     * Method to set the name of an item
     * 
     * @param name name of the item
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Method to check if two items are equal
     * 
     * @param other object to compare
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Item || other == null))
            return false;
        Item i = (Item) other;
        return this.name.equals(i.name) && this.WEIGHT == i.WEIGHT && this.currentRoom == i.currentRoom && this.PICKABLE == i.PICKABLE;
    }

    /**
     * Method to clone an item
     * 
     * @return a new item with the same attributes
     */
    @Override
    public Item clone() {
        return new Item(this.name, this.icon.toString(), this.WEIGHT, this.currentRoom, this.PICKABLE);
    }
}