import java.awt.image.*;

public class Map 
{
    final int MapSize = 9;
    final int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    final String[] roomNames = {"Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7", "Room 8", "Room 9"};
    final int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};
    Room[] rooms= new Room[MapSize];
    public Map(int MapSize, int[] passageWays, int[] roomDoors, String[] roomNames)
    {
        for (int i = 0; i < MapSize; i++)
        {
            Room room = new Room(i, roomDoors[i], roomNames[i]);
            rooms[i] = room;
            BufferedImage[] DoorWalls = new BufferedImage[roomDoors[i]];
            for(int j=0; j < roomDoors[i]; j++)
            {
                if(j == 0)
                {
                    DoorWalls[j] = room.getNWall();
                }
                else if(j == 1)
                {
                    DoorWalls[j] = room.getSWall();
                }
                else if(j == 2)
                {
                    DoorWalls[j] = room.getEWall();
                }
            }
            for(int j=0; j < roomDoors[i]; j++)
            {
                
            }

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
    }
}
