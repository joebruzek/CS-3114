import junit.framework.TestCase;

/**
 * test the DoublyLinkedList Class
 * 
 * @author jbruzek
 * @author sucram20
 * @version 2014.10.14
 */
public class DoublyLinkedListTest extends TestCase {

    private DoublyLinkedList<String> dll;

    /**
     * set up the tests
     */
    public void setUp() {
        dll = new DoublyLinkedList<String>();
    }

    /**
     * test the add method
     */
    public void testAdd() {
        dll.add("wheee");
        dll.add("2");
        dll.head();

        assertEquals(2, dll.size());
        assertEquals("wheee", dll.next());
        assertEquals("2", dll.next());
    }

    /**
     * test add here
     */
    public void testAddHere() {
        dll.add("one");
        dll.head();
        dll.addHere("FRONT");
        dll.head();

        assertEquals(2, dll.size());
        assertEquals("FRONT", dll.next());
        assertEquals("one", dll.next());
    }

    /**
     * test the clear method
     */
    public void testClear() {
        dll.add("q");
        dll.add("q");
        dll.add("q");
        dll.add("q");
        dll.add("q");

        assertEquals(5, dll.size());
        dll.clear();
        assertEquals(0, dll.size());
    }

    /**
     * test the front item method
     */
    public void testFrontItem() {
        dll.add("1");
        dll.add("2");

        assertEquals("1", dll.frontItem());
    }

    /**
     * test the remove method
     */
    public void testRemove() {
        dll.add("one");
        dll.add("two");
        dll.add("three");
        dll.head();
        dll.next();
        dll.next();

        dll.remove();

        assertEquals(2, dll.size());
        assertEquals("one", dll.current().data());
        assertEquals("three", dll.next());
    }

    /**
     * test hasNExt
     */
    public void testHasNext() {
        dll.add("one");
        dll.add("two");
        dll.head();

        assertTrue(dll.hasNext());
        dll.next();
        dll.hasNext();
        assertEquals("one", dll.current().data());
    }

    /**
     * test has previous
     */
    public void testHasPrevious() {
        dll.add("1");
        dll.add("2");

        dll.tail();
        assertTrue(dll.hasPrevious());
        dll.previous();
        assertTrue(dll.hasPrevious());
        dll.previous();
        assertFalse(dll.hasPrevious());
    }

}
