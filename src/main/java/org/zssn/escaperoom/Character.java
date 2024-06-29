package org.zssn.escaperoom;

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
     * Constants value to move the character in the north room
     */
    public final static int CROSS_NORTH = -3; 

    /**
     * Constants value to move the character in the south room
     */
    public final static int CROSS_SOUTH = 3;

    /**
     * Constants value to move the character in the east room
     */
    public final static int CROSS_EAST = 1;

    /**
     * Constants value to move the character in the west room
     */
    public final static int CROSS_WEST = -1;

    /**
     * Default constructor for the Character
     */
    public Character() {}
    
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
     * Method to set the gender
     * 
     * @param g the gender to set
     */
    public void setGender(Gender g)
    {
        this.gender = g;
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

    /**
     * Method to get the current room of the player
     * 
     * @return current room of the player as integer
     */
    public int getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Returns the pronoun of the character. If not specified it just returns "they".
     * 
     * @return a string containing the pronoun of the character ("he", "she" or "they")
     */
    public String returnPronoun() {
        switch (gender) {
            case MALE:
                return "he";
            case FEMALE:
                return "she";
            case NEUTRAL:
                return "they";
            default:
                return "they";
        }
    }
}