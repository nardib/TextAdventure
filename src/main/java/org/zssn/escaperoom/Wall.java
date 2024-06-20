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
     * Constructor for the Wall
     * @param w The image of the wall
     * @param d Tells if the wall has a door
     */
    public Wall(BufferedImage w, boolean d) 
    {
        this.wall = w;
        this.hasDoor = d;
        this.combined = deepCopy(w);  // Copy the wall image to combined
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
}
