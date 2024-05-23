import javax.swing.ImageIcon;

public class Cabinet extends Item {
    /*
     * constructor
     */
    public Cabinet(String name, String image, int currentRoom, int weigth, boolean pikable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        super.weight = weight; 
        super.name = name;
        super.currentRoom = currentRoom;
        super.pikable = pikable;
    }    
}
