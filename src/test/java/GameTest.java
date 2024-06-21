import org.zssn.escaperoom.*;
import org.junit.*;

public class GameTest {

    @Test
    public void playerDirectionChange() {
        var g = new Game("Player", "f", "Enemy", "m", 7);
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
        var g = new Game("Player", "f", "Enemy", "m", 1);
        //player is in the room 5
        Assert.assertEquals(5, g.getPlayer().getCurrentRoom());
        //test passage number 9
        g.nextMove("cross south");
        Assert.assertEquals(8, g.getPlayer().getCurrentRoom());
        //test passage number 11
        g.nextMove("cross w");
        Assert.assertEquals(7, g.getPlayer().getCurrentRoom());
        //test passage number 8
        g.nextMove("cross n  ");
        Assert.assertEquals(4, g.getPlayer().getCurrentRoom());
        //test passage number 3
        g.nextMove("cross");
        Assert.assertEquals(1, g.getPlayer().getCurrentRoom());
        //test passage number 1
        g.nextMove("cross east");
        Assert.assertEquals(2, g.getPlayer().getCurrentRoom());
        //test passage number 4 (it doesn't exist)
        g.nextMove("cross south");
        Assert.assertEquals(2, g.getPlayer().getCurrentRoom());
        //test passage number 2
        g.nextMove("cross e");
        Assert.assertEquals(3, g.getPlayer().getCurrentRoom());
        //test passage number 5
        g.nextMove("cross s");
        Assert.assertEquals(6, g.getPlayer().getCurrentRoom());
        //test passage number 10
        g.nextMove("cross ");
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
        var g = new Game("Player", "f", "Enemy", "m", 1);
        var initP = g.getPlayer().clone();
        var initE = g.getEnemy().clone();

        //i do some random moves to change the state of the game
        g.nextMove("south");
        g.nextMove("cross east");
        g.nextMove("cross north");
        var p = g.getPlayer().clone();
        var e = g.getEnemy().clone();
        Assert.assertEquals(p, g.getPlayer());
        Assert.assertEquals(e, g.getEnemy());
        //i should implement a way to check the map state
        g.nextMove("cross west");
        g.nextMove("cross");

        //i go back 2 states and i check if the state is the same as the initial one
        g.nextMove("undo");
        g.nextMove("back");

        //i check the single parameters so that i can avoid the check by copy
        Assert.assertEquals(p, g.getPlayer());
        Assert.assertEquals(e, g.getEnemy());

        //now i go back to the initial state to see if the state is diffrent
        g.nextMove("undo");
        g.nextMove("undo");
        g.nextMove("undo");

        Assert.assertEquals(initP, g.getPlayer());
        //sometimes this fails becauce the enemy may not have been moved since the last record state
        Assert.assertEquals(initE, g.getEnemy());
    }

    //i test the dynamics of picking the items in the map
    @Test
    public void ItemsTest() {
        
    }
}