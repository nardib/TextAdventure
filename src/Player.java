/*
 * enum for all the accepted gender types
*/
enum Gender{
    MALE,
    FEMALE,
    NEUTRAL
}

/*
 * enum for all the accepted directions
 */
enum Direction{
    NORTH,
    SOUTH,
    EAST,
    WEST
}

public class Player extends Character{
    /*
     * state variables
     */
    private int health, score, ArrayIndexCount, WeightCount;
    private Item[] inventory;
    private Direction currentDirection;

    /*
     * Constructor for the player
     */
    public Player(String n, String g)
    {
        super(n, g);
        health = 5;
        score = 0;
        ArrayIndexCount = 0;
        WeightCount = 0;
        inventory = new Item[10];
        currentDirection = Direction.NORTH;
    }

    /*
     * decrease the health of the player
     */
    public int decreaseHealth()
    {
        if (health - 1 < 0)
            throw new IllegalStateException("the player can't have an health score of less than zero");
        return health--;
    }

    /*
     * increase the health of the player
     */
    public int increaseHealth()
    {
        if (health + 1 > 5)
            throw new IllegalStateException("Player's health can't be more than 5");
        return health++;
    }

    /*
     * Return the health of the player
     */
    public int getHealth()
    {
        return health;
    }

    /*
     * Insert an Item in the inventory
     */
    public void insertItem(Item i)
    {
        if (/*maxArrayIndexCount == 9 ||*/ WeightCount + i.getWeight() > 10)
            throw new IllegalStateException("The item weigth exiding the max carriable weigth");
        WeightCount += i.getWeight();
        ArrayIndexCount++;
    }

    /*
     * removes and returns the Item at index i
     */
    public Item removeItem(int i)
    {
        if (i < 0 || i > ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        WeightCount -= inventory[i].getWeight();
        ArrayIndexCount--;
        Item out = inventory[i];
        //i swap all the elemnts in i-maxIndex one position below
        for (int j = i; j <= ArrayIndexCount; j++) 
            inventory[j] = inventory[j+1];
        return out;
    }

    /*
     * returns the inventory as an array
     */
    public Item[] getInventory()
    {
        return inventory;
    }

    /*
     * function to cross the door. Ability to cross must be checked out of the function. The parameter is the number of the door the player will end in.
     */
    public void crossDoor(Room newRoom) {
        currentRoom = newRoom;
    }

    /*
     * function to change the wall. The parameter is the number of the door the player will end in.
     */
    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }
}
