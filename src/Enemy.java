import java.lang.Math;

public class Enemy extends Character{ //AKA ER CATTIVONE

    /*
     * state variables
     */
    public final int DAMAGE = 1; //number of HP decreased to the player every 1.5 seconds
    
    public int move(Room n/*, Room s, Room e, Room w*/) //oggetto stanza in cui mi trovo. non uso currentRoom perchè è solo un numero e non l'oggetto con le proprietà
    {
        boolean[] rooms = new boolean[4]; //contiene i boleani N, S, E, W per sapere se la facciata ha la porta o meno
        rooms[0] = n.getCrossableNorth(); //le stanze adiacenti sono accessibili?
        rooms[1] = n.getCrossableSouth(); //
        rooms[2] = n.getCrossableEast();  //
        rooms[3] = n.getCrossableWest();  //
        while(true) {
            int i = (int) (Math.random()*4);  //genera un intero da 0 a 3 che sarà usato per vedere se la facciata è attraversabile o meno. Se non lo è riprova. L'ordine delle facciate è 0=N, 1=S, 2=E e 3=W;
            if(rooms[i]) {
                switch(i) { //se la stanza selezionata è attraversabile allora setta la nuova stanza
                    case 0: currentRoom += CROSS_NORTH;
                    return currentRoom;
                    case 1: currentRoom += CROSS_SOUTH;
                    return currentRoom;
                    case 2: currentRoom += CROSS_EAST;
                    return currentRoom;
                    case 3: currentRoom += CROSS_WEST;
                    return currentRoom;
                }
            }
        }
    }

    public Enemy(String n, String g) {
        super(n, g, generateStartingPoint());
    }

    public Enemy(String n,Gender g) {
        super(n, g, generateStartingPoint());
    }

    //private function to generate a random numeber for the starting point between 1 and 9 except 5
    private static int generateStartingPoint() {
        int room = (int) Math.round(Math.random()*8)+1;
        //if i get 5, i recursively call the function again
        if(room == 5)
            return generateStartingPoint();
        return room;
    }

    /*
     * method to check equality between two enemies
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Enemy || other == null))
            return false;
        Enemy e = (Enemy) other;
        return this.name.equals(e.name) && this.gender == e.gender && this.currentRoom == e.currentRoom;
    }

    /*
     * method to clone this enemy (deep copy)
     */
    @Override
    public Enemy clone() {
        Enemy e = new Enemy(this.getName(), this.getGender());
        e.setCurrentRoom(this.getCurrentRoom());
        return e;
    }
}