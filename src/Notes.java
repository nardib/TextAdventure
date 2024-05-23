import javax.swing.ImageIcon;

public class Notes extends Item {
    private String message;

    public String getMessage() 
    {
        return message;
    }
    /*
     * constructor
     */
    public Notes(String name, String image, int weight, int currentRoom, boolean pikable, String message)
    {
        icon = new ImageIcon(getClass().getResource(image));
        this.message = message;
        super.weight = weight; 
        super.name = name;
        super.currentRoom = currentRoom;
        super.pikable = pikable;
    }    
}