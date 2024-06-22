package org.zssn.escaperoom;

/**
 * Class to manage the Player properties. In particular it manages the health, the inventory, the name and the current room of the player
 */
public class Player extends Character{
    /**
     * The health of the Player
     */
    private int health;

    /**
     * The number of items in the inventory
     */
    private int ArrayIndexCount;

    /**
     * The total weigth of the items in the inventory
     */
    private int WeightCount;

    /**
     * Tells if the player is hidden or not
     */
    private boolean hidden;

    /**
     * The inventory of the player
     */
    private Item[] inventory;

    /**
     * The inventory for the notes of the player
     */
    private Note[] notesInventory;

    /**
     * Counter for the notes in the inventory
     */
    private int notesCount;

    /**
     * Max number of notes in the map
     */
    private final int MAX_NOTES = 10;

    /**
     * The direction the player is facing
     */
    private Direction currentDirection;

    /**
     * The defualt value for the max health of the player
     */
    public final int MAX_HEALTH = 5;

    /**
     * The defualt value for the max weigth the player can carry
     */
    public final static int MAX_WEIGHT = 10;

    /**
     * The defualt value for the starting room of the player
     */
    public final static int STARTING_ROOM = 5;

    /**
     * Creates a new player with the given name and a given gender. The standard position for the spawn is the room 5 and the standard direction is north. The standard health is 5
     *
     * @param n name of the player
     * @param g gender of the player given as a string ("m","f","n" or "male", "female", "neutral" are the valid inputs)
     * @throws IllegalArgumentException if the gender is not given in a valid format
     */
    public Player(String n, String g)
    {
        super(n, g, STARTING_ROOM);
        health = MAX_HEALTH;
        ArrayIndexCount = 0;
        WeightCount = 0;
        inventory = new Item[MAX_WEIGHT];
        currentDirection = Direction.NORTH;
        notesInventory = new Note[MAX_NOTES];
        notesCount = 0;
        hidden = false;
    }

    /**
     * Constructor for the player with gender as argument
     * 
     * @param n name of the player
     * @param g gender of the player given as Gender type
     */
    public Player(String n, Gender g)
    {
        super(n, g, STARTING_ROOM);
        health = MAX_HEALTH;
        ArrayIndexCount = 0;
        WeightCount = 0;
        inventory = new Item[MAX_WEIGHT]; 
        currentDirection = Direction.NORTH;
        notesInventory = new Note[MAX_NOTES];
        notesCount = 0;
        hidden = false;
    }

    /**
     * Decreses the health of the player
     * 
     * @param damage the amount of health to decrease
     * @return the new health of the player
     */
    public int decreaseHealth(int damage)
    {
        if (health - damage < 0)
            return health = 0;
        return health -= damage;
    }

    /**
     * Increases the health of the player
     * 
     * @param healingPoints the amount of health to increase
     * @return the new health of the player
     */
    public int increaseHealth(int healingPoints)
    {
        if (health + healingPoints > 5)
            return health = 5;
        return health += healingPoints;
    }

    /**
     * Return the health of the player
     * 
     * @return the health of the player
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * Insert an Item in the inventory
     * 
     * @param i the item to insert
     */
    public void insertItem(Item i)
    {
        if (!i.PICKABLE)
            throw new IllegalArgumentException("The item is not pickable");
        if (i instanceof Note)
        {
            if (notesCount == MAX_NOTES)
                throw new IllegalStateException("The notes inventory is full");
            notesInventory[notesCount] = (Note) i;
            notesCount++;
            return;
        }
        if (WeightCount + i.WEIGHT > 10)
            throw new IllegalStateException("The item weight exceeds the max carriable weight");
        WeightCount += i.WEIGHT;
        inventory[ArrayIndexCount] = i;
        ArrayIndexCount++;
    }

