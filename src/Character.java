public class Character {
    /*
     * state variables
     */
    protected String name;
    protected Gender gender;
    protected int currentRoom;

    /*
     * method to move the character
     */
    //public void move() {}

    /*
     * Constructior for the character
     */
    public Character(String n, String g/*aggingeremo il parametro Room r che sarà la stanza di partenza */)
    {
        name = n;
        if(g.toLowerCase().compareTo("m") == 0 || g.toLowerCase().compareTo("male") == 0)
            gender = Gender.MALE;
        else if (g.toLowerCase().compareTo("f") == 0 || g.toLowerCase().compareTo("female") == 0)
            gender = Gender.FEMALE;
        else if (g.toLowerCase().compareTo("n") == 0 || g.toLowerCase().compareTo("neutral") == 0)
            gender = Gender.NEUTRAL;
        else
            throw new IllegalArgumentException("Invalid Gender");
        currentRoom = 0;
    }

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
     * Method to set the current room of the player
     */
    public void setCurrentRoom(Room r)
    {
        currentRoom = r.getRoomNumber();
    }

    /*
     * Method to get the current room of the player
     */
    public int getCurrentRoom()
    {
        return currentRoom;
    }
}
