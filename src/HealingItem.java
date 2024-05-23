public class HealingItem extends Item {
    private int healthPoints;
    
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
        this.healthPoints = healthPoints;
    }

    /*
     * getter for healthPoints
     */
    public int getHealthPoints()
    {
        return healthPoints;
    }
}