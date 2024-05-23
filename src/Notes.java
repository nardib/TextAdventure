public class Notes extends Item {
    private String message;

    public String getMessage() 
    {
        return message;
    }
    
    /*
     * constructor
     */
    public Notes(String name, String image, int weight, int currentRoom, boolean pickable, String message)
    {
        super(name, image, weight, currentRoom, pickable);
        this.message = message;
    }    
}