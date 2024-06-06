/**
 * Key class is a subclass of Item. It is used to unlock doors in the game.
 */
public class Key extends Item {
    /**
     * Id of the key to match the correct lock
     */
    public final int ID;

    /**
     * The weight of the key is 1
     */
    public final static int WEIGHT = 1;

    /**
     * The key is pickable
     */
    public final static boolean PICKABLE = true;

    /**
     * Constructor for the Key
     * 
     * @param name name of the key
     * @param id id of the key
     * @param image image of the key given as a path
     * @param currentRoom current room of the key
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public Key(String name, int id, String image, int currentRoom)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = id;
        usingMessage = "You used the " + name;
    }
}