package org.zssn.escaperoom;

import java.util.Stack;

/**
 * Game class. This class represents the game. 
 * The game is composed by a player, an enemy and a map. 
 * The player can move in the map and interact with the items in the rooms. 
 * The enemy moves randomly in the map and can attack the player.
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
    private int enemycount;

    /**
     * Tells if the enemey attacks the player
     */
    private boolean enemyAttacks;

    /**
     * Counter for the number of player turns before the enemy moves
     */
    private int count = 0;

    /**
     * Object to save the mementos
     */
    private GameCaretaker caretaker;

    /**
     * Number of star holes filled
     */
    private int filledStarHoles;

    /**
     * Array of boolean to check all the star holes (true if filled, false otherwise)
     */
    private boolean[] starHoles;

    /**
     * The help message
     */
    public final static String HELP = "The commands are:\n"
            + "- <direction> : allows to face a specified direction given a cardinal points; it can be either \"north\" or \"n\"\n"
            + "- cross <direction> : allows to cross in a specified direction; if <direction> argument is omitted, then the player crosses the current facing direction\n"
            + "- look : returns a list of the items in the current room\n"
            + "- inventory : shows the items in the player's inventory\n"
            + "- take <item> : allows to pick an item in the facing wall given it's name as argument\n"
            + "- use <item> : allows to use an item in the inventory or in the facing wall\n"
            + "- status : gives the status of the player, in particular it returns the health of the player and the items in the inventory\n"
            + "- undo/back : goes back of a move in the game\n"
            + "- save : save the current state of the game\n"
            + "- exit/quit : closes the game";

    /** 
     * Default constructor for the game
     */
    public Game() {
        caretaker = new GameCaretaker();
    }

    /**
     * Constructor to initialize the game variables.
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
        player = new Player(pn, pg);
        map = new Map();
        enemy = new Enemy(en, eg);
        isGameOn = true;
        if (count <= 0)
            throw new IllegalArgumentException("The count must be a positive integer");
        this.enemycount = count;
        caretaker = new GameCaretaker();
        this.enemyAttacks = enemyAttacks;
        filledStarHoles = 0;
        starHoles = new boolean[10];
        for (int i = 0; i < 10; i++)
            starHoles[i] = false;
        saveCurrentState();
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
            if (input.equalsIgnoreCase("unhide")) {
                player.setHidden();
                return player.getName() + " is no longer hidden";
            }
            else if (input.equalsIgnoreCase("wait")) {
                return player.getName() + " is still hidden";
            }
            throw new IllegalArgumentException(player.getName() + " is hidden, " + player.returnPronoun() + " can only \"wait\" or \"unhide\".");
        }

        //commands to change the wall the player is facing
        if (input.equalsIgnoreCase("north") || input.equalsIgnoreCase("n")){
            player.setCurrentDirection(Direction.NORTH);
            return player.getName() + " is now facing north\n" + printLook();
        }
        else if (input.equalsIgnoreCase("south") || input.equalsIgnoreCase("s")){
            player.setCurrentDirection(Direction.SOUTH);
            return player.getName() + " is now facing south\n" + printLook();
        }
        else if (input.equalsIgnoreCase("east") || input.equalsIgnoreCase("e")){
            player.setCurrentDirection(Direction.EAST);
            return player.getName() + " is now facing east\n" + printLook();
        }
        else if (input.equalsIgnoreCase("west") || input.equalsIgnoreCase("w")){
            player.setCurrentDirection(Direction.WEST);
            return player.getName() + " is now facing west\n" + printLook();
        }
        
        //commands to move the player
        else if(input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("cross"))
        {
            if(input.length() == 5){
                if(player.getCurrentDirection() == Direction.NORTH && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
                }
                else if(player.getCurrentDirection() == Direction.SOUTH && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
                }
                else if(player.getCurrentDirection() == Direction.EAST && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
                }
                else if(player.getCurrentDirection() == Direction.WEST && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                    player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                    return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
                }
            }
            else if ((input.substring(6).equalsIgnoreCase("north") || input.substring(6).equalsIgnoreCase("n")) && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                player.setCurrentDirection(Direction.NORTH);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
            }
            else if ((input.substring(6).equalsIgnoreCase("south") || input.substring(6).equalsIgnoreCase("s")) && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                player.setCurrentDirection(Direction.SOUTH);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
            }
            else if ((input.substring(6).equalsIgnoreCase("east") || input.substring(6).equalsIgnoreCase("e")) && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                player.setCurrentDirection(Direction.EAST);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
            }
            else if ((input.substring(6).equalsIgnoreCase("west") || input.substring(6).equalsIgnoreCase("w")) && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                player.setCurrentDirection(Direction.WEST);
                return player.getName() + " moved to the " + map.getRoom(player.getCurrentRoom()).getName() + "\n" + printLook();
            }
            throw new IllegalArgumentException(player.getName() + " can't cross in that direction");
        }
        
        //command to take an item in the room
        else if (input.length() > 5 && input.substring(0, 4).equalsIgnoreCase("take")) {
            try {
                Item[] items = returnItemsInWall();
                
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getName().equalsIgnoreCase(input.substring(5))) {
                        if (!enemyAttacks && items[i] instanceof HealingItem)
                            return "The enemy doesn't attack, " + player.getName() + " doesn't need to use the " + items[i].getName().toLowerCase();
                        if (!items[i].isPickable())
                            return player.getName() + " can't take " + items[i].getName().toLowerCase() + ", it's not pickable";
                        if (player.getInventoryWeight() + items[i].getWeight() > Player.MAX_WEIGHT)
                            return player.getName() + " can't take " + items[i].getName().toLowerCase() + ", it's too heavy";
                        Item item = items[i].clone();
                        player.insertItem(item);
                        Wall w = map.getWall(player.getCurrentRoom(), player.getCurrentDirection());
                        w.removeItem(i);
                        map.setWall(player.getCurrentRoom(), player.getCurrentDirection(), w);
                        return player.getName() + " took the item: " + item.getName().toLowerCase();
                    }
                }
            
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
            return player.getName() + " can't take this item";
        }

        //command to use an item in the inventory
        else if(input.length() > 3 && input.substring(0, 3).equalsIgnoreCase("use")) {
            if (player.getInventoryCount() != 0) {
                for (int i = 0; i < player.getInventoryCount(); i++) {
                    if (player.getItem(i).getName().equalsIgnoreCase(input.substring(4))) {
                        Item item = player.getItem(i);
                        //i have to check the type of the item and do the right action
                        if (item instanceof Key) {
                            Key key = (Key) item;
                            return key.getUsingMessage();
                        }
                        else if (item instanceof HealingItem) {
                            if (player.getHealth() == 5)
                                return player.getName() + " already has full health";
                            HealingItem healingItem = (HealingItem) item;
                            player.increaseHealth(healingItem.getHealingPoints());
                            player.removeItem(i);
                            return healingItem.getUsingMessage();
                        }
                        else if (item instanceof Star) {
                            Star star = (Star) item;
                            return star.getUsingMessage();
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
                items = returnItemsInWall();
            } catch (IllegalAccessException e) {
                return e.getMessage();
            } catch (IllegalStateException e) {
                return e.getMessage();
            }

            for (int i = 0; i < items.length; i++) {
                String itemInput = "";
                try {
                    itemInput = input.substring(4, 4 + items[i].getName().length());
                } catch (StringIndexOutOfBoundsException e) {}
                Item item = items[i];

                if (items[i].getName().equalsIgnoreCase(input.substring(4))) {
                    //i have to check the type of the item and do the right action
                    if (item instanceof Key || item instanceof Note || item instanceof HealingItem || item instanceof Star)
                        return player.getName() + " must take the " + item.getName().toLowerCase() + " before using it!";
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
                                break;
                            case EAST:
                                map.getRoom(player.getCurrentRoom()).getEWall().setItems(items);
                                break;
                            case SOUTH:
                                map.getRoom(player.getCurrentRoom()).getSWall().setItems(items);
                                break;
                            case WEST:
                                map.getRoom(player.getCurrentRoom()).getWWall().setItems(items);
                                break;
                        }
                        return hiderItem.getUsingMessage();
                    }
                    else if (item instanceof HidingItem) {
                        if (!enemyAttacks)
                            return "The enemy doesn't attack, " + player.getName() + " doesn't need to hide.";
                        HidingItem hidingItem = (HidingItem) item;
                        player.setHidden();
                        return hidingItem.getUsingMessage();
                    }
                    else if (item instanceof StarHole) {
                        StarHole starHole = (StarHole) item;
                        if (starHole.isFilled())
                            return player.getName() + " can't fill the " + starHole.getName().toLowerCase() + ", it's already filled";
                        if (player.getInventoryCount() == 0)
                            return player.getName() + " must have a star in the inventory to fill the star hole";
                        for (int j = 0; j < player.getInventoryCount(); j++) {
                            if (player.getItem(j) instanceof Star) {
                                Star star = (Star) player.getItem(j);
                                if (star.getID() == starHole.getID()) {
                                    starHole.fill(star);
                                    player.removeItem(j);
                                    filledStarHoles++;
                                    starHoles[starHole.getID()-1] = true;
                                    return player.getName() + " filled the star hole " + starHole.getID() + " with the " + star.getName().toLowerCase();
                                }
                            }
                        }
                        return player.getName() + " must have a star with the same ID of the star hole in the inventory to fill the star hole";
                    }
                }

                if (items[i].getName().equalsIgnoreCase(itemInput)) {
                    if (item instanceof ItemContainer) {
                        ItemContainer itemContainer = (ItemContainer) item;

                        //if it is locked i have to unlock it
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
                                        if (key.getID() == itemContainer.getID()) {
                                            itemContainer.unlock(key);
                                            player.removeItem(j);
                                            return player.getName() + " unlocked the " + itemContainer.getName() + " with the " + key.getName().toLowerCase() + ".\n" + itemContainer.getUsingMessage();
                                        }
                                    }
                                }
                                return "The " + itemContainer.getName() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + "' with the correct key in the inventry";
                            }
                            else if (itemContainer.getLockType() == LockType.KEY)
                                return "The " + itemContainer.getName() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + "' with the correct key in the inventry";
                            else if (itemContainer.getLockType() == LockType.COMBINATION) {
                                if (newInput.length() == 0)
                                    return "The " + itemContainer.getName().toLowerCase() + " is locked, " + player.getName() + " must unlock it first!\nTo unlock type 'use " + itemContainer.getName().toLowerCase() + " <id>' where <id> is the correct combination";
                                else {
                                    try {
                                        Integer.parseInt(newInput);
                                    } catch (NumberFormatException e) {
                                        return "The combination must be a number";
                                    }
                                    if (Integer.parseInt(newInput) == itemContainer.getID()) {
                                        itemContainer.unlock(Integer.parseInt(newInput));
                                        return player.getName() + " unlocked the " + itemContainer.getName().toLowerCase() + " with the combination " + newInput + ".\n" + itemContainer.getUsingMessage();
                                    }
                                    return "The combination is not correct, try again!";
                                }
                            }
                        }
                        
                        //i can use the item container to take an item inside when it's unlocked
                        String newInput = "";
                        try {
                            newInput = input.substring(5 + itemContainer.getName().length());
                        } catch (StringIndexOutOfBoundsException e) {}

                        if (newInput.length() == 0)
                            return itemContainer.getUsingMessage();
                        else {
                            for (int j = 0; j < itemContainer.getItemsLength(); j++) {
                                if (itemContainer.getItem(j).getName().equalsIgnoreCase(newInput)) {
                                    if (itemContainer.getItem(j) instanceof HealingItem && !enemyAttacks)
                                        return "The enemy doesn't attack, " + player.getName() + " doesn't need to use the " + items[i].getName().toLowerCase();
                                    if (itemContainer.getItem(j).getWeight() + player.getInventoryWeight() > Player.MAX_WEIGHT)
                                        return player.getName() + " can't take the item, it's too heavy";
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
            return enemy.getName() + " attacked " + player.getName() + ", now " + player.returnPronoun() + " has " + player.getHealth() + " health points left";
        }
        else if (count % enemycount == 0){
            enemy.move(map.getRoom(enemy.getCurrentRoom()));
            if (enemy.getCurrentRoom() == player.getCurrentRoom())
                return enemy.getName() + " moved to the same room of " + player.getName() + " and is now in the " + map.getRoom(enemy.getCurrentRoom()).getName().toLowerCase();
            if (player.isHidden())
                return enemy.getName() + " is now in room " + map.getRoom(enemy.getCurrentRoom()).getName().toLowerCase();
            return enemy.getName() + " moved to another room";
        }
        if (player.isHidden())
            return enemy.getName() + " is now in room " + map.getRoom(enemy.getCurrentRoom()).getName().toLowerCase();
        return enemy.getName() + " didn't move";
    }

    /**
     * Method to generate the actions of a move given a specific input by the user
     * 
     * @param input the input of the player
     * @return the output of the move in a String format
     */
    public String nextMove(String input) {

        input = input.trim(); //remove leading and trailing whitespaces

        String out = "\n-------------------------- Input : " + input + " --------------------------\n\n";

        if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("h"))
            return out + HELP;

        if (input.equalsIgnoreCase("status"))
            return out + printStatus();
        
        if (input.equalsIgnoreCase("undo") || input.equalsIgnoreCase("back")) {
            if (undo()) {
                return out + "Undo successful!\n" + printStatus() + printLook();
            }
            return out + "No previous moves to undo";
        }
        //command to check the items in the inventory
        if (input.equalsIgnoreCase("inventory")) {
            return out + "In " + player.getName() + "'s inventory there are the following items: " + player.printInventory() + "\nThe total weight of the items in " + player.getName() + "'s inventory is  " + player.getInventoryWeight() + "/10\n";
        }
        //command to check the items in the room
        if (input.equalsIgnoreCase("look") && !player.isHidden()) {
            return out + printLook();
        }

        // player turn
        try {
            out += playerTurn(input);
        } catch (IllegalArgumentException e) {
            return out + e.getMessage();
        }

        // enemy turn
        if (enemyAttacks)
            out += "\n" + enemyTurn();
        count++;
        
        if(checkGameOver() && player.getHealth() == 0)
            return out;
        if(checkGameOver() && filledStarHoles == 10)
            return out + "\nYou win! You filled all the star holes!";
        
        //i save the state of the game after each move
        saveCurrentState();

        return out;
    }

    /**
     * Method to check if the game is over, i.e. if the player is dead or if all the star holes are filled
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean checkGameOver() {
        if (player.getHealth() == 0)
            isGameOn = false;

        if (filledStarHoles == 10)
            isGameOn = false;

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
     * Method to set the game on
     * 
     * @param isGameOn tells if the game is on or not
     */
    public void setGameOn(boolean isGameOn) {
        this.isGameOn = isGameOn;
    }

    /**
     * Method to get if the game is win
     * 
     * @return true if the game is win, false otherwise
     */
    public boolean checkWin() {
        return filledStarHoles == 10 && !isGameOn;
    }

    /**
     * Method to get if the game is lost
     * 
     * @return true if the game is lost, false otherwise
     */
    public boolean checkLost() {
        return player.getHealth() == 0 && !isGameOn;
    }

    /**
     * Method to get the player of the game
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method to set the player of the game
     * 
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method to get the enemy of the game
     * 
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Method to set the enemy of the game
     * 
     * @param enemy the enemy to set
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Method to get the map of the game
     * 
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Method to set the map of the game
     * 
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Method to get the number of player turns before the enemy moves
     * 
     * @return the number of player turns before the enemy moves
     */
    public int getEnemyCount() {
        return enemycount;
    }

    /**
     * Method to set the number of player turns before the enemy moves
     * 
     * @param enemycount the number of player turns before the enemy moves
     */
    public void setEnemyCount(int enemycount) {
        this.enemycount = enemycount;
    }

    /**
     * Method to get if the enemy attacks the player
     * 
     * @return true if the enemy attacks the player, false otherwise
     */
    public boolean getEnemyAttacks() {
        return enemyAttacks;
    }

    /**
     * Method to set if the enemy attacks the player
     * 
     * @param enemyAttacks tells if the enemy attacks the player
     */
    public void setEnemyAttacks(boolean enemyAttacks) {
        this.enemyAttacks = enemyAttacks;
    }

    /**
     * Counter for the number of player turns before the enemy moves
     * 
     * @return the counter for the number of player turns before the enemy moves
     */
    public int getCount() {
        return count;
    }

    /**
     * Method to set the counter for the number of player turns before the enemy moves
     * 
     * @param count the counter for the number of player turns before the enemy moves
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Method to get the number of star holes filled
     * 
     * @return the number of star holes filled
     */
    public int getFilledStarHoles() {
        return filledStarHoles;
    }

    /**
     * Method to set the number of star holes filled
     * 
     * @param filledStarHoles the number of star holes filled
     */
    public void setFilledStarHoles(int filledStarHoles) {
        this.filledStarHoles = filledStarHoles;
    }

    /**
     * Method to get the array of boolean to check all the star holes
     * 
     * @return the array of boolean to check all the star holes
     */
    public boolean[] getStarHoles() {
        return starHoles;
    }

    /**
     * Method to set the array of boolean to check all the star holes
     * 
     * @param starHoles the array of boolean to check all the star holes
     */
    public void setStarHoles(boolean[] starHoles) {
        this.starHoles = starHoles;
    }

    /**
     * Method to get the element i of the array of boolean to check all the star holes
     * 
     * @param i the index of the array
     * @return the element i of the array of boolean to check all the star holes
     * 
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public boolean getStarHole(int i) {
        if (i < 0 || i >= 10)
            throw new IllegalArgumentException("The index must be between 0 and 9");
        return starHoles[i];
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
        * Count of the move
        */
        private final int count;

        /**
         * Number of star holes filled
         */
        private final int filledStarHoles;

        /**
         * Array of boolean to check all the star holes
         */
        private final boolean[] starHoles;

        /**
         * Constructor to initialize the memento
         * 
         * @param player the player
         * @param enemy the enemy
         * @param map the map
         * @param isGameOn tells if the game is on or not
         * @param count tells the number of the move
         * @param filledStarHoles tells the number of star holes filled
         */
        public GameMemento(Player player, Enemy enemy, Map map, boolean isGameOn, int count, int filledStarHoles, boolean[] starHoles) {
            this.player = player.clone();
            this.enemy = enemy.clone();
            this.wall = map.getWall(player.getCurrentRoom(), player.getCurrentDirection()).clone();
            this.isGameOn = isGameOn;
            this.count = count;
            this.filledStarHoles = filledStarHoles;
            this.starHoles = new boolean[10];
            for (int i = 0; i < 10; i++)
                this.starHoles[i] = starHoles[i];
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

        /**
         * Method to get the count of the move
         * 
         * @return the count of the move
         */
        public int getCount() {
            return count;
        }

        /**
         * Method to get the number of star holes filled
         * 
         * @return the number of star holes filled
         */
        public int getFilledStarHoles() {
            return filledStarHoles;
        }

        /**
         * Method to get the array of boolean to check all the star holes
         * 
         * @return the array of boolean to check all the star holes
         */
        public boolean[] getStarHoles() {
            return starHoles;
        }
    }

    /**
     * Method to save the current state of the game and add it to the stack of mementos
     */
    public void saveCurrentState() {
        caretaker.addMemento(new GameMemento(player, enemy, map, isGameOn, count, filledStarHoles, starHoles));
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
            count = memento.getCount();
            filledStarHoles = memento.getFilledStarHoles();
            for (int i = 0; i < 10; i++)
                starHoles[i] = memento.getStarHoles()[i];
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
    public Item[] returnItemsInWall() throws IllegalAccessException, IllegalStateException{
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

    /**
     * Method to print the items in the wall the player is facing
     * 
     * @return the items in the wall the player is facing in a String format
     */
    private String printLook() {
        try {
            Item[] items = returnItemsInWall();
            if (items.length == 0)
                return "There are no items in this wall";

            String out = "In this wall there are the following items:\n";
            for (int i = 0; i < items.length; i++) {
                Item item = items[i];
                out += "-" + items[i].getName();
                if (item instanceof ItemContainer && !((ItemContainer) item).isLocked())
                    out += " (unlocked)";
                if (item instanceof StarHole && ((StarHole) item).isFilled())
                    out += " (filled)";
                out += "\n";
            }
            return out.substring(0, out.length() - 1);
        } catch (IllegalAccessException e) {
            return e.getMessage();
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    /**
     * Method to print the status of the player
     * 
     * @return the status of the player in a String format
     */
    private String printStatus() {
        return player.getName() +" is in the " + map.getRoom(player.getCurrentRoom()).getName().toLowerCase() + " facing " + player.getCurrentDirection() + " direction, and " + player.returnPronoun() + " has " + player.getHealth() + " health points.\n"
                + capitalizeFistLetter(player.returnPronoun()) + " has the following items in the inventory: " + player.printInventory()
                + "\nThe total weight of the items in " + player.getName() + "'s inventory is  " + player.getInventoryWeight() + "/10\n"
                + "The number of star holes filled is " + filledStarHoles + "/10\n";
    }
}
