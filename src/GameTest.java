import org.junit.*;

public class GameTest {

    //i verify that all the commands to change the direction are correct
    @Test
    public void playerDirectionChange() {
        var g = new Game("Player", "f", "Enemy", "m");
        g.nextMove("north");
        Assert.assertEquals(Direction.NORTH, g.getPlayer().getCurrentDirection());
        g.nextMove("south");
        Assert.assertEquals(Direction.SOUTH, g.getPlayer().getCurrentDirection());
        g.nextMove("east");
        Assert.assertEquals(Direction.EAST, g.getPlayer().getCurrentDirection());
        g.nextMove("west");
        Assert.assertEquals(Direction.WEST, g.getPlayer().getCurrentDirection());
    }

    //i verify that the player moves in the rooms with a specified command
    @Test
    public void playerMove() {
        var g = new Game("Player", "f", "Enemy", "m");
        g.nextMove("cross south");
        Assert.assertEquals(3, g.getPlayer().getCurrentRoom());
        g.nextMove("cross east");
        Assert.assertEquals(4, g.getPlayer().getCurrentRoom());
        g.nextMove("cross north");
        Assert.assertEquals(1, g.getPlayer().getCurrentRoom());
        g.nextMove("cross west");
        Assert.assertEquals(0, g.getPlayer().getCurrentRoom());
    }

    //i verify that the game returns to a previous state after calling the undo method
    @Test
    public void undoTest() {
        var g = new Game("Player", "f", "Enemy", "m");
        var p = g.getPlayer();
        var e = g.getEnemy();
        var m = g.getMap();
        var b = g.isGameOver();

        //i do some random moves to change the state of the game
        g.nextMove("south");
        g.nextMove("cross east");
        g.nextMove("look");

        //i go back three states and i check if the state is the same as the initial one
        g.nextMove("undo");
        g.nextMove("undo");
        g.nextMove("undo");

        Assert.assertEquals(p, g.getPlayer());
        Assert.assertEquals(e, g.getEnemy());
        Assert.assertEquals(m, g.getMap());
        Assert.assertEquals(b, g.isGameOver());
    }
}