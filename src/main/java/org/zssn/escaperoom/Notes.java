package org.zssn.escaperoom;

/**
 * Notes class that extends Item. It represents a note that can be read by the player.
 */
public class Notes extends Item {

    /**
     * Message that the note contains
     */
    public final String message;

    /**
     * Weight of the note is 0
     */
    public final static int WEIGHT = 0;

    /**
     * The note is pickable
     */
    public final static boolean PICKABLE = true;
    
    /**
     * Constructor for the Notes
     * 
     * @param name name of the note
     * @param image image of the note given as a name of the file in the src/main/resources folder
     * @param currentRoom current room of the note
     * @param message message that the note contains
     * @throws IllegalArgumentException if the weight is less than 0 or the room is not between 0 and 9
     */
    public Notes(String name, String image, int currentRoom, String message)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.message = message;
        usingMessage = "You read the note";
    }    
}