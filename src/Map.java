import java.awt.image.*;

public class Map 
{
    final int MAPSIZE = 9;
    final int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    //array of passable walls
    final String[] roomNames = {"Home Office", "Game Room", "Kitchen", "Garage", "Central Room", "Living Room", "Room 7", "Room 8", "Room 9"};
    final int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};
    //array of all walls images
    private BufferedImage[] walls = new BufferedImage[36];
    //array containing Walls with doors
    private Wall[] DoorWall= new Wall[4];
    private Wall[] PlainWall= new Wall[3];
    private Room[] rooms= new Room[MAPSIZE];
    public Map()
    {
        for (int i = 0; i < MAPSIZE; i++)
        {
            Room room = new Room(i+1, roomDoors[i], roomNames[i]);
            rooms[i] = room;
            rooms[i].setNWall(walls[i*4], false);
            rooms[i].setEWall(walls[i*4+1], false);
            rooms[i].setSWall(walls[i*4+2], false);
            rooms[i].setWWall(walls[i*4+3], false);
            
        }
        for (int i=0; i < passageWays.length; i++)
        {
            switch(passageWays[i])
            {
                case 1:
                    // Add passage way room1 to room2
                    rooms[0].addPassageWay(rooms[1]);
                    rooms[1].addPassageWay(rooms[0]);
                    break;
                case 2:
                    // Add passage way room2 to room3
                    rooms[1].addPassageWay(rooms[2]);
                    rooms[2].addPassageWay(rooms[1]);
                    break;
                case 3:
                    // Add passage way room1 to room4
                    rooms[0].addPassageWay(rooms[3]);
                    rooms[3].addPassageWay(rooms[0]);
                    break;
                case 4:
                    // Add passage way room2 to room5
                    rooms[1].addPassageWay(rooms[4]);
                    rooms[4].addPassageWay(rooms[1]);
                    break;
                case 5:
                    // Add passage way room3 to room6
                    rooms[2].addPassageWay(rooms[5]);
                    rooms[5].addPassageWay(rooms[2]);
                    break;
                case 6:
                    // Add passage way room4 to room5
                    rooms[3].addPassageWay(rooms[4]);
                    rooms[4].addPassageWay(rooms[3]);
                    break;
                case 7:
                    // Add passage way room5 to room6
                    rooms[4].addPassageWay(rooms[5]);
                    rooms[5].addPassageWay(rooms[4]);
                    break;
                case 8:
                    // Add passage way room4 to room7
                    rooms[3].addPassageWay(rooms[6]);
                    rooms[6].addPassageWay(rooms[3]);
                    break;
                case 9:
                    // Add passage way room5 to room8
                    rooms[4].addPassageWay(rooms[7]);
                    rooms[7].addPassageWay(rooms[4]);
                    break;
                case 10:
                    // Add passage way room6 to room9
                    rooms[5].addPassageWay(rooms[8]);
                    rooms[8].addPassageWay(rooms[5]);
                    break;
                case 11:
                    // Add passage way room7 to room8
                    rooms[6].addPassageWay(rooms[7]);
                    rooms[7].addPassageWay(rooms[6]);
                    break;
                case 12:
                    // Add passage way room8 to room9
                    rooms[7].addPassageWay(rooms[8]);
                    rooms[8].addPassageWay(rooms[7]);
                    break;
            }
        }
        for (int i = 0; i < MAPSIZE; i++)
        {
            for(int j=0; j<rooms[i].getDoors(); j++)
            {
                if(j==0)
                    DoorWall[j] = rooms[i].getNWall();
                else if(j==1)
                    DoorWall[j] = rooms[i].getEWall();
                else if(j==2)
                    DoorWall[j] = rooms[i].getSWall();
                else if(j==3)
                    DoorWall[j] = rooms[i].getWWall();
                if(j==rooms[i].getDoors()-1)
                {
                    switch(j)
                    {
                        case 0:
                            PlainWall[0]=rooms[i].getEWall();
                            PlainWall[1]=rooms[i].getSWall();
                            PlainWall[2]=rooms[i].getWWall();
                        case 1:
                            PlainWall[0]=rooms[i].getSWall();
                            PlainWall[1]=rooms[i].getWWall();
                        case 2:
                            PlainWall[0]=rooms[i].getWWall();
                    }
                }
            }
            //place walls based on crossable/non-crossable            
            if(!rooms[i].getCrossableNorth())
                rooms[i].setNWall(PlainWall[0].getCombinedImage(), false);
            else
                rooms[i].setNWall(DoorWall[0].getCombinedImage(), true);
            if(!rooms[i].getCrossableEast())
                rooms[i].setEWall(PlainWall[1].getCombinedImage(), false);
            else
                rooms[i].setEWall(DoorWall[1].getCombinedImage(), true);
            if(!rooms[i].getCrossableSouth())
                rooms[i].setSWall(PlainWall[2].getCombinedImage(), false);
            else
                rooms[i].setSWall(DoorWall[2].getCombinedImage(), true);
            if(!rooms[i].getCrossableWest())
                rooms[i].setWWall(PlainWall[3].getCombinedImage(), false);
            else
                rooms[i].setWWall(DoorWall[3].getCombinedImage(), true);
        }
    }

    public Room getRoom(int n)
    {
        if(0<=n || n<9)
            return rooms[n];
        else
            throw new IllegalArgumentException("Room index out of bounds.");
    }
}
