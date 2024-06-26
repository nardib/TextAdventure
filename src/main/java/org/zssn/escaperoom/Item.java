package org.zssn.escaperoom;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClueItem.class, name = "clue item"),
    @JsonSubTypes.Type(value = HealingItem.class, name = "healing item"),
    @JsonSubTypes.Type(value = HiderItem.class, name = "hider item"),
    @JsonSubTypes.Type(value = HidingItem.class, name = "hiding item"),
    @JsonSubTypes.Type(value = ItemContainer.class, name = "item container"),
    @JsonSubTypes.Type(value = Key.class, name = "key"),
    @JsonSubTypes.Type(value = Note.class, name = "note"),
    @JsonSubTypes.Type(value = Star.class, name = "star"),
    @JsonSubTypes.Type(value = StarHole.class, name = "star hole"),})

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
    protected String icon;

    /**
     * Weight of the Item
     */
    protected int weight;

    /**
     * Current room of the Item
     */
    protected int currentRoom;

    /**
     * Pickable state of the Item
     */
    protected boolean pickable;

    /**
     * Message to show when the item is used
     */
    protected String usingMessage;

    /**
     * Default constructor for the Item
     */
    public Item () {}

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
        icon = image;
        setName(name);
        setCurrentRoom(currentRoom);
        this.pickable = pickable;

        if(weight >= 0)
            this.weight = weight;
        else
            throw new IllegalArgumentException("Weight must be greater than or equal to 0");
    }

    /**
     * Return the name of the item
     * 
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Return the weight of the item
     * 
     * @return weight of the item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Return the current room of the item
     * 
     * @return current room of the item
     */
    public int getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Return the pickable state of the item
     * 
     * @return pickable state of the item
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     * Return the using message of the item
     * 
     * @return using message of the item
     */
    public String getUsingMessage() {
        return usingMessage;
    }

    /**
     * Set the name of the item
     * 
     * @param name name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the icon of the item
     * 
     * @param icon icon of the item
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Set the weight of the item
     * 
     * @param weight weight of the item
     * @throws IllegalArgumentException if the weight is less than 0
     */
    public void setWeight(int weight) {
        if(weight >= 0)
            this.weight = weight;
        else
            throw new IllegalArgumentException("Weight must be greater than or equal to 0");
    }

    /**
     * Set the current room of the item
     * 
     * @param currentRoom current room of the item
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public void setCurrentRoom(int currentRoom) {
        if(currentRoom >= 0 && currentRoom <= 9) {
            this.currentRoom = currentRoom;
            return;
        }
        throw new IllegalArgumentException("Room must be between 0 and 9");
    }

    /**
     * Set the pickable state of the item
     * 
     * @param pickable pickable state of the item
     */
    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }

    /**
     * Set the using message of the item
     * 
     * @param usingMessage using message of the item
     */
    public void setUsingMessage(String usingMessage) {
        this.usingMessage = usingMessage;
    }   

    /**
     * Clone the item
     * 
     * @return a clone of the item
     */
    @Override
    public Item clone()
    {
        return new Item(name, icon, weight, currentRoom, pickable);
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
        return name.equals(i.name) && icon.equals(i.icon) && weight == i.weight && currentRoom == i.currentRoom && pickable == i.pickable;
    }
}