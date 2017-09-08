import my.util.HashSet;
import my.util.LinkedList;
import my.util.List;
import my.util.Set;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Henrik on 12/1/2015.
 */
public abstract class TestSet {

    Set<String> set;

    private static final String WRONG_INDEX = "Wrong Index!";
    private static final String WRONG_SIZE = "Wrong Size!";
    private static final String NOT_EXPECTED = "Not expected!";
    private static final String WRONG_ELEMENT = "Wrong element!";

    @Before
    public void init() {
        set = getSet();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        set.add("e");
        set.add("f");
    }

    public abstract Set<String> getSet();

    @Test(timeout = 1000)
    public void testContains() {
        assertTrue(NOT_EXPECTED, set.contains("a"));
        assertTrue(NOT_EXPECTED, set.contains("c"));
        assertTrue(NOT_EXPECTED, set.contains("f"));
        assertFalse(NOT_EXPECTED, set.contains("x"));
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testInitCapNeg() {
        new HashSet<String>(-1);
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testMaxLoadFactNeg() {
        new HashSet<String>(-0.5);
    }

    @Test(timeout = 1000)
    public void testAdd() {
        assertTrue(set.add("g"));
        assertFalse(set.add("g"));
    }

    @Test(timeout = 1000)
    public void testRemove() {
        assertTrue(set.contains("a"));
        System.out.println(set);
        assertTrue(set.remove("a"));
        System.out.println(set);
        assertFalse(set.remove("a"));
        assertFalse(set.contains("a"));

        assertTrue(set.contains("d"));
        assertTrue(set.remove("d"));
        assertFalse(set.remove("d"));
        assertFalse(set.contains("d"));
    }

    @Test(timeout = 1000)
    public void testToArray() {
        String[] actual = set.toArray();
        Arrays.sort(actual);
        String[] expected = {"a", "b", "c", "d", "e", "f"};
        assertArrayEquals(expected, actual);
    }

    @Test(timeout = 1000)
    public void testContainsAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("a");
        con.add("c");
        con.add("f");
        assertTrue(NOT_EXPECTED, set.containsAll(con));
        con.add("x");
        assertFalse(NOT_EXPECTED, set.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testAddAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("x");
        con.add("y");
        con.add("z");
        set.addAll(con);
        assertTrue(NOT_EXPECTED, set.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testRemoveAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("x");
        con.add("y");
        con.add("z");
        set.addAll(con);
        assertTrue(NOT_EXPECTED, set.containsAll(con));
        set.removeAll(con);
        assertFalse(NOT_EXPECTED, set.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testClear() {
        set.clear();
        assertEquals(WRONG_SIZE, 0, set.size());
        assertTrue(NOT_EXPECTED, set.isEmpty());
    }

    @Test(timeout = 1000)
    public void testdescItr() {
        Iterator<String> descIt = set.descendingIterator();
        while (descIt.hasNext()) {
            descIt.next();
            descIt.remove();
        }
        assertEquals(0, set.size());
        assertTrue(set.isEmpty());
    }

    @Test(timeout = 1000)
    public void testdescItr2() {
        //descending iterator through set
        Iterator<String> descIt = set.descendingIterator();
        StringBuilder sb = new StringBuilder();
        while (descIt.hasNext()) {
            sb.append(descIt.next());
        }
        char[] desc = sb.toString().toCharArray();
        //ascending iterator through set
        Iterator<String> ascIt = set.iterator();
        StringBuilder sb2 = new StringBuilder();
        while (ascIt.hasNext()) {
            sb2.append(ascIt.next());
        }
        char[] asc = sb2.toString().toCharArray();
        Arrays.sort(asc);
        Arrays.sort(desc);
        assertArrayEquals(asc, desc);
    }

}

