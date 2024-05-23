import javax.swing.ImageIcon;

public class Lock extends Item {
    private int id; //used to identify the correct key to interract with depending on the key used. There are more keys.

    
    /*
     * used to get the id to match the correct lock with the key
     */
    public int getId()
    {
        return id;
    }
    /*
     * constructor
     */
    public Lock(String name, int id, String image, int weight, int currentRoom, boolean pikable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        super.weight = weight;
        this.id = id; 
        super.name = name;
        super.currentRoom = currentRoom;
        super.pikable = pikable;
    }    
}