/**
 * Hammer class that extends Item. It represents a hammer that can be used to break walls.
 */
public class Hammer extends Item {

    /**
     * Weight of the hammer is 5
     */
    public final static int WEIGHT = 5;

    /**
     * The hammer is pickable
     */
    public final static boolean PICKABLE = true;

    /**
     * Constructor for the Hammer
     * 
     * @param name name of the hammer
     * @param image image of the hammer given as a path
     * @param currentRoom current room of the hammer
     */
    public Hammer(String name, String image, int currentRoom)
    {
        super(name, image, WEIGHT, currentRoom, true);
        usingMessage = "You have used " + name + " to break the wall";
    }
}