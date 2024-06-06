/**
 * Class to create a character with name and gender
 */
public class Character {
    /**
     * Name of the Character
     */
    protected String name;

    /**
     * Gender of the Character
     */
    protected Gender gender;

    /**
     * Current room of the Character
     */
    protected int currentRoom;

    /**
     * constants value to move the character in the north room
     */
    final static int CROSS_NORTH = -3; 

    /**
     * constants value to move the character in the south room
     */
    final static int CROSS_SOUTH = 3;

    /**
     * constants value to move the character in the east room
     */
    final static int CROSS_EAST = 1;

    /**
     * constants value to move the character in the west room
     */
    final static int CROSS_WEST = -1;
    
    /**
     * Constructior for the Character
     * 
     * @param n name of the character
     * @param g gender of the character given as a string ("m","f","n" or "male", "female", "neutral" are the valid inputs)
     * @param currRoom current room of the character given as integer
     * 
     * @throws IllegalArgumentException if the gender is not given in a valid format
     */
    public Character(String n, String g, int currRoom)
    {
        setName(n);
        setGender(g);
        setCurrentRoom(currRoom);
    }

    /**
     * Constructior for the character with gender as argument
     * 
     * @param n name of the character
     * @param g gender of the character given as gender type
     * @param currRoom current room of the character given as integer
     */
    public Character (String n, Gender g, int currRoom)
    {
        setName(n);
        gender = g;
        setCurrentRoom(currRoom);
    }

    /**
     * Method to set the name of the character
     * 
     * @param n name of the character
     */
    public void setName(String n)
    {
        name = n;
    }

    /**
     * Method to get the name of the character
     * 
     * @return name of the character
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method to set the gender of the character
     * 
     * @param g gender of the character given as a string ("m", f", "n" or "male", "female", "neutral" are the valid inputs)
     * @throws IllegalArgumentException if the gender is not given in a valid format
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

    /**
     * Method to get the gender of the character as gender type
     * 
     * @return gender of the character
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * Method to set the current room of the player
     * 
     * @param n room in which the character should move
     * @throws IllegalArgumentException if the room is not in the range of 1 to 9
     */
    public void setCurrentRoom(int n)
    {
        if (n < 1 || n > 9)
            throw new IllegalArgumentException("Invalid Room");
        currentRoom = n;
    }

    /*
     * Method to get the current room of the player
     * 
     * @return current room of the player as integer
     */
    public int getCurrentRoom()
    {
        return currentRoom;
    }
}