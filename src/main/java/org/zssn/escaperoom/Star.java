package org.zssn.escaperoom;

import javax.swing.ImageIcon;

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
    public final int ID;

    /**
     * Constructor for the Star class
     * 
     * @param name name of the star
     * @param image image of the star
     * @param currentRoom current room of the star
     */
    public Star (String name, String image, int currentRoom, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = ID;
        usingMessage = "To use the star to fill a star hole type 'use <star_hole_name>' facing the direction of the star hole";
    }

    /**
     * Constructor for the Star class
     * 
     * @param name name of the star
     * @param image image of the star given as a ImageIcon object
     * @param currentRoom current room of the star
     */
    public Star (String name, ImageIcon image, int currentRoom, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = ID;
        usingMessage = "To use the star to fill a star hole type 'use <star_hole_name>' facing the direction of the star hole";
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
