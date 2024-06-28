package org.zssn.escaperoom;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Room class is used to create the rooms of the game. Each room has 4 walls and each wall can have items.
 */
public class Room 
{
    /**
     * Number of the room
     */
    private int roomNumber;
    /**
     * Name of the room
     */
    private String name;
    /**
     * Number of doors in the room
     */
    private int doors;
    /**
     * North wall of the room
     */
    private Wall NWall;
    /**
     * East wall of the room
     */
    private Wall EWall;
    /**
     * South wall of the room
     */
    private Wall SWall;
    /**
     * West wall of the room
     */
    private Wall WWall;
    /**
     * Crossable state of the room on the north side
     */
    private boolean crossableNorth;
    /**
     * Crossable state of the room on the east side
     */
    private boolean crossableEast;
    /**
     * Crossable state of the room on the south side
     */
    private boolean crossableSouth;
    /**
     * Crossable state of the room on the west side
     */
    private boolean crossableWest;
    /**
     * Array of walls in the game
     */
    public static Wall[] Walls= new Wall[36];

    /**
     * Load images for the walls
     */
    public static void loadImages() {
        for (int i = 0; i < 36; i++) {
            try {
                InputStream in = Room.class.getResourceAsStream("/rooms/" + i + ".jpg");
                BufferedImage image = ImageIO.read(in);
                Walls[i] = new Wall(image, false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Room class defualt constructor
     */
    public Room() {}

    /**
     * Room class constructor
     * 
     * @param n number of the room
     * @param d number of doors in the room
     * @param nam name of the room
     * @param NItems items in the north wall
     * @param EItems items in the east wall
     * @param SItems items in the south wall
     * @param WItems items in the west wall
     */
    public Room(int n, int d, String nam, Item[] NItems, Item[] EItems, Item[] SItems, Item[] WItems) 
    {
        this.roomNumber = n;
        this.name = nam;
        this.doors = d;
        switch(n)
        {
            case 1:
                Walls[0].setItems(NItems);
                Walls[1].setItems(EItems);
                Walls[2].setItems(SItems);
                Walls[3].setItems(WItems);
                this.NWall=Walls[0];
                this.EWall=Walls[1];
                this.SWall=Walls[2];
                this.WWall=Walls[3];
                break;
            case 2:
                Walls[4].setItems(NItems);
                Walls[5].setItems(EItems);
                Walls[6].setItems(SItems);
                Walls[7].setItems(WItems);
                this.NWall=Walls[4];
                this.EWall=Walls[5];
                this.SWall=Walls[6];
                this.WWall=Walls[7];
                break;
            case 3:
                Walls[8].setItems(NItems);
                Walls[9].setItems(EItems);
                Walls[10].setItems(SItems);
                Walls[11].setItems(WItems);
                this.NWall=Walls[8];
                this.EWall=Walls[9];
                this.SWall=Walls[10];
                this.WWall=Walls[11];
                break;
            case 4:
                Walls[12].setItems(NItems);
                Walls[13].setItems(EItems);
                Walls[14].setItems(SItems);
                Walls[15].setItems(WItems);
                this.NWall=Walls[12];
                this.EWall=Walls[13];
                this.SWall=Walls[14];
                this.WWall=Walls[15];
                break;
            case 5:
                Walls[16].setItems(NItems);
                Walls[17].setItems(EItems);
                Walls[18].setItems(SItems);
                Walls[19].setItems(WItems);
                this.NWall=Walls[16];
                this.EWall=Walls[17];
                this.SWall=Walls[18];
                this.WWall=Walls[19];
                break;
            case 6:
                Walls[20].setItems(NItems);
                Walls[21].setItems(EItems);
                Walls[22].setItems(SItems);
                Walls[23].setItems(WItems);
                this.NWall=Walls[20];
                this.EWall=Walls[21];
                this.SWall=Walls[22];
                this.WWall=Walls[23];
                break;
            case 7:
                Walls[24].setItems(NItems);
                Walls[25].setItems(EItems);
                Walls[26].setItems(SItems);
                Walls[27].setItems(WItems);
                this.NWall=Walls[24];
                this.EWall=Walls[25];
                this.SWall=Walls[26];
                this.WWall=Walls[27];
                break;
            case 8:
                Walls[28].setItems(NItems);
                Walls[29].setItems(EItems);
                Walls[30].setItems(SItems);
                Walls[31].setItems(WItems);
                this.NWall=Walls[28];
                this.EWall=Walls[29];
                this.SWall=Walls[30];
                this.WWall=Walls[31];
                break;
            case 9:
                Walls[32].setItems(NItems);
                Walls[33].setItems(EItems);
                Walls[34].setItems(SItems);
                Walls[35].setItems(WItems);
                this.NWall=Walls[32];
                this.EWall=Walls[33];
                this.SWall=Walls[34];
                this.WWall=Walls[35];
                break;
        }
    }

    /**
     * Get the name of the room
     * 
     * @return name of the room
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Get the number of the room
     * 
     * @return number of the room
     */
    public int getRoomNumber() 
    {
        return roomNumber;
    }

    /**
     * Get the number of doors in the room
     * 
     * @return number of doors in the room
     */
    public int getDoors() 
    {
        return doors;
    }

    /**
     * Add a passage way to the room
     * 
     * @param room room to add the passage way to
     */
    public void addPassageWay(Room room) 
    {
        // Add passage way to room
        if(room.getRoomNumber() == roomNumber + 1)
        {
            crossableEast = true;
            this.getEWall().setDoor();
        }
        else if(room.getRoomNumber() == roomNumber - 1)
        {
            crossableWest = true;
            this.getWWall().setDoor();
        }
        else if(room.getRoomNumber() == roomNumber + 3)
        {
            crossableSouth = true;
            this.getSWall().setDoor();
        }
        else if(room.getRoomNumber() == roomNumber - 3)
        {
            crossableNorth = true;
            this.getNWall().setDoor();
        }
    }

    /**
     * Get the north wall of the room
     * 
     * @return north wall of the room
     */
    public Wall getNWall()
    {
        return NWall;
    }

    /**
     * Get the south wall of the room
     * 
     * @return south wall of the room
     */
    public Wall getSWall()
    {
        return SWall;
    }

    /**
     * Get the east wall of the room
     * 
     * @return east wall of the room
     */
    public Wall getEWall()
    {
        return EWall;
    }

    /**
     * Get the west wall of the room
     * 
     * @return west wall of the room
     */
    public Wall getWWall()
    {
        return WWall;
    }

    /**
     * Set the north wall of the room
     * 
     * @param image image of the wall
     * @param door door in the wall
     */
    public void setNWall(BufferedImage image, boolean door)
    {
        NWall = new Wall(image, door, null);
    }

    /**
     * Set the south wall of the room
     * 
     * @param image image of the wall
     * @param door door in the wall
     */
    public void setSWall(BufferedImage image, boolean door)
    {
        SWall = new Wall(image, door, null);
    }

    /**
     * Set the east wall of the room
     * 
     * @param image image of the wall
     * @param door door in the wall
     */
    public void setEWall(BufferedImage image, boolean door)
    {
        EWall = new Wall(image, door, null);
    }

    /**
     * Set the west wall of the room
     * 
     * @param image image of the wall
     * @param door door in the wall
     */
    public void setWWall(BufferedImage image, boolean door)
    {
        WWall = new Wall(image, door, null);
    }

    /**
     * Set the north wall of the room
     * 
     * @param wall north wall of the room
     */
    public void setNWall(Wall wall)
    {
        NWall = wall;
    }

    /**
     * Set the south wall of the room
     * 
     * @param wall south wall of the room
     */
    public void setSWall(Wall wall)
    {
        SWall = wall;
    }

    /**
     * Set the east wall of the room
     * 
     * @param wall east wall of the room
     */
    public void setEWall(Wall wall)
    {
        EWall = wall;
    }

    /**
     * Set the west wall of the room
     * 
     * @param wall west wall of the room
     */
    public void setWWall(Wall wall)
    {
        WWall = wall;
    }

    /**
     * Get the crossable state of the room on the north side
     * 
     * @return true if the room is crossable on the north side, false otherwise
     */
    public boolean getCrossableNorth()
    {
        return crossableNorth;
    }

    /**
     * Get the crossable state of the room on the east side
     * 
     * @return true if the room is crossable on the east side, false otherwise
     */
    public boolean getCrossableEast()
    {
        return crossableEast;
    }

    /**
     * Get the crossable state of the room on the south side
     * 
     * @return true if the room is crossable on the south side, false otherwise
     */
    public boolean getCrossableSouth()
    {
        return crossableSouth;
    }

    /**
     * Get the crossable state of the room on the west side
     * 
     * @return true if the room is crossable on the west side, false otherwise
     */
    public boolean getCrossableWest()
    {
        return crossableWest;
    }

    /**
     * Clone the room
     * 
     * @return a clone of the room
     */
    @Override
    public Room clone() {
        Item[] NItems = new Item[NWall.getItemsLength()];
        Item[] EItems = new Item[EWall.getItemsLength()];
        Item[] SItems = new Item[SWall.getItemsLength()];
        Item[] WItems = new Item[WWall.getItemsLength()];
        for (int i = 0; i < NWall.getItemsLength(); i++)
            NItems[i] = NWall.getItem(i).clone();
        for (int i = 0; i < EWall.getItemsLength(); i++)
            EItems[i] = EWall.getItem(i).clone();
        for (int i = 0; i < SWall.getItemsLength(); i++)
            SItems[i] = SWall.getItem(i).clone();
        for (int i = 0; i < WWall.getItemsLength(); i++)
            WItems[i] = WWall.getItem(i).clone();
        return new Room(roomNumber, doors, name, NItems, EItems, SItems, WItems);
    }

    /**
     * Check if the room is equal to another room
     * 
     * @param other room to compare
     * @return true if the rooms are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Room || other == null))
            return false;
        Room r = (Room) other;
        return this.roomNumber == r.roomNumber && this.name.equals(r.name) && this.doors == r.doors && this.NWall.equals(r.NWall) && this.EWall.equals(r.EWall) && this.SWall.equals(r.SWall) && this.WWall.equals(r.WWall) && this.crossableNorth == r.crossableNorth && this.crossableEast == r.crossableEast && this.crossableSouth == r.crossableSouth && this.crossableWest == r.crossableWest;
    }
}
