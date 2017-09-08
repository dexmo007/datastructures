import my.util.ArrayList;
import my.util.LinkedList;
import my.util.List;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Henrik on 12/1/2015.
 */
public abstract class TestList {

    List<String> list;

    private static final String WRONG_INDEX = "Wrong Index!";
    private static final String WRONG_SIZE = "Wrong Size!";
    private static final String NOT_EXPECTED = "Not expected!";
    private static final String WRONG_ELEMENT = "Wrong element!";

    @Before
    public void init() {
        list = getList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
    }

    public abstract List<String> getList();

    @Test(timeout = 1000)
    public void testGetIndex() {
        assertEquals(WRONG_ELEMENT, "c", list.get(2));
        assertEquals(WRONG_ELEMENT, "a", list.get(0));
        assertEquals(WRONG_ELEMENT, "f", list.get(5));
    }

    @Test(timeout = 1000, expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        list.get(-1);
    }

    @Test(timeout = 1000, expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds2() {
        list.get(6);
    }

    @Test(timeout = 1000)
    public void testSetIndex() {
        list.set(0, "NEW");
        list.set(2, "NEW");
        list.set(5, "NEW");
        assertEquals(WRONG_ELEMENT, "NEW", list.get(2));
        assertEquals(WRONG_ELEMENT, "NEW", list.get(0));
        assertEquals(WRONG_ELEMENT, "NEW", list.get(5));
    }

    @Test(timeout = 1000)
    public void testAddIndex() {
        list.add(0, "NEW1");
        list.add(3, "NEW2");
        list.add(8, "NEW3");
        assertEquals(WRONG_SIZE, 9, list.size());
        assertEquals(WRONG_ELEMENT, "NEW1", list.get(0));
        assertEquals(WRONG_ELEMENT, "NEW2", list.get(3));
        assertEquals(WRONG_ELEMENT, "NEW3", list.get(8));
    }

    @Test(timeout = 1000)
    public void testRemoveIndex() {
        list.remove(0);
        list.remove(4);
        list.remove(2);
        assertEquals(WRONG_SIZE, 3, list.size());
        assertEquals(WRONG_ELEMENT, "bce", list.get(0) + list.get(1) + list.get(2));
    }

    @Test(timeout = 1000)
    public void testIndexOf() {
        assertEquals(WRONG_INDEX, 0, list.indexOf("a"));
        assertEquals(WRONG_INDEX, 3, list.indexOf("d"));
        assertEquals(WRONG_INDEX, 5, list.indexOf("f"));
    }

    @Test(timeout = 1000)
    public void testLastIndexOf() {
        list.add("a");
        list.add("x");
        list.add("x");
        assertEquals(WRONG_INDEX, 6, list.lastIndexOf("a"));
    }

    @Test(timeout = 1000)
    public void testSubList() {
        List<String> subList = list.subList(2, 4);
        assertEquals(WRONG_ELEMENT, "cd", subList.get(0) + subList.get(1));
    }

    @Test(timeout = 1000)
    public void testContains() {
        assertTrue(NOT_EXPECTED, list.contains("a"));
        assertTrue(NOT_EXPECTED, list.contains("d"));
        assertTrue(NOT_EXPECTED, list.contains("f"));
        assertFalse(NOT_EXPECTED, list.contains("x"));
    }

    @Test(timeout = 1000)
    public void testToArray() {
        String[] arr = list.toArray();
        for (int i = 0; i < arr.length; i++) {
            assertEquals(WRONG_ELEMENT, list.get(i), arr[i]);
        }
    }

    @Test(timeout = 1000)
    public void testToArrayCast() {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        Integer[] copy = integers.toArray(new Integer[0]);
        for (int i = 0; i < integers.size(); i++) {
            assertEquals(WRONG_ELEMENT, integers.get(i).intValue(), copy[i].intValue());
        }
    }

    @Test(timeout = 1000)
    public void testRemove() {
        assertTrue(NOT_EXPECTED, list.remove("a"));
        assertTrue(NOT_EXPECTED, list.remove("d"));
        assertTrue(NOT_EXPECTED, list.remove("f"));
        assertFalse(NOT_EXPECTED, list.remove("x"));
        assertEquals(WRONG_SIZE, 3, list.size());
        assertEquals(WRONG_ELEMENT, "b", list.get(0));
        assertEquals(WRONG_ELEMENT, "c", list.get(1));
        assertEquals(WRONG_ELEMENT, "e", list.get(2));
    }

    @Test(timeout = 1000)
    public void testContainsAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("a");
        con.add("c");
        con.add("f");
        assertTrue(NOT_EXPECTED, list.containsAll(con));
        con.add("x");
        assertFalse(NOT_EXPECTED, list.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testAddAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("x");
        con.add("y");
        con.add("z");
        list.addAll(con);
        assertTrue(NOT_EXPECTED, list.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testRemoveAll() {
        LinkedList<String> con = new LinkedList<>();
        con.add("x");
        con.add("y");
        con.add("z");
        list.addAll(con);
        assertTrue(NOT_EXPECTED, list.containsAll(con));
        list.removeAll(con);
        assertFalse(NOT_EXPECTED, list.containsAll(con));
    }

    @Test(timeout = 1000)
    public void testClear() {
        list.clear();
        assertEquals(WRONG_SIZE, 0, list.size());
        assertTrue(NOT_EXPECTED, list.isEmpty());
    }

    @Test(timeout = 1000)
    public void testDescItr() {
        Iterator<String> descIt = list.descendingIterator();
        while (descIt.hasNext()) {
            descIt.next();
            descIt.remove();
        }
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test(timeout = 1000)
    public void testDescItr2() {
        Iterator<String> descIt = list.descendingIterator();
        StringBuilder sb = new StringBuilder();
        while (descIt.hasNext()) {
            sb.append(descIt.next());
        }
        char[] desc = sb.toString().toCharArray();

        Iterator<String> ascIt = list.iterator();
        StringBuilder sb2 = new StringBuilder();
        while (ascIt.hasNext()) {
            sb2.append(ascIt.next());
        }
        char[] asc = sb2.toString().toCharArray();

        Arrays.sort(asc);
        Arrays.sort(desc);
        assertArrayEquals(asc, desc);
//        for (int i = 0; i < desc.length; i++) {
//            assertEquals(asc[i], desc[desc.length - 1 - i]);
//        }
    }

}

