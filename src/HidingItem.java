public class HidingItem extends Item {
    final int WEIGHT = 11;

    /*
     * constructor
     */
    public HidingItem(String name, String image, int currentRoom)
    {
        super (name, image, currentRoom, 11, false);
        usingMessage = "You are now hidden in the " + name + "\n The enemy now can't see you.";
    }    
}