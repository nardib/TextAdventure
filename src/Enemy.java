import java.lang.Math;
w
public class Enemy {
    /*
     * state variables
     */
    private String name;
    private Gender gender;
    private Room currentRoom; 
    private int damage = 1; //number of HP decreased to the player every 1.5 seconds
    private boolean presence; //is the enemy in the map?
    private boolean[] rooms = new boolean[4]; //contiene i boleani N, S, E, W per sapere se la facciata ha la porta o meno

    public Room move(Room n, Room s, Room e, Room w) //quali sono le stanze adiacenti?
    {
        rooms[0] = currentRoom.crossableNorth; //le stanze adiacenti sono accessibili?
        rooms[1] = currentRoom.crossableSouth; //
        rooms[2] = currentRoom.crossableEast;  //
        rooms[3] = currentRoom.crossableWest;  //
        while(true) {
            int i = Math.round(Math.random()*3);  //genera un intero da 0 a 3 che sarà usato per vedere se la facciata è attraversabile o meno. Se non lo è riprova. L'ordine delle facciate è 0=N, 1=S, 2=E e 3=W;
            if(rooms[i]==true) {
                switch() { //se la stanza selezionata è attraversabile allora setta la nuova stanza
                    case 0: currentRoom = n;
                    break;
                    case 1: currentRoom = s;
                    break;
                    case 2: currentRoom = e;
                    break;
                    case 3: currentRoom = w;
                    break;
                }
            }
            return currentRoom; //interrompe il ciclo e ritorna l'oggetto stanza in cui finirà. Tale risultato sarà usato per modificare i valore delle stanze in cui era e in cui si trova ora il nemico
        } 
    }
}
