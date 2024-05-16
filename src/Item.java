import javax.swing.ImageIcon;

public class Item {
    /*
     * state variable
     */
    protected ImageIcon icon;
    protected int weight;

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
}