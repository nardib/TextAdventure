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
}
