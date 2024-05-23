import org.junit.*;

public class GameTest {
    @Test
    public void playerDirectionChange() {
        var g = new Game("Player", "f", "Enemy", "m");
        g.nextMove("north");
        Assert.assertEquals(Direction.NORTH, g.getPlayer().getCurrentDirection());
    }
}
