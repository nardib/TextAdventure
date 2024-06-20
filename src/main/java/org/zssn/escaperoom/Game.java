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
        else if (input.equalsIgnoreCase("look")) {
            try {
                Item[] items = getItemsInWall();
                
                String out = "In this room there are the following items: ";
                for (int i = 0; i < items.length; i++)
                    out += items[i].getName() + ", ";
                return out.substring(0, out.length() - 2);
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        }
        
        //command to take an item in the room
        else if (input.length() > 5 && input.substring(0, 4).equalsIgnoreCase("take")) {
            try {
                Item[] items = getItemsInWall();
                
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getName().equalsIgnoreCase(input.substring(5))) {
                        if (!items[i].PICKABLE)
                            return player.getName() + " can't take " + items[i].getName();
                        if (player.getWeight() + items[i].WEIGHT > Player.MAX_WEIGHT)
                            return player.getName() + " can't take " + items[i].getName() + ", it's too heavy";
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
                        return player.getName() + " took the item: " + items[i].getName();
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
                        else if (item instanceof Note) {
                            Note note = (Note) item;
                            return note.getUsingMessage();
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
                        return player.getName() + " must pick the " + item.getName() + " before using it!";
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
                            if (itemContainer.getLockType() == LockType.NONE)
                                itemContainer.unlock(null);
                            else if (itemContainer.getLockType() == LockType.KEY) {
                                for (int j = 0; j < player.getInventoryCount(); j++) {
                                    if (player.getItem(j) instanceof Key) {
                                        Key key = (Key) player.getItem(j);
                                        if (key.ID == itemContainer.getID()) {
                                            itemContainer.unlock(key);
                                            player.removeItem(i);
                                            return "You unlocked the " + itemContainer.getName() + " with the key " + key.getName();
                                        }
                                    }
                                }
                                return "The container is locked, you must unlock it first!\nTo unlock type 'use " + itemContainer.getName() + "' with the correct key in your inventry";
                            }
                            else if (itemContainer.getLockType() == LockType.COMBINATION) {
                                String newInput = "";
                                try {
                                    newInput = input.substring(5 + itemContainer.getName().length());
                                } catch (StringIndexOutOfBoundsException e) {}
                                if (newInput.length() == 0)
                                    return "The container is locked, you must unlock it first!\nTo unlock type 'use " + itemContainer.getName() + " <id>' where <id> is the correct combination";
                                else {
                                    if (Integer.parseInt(newInput) == itemContainer.getID()) {
                                        itemContainer.unlock(Integer.parseInt(newInput));
                                        return "You unlocked the " + itemContainer.getName() + " with the combination " + newInput;
                                    }
                                    return "The combination is not correct";
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
                                    itemContainer.setUsingMessage();
                                    return player.getName() + " took the item: " + removedItem.getName();
                                }
                            }
                            return "The item is not in the container";
                        }
                    }
                }
            }
            return player.getName() + " can't use this item";
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
