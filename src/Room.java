import java.awt.image.*;

public class Room 
{
    /*
     * Room class variables
     */
    private int roomNumber;
    private String name;
    private int doors;
    private BufferedImage NWall;
    private BufferedImage EWall;
    private BufferedImage SWall;
    private BufferedImage WWall;
    boolean crossableNorth;
    boolean crossableEast;
    boolean crossableSouth;
    boolean crossableWest;

    /*
     * Room class constructor
     */
    public Room(int n, int d, String nam) 
    {
        this.roomNumber = n;
        this.name = nam;
        this.doors = d;
        //this.NWall = image;
        //this.EWall = image;
        //this.SWall = image;
        //this.WWall = image;
    }
    /*
     * Room class methods
     */
    public String getName() 
    {
        return name;
    }
    public int getRoomNumber() 
    {
        return roomNumber;
    }
    public int getDoors() 
    {
        return doors;
    }
    public void addPassageWay(Room room) 
    {
        // Add passage way to room
        if(room.getRoomNumber() == roomNumber + 1)
        {
            crossableEast = true;
        }
        else if(room.getRoomNumber() == roomNumber - 1)
        {
            crossableWest = true;
        }
        else if(room.getRoomNumber() == roomNumber + 3)
        {
            crossableSouth = true;
        }
        else if(room.getRoomNumber() == roomNumber - 3)
        {
            crossableNorth = true;
        }
    }

    public BufferedImage getNWall()
    {
        return NWall;
    }
    public BufferedImage getSWall()
    {
        return SWall;
    }
    public BufferedImage getEWall()
    {
        return EWall;
    }
    public BufferedImage getWWall()
    {
        return WWall;
    }

    public void setNWall(BufferedImage image)
    {
        NWall = image;
    }
    public void setSWall(BufferedImage image)
    {
        SWall = image;
    }
    public void setEWall(BufferedImage image)
    {
        EWall = image;
    }
    public void setWWall(BufferedImage image)
    {
        WWall = image;
    }
}
