import java.util.List;
import java.util.ArrayList;

public class Game {
    /*
     * State variables
     */
    private Player player;
    private Map map;
    private Enemy enemy;
    private boolean isGamePaused, isGameOn;
    private List<GameMemento> mementos = new ArrayList<>();

    /*
     * Constructor for initializing the game variables
     */
    public Game(String pn, String pg, String en, String eg) {
        player = new Player(pn, pg);
        map = new Map();
        enemy = new Enemy(en, eg);
        isGameOn = true;
        isGamePaused = false;
    }

    /*
     * Method to manage the player moves
     */
    private String playerTurn (String input) {
        if (input.equalsIgnoreCase("north")){
            player.changeDirection(Direction.NORTH);
            return "You are now facing north";
        }
        else if (input.equalsIgnoreCase("south")){
            player.changeDirection(Direction.SOUTH);
            return "You are now facing south";
        }
        else if (input.equalsIgnoreCase("east")){
            player.changeDirection(Direction.EAST);
            return "You are now facing east";
        }
        else if (input.equalsIgnoreCase("west")){
            player.changeDirection(Direction.WEST);
            return "You are now facing west";
        }
        else if(input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("cross"))
        {
            if (input.substring(6).equalsIgnoreCase("north") && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                return "You moved to the north room";
            }
            else if (input.substring(6).equalsIgnoreCase("south") && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                return "You moved to the south room";
            }
            else if (input.substring(6).equalsIgnoreCase("east") && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                return "You moved to the east room";
            }
            else if (input.substring(6).equalsIgnoreCase("west") && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                return  "You moved to the west room";
            }
            throw new IllegalArgumentException("You can't cross in that direction");
        }
        /*
        else if (input.equalsIgnoreCase("look"))
        {
            Item[] roomItems = map.getRoom(player.getCurrentRoom()).getItems());
            String items = "In this room there are these items:\n";
            for (i = 0; i < roomItems.length; i++)
            {
                items += (i+1) + ") " + roomItems[i].getName() + "\n";
            }
            return items;
        }*/
        /*
        else if (input.substring(0, 4).equalsIgnoreCase("take"))
        {
            if (map.getRoom(player.getCurrentRoom()).getItems().length == 0)
            {
                return "There are no items in this room";
            }
            else
            {
                //chech if item is in the room and then add it to the player inventory
            }
        }
        */
        else if(input.substring(0, 3).equalsIgnoreCase("use"))
        {
            return "You used the " + input.substring(4);
        }
        throw new IllegalArgumentException("Invalid input");
     }

    /*
     * Method to manage the enemy moves
     */
    private String enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom()) {
            player.decreaseHealth();
            return "The enemy attacked you";
        }
        else {
            enemy.move(map.getRoom(enemy.getCurrentRoom()));
            return "The enemy moved to another room";
        }
    }

    /*
     * Method to generate the actions of a move
     */
    public String nextMove(String input) {
        if (!isGameOn) {
            return "Game is Over!";
        }
        if (input.equalsIgnoreCase("pause")) {
            isGamePaused = true;
            return "Game paused";
        }
        else if (isGamePaused) {
            if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                isGameOn = false;
                return "Game over";
            }
            if (input.equalsIgnoreCase("save")) {
                return "Game saved";
            }
            if (input.equalsIgnoreCase("resume")) {
                isGamePaused = false;
                return "Game resumed";
            }
        }
        if (input.equalsIgnoreCase("undo") || input.equalsIgnoreCase("back")) {
            if (undo()) {
                return "Undo successful";
            }
            return "No previous moves to undo";
        }
        String out = "";
        // player turn
        try {
            out += playerTurn(input);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        // enemy turn
        out +="\n" + enemyTurn();
        //i save the state of the game after each move
        saveCurrentState();
        return out;
    }

    /*
     * Method to check if the game is over
     */
    public boolean isGameOver() {
        if (player.getHealth() == 0) {
            isGameOn = false;
        }
        //i should probably add a win condition here
        return !isGameOn;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Map getMap() {
        return map;
    }

    /*
     * class for memento pattern to implement undo function
     */
    private class GameMemento {
        private final Player player;
        private final Enemy enemy;
        private final Map map;
        private final boolean isGameOn;

        public GameMemento(Player player, Enemy enemy, Map map, boolean isGameOn) {
            this.player = player;
            this.enemy = enemy;
            this.map = map;
            this.isGameOn = isGameOn;
        }

        public Player getPlayer() {
            return player;
        }

        public Enemy getEnemy() {
            return enemy;
        }

        public Map getMap() {
            return map;
        }

        public boolean getisGameOn() {
            return isGameOn;
        }
    }

    /*
     * Method to save the current state of the game
     */
    private void saveCurrentState() {
        mementos.add(new GameMemento(player, enemy, map, isGameOn));
    }

    /*
     * Method to undo the last move
     * 
     * @return true if undo is successful, false otherwise
     */
    public boolean undo() {
        if (mementos.size() > 0) {
            GameMemento memento = mementos.get(mementos.size() - 1);
            player = memento.getPlayer();
            enemy = memento.getEnemy();
            map = memento.getMap();
            isGameOn = memento.getisGameOn();
            mementos.remove(mementos.size() - 1);
            return true;
        }
        return false;
    }
}
