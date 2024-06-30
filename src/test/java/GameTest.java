import org.zssn.escaperoom.*;
import org.junit.*;

public class GameTest {

    //i test a win condition
    @Test
    public void winTest() {
        Game g = new Game("Player", "f", "Enemy", "m", 1, false);
        Assert.assertEquals(0, g.getFilledStarHoles());
        g.nextMove("cross w");
        g.nextMove("cross n");
        g.nextMove("use drawer star 1");
        Assert.assertEquals("Star 1", g.getPlayer().getItem(0).getName());
        g.nextMove("e");
        g.nextMove("use painting");
        g.nextMove("use safe 352");
        g.nextMove("use safe star 2");
        Assert.assertEquals("Star 2", g.getPlayer().getItem(1).getName());
        g.nextMove("take key on pinboard");
        Assert.assertEquals("Key on pinboard", g.getPlayer().getItem(2).getName());
        g.nextMove("cross");
        g.nextMove("use dice 143");
        g.nextMove("use dice star 4");
        Assert.assertEquals("Star 4", g.getPlayer().getItem(3).getName());
        g.nextMove("use board games");
        g.nextMove("take bedroom safe key");
        Assert.assertEquals("Bedroom safe key", g.getPlayer().getItem(4).getName());
        g.nextMove("cross");
        g.nextMove("n");
        g.nextMove("use fridge 151");
        g.nextMove("use fridge star 5");
        Assert.assertEquals("Star 5", g.getPlayer().getItem(5).getName());
        g.nextMove("cross s");
        g.nextMove("n");
        g.nextMove("use safe");
        g.nextMove("use safe star 6");
        Assert.assertEquals("Star 6", g.getPlayer().getItem(5).getName());
        g.nextMove("s");
        g.nextMove("cross");
        g.nextMove("use lock 1313");
        g.nextMove("use lock star 10");
        Assert.assertEquals("Star 10", g.getPlayer().getItem(6).getName());
        g.nextMove("cross n");
        g.nextMove("cross w");
        g.nextMove("use star hole 1");
        Assert.assertEquals(1, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(0));
        g.nextMove("use star hole 2");
        Assert.assertEquals(2, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(1));
        g.nextMove("n");
        g.nextMove("use star hole 4");
        Assert.assertEquals(3, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(3));
        g.nextMove("use star hole 5");
        Assert.assertEquals(4, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(4));
        g.nextMove("use star hole 6");
        Assert.assertEquals(5, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(5));
        g.nextMove("s");
        g.nextMove("use star hole 10");
        Assert.assertTrue(g.getStarHole(9));
        Assert.assertEquals(6, g.getFilledStarHoles());
        g.nextMove("cross");
        g.nextMove("use bed safe");
        g.nextMove("use bed safe star 9");
        g.nextMove("e");
        g.nextMove("use chess drawer 358");
        g.nextMove("use chess drawer key for games chest");
        g.nextMove("cross w");
        g.nextMove("n");
        g.nextMove("use vase");
        g.nextMove("take star 7");
        g.nextMove("s");
        g.nextMove("use vase with wheels");
        g.nextMove("use pinpad 1563");
        g.nextMove("use pinpad star 8");
        g.nextMove("cross n");
        g.nextMove("cross n");
        g.nextMove("cross e");
        g.nextMove("n");
        g.nextMove("use games chest");
        g.nextMove("use games chest star 3");
        g.nextMove("cross w");
        g.nextMove("cross s");
        g.nextMove("cross e");
        g.nextMove("use star hole 7");
        Assert.assertEquals(7, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(6));
        g.nextMove("use star hole 8");
        Assert.assertEquals(8, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(7));
        g.nextMove("s");
        g.nextMove("use star hole 9");
        Assert.assertEquals(9, g.getFilledStarHoles());
        Assert.assertTrue(g.getStarHole(8));
        g.nextMove("n");
        g.nextMove("use star hole 3");
        Assert.assertTrue(g.getStarHole(2));
        Assert.assertEquals(10, g.getFilledStarHoles());
    }

