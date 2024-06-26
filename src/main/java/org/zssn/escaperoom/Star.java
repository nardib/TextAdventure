package org.zssn.escaperoom;

import javax.swing.ImageIcon;

/**
 * Star class that represents a star in the game that can be used to fill a star hole to complete the final puzzle
 
 
 
 */
public class Star extends Item {
    
    /**
     * Weight for the Star
     */
    public final static int WEIGHT = 1;

    /**
     * Pickable state of the Star
     */
    public final static boolean PICKABLE = true;

    /**
     * ID of the star
     */
    private int ID;

    /** 
     * Defualt constructor for Star item 
    */
    public Star() {super();}

    /**
     * Constructor for the Star class
     * 
     * @param name name of the star
     * @param image image of the star
     * @param currentRoom current room of the star
     * @param ID ID of the star
     */
    public Star (String name, String image, int currentRoom, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = ID;
        usingMessage = "To use the star to fill a star hole type 'use <star_hole_name>' facing the direction of the star hole";
    }

    /**
     * Returns the ID of the star
     * 
     * @return the ID of the star
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the ID of the star
     * 
     * @param ID the ID of the star
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    
    /**
     * Clone the star
     * 
     * @return the cloned star
     */
    public Star clone() {
        return new Star(this.name, this.icon, this.currentRoom, this.ID);
    }

    /**
     * Check if the star is the same as another star
     * 
     * @param other the star to compare
     * @return true if the stars are the same, false otherwises
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Star) {
            Star s = (Star) other;
            return this.ID == s.ID && this.name.equals(s.name) && this.currentRoom == s.currentRoom && this.icon.equals(s.icon);
        }
        return false;
    }
}
