import javax.swing.ImageIcon;

public class Bandages extends Item {
    private int healtPoints = 2;

    public int getHealthPoints() 
    {
        return healtPoints;
    }
    /*
     * constructor
     */
    public Bandages(String name, String image, int weight, int currentRoom, boolean pikable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        super.weight = weight; 
        super.name = name;
        super.currentRoom = currentRoom;
        super.pikable = pikable;
    }    
}