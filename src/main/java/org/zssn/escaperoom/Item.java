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
     * Constructor for the Item
     * 
     * @param name name of the item
     * @param image image of the item given as ImageIcon object
     * @param weight weight of the item
     * @param currentRoom current room of the item
     * @param pickable pickable state of the item
     * @throws IllegalArgumentException if the weight is less than 0 or the room is not between 0 and 9
     */
    public Item(String name, ImageIcon image, int weight, int currentRoom, boolean pickable)
    {
        icon = image;
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
    private void setName(String name)
    {
        this.name = name;
    }

    /**
     * Clone the item
     * 
     * @return a clone of the item
     */
    @Override
    public Item clone()
    {
        return new Item(name, icon, WEIGHT, currentRoom, PICKABLE);
    }

    /**
     * Check if two items are equal
     * 
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Item || obj == null))
            return false;
        Item i = (Item) obj;
        return name.equals(i.name) && icon.equals(i.icon) && WEIGHT == i.WEIGHT && currentRoom == i.currentRoom && PICKABLE == i.PICKABLE;
    }
}