public class Key extends Item {
    private int id; //used to identify the correct lock to interract with depending on the key used. There are more keys

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
    public Key(String name, int id, String image, int weight, int currentRoom, boolean pickable)
    {
        super(name, image, weight, currentRoom, pickable);
        this.id = id;
    }    
}