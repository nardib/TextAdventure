public class Lock extends Item {
    private int id; //used to identify the correct key to interract with depending on the key used. There are more keys.
    private boolean locked;

    /*
     * used to get the id to match the correct lock with the key
     */
    public int getId()
    {
        return id;
    }

    /*
     * used to get the lock status
     */
    public boolean isLocked() 
    {
        return locked;
    }

    /*
     * check if the lock can be unlocked
     */
    public boolean unlock(Key k)
    {
        if(k.getId() == this.id)
            this.locked = false;
        return this.locked;
    }

    /*
     * constructor
     */
    public Lock(String name, int id, String image, int weight, int currentRoom)
    {
        super(name, image, weight, currentRoom, false);
        this.id = id;
        this.locked = true;
        usingMessage = "You unlock the " + name + " with the key.";
    }    
}