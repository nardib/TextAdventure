import org.zssn.escaperoom.*;
import org.junit.*;

public class PlayerTest {
    @Test
    //test the clone and equals methods of the player
    public void testEquality() {
        Player p = new Player("Filippo", "n");
        var item1 = new Note("Note", "You found a note!");
        var item2 = new Key("key", 1);
        var item3 = new HealingItem("Heal", 5, 3);
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
    //tests the inventory of the player
    public void testInventory() {
        Key key = new Key("key", 1);
        HealingItem medKit = new HealingItem("med kit", 5, 3);
        HidingItem chest = new HidingItem("chest");
        HealingItem potion = new HealingItem("potion", 3, 3);

        Player p = new Player("Filippo", "n");
        p.insertItem(key);
        p.insertItem(medKit);
        //this item is not pickable
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            p.insertItem(chest);
        });
        p.insertItem(potion);
        //this item is too heavy
        Assert.assertThrows(IllegalStateException.class, () -> {
            p.insertItem(medKit);
        });

        //inventory should now be [key, med kit, potion]
        Assert.assertEquals(3, p.getInventoryCount());
        Assert.assertEquals(9, p.getInventoryWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("med kit", p.getItem(1).getName());
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

        p.insertItem(medKit);
        p.insertItem(key);
        //inventory should now be [key, potion, med kit, key]
        Assert.assertEquals(4, p.getInventoryCount());
        Assert.assertEquals(10, p.getInventoryWeight());
        Assert.assertEquals("key", p.getItem(0).getName());
        Assert.assertEquals("potion", p.getItem(1).getName());
        Assert.assertEquals("med kit", p.getItem(2).getName());
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
        Assert.assertEquals("med kit", p.getItem(1).getName());
    }
    
    @Test
    //test if the health of the player is correctly updated
    public void testHealth() {
        Player p = new Player("Filippo", "n");
        Enemy e = new Enemy("Enemy", "m");
        Item i = new HealingItem("potion", 3, 2);
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

    @Test
    //test if the player change hidden state correctly
    public void testHidden() {
        Player p = new Player("Filippo", "n");
        Assert.assertFalse(p.isHidden());
        p.setHidden();
        Assert.assertTrue(p.isHidden());
        p.setHidden();
        Assert.assertFalse(p.isHidden());
    }
}