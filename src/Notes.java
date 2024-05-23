public class Notes extends Item {
    private String message;

    public String getMessage() 
    {
        return message;
    }
    
    /*
     * constructor
     */
    public Notes(String name, String image, int weight, int currentRoom, boolean pikable, String message)
    {
        super(name, image, weight, currentRoom, pikable);
        this.message = message;
    }    
}