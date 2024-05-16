/*
 * enum for all the accepted gender types
*/
enum Gender{
    MALE,
    FEMALE,
    NEUTRAL
}

public class Player {
    /*
     * state variables
     */
    private String name;
    private Gender gender;
    private int health = 5, score, ArrayIndexCount = 0, WeightCount = 0;
    private Item[] inventory = new Item[10];
    private int currentRoomNumber; 
    private Direction currentDirection;

    /*
     * method to set the name of the player
     */
    public void setName(String n)
    {
        name = n;
    }

    /*
     * Method to get the name of the player
     */
    public String getName()
    {
        return name;
    }

    /*
     * Method to set the gender of the player
     */
    public void setGender(String g)
    {
        if(g.toLowerCase().compareTo("m") == 0 || g.toLowerCase().compareTo("male") == 0)
            gender = Gender.MALE;
        else if (g.toLowerCase().compareTo("f") == 0 || g.toLowerCase().compareTo("female") == 0)
            gender = Gender.FEMALE;
        else if (g.toLowerCase().compareTo("n") == 0 || g.toLowerCase().compareTo("neutral") == 0)
            gender = Gender.NEUTRAL;
        else
            throw new IllegalArgumentException("Invalid Gender");
    }

    /*
     * Method to get the gender of the player
     */
    public Gender getGender()
    {
        return gender;
    }

    /*
     * decrease the health of the player
     */
    public void decreaseHealth()
    {
        if (health - 1 < 0)
            throw new IllegalStateException("the player can't have an health score of less than zero");
        health--;
    }

    /*
     * increase the health of the player
     */
    public void increaseHealth()
    {
        if (health + 1 > 5)
            throw new IllegalStateException("Player's health can't be more than 5");
        health++;
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
        if (/*maxArrayIndexCount == 9 ||*/ WeightCount + i.getWeigth() > 10)
            throw new IllegalStateException("The item weigth exiding the max carriable weigth");
        WeightCount += i.getWeigth();
        ArrayIndexCount++;
    }

    /*
     * removes and returns the Item at index i
     */
    public Item removeItem(int i)
    {
        if (i < 0 || i > ArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-(maxIndex-1)");
        WeightCount -= inventory[i].getWeigth();
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
    public crossDoor(int newRoomNumber) {
        currentRoomNumber = newRoomNumber;
    }

    /*
     * function to change the wall. The parameter is the number of the door the player will end in.
     */
    public changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }
}
