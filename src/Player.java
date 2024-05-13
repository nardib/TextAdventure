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
    private int health = 5, score, maxArrayIndexCount = 0;
    private Item[] inventory = new Item[10];

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
        if (maxArrayIndexCount == 9)
            throw new IllegalStateException("The inventory is already full, you can't add other elements");
        inventory[maxArrayIndexCount++] = i;
    }

    /*
     * removes and returns the Item at index i
     */
    public Item removeItem(int i)
    {
        if (i < 0 || i > maxArrayIndexCount)
            throw new IllegalArgumentException("invalid index, it must be in the range of 0-maxIndex");
        maxArrayIndexCount--;
        Item out = inventory[i];
        //i swap all the elemnts in i-maxIndex one position below
        for (int j = i; j <= maxArrayIndexCount; j++)
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
}
