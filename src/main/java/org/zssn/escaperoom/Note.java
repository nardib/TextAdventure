package org.zssn.escaperoom;

import javax.swing.ImageIcon;
/**
 * Notes class that extends Item. It represents a note that can be read by the player.
 */
public class Note extends Item {
    /**
     * Weight of the note is 0
     */
    public final static int WEIGHT = 0;

    /**
     * The note is pickable
     */
    public final static boolean PICKABLE = true;

    /**
     * Default constructor for the Notes
     */
    public Note() {super();}
    
    /**
     * Constructor for the Notes
     * 
     * @param name name of the note
     * @param image image of the note given as a name of the file in the src/main/resources folder
     * @param currentRoom current room of the note
     * @param message message that the note contains
     * @throws IllegalArgumentException if the weight is less than 0 or the room is not between 0 and 9
     */
    public Note(String name, String image, int currentRoom, String message)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        usingMessage = "This note says: " + message;
    }

    /**
     * Clone the note
     * 
     * @return a new note with the same attributes
     */
    @Override
    public Note clone()
    {
        return new Note(name, icon, currentRoom, usingMessage.substring(16));
    }

    /**
     * Check if two notes are equal
     * 
     * @param obj note to compare
     * @return true if the notes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Note || obj == null))
            return false;
        Note other = (Note) obj;
        return name.equals(other.name) && usingMessage.equals(other.usingMessage) && currentRoom == other.currentRoom && icon.equals(other.icon);
    }
}