package org.zssn.escaperoom;

public class Map 
{
    final int MAPSIZE = 9;
    final int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    //array of passable walls
    final String[] roomNames = {"Home Office", "Game Room", "Kitchen", "Garage", "Central Room", "Living Room", "Indoor Garden", "Bedroom", "Bathroom"};
    final int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};

    //array for the items contained in the container items
    private final Item[] drawerItems = {new Note("Note", "test.png", 1, "Message"), new Key("Key", 123, "test.png", 1)};
    private final Item[] safeItems = {new Key("Key", 111, "test.png", 1)};
    private final Item[] itemChestItems = {new Key("Key", 222, "test.png", 1)};
    private final Item[] diceItems = {new Key("Key", 333, "test.png", 1)};
    private final Item[] fridgeItems = {new Key("Key", 444, "test.png", 1)};
    private final Item[] safe2Items = {new Key("Key", 555, "test.png", 1)};
    private final Item[] chessDrawerItems = {new Key("Key", 666, "test.png", 1)};
    private final Item[] safe3Items = {new Key("Key", 777, "test.png", 1)};
    private final Item[] mirrorCabinetItems = {new Key("Key", 888, "test.png", 1)};
    private final Item[] pinpadItems = {new Key("Key", 999, "test.png", 1)};
    private final Item[] clockDrawerItems = {new Key("Key", 101, "test.png", 1)};


    //items for each wall of each room
    private final Item[] itemsN1 = {new ItemContainer("Drawer", "test.png", 1, "Message", drawerItems, false)};
    private final Item[] itemsE1 = {new HiderItem("Painting", "test.png", 1, null, new ItemContainer("Safe", "test.png", 1, null, safeItems, true)), new Note("Note", "test.png", 1, "Message"), new Key("Key", 111, "test.png", 1)};
    private final Item[] itemsS1 = {new ClueItem("Clock", "test.png", 1, "It's 07:35"), new ClueItem("Phone", "test.png", 1, "Clue message")};
    private final Item[] itemsW1 = {new HidingItem("Hiding chest", "test.png", 1)};
    private final Item[] itemsN2 = {new ItemContainer("Item chest", "test.png", 2, "Message", itemChestItems, true)};
    private final Item[] itemsE2 = {new ItemContainer("Dice", "test.png", 2, "Message", diceItems, true)};
    private final Item[] itemsS2 = {new ClueItem("Chess board", "test.png", 2, "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    private final Item[] itemsW2 = null;
    private final Item[] itemsN3 = {new ItemContainer("Fridge", "test.png", 3, "Message", fridgeItems, true)};
    private final Item[] itemsE3 = {new Key("Key", 222, "test.png", 3)};
    private final Item[] itemsS3 = {new HidingItem("Hiding cabinet", "test.png", 3)};
    private final Item[] itemsW3 = null;
    private final Item[] itemsN4 = null;
    private final Item[] itemsE4 = {new HealingItem("Bendage", "test.png", 2, 4, 2)};
    private final Item[] itemsS4 = null;
    private final Item[] itemsW4 = null;
    private final Item[] itemsN5 = {new HidingItem("Hiding chest", "test.png", 5)};
    private final Item[] itemsE5 = null;
    private final Item[] itemsS5 = null;
    private final Item[] itemsW5 = null;
    private final Item[] itemsN6 = {new ItemContainer("Safe", "test.png", 6, "Message", safe2Items, true)};
    private final Item[] itemsE6 = {new ClueItem("Television", "test.png", 6, "Clue message")};
    private final Item[] itemsS6 = {new Note("Note in the coat", "test.png", 6, "0335765")};
    private final Item[] itemsW6 = {new HiderItem("Sofa", "test.png", 6, null, new Key("Key", 333, "test.png", 6))};
    private final Item[] itemsN7 = {new HiderItem("Vase", "test.png", 7, null, null)};
    private final Item[] itemsE7 = {new ClueItem("Floreal composition", "test.png", 7, "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    private final Item[] itemsS7 = {new HiderItem("Vase with wheels", "test.png", 7, null, new ItemContainer("Pinpad", "test.png", 7, null, pinpadItems, true))};
    private final Item[] itemsW7 = null;
    private final Item[] itemsN8 = null;
    private final Item[] itemsE8 = {new ItemContainer("Chess drawer", "test.png", 8, null, chessDrawerItems, true)};
    private final Item[] itemsS8 = {new ItemContainer("Safe", "test.png", 8, null, safe3Items, true)};
    private final Item[] itemsW8 = {new ItemContainer("Clock drawer", "test.png", 8, null, clockDrawerItems, true)};
    private final Item[] itemsN9 = {new HidingItem("Hiding cabinet", "test.png", 9), new ItemContainer("Mirror cabinet", "test.png", 9, "Message", mirrorCabinetItems, false)};
    private final Item[] itemsE9 = null;
    private final Item[] itemsS9 = null;
    private final Item[] itemsW9 = null;

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
