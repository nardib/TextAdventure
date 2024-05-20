import javax.swing.ImageIcon;

public class PickableItem extends Item {
    /*
     * constructor
     */
    public PickableItem()
    {
        icon = new ImageIcon(getClass().getResource("Key.jpeg"));
        weight = 1;
    }

    
}