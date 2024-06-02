public class Key extends Item {
    private int id; //used to identify the correct lock to interract with depending on the key used. There are more keys
    final int WEIGHT = 1;
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
    public Key(String name, int id, String image, int weight, int currentRoom)
    {
        super(name, image, weight, currentRoom, true);
        this.id = id;
        usingMessage = "You used the " + name;
    }    
}