public class HealingItem extends Item {
    final int HEALING_POINTS;
    
    /*
     * constructor
     */
    public HealingItem(String name, String image, int weight, int currentRoom, int healthPoints)
    {
        super(name, image, weight, currentRoom, true);
        if (healthPoints < 0)
        {
            throw new IllegalArgumentException("Health points must be greater than or equal to 0");
        }
        this.HEALING_POINTS = healthPoints;
        usingMessage = "You have used " + name + " and gained " + healthPoints + " health points";
    }
}