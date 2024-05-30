public class HidingItem extends Item {
    /*
     * constructor
     */
    public HidingItem(String name, String image, int currentRoom, int weigth)
    {
        super (name, image, currentRoom, weigth, false);
        usingMessage = "You are now hidden in the " + name + "\n The enemy now can't see you.";
    }    
}