package org.zssn.escaperoom;


/**
 * StarHole class that represents a star hole in the game that can be filled with a star to complete the final puzzle
 */
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
    private int ID;

    /**
     * Filled state of the starHole
     */
    private boolean filled;

    /**
     * Default constructor for the StarHole class
     */
    public StarHole() {super();}

    /**
     * Constructor for the StarHole class
     * 
     * @param name name of the starHole
     * @param ID ID of the starHole
     */
    public StarHole (String name, int ID) {
        super(name, WEIGHT, PICKABLE);
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
        if (star.getID() == this.ID) {
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
     * Method to get the ID of the starHole
     * 
     * @return the ID of the starHole
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the ID of the starHole
     * 
     * @param ID the ID of the starHole
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Set the filled state of the starHole
     * 
     * @param filled the filled state of the starHole
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    /**
     * Clone the starHole
     * 
     * @return the cloned starHole
     */
    public StarHole clone() {
        StarHole sh = new StarHole(this.name, this.ID);
        sh.filled = this.filled;
        return sh;
    }

    /**
     * Check if the starHole is the same as another starHole
     * 
     * @param other the starHole to compare
     * @return true if the starHoles are the same, false otherwises
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof StarHole) {
            StarHole starHole = (StarHole) other;
            return this.ID == starHole.ID && this.name.equals(starHole.name) && this.filled == starHole.filled;
        }
        return false;
    }
}
