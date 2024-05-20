import javax.swing.ImageIcon;

public class Item {
    /*
     * state variable
     */
    protected ImageIcon icon;
    protected int weight;
    private int currentRoom;

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
}