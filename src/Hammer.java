import javax.swing.ImageIcon;

public class Hammer extends Item {
    /*
     * constructor
     */
    public Hammer(String name, String image, int weight, int currentRoom, boolean pikable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        super.weight = weight; 
        super.name = name;
        super.currentRoom = currentRoom;
        super.pikable = pikable;
    }    
}