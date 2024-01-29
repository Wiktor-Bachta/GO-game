package tp;

import org.junit.Before;
import org.junit.Test;

import tp.gamelogic.Group;
import tp.gamelogic.Stone;
import tp.gamelogic.StoneState;

import java.util.Set;

import static org.junit.Assert.*;

public class GroupTest {
    private Group group;
    private Stone stone1;
    private Stone stone2;

    @Before
    public void setup() {
        group = new Group(StoneState.BLACK);
        stone1 = new Stone(1, 1);
        stone2 = new Stone(2, 2);
    }

    @Test
    public void testAddStone() {
        group.addStone(stone1);
        assertTrue(group.getStones().contains(stone1));
    }

    @Test
    public void testMergeWith() {
        Group anotherGroup = new Group(StoneState.BLACK, stone2);
        group.addStone(stone1);
        group.mergeWith(anotherGroup);
        assertTrue(group.getStones().contains(stone1));
        assertTrue(group.getStones().contains(stone2));
    }
    @Test
    public void testStoneReset() {
        Stone stone3 = new Stone(1, 1);
        Group thirdGroup = new Group(StoneState.BLACK, stone3);
        thirdGroup.addStone(stone3);
        stone3.setGroup(thirdGroup);
        stone3.reset();
        assertFalse(group.getStones().contains(stone1));
    }

    @Test
    public void testSetState() {
        group.setState(StoneState.WHITE);
        assertEquals(StoneState.WHITE, group.getState());
    }

    @Test
    public void testGetStones() {
        group.addStone(stone1);
        group.addStone(stone2);
        Set<Stone> stones = group.getStones();
        assertEquals(2, stones.size());
        assertTrue(stones.contains(stone1));
        assertTrue(stones.contains(stone2));
    }
    @Test
    public void testGetState() {
        assertEquals(StoneState.BLACK, group.getState());
    }
}