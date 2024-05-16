public class Room 
{
    /*
     * Room class variables
     */
    private int roomNumber;
    private String name;
    boolean crossableNorth;
    boolean crossableSouth;
    boolean crossableEast;
    boolean crossableWest;
    /*
     * Room class constructor
     */
    public Room(int roomNumber, String name) 
    {
        this.roomNumber = roomNumber;
        this.name = name;
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
}
