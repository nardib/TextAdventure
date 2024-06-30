package org.zssn.escaperoom;

/**
 * Map class that contains the rooms and the walls of the rooms
 */
public class Map 
{
    /**
     * MAPSIZE: the number of rooms in the map
     */
    public final static int MAPSIZE = 9;
    /**
     * passageWays: the array of passage ways between rooms; the index of the array is the door number that connects the rooms
     */
    public final static int[] passageWays = {1, 2, 3, 5, 6, 7, 8, 9, 10, 11};
    /**
     * roomNames: the array of room names
     */
    public final static String[] roomNames = {"Home Office", "Game Room", "Kitchen", "Garage", "Central Room", "Living Room", "Indoor Garden", "Bedroom", "Bathroom"};
    /**
     * roomDoors: the array of the number of doors in each room
     */
    public final static int[] roomDoors = {2, 2, 2, 3, 3, 3, 2, 2, 1};

    /**
     * items in the drawer
     */
    private final static Item[] drawerItems = {new Star ("Star 1", 1)};
    /**
     * items in the safe
     */
    private final static Item[] safeItems = {new Star ("Star 2", 2)};
    /**
     * items in the item chest
     */
    private final static Item[] itemChestItems = {new Star ("Star 3", 3), new HealingItem("Bandage", 3, 2)};
    /**
     * items in the house phone
     */
    private final static Item[] phoneItems = {new Note ("Phone note", "Bathroom 1313")};
    /**
     * items in the dice
     */
    private final static Item[] diceItems = {new Star ("Star 4", 4)};
    /**
     * items in the fridge
     */
    private final static Item[] fridgeItems = {new Star ("Star 5", 5)};
    /**
     * items in the safe2
     */
    private final static Item[] LivingRoomSafeItems = {new Star ("Star 6", 6)};
    /**
     * items in the chess drawer
     */
    private final static Item[] chessDrawerItems = {new Key("Yellow Key", 666)};
    /**
     * items in the safe3
     */
    private final static Item[] BedRoomSafeItems = {new Star ("Star 9", 9)};
    /**
     * items in the mirror cabinet
     */
    private final static Item[] mirrorCabinetItems = {new Note("Note in the mirror cabinet", "To unlock the fridge type: 151"), new HealingItem("Pills", 2, 1)};
    /**
     * items in the pinpad
     */
    private final static Item[] pinpadItems = { new Star ("Star 8", 8)};
    /**
     * items in the clock drawer
     */
    private final static Item[] clockDrawerItems = {new Note("Clock drawer note", "⚄ 143")};
    /**
     * items in the lock
     */
    private final static Item[] lockItems = {new Star ("Star 10", 10)};

