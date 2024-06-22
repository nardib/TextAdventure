import org.junit.*;
import org.zssn.escaperoom.*;

public class EnemyTest {
    
    @Test
    public void testMove() {
        var map = new Map();
        var enemy = new Enemy("enemy", "m");
        for (int i = 0; i < 100; i++) {
            int prevRoom = enemy.getCurrentRoom();
            int room = enemy.move(map.getRoom(enemy.getCurrentRoom()));
            Assert.assertTrue(room >= 0 && room <= 9);
            Assert.assertNotEquals(room, prevRoom);
        }
    }

    @Test
    public void testDeepCopy() {
        var enemy = new Enemy("enemy", "m");
        var enemy1 = enemy.clone();
        boolean shallowCopy = enemy == enemy1;
        boolean deepCopy = enemy.equals(enemy1);
        Assert.assertFalse(shallowCopy);
        Assert.assertTrue(deepCopy);
    }
}
