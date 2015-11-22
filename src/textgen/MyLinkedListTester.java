/**
 *
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH = 10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<>();
        shortList.add("A");
        shortList.add("B");

        emptyList = new MyLinkedList<>();

        longerList = new MyLinkedList<>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }

        list1 = new MyLinkedList<>();
        list1.add(65);
        list1.add(21);
        list1.add(42);
    }


    /**
     * Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }

    }


    /**
     * Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.
     */
    @Test
    public void testRemove() {
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());

        while (list1.size() > 0) {
            int prevSize = list1.size();
            list1.remove(list1.size() - 1);
            int nextSize = list1.size();
            assertEquals("Remove: check size getting decremented", prevSize - nextSize, 1);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutStart() throws Exception {
        list1.remove(-100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveOutEnd() throws Exception {
        list1.remove(list1.size() + 100);
    }

    /**
     * Test adding an element into the end of the list, specifically
     * public boolean add(E element)
     */
    @Test
    public void testAddEnd() {
        for (int i = 0; i < 10; i++) {
            int prevSize = emptyList.size();
            boolean result = emptyList.add(i);
            assertTrue("Add: check result true", result);
            int nextSize = emptyList.size();
            assertEquals("Add: check size getting incremented", nextSize - prevSize, 1);
        }
        assertEquals("Add: check size 10", emptyList.size(), 10);
    }


    /**
     * Test the size of the list
     */
    @Test
    public void testSize() {
        assertEquals("Size: check list1 size", 3, list1.size());
        assertEquals("Size: check empty size", 0, emptyList.size());
        assertEquals("Size: check long size", LONG_LIST_LENGTH, longerList.size());
        assertEquals("Size: check short size", 2, shortList.size());
    }


    /**
     * Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     */
    @Test
    public void testAddAtIndex() {
        shortList.add(0, "Z");
        assertEquals("Add at index: start", "Z", shortList.get(0));

        shortList.add(1, "X");
        assertEquals("Add at index: middle", "X", shortList.get(1));

        shortList.add(shortList.size(), "Y");
        assertEquals("Add at index: end", "Y", shortList.get(shortList.size() - 1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNull() throws Exception {
        shortList.add(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutStart() throws Exception {
        shortList.add(-1, "ABC");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutEnd() throws Exception {
        shortList.add(shortList.size() + 1, "ABC");
    }

    /**
     * Test setting an element in the list
     */
    @Test
    public void testSet() {
        list1.set(0, 123);
        assertEquals("Set: 0", 123, (int) list1.get(0));
        list1.set(1, 456);
        assertEquals("Set: 1", 456, (int) list1.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutStart() throws Exception {
        shortList.set(-1, "ABC");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutEnd() throws Exception {
        shortList.set(shortList.size() + 1, "ABC");
    }

    @Test(expected = NullPointerException.class)
    public void testSetNull() throws Exception {
        shortList.set(0, null);
    }

}
