public class Lock extends Item {
    private int id; //used to identify the correct key to interract with depending on the key used. There are more keys.

    /*
     * used to get the id to match the correct lock with the key
     */
    public int getId()
    {
        return id;
    }

    /*
     * constructor
     */
    public Lock(String name, int id, String image, int weight, int currentRoom, boolean pikable)
    {
        super(name, image, weight, currentRoom, pikable);
        this.id = id;
    }    
}