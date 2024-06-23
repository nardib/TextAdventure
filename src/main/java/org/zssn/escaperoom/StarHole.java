package org.zssn.escaperoom;

import javax.swing.ImageIcon;
public class StarHole extends Item {

    /**
     * Weight for the StarHole
     */
    public final static int WEIGHT = 11;

    /**
     * Pickable state of the StarHole
     */
    public final static boolean PICKABLE = false;

    /**
     * ID of the starHole
     */
    public final int ID;

    /**
     * Filled state of the starHole
     */
    private boolean filled;

    /**
     * Constructor for the StarHole class
     * 
     * @param name name of the starHole
     * @param image image of the starHole
     * @param currentRoom current room of the starHole
     */
    public StarHole (String name, String image, int currentRoom, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.filled = false;
        this.ID = ID;
        usingMessage = "To to fill a star hole type 'use <star_hole_name>' facing the direction of the star hole having in the inventory the star with the same ID";
    }

    /**
     * Constructor for the StarHole class
     * 
     * @param name name of the starHole
     * @param image image of the starHole given as a ImageIcon object
     * @param currentRoom current room of the starHole
     */
    public StarHole (String name, ImageIcon image, int currentRoom, int ID) {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.filled = false;
        this.ID = ID;
        usingMessage = "To to fill a star hole type 'use <star_hole_name>' facing the direction of the star hole having in the inventory the star with the same ID";
    }

    /**
     * Method to fill the starHole
     * 
     * @param star the star to fill the starHole
     * @return true if the starHole is filled, false otherwise
     * 
     * @throws IllegalArgumentException if the starHole is already filled
     */
    public boolean fill(Star star) {
        if (this.filled) {
            throw new IllegalArgumentException("StarHole already filled");
        }
        if (star.ID == this.ID) {
            this.filled = true;
            return true;
        }
        return false;
    }

    /**
     * Method to get the filled state of the starHole
     * 
     * @return true if the starHole is filled, false otherwise
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * Clone the starHole
     * 
     * @return the cloned starHole
     */
    public StarHole clone() {
        StarHole sh = new StarHole(this.name, this.icon, this.currentRoom, this.ID);
        sh.filled = this.filled;
        return sh;
    }

    /**
     * Check if the starHole is the same as another starHole
     * 
     * @param starHole the starHole to compare
     * @return true if the starHoles are the same, false otherwises
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof StarHole) {
            StarHole starHole = (StarHole) other;
            return this.ID == starHole.ID && this.name.equals(starHole.name) && this.currentRoom == starHole.currentRoom && this.icon.equals(starHole.icon) && this.filled == starHole.filled;
        }
        return false;
    }
}
