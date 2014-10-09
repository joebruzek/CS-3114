import junit.framework.TestCase;

/**
 * Tests for the Node class.
 *
 * @author  Joe Bruzek (jbruzek)
 * @author Aaron Kunzer (aaronk1)
 * @version 2013.10.03
 */
public class NodeTest extends TestCase {

    private Node<String> node1;
    private Node<String> node2;
    private Node<String> node3;

    /**
     * Create some new nodes for each test method.
     */
    public void setUp() {
        node1 = new Node<String>("node1");
        node2 = new Node<String>("node2");
        node3 = new Node<String>("node3");
    }


    /**
     * test the join method
     */
    public void testJoin() {
        node1.join(null);
        assertNull(node1.next());
        node1.join(node2);
        assertEquals(node1.next(), node2);
        assertEquals(node2.previous(), node1);

        Exception thrown = null;
        try {
            node1.join(node3);
        } catch (Exception e) {
            thrown = e;
        }
        
        assertTrue(thrown instanceof IllegalStateException);
        assertEquals("The node pointers must be null", thrown.getMessage());
        try {
            node3.join(node2);
        } catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalStateException);
        assertEquals("The node pointers must be null", thrown.getMessage());
    }

    /**
     * test the split method
     */
    public void testSplit() {
        node1.join(node2);
        assertEquals(node1.split(), node2);
        assertNull(node1.next());
        assertNull(node2.previous());
        assertNull(node1.split());
    }

    /**
     * test the newData method
     */
    public void testNewData() {
        node1.setData("newData");
        assertEquals("newData", node1.data());
    }
}
