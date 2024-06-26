package org.zssn.escaperoom;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class Wall - Represents a wall in the game. It has an image, (possibly) a door, and items.
 */
public class Wall {
    
    /**
     * The image of the wall
     */
    private BufferedImage wall;
    
    /**
     * Tells if the wall has a door
     */
    private boolean hasDoor;
    
    /**
     * The items on the wall
     */
    private Map<Point, BufferedImage> itemsImages = new HashMap<>();
    
    /**
     * The combined image of the wall and the items
     */
    private BufferedImage combined;

    /**
     * Items in the room
     */
    private Item[] items;

    /**
     * Length of the array of items
     */
    private int itemsLength;

    /**
     * Default constructor for the Wall
     */
    public Wall() {}

    /**
     * Constructor for the Wall
     * @param w The image of the wall
     * @param d Tells if the wall has a door
     * @param items The items in the room
     */
    public Wall(BufferedImage w, boolean d, Item[] items) 
    {
        this.wall = w;
        this.hasDoor = d;
        this.combined = deepCopy(w);  // Copy the wall image to combined
        setItems(items);
    }

    /**
     * Constructor for the Wall
     * @param w The image of the wall
     * @param d Tells if the wall has a door
     */
    public Wall(boolean d, Item[] items) 
    {
        this.hasDoor = d;
        setItems(items);
    }

    /**
     * Add an item to the wall 
     * @param i The image of the item
     * @param p The position of the item
     */
    public void addItemImages(BufferedImage i, Point p) 
    {
        this.itemsImages.put(p, i);
        drawItems();  // Draw the items onto the combined image
    }

    /**
     * Check if the wall has a door
     * @return True if the wall has a door, false otherwise
     */
    public boolean hasDoor() 
    {
        return hasDoor;
    }

    /**
     * Remove an item from the wall
     * @param p The position of the item
     */
    public void removeItemImage(Point p) 
    {
        this.itemsImages.remove(p);
        this.combined = deepCopy(wall);  // Reset the combined image to the wall image
        drawItems();  // Redraw the remaining items
    }

    /**
     * Get the combined image of the wall and the items
     * @return The combined image
     */
    public BufferedImage returnCombinedImage() 
    {
        return combined;
    }

    /**
     * Draw the items onto the combined image 
     */
    private void drawItems() 
    {
        Graphics2D g = combined.createGraphics();
        for (Map.Entry<Point, BufferedImage> entry : itemsImages.entrySet()) 
        {
            Point p = entry.getKey();
            BufferedImage i = entry.getValue();
            g.drawImage(i, p.x, p.y, null);
        }
        g.dispose();
    }

    /**
     * Set the wall to have a door
     */
    public void setDoor()
    {
        hasDoor = true;
    }

    /**
     * Set the door of the wall
     * 
     * @param hasDoor true if the wall has a door, false otherwise
     */
    public void setDoor(boolean hasDoor) 
    {
        this.hasDoor = hasDoor;
    }

    /**
     * Deep copy a BufferedImage
     * @param bi The BufferedImage to copy
     * @return The copied BufferedImage
     */
    private static BufferedImage deepCopy(BufferedImage bi)
    {
        return new BufferedImage(bi.getColorModel(),
                bi.copyData(null),
                bi.getColorModel().isAlphaPremultiplied(), null);
    }

    /**
     * Get the the items in the room
     * 
     * @param index the index of the item
     * @return the items in the room
     * 
     * @throws IllegalArgumentException if the index is not in the range of 0-(maxIndex-1)
     */
    public Item getItem(int index)
    {
        if (index < 0 || index >= itemsLength)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        return items[index];
    }

    /**
     * Get the length of the array of items
     * 
     * @return the length of the array of items
     */
    public int getItemsLength()
    {
        return itemsLength;
    }

    /**
     * Set the items length of the wall
     * 
     * @param items the items  length of the wall
     */
    public void setItemsLength(int itemsLength) {
        this.itemsLength = itemsLength;
    }

    /**
     * Check if the room has items
     * 
     * @return true if the room has items, false otherwise
     */
    public boolean hasItems()
    {
        return items != null && items.length > 0;
    }

    /**
     * Get the items of the wall
     * 
     * @return the items of the wall
     */
    public Item[] getItems()
    {
        return items;
    }

    /**
     * Set the items in the room
     * 
     * @param items the items in the room
     */
    public void setItems(Item[] items)
    {
        if (items == null || items.length == 0)
        {
            this.items = null;
            return;
        }
        else
        {
            this.items = new Item[items.length];
            for (int i = 0; i < items.length; i++)
                this.items[i] = items[i];
            itemsLength = items.length;
        }
    }

    /**
     * Remove an item from the wall
     * 
     * @param index the index of the item to remove
     * 
     * @throws IllegalArgumentException if the index is not in the range of 0-(maxIndex-1)
     */
    public void removeItem(int index) 
    {
        if (index < 0 || index >= itemsLength)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        for (int i = index; i < itemsLength - 1; i++)
            items[i] = items[i + 1];
        itemsLength--;
    }

    /*
     * Clone the wall
     * 
     * @return the cloned wall
     
    @Override
    public Wall clone(){
        if (items == null)
            return new Wall(deepCopy(wall), hasDoor, null);
        Item[] items = new Item[this.items.length];
        for (int i = 0; i < this.items.length; i++)
            items[i] = this.items[i].clone();
        Wall w = new Wall(deepCopy(wall), hasDoor, items);
        for (Map.Entry<Point, BufferedImage> entry : itemsImages.entrySet())
            w.addItemImages(deepCopy(entry.getValue()), new Point(entry.getKey()));
        w.itemsLength = this.itemsLength;
        return w;
    }*/

    /**
     * Clone the wall
     * 
     * @return the cloned wall
     */
    @Override
    public Wall clone(){
        if (items == null)
            return new Wall(hasDoor, null);
        Item[] items = new Item[this.items.length];
        for (int i = 0; i < this.items.length; i++)
            items[i] = this.items[i].clone();
        Wall w = new Wall(hasDoor, items);
        w.itemsLength = this.itemsLength;
        return w;
    }

    /**
     * Verify if the wall is equal to another wall
     * 
     * @param obj the wall to compare
     * @return true if the walls are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Wall))
            return false;
        Wall w = (Wall) obj;
        if (itemsLength != w.itemsLength)
            return false;
        if (items != null && w.items != null)
        {
            for (int i = 0; i < itemsLength; i++)
                if (!items[i].equals(w.items[i]))
                    return false;
        }
        else if (items != null || w.items != null)
            return false;
        return hasDoor == w.hasDoor;
    }
}