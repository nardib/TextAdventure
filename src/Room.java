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
    private boolean crossableNorth;
    private boolean crossableEast;
    private boolean crossableSouth;
    private boolean crossableWest;
    private BufferedImage[] Walls= new BufferedImage[36];

    /*
     * Room class constructor
     */
    public Room(int n, int d, String nam) 
    {
        this.roomNumber = n;
        this.name = nam;
        this.doors = d;
        switch(n)
        {
            case 0:
                this.NWall=Walls[0];
                this.EWall=Walls[1];
                this.SWall=Walls[2];
                this.WWall=Walls[3];
                break;
            case 1:
                this.NWall=Walls[4];
                this.EWall=Walls[5];
                this.SWall=Walls[6];
                this.WWall=Walls[7];
                break;
            case 2:
                this.NWall=Walls[8];
                this.EWall=Walls[9];
                this.SWall=Walls[10];
                this.WWall=Walls[11];
                break;
            case 3:
                this.NWall=Walls[12];
                this.EWall=Walls[13];
                this.SWall=Walls[14];
                this.WWall=Walls[15];
                break;
            case 4:
                this.NWall=Walls[16];
                this.EWall=Walls[17];
                this.SWall=Walls[18];
                this.WWall=Walls[19];
                break;
            case 5:
                this.NWall=Walls[20];
                this.EWall=Walls[21];
                this.SWall=Walls[22];
                this.WWall=Walls[23];
                break;
            case 6:
                this.NWall=Walls[24];
                this.EWall=Walls[25];
                this.SWall=Walls[26];
                this.WWall=Walls[27];
                break;
            case 7:
                this.NWall=Walls[28];
                this.EWall=Walls[29];
                this.SWall=Walls[30];
                this.WWall=Walls[31];
                break;
            case 8:
                this.NWall=Walls[32];
                this.EWall=Walls[33];
                this.SWall=Walls[34];
                this.WWall=Walls[35];
                break;
        }
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

    public boolean getCrossableNorth()
    {
        return crossableNorth;
    }
    public boolean getCrossableEast()
    {
        return crossableEast;
    }
    public boolean getCrossableSouth()
    {
        return crossableSouth;
    }
    public boolean getCrossableWest()
    {
        return crossableWest;
    }
}
