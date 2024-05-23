public class Game {
    /*
     * State variables
     */
    private Player player;
    private Map map;
    private Enemy enemy;
    private boolean gameIsOn;

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
    private void playerTurn(String input) {
        if (input.equalsIgnoreCase("north")){
            player.changeDirection(Direction.NORTH);
            return;
        }
        else if (input.equalsIgnoreCase("south")){
            player.changeDirection(Direction.SOUTH);
            return;
        }
        else if (input.equalsIgnoreCase("east")){
            player.changeDirection(Direction.EAST);
            return;
        }
        else if (input.equalsIgnoreCase("west")){
            player.changeDirection(Direction.WEST);
            return;
        }
        else if(input.substring(0, 5).equalsIgnoreCase("cross"))
        {
            if (input.substring(6).equalsIgnoreCase("north") && map.getRoom(player.getCurrentRoom()).getCrossableNorth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_NORTH);
                return;
            }
            else if (input.substring(6).equalsIgnoreCase("south") && map.getRoom(player.getCurrentRoom()).getCrossableSouth()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_SOUTH);
                return;
            }
            else if (input.substring(6).equalsIgnoreCase("east") && map.getRoom(player.getCurrentRoom()).getCrossableEast()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_EAST);
                return;
            }
            else if (input.substring(6).equalsIgnoreCase("west") && map.getRoom(player.getCurrentRoom()).getCrossableWest()){
                player.setCurrentRoom(player.getCurrentRoom() + Player.CROSS_WEST);
                return;
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
            return;
        }
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
            // player turn
            playerTurn(input);
            // enemy turn
            enemyTurn();
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
}
