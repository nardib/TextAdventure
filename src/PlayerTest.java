import org.junit.*;

public class PlayerTest {
    @Test
    public void testEquality() {
        Player p = new Player("Filippo", "n");
        var p1 = p; //shallow copy of p
        Player p2 = p.clone();  //deep copy of p
        boolean deepCopy = p1.equals(p2);
        boolean shallowCopy = p1 == p2;
        Assert.assertTrue(deepCopy);
        Assert.assertFalse(shallowCopy);
    }

    @Test
    public void testInventory() {
        Key key = new Key("key", 1, "test.png", 1, 1);
        Hammer hammer = new Hammer("hammer", "test.png", 5, 1);
        Lock lock = new Lock("lock", 1, "test.png", 1, 1);
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
        Assert.assertEquals(9, p.getWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("hammer", p.getItem(1).getName());
        Assert.assertEquals("potion", p.getItem(2).getName());

        p.removeItem(1);
        //invenotry should now be [key, potion]
        Assert.assertEquals(2, p.getInventoryCount());
        Assert.assertEquals(4, p.getWeight());
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
        Assert.assertEquals(10, p.getWeight());
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
        Assert.assertEquals(8, p.getWeight());
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
        p.increaseHealth(((HealingItem)i).HEALING_POINTS);
        Assert.assertEquals(4, p.getHealth());
        p.increaseHealth(((HealingItem)i).HEALING_POINTS);
        Assert.assertEquals(5, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(0, p.getHealth());
        p.decreaseHealth(e.DAMAGE);
        Assert.assertEquals(0, p.getHealth());
        p.increaseHealth(((HealingItem)i).HEALING_POINTS);
        p.increaseHealth(((HealingItem)i).HEALING_POINTS);
        Assert.assertEquals(4, p.getHealth());
    }
}