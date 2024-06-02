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
    EAST,
    SOUTH,
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
     * constants
     */
    final int MAX_HEALTH = 5;
    final int MAX_WEIGHT = 10;
    final static int STARTING_ROOM = 5;

    /*
     * Constructor for the player
     */
    public Player(String n, String g)
    {
        super(n, g, STARTING_ROOM);
        health = MAX_HEALTH;
        score = 0;
        ArrayIndexCount = 0;
        WeightCount = 0;
        inventory = new Item[MAX_WEIGHT]; 
        currentDirection = Direction.NORTH;
    }

    /*
     * Constructor for the player with gender as argument
     */
    public Player(String n, Gender g)
    {
        super(n, g, STARTING_ROOM);
        health = MAX_HEALTH;
        score = 0;
        ArrayIndexCount = 0;
        WeightCount = 0;
        inventory = new Item[MAX_WEIGHT]; 
        currentDirection = Direction.NORTH;
    }

    /*
     * decrease the health of the player
     */
    public int decreaseHealth(int damage)
    {
        if (health - damage < 0)
            throw new IllegalStateException("the player can't have an health score of less than zero");
        return health -= damage;
    }

    /*
     * increase the health of the player
     */
    public int increaseHealth(int healingPoints)
    {
        if (health + healingPoints > 5)
            throw new IllegalStateException("Player's health can't be more than 5");
        return health += healingPoints;
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
        if (WeightCount + i.getWeight() > 10)
            throw new IllegalStateException("The item weigth exiding the max carriable weigth");
        if (!i.isPickable())
            throw new IllegalArgumentException("The item is not pickable");
        WeightCount += i.getWeight();
        inventory[ArrayIndexCount] = i;
        ArrayIndexCount++;
    }

    /*
     * removes and returns the Item at index i
     */
    public Item removeItem(int i)
    {
        if (i < 0 || i >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        Item out = inventory[i];
        //i remove the item i and i shift all the other items to the left
        inventory[i] = null;
        inventory = compactedInventory();
        WeightCount -= out.getWeight();
        ArrayIndexCount--;
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
     * returns the element at index i of the inventory
     */
    public Item getItem(int i)
    {
        if (i < 0 || i >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        return inventory[i];
    }


    /*
     * returns the numeber of items in the inventory
     */
    public int getInventoryCount()
    {
        return ArrayIndexCount;
    }

    /*
     * returns the total weigth of the items in the inventory
     */
    public int getWeight()
    {
        return WeightCount;
    }

    /*
     * function to cross the door. Ability to cross must be checked out of the function. The parameter is the number of the door the player will end in.
     */
    public void crossDoor(Room newRoom) {
        currentRoom = newRoom.getRoomNumber();
    }

    /*
     * function to change the wall. The parameter is the number of the door the player will end in.
     */
    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }

    /* 
     * returns the current direction of the player
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /*
     * returns true if this player is equal to the other player
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Player || other == null))
            return false;
        Player p = (Player) other;
        //i also check the inventory
        if (ArrayIndexCount != p.ArrayIndexCount)
            return false;
        /*
        for (int i = 0; i < ArrayIndexCount; i++){
            if (!inventory[i].equals(p.inventory[i]))
                return false;
        }*/    
        return this.name.equals(p.name) && this.gender == p.gender && this.currentRoom == p.currentRoom && health == p.health && score == p.score && WeightCount == p.WeightCount && currentDirection == p.currentDirection;
    }

    /*
     * method to create a deep copy of the player
     */
    @Override
    public Player clone()
    {
        Player p = new Player(this.name, this.gender);
        p.currentRoom = this.currentRoom;
        p.health = this.health;
        p.score = this.score;
        p.ArrayIndexCount = this.ArrayIndexCount;
        p.WeightCount = this.WeightCount;
        //this is actually a shellow copy, must be corrected
        /*for (int i = 0; i < ArrayIndexCount; i++)
            p.inventory[i] = inventory[i];*/
        p.currentDirection = this.currentDirection;
        return p;
    }

    //private method to compact the inventory when an item is removes
    private Item[] compactedInventory()
    {
        Item[] out = new Item[MAX_WEIGHT];
        int j = 0;
        for (int i = 0; i < ArrayIndexCount; i++)
        {
            if (inventory[i] != null)
            {
                out[j] = inventory[i];
                j++;
            }
        }
        return out;
    }
}
