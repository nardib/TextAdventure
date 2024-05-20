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
    public void playerTurn(String input) {
        if (input.equalsIgnoreCase("north")){
            player.changeDirection(Direction.NORTH);
        }
        else if (input.equalsIgnoreCase("south")){
            player.changeDirection(Direction.SOUTH);
        }
        else if (input.equalsIgnoreCase("east")){
            player.changeDirection(Direction.EAST);
        }
        else if (input.equalsIgnoreCase("west")){
            player.changeDirection(Direction.WEST);
        }
     }

    /*
     * Method to manage the enemy moves
     */
    public void enemyTurn() {
        if (enemy.getCurrentRoom() == player.getCurrentRoom()) {
            player.decreaseHealth();
        }
        else {
            enemy.move(map.getRoom(1));
        }
    }

    /*
     * Method to generate the actions of a move
     */
    public void nextTurn(String input) {
            // player turn
            playerTurn(input);
            // enemy turn
            enemyTurn();
    }
}
