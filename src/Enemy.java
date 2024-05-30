import java.lang.Math;

public class Enemy extends Character{ //AKA ER CATTIVONE
    /*
     * state variables
     */
    public final int DAMAGE = 1; //number of HP decreased to the player every 1.5 seconds
    private boolean[] rooms = new boolean[4]; //contiene i boleani N, S, E, W per sapere se la facciata ha la porta o meno

    public int move(Room n/*, Room s, Room e, Room w*/) //oggetto stanza in cui mi trovo. non uso currentRoom perchè è solo un numero e non l'oggetto con le proprietà
    {
        rooms[0] = n.getCrossableNorth(); //le stanze adiacenti sono accessibili?
        rooms[1] = n.getCrossableSouth(); //
        rooms[2] = n.getCrossableEast();  //
        rooms[3] = n.getCrossableWest();  //
        while(true) {
            int i = (int) Math.round(Math.random()*3);  //genera un intero da 0 a 3 che sarà usato per vedere se la facciata è attraversabile o meno. Se non lo è riprova. L'ordine delle facciate è 0=N, 1=S, 2=E e 3=W;
            if(rooms[i]==true) {
                switch(i) { //se la stanza selezionata è attraversabile allora setta la nuova stanza
                    case 0: currentRoom = n.getRoomNumber()-3;
                    break;
                    case 1: currentRoom = n.getRoomNumber()+3;
                    break;
                    case 2: currentRoom = n.getRoomNumber()+1;
                    break;
                    case 3: currentRoom = n.getRoomNumber()-1;
                    break;
                }
            }
            return currentRoom; //interrompe il ciclo e ritorna l'oggetto stanza in cui finirà. Tale risultato sarà usato per modificare i valore delle stanze in cui era e in cui si trova ora il nemico
        }
    }

    public Enemy(String n, String g) {
        super(n, g, generateStartingPoint());
    }

    //private function to generate a random numeber for the starting point between 1 and 9 except 5
    private static int generateStartingPoint() {
        int room = (int) Math.round(Math.random()*8)+1;
        if(room == 5) {
            return generateStartingPoint();
        }
        return room;
    }
}