    /**
     * Removes and returns the Item at index i
     * 
     * @param i the index of the item to remove
     * @return the item removed
     * 
     * @throws IllegalArgumentException if the index is not in the range of 0-(maxIndex-1)
     */
    public Item removeItem(int i)
    {
        if (i < 0 || i >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        Item out = inventory[i];
        //i remove the item i and i shift all the other items to the left
        inventory[i] = null;
        inventory = compactedInventory();
        WeightCount -= out.WEIGHT;
        ArrayIndexCount--;
        return out;
    }

    /**
     * Returns the inventory as an array of items
     * 
     * @return the inventory as an array of items
     */
    public Item[] getInventory()
    {
        return inventory;
    }

    /**
     * Returns the element at index i of the inventory
     * 
     * @param i the index of the item to return
     * @return the item at index i
     * 
     * @throws IllegalArgumentException if the index is not in the range of 0-(maxIndex-1)
     */
    public Item getItem(int i)
    {
        if (i < 0 || i >= ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        return inventory[i];
    }

    /**
     * Returns the numeber of items in the inventory
     * 
     * @return the number of items in the inventory
     */
    public int getInventoryCount()
    {
        return ArrayIndexCount;
    }

    /**
     * Returns the total weigth of the items in the inventory
     * 
     * @return the total weigth of the items in the inventory
     */
    public int getWeight()
    {
        return WeightCount;
    }

    /**
     * Returns a String containing all the items in the inventory
     * 
     * @return a String containing all the items in the inventory, each one in a new line
     */
    public String printInventory(){
        if (ArrayIndexCount == 0 && notesCount == 0)
            return "no items";
        String out = "\n";
        for (int i = 0; i < ArrayIndexCount; i++)
            out += "· " + inventory[i].getName() + "\n";
        for (int i = 0; i < notesCount; i++)
            out += "· " + notesInventory[i].getName() + "\n";
        return out;
    }

    /**
     * Returns the notes inventory
     * 
     * @return the notes inventory
     */
    public Note[] getNotesInventory()
    {
        return notesInventory;
    }

    /**
     * Returns the number of notes in the inventory
     * 
     * @return the number of notes in the inventory
     */
    public int getNotesCount()
    {
        return notesCount;
    }

    /**
     * Returns the note at index i
     * 
     * @param i the index of the note
     * @return the note at index i
     * 
     * @throws IllegalArgumentException if the index is not in the range of 0-(maxIndex-1)
     */
    public Note getNoteAt(int i)
    {
        if (i < 0 || i >= notesCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        return notesInventory[i];
    }

    /**
     * Function to cross a door. 
     * 
     * @param newRoom the room the player will end in
     */
    public void crossDoor(Room newRoom) {
        currentRoom = newRoom.getRoomNumber();
    }

    /**
     * Function to change the wall the player is facing
     * 
     * @param newDirection the new direction the player will be facing
     */
    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }

    /**
     * Returns the current direction the player is facing
     * 
     * @return the current direction of the player
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Returns if the player is hidden or not
     * 
     * @return true if the player is hidden, false otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Change the state of the player from hidden to not hidden and vice versa
     */
    public void setHidden() {
        hidden = !hidden;
    }

    /**
     * Returns true if this player is equal to the other player given as argument
     * 
     * @param other the player to compare
     * @return true if the two players are equal, false otherwise
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Player || other == null))
            return false;
        Player p = (Player) other;
        // Check the inventory
        if (ArrayIndexCount != p.ArrayIndexCount || notesCount != p.notesCount)
            return false;
        if (ArrayIndexCount > 0) {
            for (int i = 0; i < ArrayIndexCount; i++) {
                if (!inventory[i].equals(p.inventory[i]))
                    return false;
            }
        }
        if (notesCount > 0) {
            for (int i = 0; i < notesCount; i++) {
                if (!notesInventory[i].equals(p.notesInventory[i]))
                    return false;
            }
        }
        return this.name.equals(p.name) && this.gender == p.gender && this.currentRoom == p.currentRoom && health == p.health && WeightCount == p.WeightCount && currentDirection == p.currentDirection && hidden == p.hidden;
    }

    /**
     * Method to create a deep copy of the player
     * 
     * @return a deep copy of the player
     */
    @Override
    public Player clone()
    {
        Player p = new Player(this.name, this.gender);
        p.currentRoom = this.currentRoom;
        p.health = this.health;
        p.currentDirection = this.currentDirection;
        p.hidden = this.hidden;
        p.ArrayIndexCount = this.ArrayIndexCount;
        p.WeightCount = this.WeightCount;
        p.notesCount = this.notesCount;
        if (this.ArrayIndexCount > 0) {
            p.inventory = new Item[MAX_WEIGHT];
            for (int i = 0; i < ArrayIndexCount; i++) {
                p.inventory[i] = this.inventory[i].clone();
            }
        } else {
            p.inventory = new Item[MAX_WEIGHT];
        }
        if (this.notesCount > 0) {
            p.notesInventory = new Note[MAX_NOTES];
            for (int i = 0; i < notesCount; i++) {
                p.notesInventory[i] = this.notesInventory[i].clone();
            }
        } else {
            p.notesInventory = new Note[MAX_NOTES];
        }
        return p;
    }

    /**
    * Private method to compact the inventory when an item is removes 
    *
    * @return the compacted inventory
    */
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