    /**
     * items in the room 1 wall N
     */
    private final static Item[] itemsN1 = {new ItemContainer("Drawer", drawerItems, LockType.NONE, 0)};
    /**
     * items in the room 1 wall E
     */
    private final static Item[] itemsE1 = {new HiderItem("Painting", new ItemContainer("Safe", safeItems, LockType.COMBINATION, 352)), new Note("Note on pinboard", "Use this key for the safe in the Living Room"), new Key("Green Key", 151)};
    /**
     * items in the room 1 wall S
     */
    private final static Item[] itemsS1 = {new ClueItem("Clock", "It's 7:35"), new ItemContainer("House phone", phoneItems, LockType.COMBINATION, 8335)};
    /**
     * items in the room 1 wall W
     */
    private final static Item[] itemsW1 = {new HidingItem("Hiding chest")};
    /**
     * items in the room 2 wall N
     */
    private final static Item[] itemsN2 = {new ItemContainer("chest with a Yellow Lock", itemChestItems, LockType.KEY, 666)};
    /**
     * items in the room 2 wall E
     */
    private final static Item[] itemsE2 = {new ItemContainer("Dice", diceItems, LockType.COMBINATION, 143), new HiderItem("Board games", new Key ("Brown Key", 777))};
    /**
     * items in the room 2 wall S
     */
    private final static Item[] itemsS2 = {new ClueItem("Chess board", "You can notice three pawns in position 3, 5, 8")};
    /**
     * items in the room 2 wall W
     */
    private final static Item[] itemsW2 = {new ClueItem("Game poster", "In the poster there is a safe with the code 352")};
    /**
     * items in the room 3 wall N
     */
    private final static Item[] itemsN3 = {new ItemContainer("Fridge", fridgeItems, LockType.COMBINATION, 151)};
    /**
     * items in the room 3 wall E
     */
    private final static Item[] itemsE3 = {new HealingItem("Pills", 2, 1)};
    /**
     * items in the room 3 wall S
     */
    private final static Item[] itemsS3 = {new HidingItem("Hiding cabinet")};
    /**
     * items in the room 3 wall W
     */
    private final static Item[] itemsW3 = null;
    /**
     * items in the room 4 wall N
     */
    private final static Item[] itemsN4 = null;
    /**
     * items in the room 4 wall E
     */
    private final static Item[] itemsE4 = {new HealingItem("Bandage", 3, 2)};
    /**
     * items in the room 4 wall S
     */
    private final static Item[] itemsS4 = null;
    /**
     * items in the room 4 wall W
     */
    private final static Item[] itemsW4 = null;
    /**
     * items in the room 5 wall N
     */
    private final static Item[] itemsN5 = {new HidingItem("Hiding chest"), new StarHole("Star hole 3", 3), new StarHole("Star hole 4", 4), new StarHole("Star hole 5", 5), new StarHole("Star hole 6", 6)};
    /**
     * items in the room 5 wall E
     */
    private final static Item[] itemsE5 = {new StarHole("Star hole 7", 7), new StarHole("Star hole 8", 8)};
    /**
     * items in the room 5 wall S
     */
    private final static Item[] itemsS5 = {new StarHole("Star hole 9", 9), new StarHole("Star hole 10", 10)};
    /**
     * items in the room 5 wall W
     */
    private final static Item[] itemsW5 = {new StarHole("Star hole 1", 1), new StarHole("Star hole 2", 2)};
    /**
     * items in the room 6 wall N
     */
    private final static Item[] itemsN6 = {new ItemContainer("Safe with a Green Lock", LivingRoomSafeItems, LockType.KEY, 151)};
    /**
     * items in the room 6 wall E
     */
    private final static Item[] itemsE6 = {new ClueItem("Television", "The television turns on and shows the code \"1563\" on a pinpad")};
    /**
     * items in the room 6 wall S
     */
    private final static Item[] itemsS6 = {new Note("Note in the coat", "Telephone number: 8335")};
    /**
     * items in the room 6 wall W
     */
    private final static Item[] itemsW6 = {new HiderItem("Sofa", new Key("Weird Key", 333))};
    /**
     * items in the room 7 wall N
     */
    private final static Item[] itemsN7 = {new HiderItem("Vase", new Star ("Star 7", 7))};
    /**
     * items in the room 7 wall E
     */
    private final static Item[] itemsE7 = {new ClueItem("Floreal composition", "The order is: 1, 2, 3, 4, 5, 6, 7, 8")};
    /**
     * items in the room 7 wall S
     */
    private final static Item[] itemsS7 = {new HiderItem("Vase with wheels", new ItemContainer("Pinpad", pinpadItems, LockType.COMBINATION, 1563))};
    /**
     * items in the room 7 wall W
     */
    private final static Item[] itemsW7 = null;
    /**
     * items in the room 8 wall N
     */
    private final static Item[] itemsN8 = null;
    /**
     * items in the room 8 wall E
     */
    private final static Item[] itemsE8 = {new ItemContainer("Chess drawer", chessDrawerItems, LockType.COMBINATION, 358)};
    /**
     * items in the room 8 wall S
     */
    private final static Item[] itemsS8 = {new ItemContainer("Bed Safe with Brown Lock", BedRoomSafeItems, LockType.KEY, 777)};
    /**
     * items in the room 8 wall W
     */
    private final static Item[] itemsW8 = {new ItemContainer("Clock drawer", clockDrawerItems, LockType.COMBINATION, 735)};
    /**
     * items in the room 9 wall N
     */
    private final static Item[] itemsN9 = {new HidingItem("Hiding cabinet"), new ItemContainer("Mirror cabinet", mirrorCabinetItems, LockType.NONE, 0)};
    /**
     * items in the room 9 wall E
     */
    private final static Item[] itemsE9 = null;
    /**
     * items in the room 9 wall S
     */
    private final static Item[] itemsS9 = {new ItemContainer("Lock", lockItems, LockType.COMBINATION, 1313)};
    /**
     * items in the room 9 wall W
     */
    private final static Item[] itemsW9 = null;

    /**
     * items in the room walls
     */
    public final static Item[][][] items = {
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
     * Check if the map is equal to the object given as argument
     * 
     * @param obj the object to compare
     * @return true if the maps are equal, false otherwise
     */
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Map || obj == null))
            return false;
        Map m = (Map) obj;
        for (int i = 0; i < MAPSIZE; i++)
        {
            if (!this.rooms[i].equals(m.rooms[i]))
                return false;
        }
        return true;
    }

    /**
     * Returns a copy of the wall given as argument
     * 
     * @param room the room index
     * @param direction the direction of the wall
     * @return the wall
     * 
     * @throws IllegalArgumentException if the room index is out of bounds
     */
    public Wall getWall(int room, Direction direction)
    {
        if (room < 1 || room > MAPSIZE)
            throw new IllegalArgumentException("Room index out of bounds.");
        switch(direction) {
            case NORTH:
                return rooms[room-1].getNWall();
            case EAST:
                return rooms[room-1].getEWall();
            case SOUTH:
                return rooms[room-1].getSWall();
            case WEST:
                return rooms[room-1].getWWall();
            default:
                throw new IllegalArgumentException("Direction not valid.");
        }
    }

    /**
     * Set the wall of the room given as argument
     * 
     * @param room the room index
     * @param direction the direction of the wall
     * @param wall the wall to set
     * 
     * @throws IllegalArgumentException if the room index is out of bounds
     */
    public void setWall(int room, Direction direction, Wall wall) {
        if (room < 1 || room > MAPSIZE)
            throw new IllegalArgumentException("Room index out of bounds.");
        switch(direction) {
            case NORTH:
                rooms[room-1].setNWall(wall);
                break;
            case EAST:
                rooms[room-1].setEWall(wall);
                break;
            case SOUTH:
                rooms[room-1].setSWall(wall);
                break;
            case WEST:
                rooms[room-1].setWWall(wall);
                break;
            default:
                throw new IllegalArgumentException("Direction not valid.");
        }
    }

    /**
     * Returns the rooms in the map
     * 
     * @return the rooms in the map
     */
    public Room[] getRooms() {
        return rooms;
    }

    /**
     * Set the rooms in the map
     * 
     * @param r the rooms to set
     */
    public void setRooms(Room[] r) {
        rooms = r;
    }
}
