public class Notes extends Item {
    final private String message;

    public String getMessage() 
    {
        return "It says: \n" + message;
    }
    
    /*
     * constructor
     */
    public Notes(String name, String image, int weight, int currentRoom, String message)
    {
        super(name, image, weight, currentRoom, true);
        this.message = message;
        usingMessage = "You read the note";
    }    
}