package org.zssn.escaperoom;

import java.util.Stack;

/**
 * Game class. This class represents the game. The game is composed by a player, an enemy and a map. The player can move in the map and interact with the items in the rooms. The enemy moves randomly in the map and can attack the player. The game can be saved and the player can undo the last move.
 */
public class Game {
    /**
     * Player in the game
     */
    private Player player;

    /**
     * Map of the game
     */
    private Map map;

    /**
     * Enemy in the game
     */
    private Enemy enemy;

    /**
     * Tells if the game is on or not
     */
    private boolean isGameOn;

    /**
     * Stack of mementos to implement the undo function
     */
    private Stack<GameMemento> mementos = new Stack<>();

    /**
     * The help message
     */
    public final String HELP = "The commands are:\n"
            + "· <direction> : allows face a specified direction given the cardinal points; it can be either \"north\" or \"n\"\n"
            + "· cross <direction> : allows to cross in a specified direction; if <direction> argument is omitted, then the player crosses the current facing direction\n"
            + "· look : returns a list of the items in the current room\n"
            + "· inventory : shows the items in the player's inventory\n"
            + "· take <item> : allows to pick an item in the current room given it's name as argument\n"
            + "· use <item> : allows to use an item in the inventory or an hiding item if there is one in the room\n"
            + "· throw <item> : allows to throw an item in the current room\n"
            + "· status : give the status of the player, in particular it returns the health of the player and the items in the inventory\n"
            + "· undo/back : goes back of a move in the game\n"
            + "· save : save the current state of the game\n"
            + "· quit/exit : exit the game without saving the changes\n";

    /**
     * Constructor to initialize the game variables. It automatically capitalizes the first letter of the names given as argument.
     * 
     * @param pn name of the player
     * @param pg the gender of the player given as a string ("m","f","n" or "male", "female", "neutral" are the valid inputs)
     * @param en name of the enemy
     * @param eg the gender of the enemy given as a string ("m","f","n" or "male", "female", "neutral" are the valid inputs)
     * 
     * @throws IllegalArgumentException if either the gender of the player or the gender of the enemy is not given in a valid format
     */
    public Game(String pn, String pg, String en, String eg) {
        player = new Player(capitalizeFistLetter(pn), pg);
        map = new Map();
        enemy = new Enemy(capitalizeFistLetter(en), eg);
        isGameOn = true;
        mementos.push(new GameMemento(player, enemy, map, isGameOn));
    }