    @Test
    public void playerDirectionChange() {
        var g = new Game("Player", "f", "Enemy", "m", 1, false);
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
        var g = new Game("Player", "f", "Enemy", "m", 1, false);
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
        var g = new Game("Player", "f", "Enemy", "m", 1, true);
        var initP = g.getPlayer().clone();
        var initE = g.getEnemy().clone();
        var initW = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).clone();

        //i do some random moves to change the state of the game
        g.nextMove("cross w");
        g.nextMove("cross n");
        g.nextMove("use drawer note");
        var p = g.getPlayer().clone();
        var e = g.getEnemy().clone();
        var w = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).clone();
        Assert.assertEquals(p, g.getPlayer());
        Assert.assertEquals(e, g.getEnemy());
        Assert.assertEquals(w, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()));
        Assert.assertNotEquals(initP, g.getPlayer());
        Assert.assertNotEquals(initE, g.getEnemy());
        Assert.assertNotEquals(initW, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()));
        g.nextMove("south");
        g.nextMove("cross");
        g.nextMove("e");
        var p1 = g.getPlayer().clone();
        var e1 = g.getEnemy().clone();
        var w1 = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).clone();
        g.nextMove("take bendage");

        g.nextMove("undo");
        Assert.assertEquals(p1, g.getPlayer());
        Assert.assertEquals(e1, g.getEnemy());
        Assert.assertEquals(w1, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()));
        
        g.nextMove("back");
        g.nextMove("undo");
        g.nextMove("undo");

        //i check the single parameters so that i can avoid the check by copy
        Assert.assertEquals(p, g.getPlayer());
        Assert.assertEquals(e, g.getEnemy());
        Assert.assertEquals(w, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()));

        //now i go back to the initial state to see if the state is diffrent
        g.nextMove("undo");
        g.nextMove("back");
        g.nextMove("undo");

        Assert.assertEquals(initP, g.getPlayer());
        Assert.assertEquals(initE, g.getEnemy());
        Assert.assertEquals(initW, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()));


    }

    //i test the dynamics of picking the items in the map (i test one item for each type)
    @Test
    public void ItemsTest() {
        var g = new Game("Player", "f", "Enemy", "m", 1, true);

        //i try to hide in the chest in the first room wall north (hiding item)
        g.nextMove("use hiding chest");
        int room = g.getEnemy().getCurrentRoom();
        Assert.assertTrue(g.getPlayer().isHidden());
        g.nextMove("wait");
        Assert.assertTrue(g.getPlayer().isHidden());
        Assert.assertNotEquals(room, g.getEnemy().getCurrentRoom());
        Direction dir = g.getPlayer().getCurrentDirection();
        g.nextMove("west");
        Assert.assertTrue(g.getPlayer().isHidden());
        Assert.assertEquals(dir, g.getPlayer().getCurrentDirection());
        g.nextMove("unhide");
        Assert.assertFalse(g.getPlayer().isHidden());

        //i test if i can pick the key in the board games in the second room wall east (hider item test)
        g.getPlayer().increaseHealth(100);
        g.nextMove("cross east");
        g.nextMove("cross north");
        g.nextMove("cross west");
        g.nextMove("e");
        Assert.assertEquals(g.getPlayer().getCurrentDirection(), Direction.EAST);
        Assert.assertEquals(g.getPlayer().getCurrentRoom(), 2);
        g.nextMove("use board games");
        int wallElem = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength();
        g.nextMove("take bedroom safe key");
        Assert.assertEquals(wallElem - 1, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength());
        Assert.assertTrue(g.getPlayer().getInventoryCount() == 1);
        Assert.assertTrue(g.getPlayer().getItem(0).getName().equals("Bedroom safe key"));

        //i test if using the key i can unlock the safe in room 8 wall south (item container and key test)
        g.getPlayer().increaseHealth(100);
        g.nextMove("cross");
        g.nextMove("cross south");
        g.nextMove("cross w");
        g.nextMove("cross s");
        Assert.assertEquals(g.getPlayer().getCurrentDirection(), Direction.SOUTH);
        Assert.assertEquals(g.getPlayer().getCurrentRoom(), 8);
        Assert.assertTrue(((ItemContainer) g.getMap().getWall(8, Direction.SOUTH).getItem(0)).isLocked());
        g.nextMove("use bed safe key");
        Assert.assertEquals(g.getPlayer().getItem(0).getName(), "Bedroom safe key");
        Assert.assertTrue(((ItemContainer) g.getMap().getWall(8, Direction.SOUTH).getItem(0)).isLocked());
        g.nextMove("use bed safe");
        Assert.assertEquals(0, g.getPlayer().getInventoryCount());
        Assert.assertFalse(((ItemContainer) g.getMap().getWall(8, Direction.SOUTH).getItem(0)).isLocked());
        g.nextMove("w");
        g.nextMove("use clock drawer 735");
        g.nextMove("use clock drawer clock drawer note");
        Assert.assertEquals("Clock drawer note", g.getPlayer().getNoteAt(0).getName());

        //i test if i can increase the player's health using a healing item (healing item test)
        g.getPlayer().increaseHealth(100);
        g.nextMove("cross w");
        g.nextMove("cross n");
        g.nextMove("east");
        Assert.assertEquals(4, g.getPlayer().getCurrentRoom());
        Assert.assertEquals(Direction.EAST, g.getPlayer().getCurrentDirection());
        wallElem = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength();
        g.nextMove("take bendage");
        Assert.assertEquals(wallElem - 1, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength());
        Assert.assertEquals("Bendage", g.getPlayer().getItem(0).getName());
        g.getPlayer().increaseHealth(100);
        g.setEnemyAttacks(false);
        g.getPlayer().decreaseHealth(2);
        Assert.assertEquals(3, g.getPlayer().getHealth());
        g.nextMove("use bendage");
        Assert.assertEquals(5, g.getPlayer().getHealth());
        Assert.assertEquals(0, g.getPlayer().getInventoryCount());

        //i test if a note goes in the note inventory and not in the inventory (note test)
        g.getPlayer().increaseHealth(100);
        Assert.assertEquals(0, g.getPlayer().getInventoryCount());
        Assert.assertEquals(1, g.getPlayer().getNotesCount());
        g.nextMove("cross");
        g.nextMove("cross");
        g.nextMove("s");
        Assert.assertEquals(6, g.getPlayer().getCurrentRoom());
        Assert.assertEquals(Direction.SOUTH, g.getPlayer().getCurrentDirection());
        wallElem = g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength();
        g.nextMove("take note in the coat");
        Assert.assertEquals(wallElem - 1, g.getMap().getWall(g.getPlayer().getCurrentRoom(), g.getPlayer().getCurrentDirection()).getItemsLength());
        Assert.assertEquals(0, g.getPlayer().getInventoryCount());
        Assert.assertEquals(2, g.getPlayer().getNotesCount());

        //i test if a clue item returns it's clue
        g.getPlayer().increaseHealth(100);
        g.nextMove("e");
        String message = g.nextMove("use television").substring(79, 140);
        Assert.assertEquals("The television turns on and shows the code \"1563\" on a pinpad", message);

        // i test an item container with no lock
        g.getPlayer().increaseHealth(100);
        g.nextMove("cross south");
        g.nextMove("noRth");
        Assert.assertFalse(((ItemContainer) g.getMap().getWall(9, Direction.NORTH).getItem(1)).isLocked());
        g.nextMove("use mirror cabinet");
        g.nextMove("use mirror cabinet PILLS");
        Assert.assertEquals("Pills", g.getPlayer().getItem(0).getName());
        Assert.assertEquals(1, g.getPlayer().getInventoryCount());
        Assert.assertEquals(2, g.getPlayer().getNotesCount());
    }
}