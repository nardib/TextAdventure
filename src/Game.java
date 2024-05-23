import java.util.List;
import java.util.ArrayList;

public class Game {
    /*
     * State variables
     */
    private Player player;
    private Map map;
    private Enemy enemy;
    private boolean gameIsOn;
    private List<GameMemento> mementos = new ArrayList<>();

    /*
     * Constructor for initializing the game variables
     */
    public Game(String pn, String pg, String en, String eg) {
        player = new Player(pn, pg);
        map = new Map();
        enemy = new Enemy(en, eg);
        boolean gameIsOn = true;
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
        else if(input.substring(0, 5).equalsIgnoreCase("cross"))
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
            Map.getRoom(player.getCurrentRoom()).);
        }*/
        /*
        else if (input.substring(0, 4).equalsIgnoreCase("take"))
        {
            if (input.substring(5).equalsIgnoreCase()
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
    private void enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom()) {
            player.decreaseHealth();
        }
        else {
            enemy.move(map.getRoom(enemy.getCurrentRoom()));
        }
    }

    /*
     * Method to generate the actions of a move
     */
    public void nextMove(String input) {
        if (!gameIsOn) {
            throw new IllegalArgumentException("Game is over");
        }
        else if (input.equalsIgnoreCase("undo")) {
            undo();
            return;
        }
        else if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
            gameIsOn = false;
            return;
        }
        // player turn
        playerTurn(input);
        // enemy turn
        enemyTurn();
        //i save the state of the game after each move
        saveCurrentState();
    }

    /*
     * Method to check if the game is over
     */
    public boolean isGameOver() {
        if (player.getHealth() == 0) {
            gameIsOn = false;
        }
        //i should probably add a win condition here
        return gameIsOn;
    }

    public Player getPlayer() {
        return player;
    }

    /*
     * class for memento pattern to implement undo function
     */
    private class GameMemento {
        private final Player player;
        private final Enemy enemy;
        private final Map map;
        private final boolean gameIsOn;

        public GameMemento(Player player, Enemy enemy, Map map, boolean gameIsOn) {
            this.player = player;
            this.enemy = enemy;
            this.map = map;
            this.gameIsOn = gameIsOn;
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

        public boolean getGameIsOn() {
            return gameIsOn;
        }
    }

    /*
     * Method to save the current state of the game
     */
    public void saveCurrentState() {
        mementos.add(new GameMemento(player, enemy, map, gameIsOn));
    }

    /*
     * Method to undo the last move
     */
    public void undo() {
        if (mementos.size() > 0) {
            GameMemento memento = mementos.get(mementos.size() - 1);
            player = memento.getPlayer();
            enemy = memento.getEnemy();
            map = memento.getMap();
            gameIsOn = memento.getGameIsOn();
            mementos.remove(mementos.size() - 1);
        }
    }
}
