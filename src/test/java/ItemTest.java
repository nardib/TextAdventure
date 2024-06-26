import org.zssn.escaperoom.*;
import org.junit.*;

public class ItemTest {
    
    //generic test for an item
    @Test
    public void GenericItemTest() {
        var i = new Item("Item", 1, true);
        Assert.assertEquals("Item", i.getName());
        Assert.assertEquals(1, i.getWeight());
        Assert.assertEquals(true, i.isPickable());

        //i change the room of the item
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            i.setWeight(-1);
        });
    }

    //test for a ClueItem
    @Test
    public void ClueItemTest() {
        var c = new ClueItem("Clue", "You found a clue!");
        Assert.assertEquals("Clue", c.getName());
        Assert.assertEquals(11, c.getWeight());
        Assert.assertEquals(false, c.isPickable());
        Assert.assertEquals("You found a clue!", c.getUsingMessage());
    }

    //test for a Note item
    @Test
    public void NoteTest() {
        var n = new Note("Note", "You found a note!");
        Assert.assertEquals("Note", n.getName());
        Assert.assertEquals(0, n.getWeight());
        Assert.assertEquals(true, n.isPickable());
        Assert.assertEquals("This note says: You found a note!", n.getUsingMessage());
    }

    //test for a ItemContainer
    @Test
    public void ItemContainerTest() {
        var n = new Note("Note", "You found a note!");
        var c = new ClueItem("Clue", "You found a clue!");
        final ItemContainer ic = new ItemContainer("safe", new Item[]{n, c}, LockType.NONE, 0);
        Assert.assertFalse(ic.isLocked());
        Assert.assertEquals(ic.getLockType(), LockType.NONE);
        Assert.assertEquals(ic.getID(), 0);
        Assert.assertEquals(ic.getItem(0), n);
        Assert.assertEquals(c, ic.getItem(1));
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic.getItem(2);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic.getItem(-1);
        });
        String usingMessage = "In this container you find: \n";
        for (int i = 0; i < ic.getItemsLength(); i++)
            usingMessage += "- " + ic.getItem(i).getName() + "\n";
        usingMessage += "If you want to pick an item from the container, type 'use " + ic.getName() + " <item_name>'";
        Assert.assertEquals(usingMessage, ic.getUsingMessage());
        ic.removeItem(0);
        Assert.assertEquals(c, ic.getItem(0));
        Assert.assertEquals(1, ic.getItemsLength());
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic.getItem(1);
        });
        ic.removeItem(0);
        Assert.assertEquals(0, ic.getItemsLength());
        Assert.assertEquals("There are no items in " + ic.getName(), ic.getUsingMessage());
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic.getItem(0);
        });


        //now i check the lock types different from NONE
        Key k = new Key ("key", 1234);
        Key k1 = new Key ("key", 1235);
        var ic1 = new ItemContainer("safe", new Item[]{n, c}, LockType.COMBINATION, 1234);
        Assert.assertTrue(ic1.isLocked());
        Assert.assertEquals(ic1.getLockType(), LockType.COMBINATION);
        Assert.assertEquals(ic1.getID(), 1234);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic1.getItem(0);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic1.unlock(k);
        });
        Assert.assertFalse(ic1.unlock(1235));
        Assert.assertTrue(ic1.unlock(1234));
        Assert.assertFalse(ic1.isLocked());

        var ic2 = new ItemContainer("safe", new Item[]{n, c}, LockType.KEY, 1234);
        Assert.assertTrue(ic2.isLocked());
        Assert.assertEquals(ic2.getLockType(), LockType.KEY);
        Assert.assertEquals(ic2.getID(), 1234);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic2.getItem(0);
        });
        Assert.assertFalse(ic2.unlock(k1));
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            ic2.unlock(1234);
        });
        Assert.assertTrue(ic2.unlock(k));
        Assert.assertFalse(ic2.isLocked());
    }

    //test for HiderItem
    @Test
    public void HiderItemTest () {
        var i = new Item ("Item", 1, true);
        var h = new HiderItem("Hider", i);
        Assert.assertEquals("Hider", h.getName());
        Assert.assertEquals(11, h.getWeight());
        Assert.assertEquals(false, h.isPickable());
        Assert.assertTrue(h.isHiding());
        Item hidden = h.reveal();
        Assert.assertFalse(h.isHiding());
        Assert.assertEquals(i, hidden);
    }

    //test for Star and StarHole
    @Test
    public void StarTest () {
        var s = new Star("Star", 1);
        var sh = new StarHole("StarHole", 1);
        Assert.assertFalse(sh.isFilled());
        sh.fill(s);
        Assert.assertTrue(sh.isFilled());
    }

    //test for deep clone
    @Test
    public void CloneTest() {
        var i = new Item("Item", 1, true);
        var c = new ClueItem("Clue", "You found a clue!");
        var n = new Note("Note", "You found a note!");
        ItemContainer ic = new ItemContainer("safe", new Item[]{n, c}, LockType.NONE, 0);
        var h = new HiderItem("Hider", i);
        var hiding = new HidingItem("Hiding");
        var k = new Key ("key", 1234);
        var heal = new HealingItem("Heal", 1, 2);
        var s = new Star("Star", 1);
        var sh = new StarHole("StarHole", 1);
        var iClone = i.clone();
        var cClone = c.clone();
        var nClone = n.clone();
        var icClone = ic.clone();
        var hClone = h.clone();
        var hidingClone = hiding.clone();
        var kClone = k.clone();
        var healClone = heal.clone();
        var sClone = s.clone();
        var shClone = sh.clone();
        Assert.assertEquals(i, iClone);
        Assert.assertEquals(c, cClone);
        Assert.assertEquals(n, nClone);
        Assert.assertEquals(ic, icClone);
        Assert.assertEquals(h, hClone);
        Assert.assertEquals(hiding, hidingClone);
        Assert.assertEquals(k, kClone);
        Assert.assertEquals(heal, healClone);
        Assert.assertEquals(s, sClone);
        Assert.assertEquals(sh, shClone);
    }
}
