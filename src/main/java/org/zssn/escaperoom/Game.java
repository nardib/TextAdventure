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
     * Number of player turns before the enemy moves
     */
    private final int enemycount;

    /**
     * Tells if the enemey attacks the player
     */
    private final boolean enemyAttacks;

    /**
     * Counter for the number of player turns before the enemy moves
     */
    private int count = 0;

    /**
     * Object to save the mementos
     */
    private GameCaretaker caretaker;

    /**
     * The help message
     */
    public final String HELP = "The commands are:\n"
            + "· <direction> : allows face a specified direction given the cardinal points; it can be either \"north\" or \"n\"\n"
            + "· cross <direction> : allows to cross in a specified direction; if <direction> argument is omitted, then the player crosses the current facing direction\n"
            + "· look : returns a list of the items in the current room\n"
            + "· inventory : shows the items in the player's inventory\n"
            + "· take <item> : allows to pick an item in the facing wall given it's name as argument\n"
            + "· use <item> : allows to use an item in the inventory or in the facing wall\n"
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
     * @param count the number of player turns before the enemy moves (it should be a positive integer)
     * @param enemyAttacks tells if the enemy attacks the player
     * 
     * @throws IllegalArgumentException if either the gender of the player or the gender of the enemy is not given in a valid format or if the count is not a positive integer
     */
    public Game(String pn, String pg, String en, String eg, int count, boolean enemyAttacks) {
        player = new Player(capitalizeFistLetter(pn), pg);
        map = new Map();
        enemy = new Enemy(capitalizeFistLetter(en), eg);
        isGameOn = true;
        if (count <= 0)
            throw new IllegalArgumentException("The count must be a positive integer");
        this.enemycount = count;
        caretaker = new GameCaretaker();
        saveCurrentState();
        this.enemyAttacks = enemyAttacks;
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

        //if the player is hidden i have to unhide him or wait for the enemy to go in another room
        if (player.isHidden()) {
            if (input.equals("unhide")) {
                player.setHidden();
                return player.getName() + " is no longer hidden";
            }
            else if (input.equals("wait")) {
                return player.getName() + " is still hidden";
            }
            throw new IllegalArgumentException(player.getName() + " is hidden, " + player.getPronoun() + " can only \"wait\" or \"unhide\".");
        }

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
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
                }
                else if(player.getCurrentDirection() == Direction.SOUTH && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
                }
                else if(player.getCurrentDirection() == Direction.EAST && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
                }
                else if(player.getCurrentDirection() == Direction.WEST && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
                }
            }
            else if ((input.substring(6).equalsIgnoreCase("north") || input.substring(6).equalsIgnoreCase("n")) && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                player.changeDirection(Direction.NORTH);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
            }
            else if ((input.substring(6).equalsIgnoreCase("south") || input.substring(6).equalsIgnoreCase("s")) && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                player.changeDirection(Direction.SOUTH);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
            }
            else if ((input.substring(6).equalsIgnoreCase("east") || input.substring(6).equalsIgnoreCase("e")) && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                player.changeDirection(Direction.EAST);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
            }
            else if ((input.substring(6).equalsIgnoreCase("west") || input.substring(6).equalsIgnoreCase("w")) && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                player.changeDirection(Direction.WEST);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName();
            }
            throw new IllegalArgumentException(player.getName() + " can't cross in that direction");
        }
        
        //command to take an item in the room
        else if (input.length() > 5 && input.substring(0, 4).equalsIgnoreCase("take")) {
            try {
                Item[] items = getItemsInWall();
                
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getName().equalsIgnoreCase(input.substring(5))) {
                        if (!items[i].PICKABLE)
                            return player.getName() + " can't take " + items[i].getName().toLowerCase() + ", it's not pickable";
                        if (player.getWeight() + items[i].WEIGHT > Player.MAX_WEIGHT)
                            return player.getName() + " can't take " + items[i].getName().toLowerCase() + ", it's too heavy";
                        items[i].setRoom(0);
                        player.insertItem(items[i]);
                        switch (player.getCurrentDirection()) {
                            case NORTH:
                                map.getRoom(player.getCurrentRoom()).getNWall().removeItem(i);
                                break;
                            case EAST:
                                map.getRoom(player.getCurrentRoom()).getEWall().removeItem(i);
                                break;
                            case SOUTH:
                                map.getRoom(player.getCurrentRoom()).getSWall().removeItem(i);
                                break;
                            case WEST:
                                map.getRoom(player.getCurrentRoom()).getWWall().removeItem(i);
                                break;
                        }
                        return player.getName() + " took the item: " + items[i].getName().toLowerCase();
                    }
                }
            
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        }

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
                        else if (item instanceof HealingItem) {
                            if (player.getHealth() == 5)
                                return player.getName() + " already has full health";
                            HealingItem healingItem = (HealingItem) item;
                            player.increaseHealth(healingItem.HEALING_POINTS);
                            player.removeItem(i);
                            return healingItem.getUsingMessage();
                        }
                    }
                }
            }
            if (player.getNotesCount() != 0) {
                for (int i = 0; i < player.getNotesCount(); i++) {
                        if (player.getNoteAt(i).getName().equalsIgnoreCase(input.substring(4))) {
                            return player.getNoteAt(i).getUsingMessage();
                        }
                    }
            }

            //if there is no item in the inventory i have to check if there is an hiding item in the room
            Item[] items = null;            
            try {
                items = getItemsInWall();
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }

            for (int i = 0; i < items.length; i++) {
                if (items[i].getName().equalsIgnoreCase(input.substring(4, 4 + items[i].getName().length()))) {
                    Item item = items[i];
                    if (item instanceof Key || item instanceof Note || item instanceof HealingItem)
                        return player.getName() + " must pick the " + item.getName().toLowerCase() + " before using it!";
                    else if (item instanceof ClueItem) {
                        ClueItem clueItem = (ClueItem) item;
                        return clueItem.getUsingMessage();
                    }
                    else if (item instanceof HiderItem) {
                        HiderItem hiderItem = (HiderItem) item;
                        Item hiddenItem = hiderItem.reveal();
                        //i change the item in the wall with the hidden one
                        items[i] = hiddenItem;
                        switch (player.getCurrentDirection()) {
                            case NORTH:
                                map.getRoom(player.getCurrentRoom()).getNWall().setItems(items);
                            case EAST:
                                map.getRoom(player.getCurrentRoom()).getEWall().setItems(items);
                            case SOUTH:
                                map.getRoom(player.getCurrentRoom()).getSWall().setItems(items);
                            case WEST:
                                map.getRoom(player.getCurrentRoom()).getWWall().setItems(items);
                        }
                        return hiderItem.getUsingMessage();
                    }
                    else if (item instanceof HidingItem) {
                        HidingItem hidingItem = (HidingItem) item;
                        player.setHidden();
                        return hidingItem.getUsingMessage();
                    }
                    else if (item instanceof ItemContainer) {
                        ItemContainer itemContainer = (ItemContainer) item;
                        if (itemContainer.isLocked()){
                            String newInput = "";
                            try {
                                newInput = input.substring(5 + itemContainer.getName().length());
                            } catch (StringIndexOutOfBoundsException e) {}
                            if (itemContainer.getLockType() == LockType.KEY && newInput.length() == 0) {
                                if (player.getInventoryCount() == 0)
                                    return "The " + itemContainer.getName() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + "' with the correct key in the inventory";
                                for (int j = 0; j < player.getInventoryCount(); j++) {
                                    if (player.getItem(j) instanceof Key) {
                                        Key key = (Key) player.getItem(j);
                                        if (key.ID == itemContainer.getID()) {
                                            itemContainer.unlock(key);
                                            player.removeItem(i);
                                            return player.getName() + " unlocked the " + itemContainer.getName() + " with the " + key.getName().toLowerCase();
                                        }
                                    }
                                }
                                return "The " + itemContainer.getName() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + "' with the correct key in the inventry";
                            }
                            else if (itemContainer.getLockType() == LockType.COMBINATION) {
                                if (newInput.length() == 0)
                                    return "The " + itemContainer.getName().toLowerCase() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + " <id>' where <id> is the correct combination";
                                else {
                                    if (Integer.parseInt(newInput) == itemContainer.getID()) {
                                        itemContainer.unlock(Integer.parseInt(newInput));
                                        return player.getName() + " unlocked the " + itemContainer.getName().toLowerCase() + " with the combination " + newInput;
                                    }
                                    return "The combination is not correct, try again!";
                                }
                            }
                        }
                        String newInput = "";
                        try {
                            newInput = input.substring(5 + itemContainer.getName().length());
                        } catch (StringIndexOutOfBoundsException e) {}
                        if (newInput.length() == 0)
                            return itemContainer.getUsingMessage();
                        else {
                            for (int j = 0; j < itemContainer.getItemsLength(); j++) {
                                if (itemContainer.getItem(j).getName().equalsIgnoreCase(newInput)) {
                                    Item removedItem = itemContainer.removeItem(j);
                                    player.insertItem(removedItem);
                                    return player.getName() + " took the item: " + removedItem.getName().toLowerCase();
                                }
                            }
                            return "The item is not in the " + itemContainer.getName().toLowerCase();
                        }
                    }
                }
            }
            return player.getName() + " can't use this item";
        }
        
        throw new IllegalArgumentException("Invalid input. For help type 'help' or 'h' to see the list of commands");
    }

    /**
     * Method to manage the enemy turn
     * 
     * @return the output of the enemy's turn in a String format
     */
    private String enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom() && !player.isHidden() && enemyAttacks){
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
            return "\n" + player.getName() +" is in the " + map.getRoom(player.getCurrentRoom()).getName().toLowerCase() + " facing " + player.getCurrentDirection() + " direction, and " + player.getPronoun() + " has " + player.getHealth() + " health points\n"
                    + capitalizeFistLetter(player.getPronoun()) + " has the following items in the invenotory: " + player.printInventory() + "\n"
                    + "The total weight of the items " + player.getName() + " is " + player.getWeight() + "/10\n"
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
        //command to check the items in the inventory (probably should be in nextMove)
        if (input.equalsIgnoreCase("inventory")) {
            return "\nIn " + player.getName() + "'s inventory there are the following items: " + player.printInventory();
        }
        //command to check the items in the room
        if (input.equalsIgnoreCase("look")) {
            try {
                Item[] items = getItemsInWall();
                if (items.length == 0)
                    return "\nThere are no items in this room";

                String out = "\nIn this room there are the following items:\n";
                for (int i = 0; i < items.length; i++)
                    out += "·" + items[i].getName().toLowerCase() + "\n";
                return out.substring(0, out.length() - 1);
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        }

        String out = "\n";

        // player turn
        try {
            out += playerTurn(input);
        } catch (IllegalArgumentException e) {
            return "\n" + e.getMessage();
        }

        // enemy turn
        if (count % enemycount == 0)
            out +="\n" + enemyTurn();
        count++;
        
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
         * The wall the player is facing
         */
        private final Wall wall;

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
            this.wall = map.getWall(player.getCurrentRoom(), player.getCurrentDirection()).clone();
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
         * Method to get the wall
         * 
         * @return the wall of the current memento
         */
        public Wall getWall() {
            return wall;
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
        caretaker.addMemento(new GameMemento(player, enemy, map, isGameOn));
    }

    /**
     * Method to undo the last move, i.e. go back to the previous state of the game
     * 
     * @return true if undo is successful, false otherwise
     */
    public boolean undo() {
        if (caretaker.size() > 0) {
            //i remove the current state from the stack and i return the previous one;
            GameMemento memento = caretaker.getMemento();
            player = memento.getPlayer().clone();
            enemy = memento.getEnemy().clone();
            map.setWall(player.getCurrentRoom(), player.getCurrentDirection(), memento.getWall().clone());
            isGameOn = memento.getisGameOn();
            return true;
        }
        return false;
    }

    /**
     * Class to save the mementos of the game
     */
    private class GameCaretaker {
        /**
         * Stack to save the mementos
         */
        private Stack<GameMemento> mementos;

        /**
         * Constructor
         */
        public GameCaretaker() {
            mementos = new Stack<>();
        }

        /**
         * Add a memento to the stack
         * 
         * @param memento the memento to add
         */
        public void addMemento(GameMemento memento) {
            mementos.push(memento);
        }

        /**
         * Get the last memento from the stack
         * 
         * @return the last memento
         
        */
        public GameMemento getMemento() {
            if (!mementos.isEmpty()){
                mementos.pop();
                return mementos.peek();
            }
            return null;
        }

        /**
         * Get the size of the stack
         * 
         * @return the size of the stack
         */
        public int size() {
            return mementos.size()-1;
        }  
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

    /**
     * Method to get the array of items in the wall the player is facing
     * 
     * @return the array of items in the wall the player is facing
     * @throws IllegalAccessException if there are no items in the wall
     * @throws IllegalStateException if the player is not facing any wall
     */
    public Item[] getItemsInWall() throws IllegalAccessException, IllegalStateException{
        Item[] items = new Item[0];
        switch (player.getCurrentDirection()) {
            case NORTH:
                if (!map.getRoom(player.getCurrentRoom()).getNWall().hasItems())
                    throw new IllegalAccessException("There are no items in this wall");
                items = new Item[map.getRoom(player.getCurrentRoom()).getNWall().getItemsLength()];
                for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getNWall().getItemsLength(); i++)
                    items[i] = map.getRoom(player.getCurrentRoom()).getNWall().getItem(i);
                return items;
            case EAST:
                if (!map.getRoom(player.getCurrentRoom()).getEWall().hasItems())
                    throw new IllegalAccessException("There are no items in this wall");
                items = new Item[map.getRoom(player.getCurrentRoom()).getEWall().getItemsLength()];
                for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getEWall().getItemsLength(); i++)
                    items[i] = map.getRoom(player.getCurrentRoom()).getEWall().getItem(i);
                    return items;
            case SOUTH:
                if (!map.getRoom(player.getCurrentRoom()).getSWall().hasItems())
                    throw new IllegalAccessException("There are no items in this wall");
                items = new Item[map.getRoom(player.getCurrentRoom()).getSWall().getItemsLength()];
                for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getSWall().getItemsLength(); i++)
                    items[i] = map.getRoom(player.getCurrentRoom()).getSWall().getItem(i);
                return items;
            case WEST:
                if (!map.getRoom(player.getCurrentRoom()).getWWall().hasItems())
                    throw new IllegalAccessException("There are no items in this wall");
                items = new Item[map.getRoom(player.getCurrentRoom()).getWWall().getItemsLength()];
                for (int i = 0; i < map.getRoom(player.getCurrentRoom()).getWWall().getItemsLength(); i++)
                    items[i] = map.getRoom(player.getCurrentRoom()).getWWall().getItem(i);
                return items;
            default:
                throw new IllegalStateException("You are not facing any wall");
        }
    }
}
