package org.zssn.escaperoom;

/**
 * Map class that contains the rooms and the walls of the rooms
 */
public class Map 
{
    /**
     * MAPSIZE: the number of rooms in the map
     */
    public final int MAPSIZE = 9;
    /**
     * passageWays: the array of passage ways between rooms; the index of the array is the door number that connects the rooms
     */
    public final int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    /**
     * roomNames: the array of room names
     */
    public final String[] roomNames = {"Home Office", "Game Room", "Kitchen", "Garage", "Central Room", "Living Room", "Indoor Garden", "Bedroom", "Bathroom"};
    /**
     * roomDoors: the array of the number of doors in each room
     */
    public final int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};

    /**
     * items in the drawer
     */
    private final Item[] drawerItems = {new Note("Note", "test.png", 1, "Message"), new Key("Key", 123, "test.png", 1)};
    /**
     * items in the safe
     */
    private final Item[] safeItems = {new Key("Key", 111, "test.png", 1)};
    /**
     * items in the item chest
     */
    private final Item[] itemChestItems = {new Key("Key", 222, "test.png", 1)};
    /**
     * items in the dice
     */
    private final Item[] diceItems = {new Key("Key", 333, "test.png", 1)};
    /**
     * items in the fridge
     */
    private final Item[] fridgeItems = {new Key("Key", 444, "test.png", 1)};
    /**
     * items in the safe2
     */
    private final Item[] safe2Items = {new Key("Key", 555, "test.png", 1)};
    /**
     * items in the chess drawer
     */
    private final Item[] chessDrawerItems = {new Key("Key", 666, "test.png", 1)};
    /**
     * items in the safe3
     */
    private final Item[] safe3Items = {new Key("Key", 777, "test.png", 1)};
    /**
     * items in the mirror cabinet
     */
    private final Item[] mirrorCabinetItems = {new Key("Key", 888, "test.png", 1)};
    /**
     * items in the pinpad
     */
    private final Item[] pinpadItems = {new Key("Key", 999, "test.png", 1)};
    /**
     * items in the clock drawer
     */
    private final Item[] clockDrawerItems = {new Note("Clock drawer note", "test.png", 8, "⚄ 143")};
    /**
     * items in the lock
     */
    private final Item[] lockItems = {new Key("Key", 000, "test.png", 9)};

    /**
     * items in the room 1 wall N
     */
    private final Item[] itemsN1 = {new ItemContainer("Drawer", "test.png", 1, drawerItems, LockType.NONE, 0)};
    /**
     * items in the room 1 wall E
     */
    private final Item[] itemsE1 = {new HiderItem("Painting", "test.png", 1, new ItemContainer("Safe", "test.png", 1, safeItems, LockType.COMBINATION, 352)), new Note("Note on pinboard", "test.png", 1, "Use this key for the safe in the office"), new Key("Key on pinboard", 151, "test.png", 1)};
    /**
     * items in the room 1 wall S
     */
    private final Item[] itemsS1 = {new ClueItem("Clock", "test.png", 1, "It's 07:35"), new ClueItem("Phone", "test.png", 1, "You have called the number 0335212 and you heard someone sayng: \"To unlock the lock in the bathroom type: 1313\"")};
    /**
     * items in the room 1 wall W
     */
    private final Item[] itemsW1 = {new HidingItem("Hiding chest", "test.png", 1)};
    /**
     * items in the room 2 wall N
     */
    private final Item[] itemsN2 = {new ItemContainer("Item chest", "test.png", 2, itemChestItems, LockType.KEY, 1)};
    /**
     * items in the room 2 wall E
     */
    private final Item[] itemsE2 = {new ItemContainer("Dice", "test.png", 2, diceItems, LockType.COMBINATION, 143), new HiderItem("Board games", "test.png", 2, new Key ("Bed safe key", 777, "test.png", 2))};
    /**
     * items in the room 2 wall S
     */
    private final Item[] itemsS2 = {new ClueItem("Chess board", "test.png", 2, "You can notice three pawns in position 3, 5, 8")};
    /**
     * items in the room 2 wall W
     */
    private final Item[] itemsW2 = {new ClueItem("game poster", "test.png", 2, "In the poster there is a safe with the code 352")};
    /**
     * items in the room 3 wall N
     */
    private final Item[] itemsN3 = {new ItemContainer("Fridge", "test.png", 3, fridgeItems, LockType.KEY, 151)};
    /**
     * items in the room 3 wall E
     */
    private final Item[] itemsE3 = {new Key("Key", 222, "test.png", 3)};
    /**
     * items in the room 3 wall S
     */
    private final Item[] itemsS3 = {new HidingItem("Hiding cabinet", "test.png", 3)};
    /**
     * items in the room 3 wall W
     */
    private final Item[] itemsW3 = null;
    /**
     * items in the room 4 wall N
     */
    private final Item[] itemsN4 = null;
    /**
     * items in the room 4 wall E
     */
    private final Item[] itemsE4 = {new HealingItem("Bendage", "test.png", 2, 4, 2)};
    /**
     * items in the room 4 wall S
     */
    private final Item[] itemsS4 = null;
    /**
     * items in the room 4 wall W
     */
    private final Item[] itemsW4 = null;
    /**
     * items in the room 5 wall N
     */
    private final Item[] itemsN5 = {new HidingItem("Hiding chest", "test.png", 5)};
    /**
     * items in the room 5 wall E
     */
    private final Item[] itemsE5 = null;
    /**
     * items in the room 5 wall S
     */
    private final Item[] itemsS5 = null;
    /**
     * items in the room 5 wall W
     */
    private final Item[] itemsW5 = null;
    /**
     * items in the room 6 wall N
     */
    private final Item[] itemsN6 = {new ItemContainer("Safe", "test.png", 6, safe2Items, LockType.KEY, 3)};
    /**
     * items in the room 6 wall E
     */
    private final Item[] itemsE6 = {new ClueItem("Television", "test.png", 6, "The television turns on and shows the code \"0563\" on a pinpad")};
    /**
     * items in the room 6 wall S
     */
    private final Item[] itemsS6 = {new Note("Note in the coat", "test.png", 6, "Telephone number: 0335212")};
    /**
     * items in the room 6 wall W
     */
    private final Item[] itemsW6 = {new HiderItem("Sofa", "test.png", 6, new Key("Key", 333, "test.png", 6))};
    /**
     * items in the room 7 wall N
     */
    private final Item[] itemsN7 = {new HiderItem("Vase", "test.png", 7, new Item("Name", "test.png", 7, 7, false))};
    /**
     * items in the room 7 wall E
     */
    private final Item[] itemsE7 = {new ClueItem("Floreal composition", "test.png", 7, "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    /**
     * items in the room 7 wall S
     */
    private final Item[] itemsS7 = {new HiderItem("Vase with wheels", "test.png", 7, new ItemContainer("Pinpad", "test.png", 7, pinpadItems, LockType.COMBINATION, 0563))};
    /**
     * items in the room 7 wall W
     */
    private final Item[] itemsW7 = null;
    /**
     * items in the room 8 wall N
     */
    private final Item[] itemsN8 = null;
    /**
     * items in the room 8 wall E
     */
    private final Item[] itemsE8 = {new ItemContainer("Chess drawer", "test.png", 8, chessDrawerItems, LockType.COMBINATION, 358)};
    /**
     * items in the room 8 wall S
     */
    private final Item[] itemsS8 = {new ItemContainer("Bed Safe", "test.png", 8, safe3Items, LockType.COMBINATION, 777)};
    /**
     * items in the room 8 wall W
     */
    private final Item[] itemsW8 = {new ItemContainer("Clock drawer", "test.png", 8, clockDrawerItems, LockType.COMBINATION, 0735)};
    /**
     * items in the room 9 wall N
     */
    private final Item[] itemsN9 = {new HidingItem("Hiding cabinet", "test.png", 9), new ItemContainer("Mirror cabinet", "test.png", 9, mirrorCabinetItems, LockType.NONE, 0)};
    /**
     * items in the room 9 wall E
     */
    private final Item[] itemsE9 = null;
    /**
     * items in the room 9 wall S
     */
    private final Item[] itemsS9 = {new ItemContainer("Lock", "test.png", 9, lockItems, LockType.COMBINATION, 1313)};
    /**
     * items in the room 9 wall W
     */
    private final Item[] itemsW9 = null;

    /**
     * items in the room walls
     */
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

    /**
     * DoorWall: the array of walls that have doors
     */
    private Wall[] DoorWall = new Wall[4];
    /**
     * PlainWall: the array of walls that do not have doors
     */
    private Wall[] PlainWall = new Wall[3];
    /**
     * rooms: the array of rooms
     */
    private Room[] rooms = new Room[MAPSIZE];

    /**
     * Constructor of the Map class
     */
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

    /**
     * Get the room based on the room number
     * @param n the room number
     * @return the room
     * @throws IllegalArgumentException if the room number is out of bounds
     */
    public Room getRoom(int n)
    {
        if(0<n || n<=9)
            return rooms[n-1];
        else
            throw new IllegalArgumentException("Room index out of bounds.");
    }

    /**
     * Clone the map
     * 
     * @return the cloned map
     */
    @Override
    public Map clone()
    {
        Map map = new Map();
        for (int i = 0; i < MAPSIZE; i++)
        {
            map.rooms[i] = this.rooms[i].clone();
        }
        return map;
    }

    /**
     * Clone the map with a deep copy of the room given as argument
     * 
     * @param room the room to deep copy
     * @return the cloned map
     */
    public Map clone(int room)
    {
        Map map = new Map();
        for (int i = 0; i < MAPSIZE; i++)
        {
            if(i == room)
                map.rooms[i] = this.rooms[i].clone();
            else
                map.rooms[i] = this.rooms[i];
        }
        return map;
    }
}
