/**
 * Lock class extends Item class. Is used to create locks that can be unlocked with a key.
 */
public class Lock extends Item {

    /**
     * Id of the lock to match the correct key
     */
    public final int ID;

    /**
     * Lock status
     */
    private boolean locked;

    /**
     * The lock is not pickable
     */
    public final static boolean PICKABLE = false;

    /**
     * Weight of the lock is 11 because it can't be picked up
     */
    public final static int WEIGHT = 11;

    /**
     * Used to get the lock status
     * 
     * @return true if the lock is locked, false otherwise
     */
    public boolean isLocked() 
    {
        return locked;
    }

    /**
     * Check if the lock can be unlocked with the key given as argument
     * 
     * @param k key to unlock the lock
     * @return true if the lock is unlocked, false otherwise
     */
    public boolean unlock(Key k)
    {
        if(k.ID == this.ID)
            this.locked = false;
        return this.locked;
    }

    /**
     * Constructor for the Lock
     * 
     * @param name name of the lock
     * @param id id of the lock
     * @param image image of the lock given as a path
     * @param currentRoom current room of the lock
     * @throws IllegalArgumentException if the room is not between 0 and 9
     */
    public Lock(String name, int id, String image, int currentRoom)
    {
        super(name, image, WEIGHT, currentRoom, PICKABLE);
        this.ID = id;
        this.locked = true;
        usingMessage = "You unlock the " + name + " with the key.";
    }    
}