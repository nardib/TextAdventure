import javax.swing.ImageIcon;

public class Item {
    /*
     * state variable
     */
    protected String name;
    protected ImageIcon icon;
    protected int weight;
    protected int currentRoom;
    protected boolean pikable; 

    /*
     * retrurns the icon of the image
     */
    public ImageIcon getIcon()
    {
        return icon;
    }

    /*
     * returns the weight of the object
     */
    public int getWeight()
    {
        return weight;
    }

    /*
     * returns the roomt of the object
     */
    public int getRoom()
    {
        return currentRoom;
    }
    
    /*
     * used to get the name
     */
    public String getName()
    {
        return name;
    }

    /*
     * is held?
     */
    public boolean isPikable()
    {
        return pikable;
    }
}