package org.zssn.escaperoom;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Room 
{
    /*
     * Room class variables
     */
    private int roomNumber;
    private String name;
    private int doors;
    private Wall NWall;
    private Wall EWall;
    private Wall SWall;
    private Wall WWall;
    private boolean crossableNorth;
    private boolean crossableEast;
    private boolean crossableSouth;
    private boolean crossableWest;
    public static Wall[] Walls= new Wall[36];

    // Load images from directory
    public static void loadImages() {
        for (int i = 0; i < 36; i++) {
            try {
                InputStream in = Room.class.getResourceAsStream("/rooms/" + i + ".png");
                BufferedImage image = ImageIO.read(in);
                Walls[i] = new Wall(image, false, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * Room class constructor
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

    public Wall getNWall()
    {
        return NWall;
    }
    public Wall getSWall()
    {
        return SWall;
    }
    public Wall getEWall()
    {
        return EWall;
    }
    public Wall getWWall()
    {
        return WWall;
    }

    public void setNWall(BufferedImage image, boolean door)
    {
        NWall = new Wall(image, door, null);
    }
    public void setSWall(BufferedImage image, boolean door)
    {
        SWall = new Wall(image, door, null);
    }
    public void setEWall(BufferedImage image, boolean door)
    {
        EWall = new Wall(image, door, null);
    }
    public void setWWall(BufferedImage image, boolean door)
    {
        WWall = new Wall(image, door, null);
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