    /**
     * Method to manage the turn of the player
     * 
     * @param input the input of the player. The accepted input are the player commands.
     * @return the output of the player's turn in a String format
     * 
     * @throws IllegalArgumentException if the input is not valid in different ways
     */
    private String playerTurn (String input) {
        //commands to change the wall the player is facing
        if (input.equalsIgnoreCase("north") || input.equalsIgnoreCase("n")){
            player.changeDirection(Direction.NORTH);
            return player.getName() + " is now facing north";
        }
        else if (input.equalsIgnoreCase("south") || input.equalsIgnoreCase("s")){
            player.changeDirection(Direction.SOUTH);
            return player.getName() + " is now facing south";
        }
        else if (input.equalsIgnoreCase("east") || input.equalsIgnoreCase("e")){
            player.changeDirection(Direction.EAST);
            return player.getName() + " is now facing east";
        }
        else if (input.equalsIgnoreCase("west") || input.equalsIgnoreCase("w")){
            player.changeDirection(Direction.WEST);
            return player.getName() + " is now facing west";
        }
        
        //commands to move the player
        else if(input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("cross"))
        {
            if(input.length() == 5){
                if(player.getCurrentDirection() == Direction.NORTH && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                    return player.getName() + " moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.SOUTH && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                    return player.getName() + " moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.EAST && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                    return "You moved to the room: " + player.getCurrentRoom();
                }
                else if(player.getCurrentDirection() == Direction.WEST && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                    return player.getName() + " moved to the room: " + player.getCurrentRoom();
                }
            }
            else if ((input.substring(6).equalsIgnoreCase("north") || input.substring(6).equalsIgnoreCase("n")) && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                player.changeDirection(Direction.NORTH);
                return player.getName() + " moved to the room: " + player.getCurrentRoom();
            }
            else if ((input.substring(6).equalsIgnoreCase("south") || input.substring(6).equalsIgnoreCase("s")) && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                player.changeDirection(Direction.SOUTH);
                return player.getName() + " moved to the room: " + player.getCurrentRoom();
            }
            else if ((input.substring(6).equalsIgnoreCase("east") || input.substring(6).equalsIgnoreCase("e")) && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                player.changeDirection(Direction.EAST);
                return player.getName() + " moved to the room: " + player.getCurrentRoom();
            }
            else if ((input.substring(6).equalsIgnoreCase("west") || input.substring(6).equalsIgnoreCase("w")) && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                player.changeDirection(Direction.WEST);
                return player.getName() + " moved to the room: " + player.getCurrentRoom();
            }
            throw new IllegalArgumentException(player.getName() + " can't cross in that direction");
        }
        
        //command to check the items in the room
        else if (input.equalsIgnoreCase("look"))
        {
            Item[] items = new Item[0];
            switch (player.getCurrentDirection()) {
                case NORTH:
                    if (!map.getRoom(player.getCurrentRoom()).getNWall().hasItems())
                        return "There are no items in this wall";
                    items = new Item[map.getRoom(player.getCurrentRoom()).getNWall().getItemsLength()];
                    for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getNWall().getItemsLength(); i++)
                        items[i] = map.getRoom(player.getCurrentRoom()).getNWall().getItem(i);
                    break;
                case EAST:
                    if (!map.getRoom(player.getCurrentRoom()).getEWall().hasItems())
                        return "There are no items in this wall";
                    items = new Item[map.getRoom(player.getCurrentRoom()).getEWall().getItemsLength()];
                    for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getEWall().getItemsLength(); i++)
                        items[i] = map.getRoom(player.getCurrentRoom()).getEWall().getItem(i);
                    break;
                case SOUTH:
                    if (!map.getRoom(player.getCurrentRoom()).getSWall().hasItems())
                        return "There are no items in this wall";
                    items = new Item[map.getRoom(player.getCurrentRoom()).getSWall().getItemsLength()];
                    for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getSWall().getItemsLength(); i++)
                        items[i] = map.getRoom(player.getCurrentRoom()).getSWall().getItem(i);
                    break;
                case WEST:
                    if (!map.getRoom(player.getCurrentRoom()).getWWall().hasItems())
                        return "There are no items in this wall";
                    items = new Item[map.getRoom(player.getCurrentRoom()).getWWall().getItemsLength()];
                    for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getWWall().getItemsLength(); i++)
                        items[i] = map.getRoom(player.getCurrentRoom()).getWWall().getItem(i);
            }
            
            String out = "In this room there are the following items: ";
            for (int i = 0; i < items.length; i++)
                out += items[i].getName() + ", ";
            return out.substring(0, out.length() - 2);
        }
        
        /*
        //command to take an item in the room
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
        }*/
        

        //command to use an item in the inventory
        else if(input.length() > 3 && input.substring(0, 3).equalsIgnoreCase("use")) {
            if (player.getInventoryCount() != 0) {
                for (int i = 0; i < player.getInventoryCount(); i++) {
                    if (player.getItem(i).getName().equalsIgnoreCase(input.substring(4))) {
                        Item item = player.getItem(i);
                        if (item instanceof Key) {
                            Key key = (Key) item;
                            //i have to check if there is a lock in the room
                            return key.getUsingMessage();
                        }
                        else if (item instanceof Note) {
                            Note note = (Note) item;
                            return note.getUsingMessage();
                        }
                        else if (item instanceof Hammer) {
                            Hammer hammer = (Hammer) item;
                            return hammer.getUsingMessage();
                        }
                        else if (item instanceof HealingItem) {
                            if (player.getHealth() == 5)
                                return player.getName() + " already have full health";
                            HealingItem healingItem = (HealingItem) item;
                            player.increaseHealth(healingItem.HEALING_POINTS);
                            player.removeItem(i);
                            return healingItem.getUsingMessage();
                        }
                    }
                }
            }
            //i also must check if there is an hiding item in the room
            return player.getName() + " can't use this item";
        }

        //command to throw an item in the invenotory
        if (input.length() > 5 && input.substring(0, 5).equalsIgnoreCase("throw")) {
            //items number goes from 1 to max
            try {
                player.removeItem(Integer.parseInt(input.substring(6)) - 1);
            } catch (Exception e) { /* do nothing */}

            if (player.getInventoryCount() != 0) {
                for (int i = 0; i < player.getInventoryCount(); i++) {
                    if (player.getItem(i).getName().equalsIgnoreCase(input.substring(6))) {
                        Item item = player.removeItem(i);
                        //map.getRoom(player.getCurrentRoom()).addItem(item);
                        return player.getName() + " threw the " + item.getName() + " in the room";
                    }
                }
            }
            throw new IllegalArgumentException(player.getName() + " can't throw this item");
        }

        //command to check the items in the inventory (probably should be in nextMove)
        else if (input.equalsIgnoreCase("inventory")) {
            return "In " + player.getName() + "'s inventory there are the following items: " + player.printInventory();
        }
        
        throw new IllegalArgumentException("Invalid input. For help type 'help' or 'h' to see the list of commands");
    }

    /**
     * Method to manage the enemy turn
     * 
     * @return the output of the enemy's turn in a String format
     */
    private String enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom() && !player.isHidden()){
            player.decreaseHealth(enemy.DAMAGE);
            if (player.getHealth() == 0){
                isGameOn = false;
                return "Game Over! " + enemy.getName() + " killed " + player.getName() + "!";
            }
            return enemy.getName() + " attacked " + player.getName() + ", now " + player.getPronoun() + " has " + player.getHealth() + " health points left";
        }
        else {
            enemy.move(map.getRoom(enemy.getCurrentRoom()));
            return enemy.getName() + " moved to another room (for testing purposes: " + enemy.getCurrentRoom() + ")";
        }
    }

    /**
     * Method to generate the actions of a move given a specific input by the user
     * 
     * @param input the input of the player
     * @return the output of the move in a String format
     */
    public String nextMove(String input) {

        input = input.trim(); //remove leading and trailing whitespaces

        if (!isGameOn)
            return "\nGame is Over!";

        if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("h"))
            return HELP;

        if (input.equalsIgnoreCase("status"))
            return "\n" + player.getName() +" is in room " + player.getCurrentRoom() + " and " + player.getPronoun() + " has " + player.getHealth() + " health points\n"
                    + capitalizeFistLetter(player.getPronoun()) + " has the following items in the invenotory: " + player.printInventory() + "\n"
                    + enemy.getName() + " is in room " + enemy.getCurrentRoom(); //this message should be removed
        
        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
            isGameOn = false;
            return "\nGame over";
       }
        if (input.equalsIgnoreCase("save"))
            return "\nGame saved";

        if (input.equalsIgnoreCase("undo") || input.equalsIgnoreCase("back")) {

            if (undo()) 
                return "\nUndo successful! Now " + player.getName() + " is in room " + player.getCurrentRoom() + " and " + enemy.getName() +" is in room " + enemy.getCurrentRoom();   //i should delete the enemy room
            
            return "\nNo previous moves to undo";
        }

        String out = "\n";

        // player turn
        try {
            out += playerTurn(input);
        } catch (IllegalArgumentException e) {
            return "\n" + e.getMessage();
        }

        // enemy turn
        out +="\n" + enemyTurn();
        if(isGameOver())
            return "\nGame Over! " + enemy.getName() + " killed you!";
        
        //i save the state of the game after each move
        saveCurrentState();
        return out;
    }

    /**
     * Method to check if the game is over
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        if (player.getHealth() == 0)
            isGameOn = false;

        //i should probably add a win condition here
        return !isGameOn;
    }

    /**
     * Method to check if the game is on
     * 
     * @return true if the game is on, false otherwise
     */
    public boolean isGameOn() {
        return isGameOn;
    }

    /**
     * Method to get the player
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method to get the enemy
     * 
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Method to get the map
     * 
     * @return the map
     */
    public Map getMap() {
        return map;
    }
    /**
     * Class for memento pattern to implement "undo" function
     */
    private class GameMemento {

        /**
         * Player in the game
         */
        private final Player player;

        /**
         * Enemy in the game
         */
        private final Enemy enemy;

        /**
         * Map of the game
         */
        private final Map map;

        /**
         * Tells if the game is on or not
         */
        private final boolean isGameOn;

        /**
         * Constructor to initialize the memento
         * 
         * @param player the player
         * @param enemy the enemy
         * @param map the map
         * @param isGameOn tells if the game is on or not
         */
        public GameMemento(Player player, Enemy enemy, Map map, boolean isGameOn) {
            this.player = player.clone();
            this.enemy = enemy.clone();
            this.map = map;
            this.isGameOn = isGameOn;
        }

        /**
         * Method to get the player
         * 
         * @return the player of the current memento
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * Method to get the enemy
         * 
         * @return the enemy of the current memento
         */
        public Enemy getEnemy() {
            return enemy;
        }

        /**
         * Method to get the map
         * 
         * @return the map of the current memento
         */
        public Map getMap() {
            return map;
        }

        /**
         * Method to get if the game is on
         * 
         * @return true if the game is on, false otherwise (in the current memento)
         */
        public boolean getisGameOn() {
            return isGameOn;
        }
    }

    /**
     * Method to save the current state of the game and add it to the stack of mementos
     */
    private void saveCurrentState() {
        mementos.push(new GameMemento(player, enemy, map, isGameOn));
    }

    /**
     * Method to undo the last move, i.e. go back to the previous state of the game
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

    /**
     * Method to capitalize the first letter of a string
     * 
     * @param s the string to capitalize
     * @return the string with the first letter capitalized
     */
    private String capitalizeFistLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
