package org.zssn.escaperoom;

public class Map 
{
    final int MAPSIZE = 9;
    final int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    //array of passable walls
    final String[] roomNames = {"Home Office", "Game Room", "Kitchen", "Garage", "Central Room", "Living Room", "Indoor Garden", "Bedroom", "Bathroom"};
    final int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};

    public final Item[] itemsN1 = {new ItemContainer("Drawer", null, 1, "Message", null, false)};
    public final Item[] itemsE1 = {new HiderItem("Painting", null, 1, null, new ItemContainer("Safe", null, 1, null, null, true)), new Note("Note", null, 1, "Message"), new Key("Key", 111, null, 1)};
    public final Item[] itemsS1 = {new ClueItem("Clock", null, 1, "It's 07:35"), new ClueItem("Phone", null, 1, "Clue message")};
    public final Item[] itemsW1 = {new HidingItem("Hiding chest", null, 1)};
    public final Item[] itemsN2 = {new ItemContainer("Item chest", null, 2, "Message", null, true)};
    public final Item[] itemsE2 = {new ItemContainer("Dice", null, 2, "Message", null, true)};
    public final Item[] itemsS2 = {new ClueItem("Chess board", null, 2, "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    public final Item[] itemsW2 = {null};
    public final Item[] itemsN3 = {new ItemContainer("Fridge", null, 3, "Message", null, true)};
    public final Item[] itemsE3 = {new Key("Key", 222, null, 3)};
    public final Item[] itemsS3 = {new HidingItem("Hiding cabinet", null, 3)};
    public final Item[] itemsW3 = {null};
    public final Item[] itemsN4 = {null};
    public final Item[] itemsE4 = {new HealingItem("Bendage", null, 2, 4, 2)};
    public final Item[] itemsS4 = {null};
    public final Item[] itemsW4 = {null};
    public final Item[] itemsN5 = {new HidingItem("Hiding chest", null, 5)};
    public final Item[] itemsE5 = {null};
    public final Item[] itemsS5 = {null};
    public final Item[] itemsW5 = {null};
    public final Item[] itemsN6 = {new ItemContainer("Safe", null, 6, "Message", null, true)};
    public final Item[] itemsE6 = {new ClueItem("Television", null, 6, "Clue message")};
    public final Item[] itemsS6 = {new Note("Note in the coat", null, 6, "0335765")};
    public final Item[] itemsW6 = {new HiderItem("Sofa", null, 6, null, new Key("Key", 333, null, 6))};
    public final Item[] itemsN7 = {new HiderItem("Vase", null, 7, null, null)};
    public final Item[] itemsE7 = {new ClueItem("Floreal composition", null, 7, "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    public final Item[] itemsS7 = {new HiderItem("Vase with wheels", null, 7, null, new ItemContainer("Pinpad", null, 7, null, null, true))};
    public final Item[] itemsW7 = {null};
    public final Item[] itemsN8 = {null};
    public final Item[] itemsE8 = {new ItemContainer("Chess drawer", null, 8, null, null, true)};
    public final Item[] itemsS8 = {new ItemContainer("Safe", null, 8, null, null, true)};
    public final Item[] itemsW8 = {new ItemContainer("Clock drawer", null, 8, null, null, true)};
    public final Item[] itemsN9 = {new HidingItem("Hiding cabinet", null, 9), new ItemContainer("Mirror cabinet", null, 9, "Message", null, false)};
    public final Item[] itemsE9 = {null};
    public final Item[] itemsS9 = {null};
    public final Item[] itemsW9 = {null};

    public final Item[][][] items = {
        {itemsN1, itemsE1, itemsS1, itemsW1},
        {itemsN2, itemsE2, itemsS2, itemsW2},
        {itemsN3, itemsE3, itemsS3, itemsW3},
        {itemsN4, itemsE4, itemsS4, itemsW4},
        {itemsN5, itemsE5, itemsS5, itemsW5},
        {itemsN6, itemsE6, itemsS6, itemsW6},
        {itemsN7, itemsE7, itemsS7, itemsW7},
        {itemsN8, itemsE8, itemsS8, itemsW8},
        {itemsN9, itemsE9, itemsS9, itemsW9}
    };

    //array containing Walls with doors
    private Wall[] DoorWall= new Wall[4];
    private Wall[] PlainWall= new Wall[3];
    private Room[] rooms= new Room[MAPSIZE];
    public Map()
    {
        Room.loadImages();
        for (int i = 0; i < MAPSIZE; i++)
        {
            Room room = new Room(i+1, roomDoors[i], roomNames[i], items[i][0], items[i][1], items[i][2], items[i][3]);
            rooms[i] = room;
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
        /*
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
        */
    }

    public Room getRoom(int n)
    {
        if(0<n || n<=9)
            return rooms[n-1];
        else
            throw new IllegalArgumentException("Room index out of bounds.");
    }
}
