import org.zssn.escaperoom.*;
import org.junit.*;

public class PlayerTest {
    @Test
    public void testEquality() {
        Player p = new Player("Filippo", "n");
        var item1 = new Note("Note", "test.png", 1, "You found a note!");
        var item2 = new Key("key", 1, "test.png", 1);
        var item3 = new HealingItem("Heal", "test.png", 5, 0, 3);
        p.insertItem(item1);
        p.insertItem(item2);
        var p1 = p.clone();
        boolean deepCopy = p1.equals(p);
        boolean shallowCopy = p1 == p;
        Assert.assertFalse(shallowCopy);
        Assert.assertTrue(deepCopy);
        p.insertItem(item3);
        Assert.assertFalse(p1.equals(p));
    }

    @Test
    public void testInventory() {
        Key key = new Key("key", 1, "test.png", 1);
        HealingItem hammer = new HealingItem("hammer", "test.png", 5, 1, 3);
        HidingItem lock = new HidingItem("lock", "test.png", 1);
        HealingItem potion = new HealingItem("potion", "test.png", 3, 1, 3);

        Player p = new Player("Filippo", "n");
        p.insertItem(key);
        p.insertItem(hammer);
        //this item is not pickable
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            p.insertItem(lock);
        });
        p.insertItem(potion);
        //this item is too heavy
        Assert.assertThrows(IllegalStateException.class, () -> {
            p.insertItem(hammer);
        });

        //inventory should now be [key, hammer, potion]
        Assert.assertEquals(3, p.getInventoryCount());
        Assert.assertEquals(9, p.getInventoryWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("hammer", p.getItem(1).getName());
        Assert.assertEquals("potion", p.getItem(2).getName());

        p.removeItem(1);
        //invenotry should now be [key, potion]
        Assert.assertEquals(2, p.getInventoryCount());
        Assert.assertEquals(4, p.getInventoryWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("potion", p.getItem(1).getName());
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            p.getItem(2);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            p.removeItem(2);
        });

        p.insertItem(hammer);
        p.insertItem(key);
        //inventory should now be [key, potion, hammer, key]
        Assert.assertEquals(4, p.getInventoryCount());
        Assert.assertEquals(10, p.getInventoryWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("potion", p.getItem(1).getName());
        Assert.assertEquals("hammer", p.getItem(2).getName());
        Assert.assertEquals("key", p.getItem(3).getName());
        //invenotory is full, so i can't insert other items
        Assert.assertThrows(IllegalStateException.class, () -> {
            p.insertItem(key);
        });

        p.removeItem(0);
        p.removeItem(2);
        //inventory should now be [potion, hammer]
        Assert.assertEquals(2, p.getInventoryCount());
        Assert.assertEquals(8, p.getInventoryWeight());
        Assert.assertEquals("potion", p.getItem(0).getName());
        Assert.assertEquals("hammer", p.getItem(1).getName());
    }
    
    //i test if the health of the player is correctly updated
    @Test
    public void testHealth() {
        Player p = new Player("Filippo", "n");
        Enemy e = new Enemy("Enemy", "m");
        Item i = new HealingItem("potion", "test.png", 3, 1, 2);
        Assert.assertEquals(5, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(4, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(3, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(2, p.getHealth());
        p.increaseHealth(((HealingItem)i).getHealingPoints());
        Assert.assertEquals(4, p.getHealth());
        p.increaseHealth(((HealingItem)i).getHealingPoints());
        Assert.assertEquals(5, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(0, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(0, p.getHealth());
        p.increaseHealth(((HealingItem)i).getHealingPoints());
        p.increaseHealth(((HealingItem)i).getHealingPoints());
        Assert.assertEquals(4, p.getHealth());
    }

    //test if the player change hidden state correctly
    @Test
    public void testHidden() {
        Player p = new Player("Filippo", "n");
        Assert.assertFalse(p.isHidden());
        p.setHidden();
        Assert.assertTrue(p.isHidden());
        p.setHidden();
        Assert.assertFalse(p.isHidden());
    }
}