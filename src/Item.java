import javax.swing.ImageIcon;

public class Item {
    /*
     * state variable
     */
    protected String name;
    protected ImageIcon icon;
    protected int weight;
    protected int currentRoom;
    protected boolean pickable;
    protected String usingMessage;

    /*
     * constructor
     */
    public Item(String name, String image, int weight, int currentRoom, boolean pickable)
    {
        icon = new ImageIcon(getClass().getResource(image));
        this.weight = weight;
        this.name = name;
        setRoom(currentRoom);
        this.pickable = pickable;
    }

    /*
     * retrurns the icon of the image
     */
    public ImageIcon getIcon()
    {
        return icon;
    }

    /*
     * returns the weight of the object
     */
    public int getWeight()
    {
        if (weight < 0)
            throw new IllegalArgumentException("Weight must be greater than or equal to 0");
        return weight;
    }

    /*
     * returns the roomt of the object
     */
    public int getRoom()
    {
        return currentRoom;
    }
    
    /*
     * used to get the name
     */
    public String getName()
    {
        return name;
    }

    /*
     * is held?
     */
    public boolean isPickable()
    {
        return pickable;
    }

    /*
     * returns the using message
     */
    public String getUsingMessage() {
        return usingMessage;
    }

    /*
     * set the current room (if =0, then it is picked by the player)
     */
    public void setRoom(int room)
    {
        if(room >= 0 && room <= 9)
            currentRoom = room;
        throw new IllegalArgumentException("Room must be between 0 and 9");
    }

    /*
     * override method equals
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Item || other == null))
            return false;
        Item i = (Item) other;
        return this.name.equals(i.name) && this.weight == i.weight && this.currentRoom == i.currentRoom && this.pickable == i.pickable;
    }

    /*
     * override method clone
     */
    @Override
    public Item clone() {
        return new Item(this.name, this.icon.toString(), this.weight, this.currentRoom, this.pickable);
    }
}