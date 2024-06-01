import java.util.Stack;
public class Game {
    /*
     * State variables
     */
    private Player player;
    private Map map;
    private Enemy enemy;
    private boolean isGamePaused, isGameOn;
    private Stack<GameMemento> mementos = new Stack<>();

    final String HELP = "The commands are:\n"
            + "north, south, east, west, cross <direction>, look, take <item>, use <item>, pause, resume, save, undo/back, quit";

    /*
     * Constructor for initializing the game variables
     */
    public Game(String pn, String pg, String en, String eg) {
        player = new Player(pn, pg);
        map = new Map();
        enemy = new Enemy(en, eg);
        isGameOn = true;
        isGamePaused = false;
        mementos.push(new GameMemento(player, enemy, map, isGameOn));
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
            if(input.length() == 5){
                if(player.getCurrentDirection() == Direction.NORTH && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                    return "You moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.SOUTH && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                    return "You moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.EAST && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                    return "You moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.WEST && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                    return "You moved to the room: " + player.getCurrentRoom();
                }
            }
            else if (input.substring(6).equalsIgnoreCase("north") && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                player.changeDirection(Direction.NORTH);
                return "You moved to the room: " + player.getCurrentRoom();
            }
            else if (input.substring(6).equalsIgnoreCase("south") && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                player.changeDirection(Direction.SOUTH);
                return "You moved to the room: " + player.getCurrentRoom();
            }
            else if (input.substring(6).equalsIgnoreCase("east") && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                player.changeDirection(Direction.EAST);
                return "You moved to the room: " + player.getCurrentRoom();
            }
            else if (input.substring(6).equalsIgnoreCase("west") && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                player.changeDirection(Direction.WEST);
                return "You moved to the room: " + player.getCurrentRoom();
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
        else if(input.substring(0, 3).equalsIgnoreCase("use")) {
            if (player.getInventoryCount() != 0) {
                for (int i = 0; i < player.getInventoryCount(); i++) {
                    if (player.getItem(i).getName().equalsIgnoreCase(input.substring(4))) {
                        Item item = player.getItem(i);
                        if (item instanceof Key) {
                            Key key = (Key) item;
                            //i have to check if there is a lock in the room
                            return key.getUsingMessage();
                        }
                        else if (item instanceof Notes) {
                            Notes note = (Notes) item;
                            return note.getUsingMessage();
                        }
                        else if (item instanceof Hammer) {
                            Hammer hammer = (Hammer) item;
                            return hammer.getUsingMessage();
                        }
                        else if (item instanceof HealingItem) {
                            HealingItem healingItem = (HealingItem) item;
                            player.increaseHealth(healingItem.HEALING_POINTS);
                            player.removeItem(i);
                            return healingItem.getUsingMessage();
                        }
                    }
                }
            }
            //i also must check if there is an hiding item in the room
            return "You can't use this item";
        }
        throw new IllegalArgumentException("Invalid input. For help type 'help' or 'h' to see the list of commands");
    }

    /*
     * Method to manage the enemy moves
     */
    private String enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom()) {
            try {
                    player.decreaseHealth(enemy.DAMAGE);
            } catch (IllegalStateException e) {
                isGameOn = false;
                return "Game Over!";
            }
            return "The enemy attacked you, now you have " + player.getHealth() + " health points left";
        }
        else {
            enemy.move(map.getRoom(enemy.getCurrentRoom()));
            return "The enemy moved to another room (for testing purposes: " + enemy.getCurrentRoom() + ")";
        }
    }

    /*
     * Method to generate the actions of a move
     */
    public String nextMove(String input) {
        if (!isGameOn) {
            return "Game is Over!";
        }
        if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("h")) {
            return HELP;
        }
        if (isGamePaused && !input.equalsIgnoreCase("resume")) {
            return "Game is paused. Type 'resume' to continue";
        }
        if (input.equalsIgnoreCase("pause")) {
            return pauseGame();
        }
        if (isGamePaused) {
            if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                isGameOn = false;
                return "Game over";
            }
            if (input.equalsIgnoreCase("save")) {
                return "Game saved";
            }
            if (input.equalsIgnoreCase("resume")) {
                return resumeGame();
            }
        }
        if (input.equalsIgnoreCase("undo") || input.equalsIgnoreCase("back")) {
            if (undo()) {
                return "Undo successful! Now the player is in room " + player.getCurrentRoom() + " and the enemy is in room " + enemy.getCurrentRoom();
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
        if(isGameOver())
            return "Game Over! The enemy killed you!";
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

    public String pauseGame() {
        isGamePaused = true;
        return "Game paused";
    }

    public String resumeGame() {
        isGamePaused = false;
        return "Game resumed";
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public boolean isGameOn() {
        return isGameOn;
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
            this.player = player.clone();
            this.enemy = enemy.clone();
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
        mementos.push(new GameMemento(player, enemy, map, isGameOn));
    }

    /*
     * Method to undo the last move
     * 
     * @return true if undo is successful, false otherwise
     */
    public boolean undo() {
        if (mementos.size() > 0) {
            //i remove the current state from the stack and i return the previous one
            mementos.pop();
            GameMemento memento = mementos.peek();
            player = memento.getPlayer().clone();
            enemy = memento.getEnemy().clone();
            map = memento.getMap();
            isGameOn = memento.getisGameOn();
            return true;
        }
        return false;
    }
}
