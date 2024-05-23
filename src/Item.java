import javax.swing.ImageIcon;

public class Item {
    /*
     * state variable
     */
    protected String name;
    protected ImageIcon icon;
    protected int weight;
    protected int currentRoom;
    protected boolean pickable; 

    /*
     * constructor
     */
    public Item(String name, String image, int weight, int currentRoom, boolean pickable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        this.weight = weight;
        this.name = name;
        this.currentRoom = currentRoom;
        this.pickable = pickable;
    }

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
    public boolean isPickable()
    {
        return pickable;
    }
}