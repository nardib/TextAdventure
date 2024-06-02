public class Hammer extends Item {
    /*
     * constructor
     */
    public Hammer(String name, String image, int weight, int currentRoom)
    {
        super(name, image, weight, currentRoom, true);
        usingMessage = "You have used " + name + " to break the wall";
    }
}