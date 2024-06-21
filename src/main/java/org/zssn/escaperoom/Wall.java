package org.zssn.escaperoom;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

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
     * Last element of the items array
     */
    private int lastItem;

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
    public BufferedImage getCombinedImage() 
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
     */
    public Item getItem(int index)
    {
        return items[index];
    }

    /**
     * Get the length of the array of items
     * 
     * @return the length of the array of items
     */
    public int getItemsLength()
    {
        return lastItem;
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
            lastItem = items.length;
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
        if (index < 0 || index >= lastItem)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        for (int i = index; i < lastItem - 1; i++)
            items[i] = items[i + 1];
        lastItem--;
    }

    /**
     * Clone the wall
     * 
     * @return the cloned wall
     */
    @Override
    public Wall clone(){
        Item[] items = new Item[this.items.length];
        for (int i = 0; i < this.items.length; i++)
            items[i] = this.items[i].clone();
        Wall w = new Wall(deepCopy(wall), hasDoor, items);
        for (Map.Entry<Point, BufferedImage> entry : itemsImages.entrySet())
            w.addItemImages(deepCopy(entry.getValue()), new Point(entry.getKey()));
        lastItem = items.length;
        return w;
    }
}