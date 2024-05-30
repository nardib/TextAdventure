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
        //player is in the room 5
        Assert.assertEquals(5, g.getPlayer().getCurrentRoom());
        //test passage number 9
        g.nextMove("cross south");
        Assert.assertEquals(8, g.getPlayer().getCurrentRoom());
        //test passage number 11
        g.nextMove("cross west");
        Assert.assertEquals(7, g.getPlayer().getCurrentRoom());
        //test passage number 8
        g.nextMove("cross north");
        Assert.assertEquals(4, g.getPlayer().getCurrentRoom());
        //test passage number 3
        g.nextMove("cross north");
        Assert.assertEquals(1, g.getPlayer().getCurrentRoom());
        //test passage number 1
        g.nextMove("cross east");
        Assert.assertEquals(2, g.getPlayer().getCurrentRoom());
        //test passage number 4 (it doesn't exist)
        g.nextMove("cross south ");
        Assert.assertEquals(2, g.getPlayer().getCurrentRoom());
        //test passage number 2
        g.nextMove("cross east");
        Assert.assertEquals(3, g.getPlayer().getCurrentRoom());
        //test passage number 5
        g.nextMove("cross south");
        Assert.assertEquals(6, g.getPlayer().getCurrentRoom());
        //test passage number 10
        g.nextMove("cross south");
        Assert.assertEquals(9, g.getPlayer().getCurrentRoom());
        //test passage number 12 (it doesn't exist)
        g.nextMove("cross west");
        Assert.assertEquals(9, g.getPlayer().getCurrentRoom());
        //test passage number 7
        g.nextMove("cross north");
        g.nextMove("cross west");
        Assert.assertEquals(5, g.getPlayer().getCurrentRoom());
        //test passage number 6
        g.nextMove("cross west");
        Assert.assertEquals(4, g.getPlayer().getCurrentRoom());
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