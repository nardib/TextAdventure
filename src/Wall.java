import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Wall {
    // Wall
    private BufferedImage wall;
    // Door
    private boolean hasDoor;
    // Items
    private Map<Point, BufferedImage> items = new HashMap<>();
    // Combined image
    private BufferedImage combined;

    // Wall constructor
    public Wall(BufferedImage w, boolean d) 
    {
        this.wall = w;
        this.hasDoor = d;
        this.combined = deepCopy(w);  // Copy the wall image to combined
    }

    // Add item to wall
    public void addItem(BufferedImage i, Point p) 
    {
        this.items.put(p, i);
        drawItems();  // Draw the items onto the combined image
    }

    public boolean hasDoor() 
    {
        return hasDoor;
    }

    // Remove item from wall
    public void removeItem(Point p) 
    {
        this.items.remove(p);
        this.combined = deepCopy(wall);  // Reset the combined image to the wall image
        drawItems();  // Redraw the remaining items
    }

    // Get the combined image
    public BufferedImage getCombinedImage() 
    {
        return combined;
    }

    // Draw the items onto the combined image
    private void drawItems() 
    {
        Graphics2D g = combined.createGraphics();
        for (Map.Entry<Point, BufferedImage> entry : items.entrySet()) 
        {
            Point p = entry.getKey();
            BufferedImage i = entry.getValue();
            g.drawImage(i, p.x, p.y, null);
        }
        g.dispose();
    }

    //Set door wall
    public void setDoor()
    {
        hasDoor = true;
    }

    // Create a copy of a BufferedImage
    private static BufferedImage deepCopy(BufferedImage bi)
    {
        return new BufferedImage(bi.getColorModel(),
                bi.copyData(null),
                bi.getColorModel().isAlphaPremultiplied(), null);
    }
}
