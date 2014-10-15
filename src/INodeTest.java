import org.junit.Before;

import junit.framework.TestCase;

/**
 * test the INode class
 * 
 * @author jbruzek sucram20
 * @version 2014.10.14
 */
public class INodeTest extends TestCase {
    
    private INode i;

    /**
     * set up before running tests
     */
    @Before
    public void setUp() {
        i = new INode();
    }

    /**
     * test the default constructor and the second constructor implicitely test
     * numRecs and getKeyV
     */
    public void testConstructors() {
        assertEquals(0, i.numRecs());

        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);

        i = new INode(k);

        assertEquals(1, i.numRecs());
        assertEquals(k, i.getKeyV(0));
    }

    /**
     * test the isLeaf method
     */
    public void testIsLeaf() {
        assertFalse(i.isLeaf());
    }

    /**
     * test the search method when the KVPair exists
     */
    public void testSearch1() {
        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);
        MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(4);
        KVPair b = new KVPair(a, s);

        i.insert(k);
        i.insert(b);

        assertEquals(1, i.search(b));
        assertEquals(0, i.search(k));
    }

    /**
     * test the search method when the KVPair does notexists
     */
    public void testSearch2() {
        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);

        assertEquals(-1, i.search(k));
    }

    /**
     * test the insert method
     */
    public void testInsert() {
        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);
        MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(4);
        KVPair b = new KVPair(a, s);

        i.insert(k);
        i.insert(b);

        MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(0);
        KVPair e = new KVPair(q, w);

        i.insert(e);

        assertEquals(0, i.search(e));
        assertEquals(k, i.getKeyV(1));
        assertEquals(b, i.getKeyV(2));
    }

    /**
     * test the split method
     */
    public void testSplit() {
        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);
        MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(4);
        KVPair b = new KVPair(a, s);
        MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(0);
        KVPair e = new KVPair(q, w);

        i.insert(k);
        i.insert(b);
        i.insert(e);

        INode temp = i.split();

        assertEquals(1, i.numRecs());
        assertEquals(1, temp.numRecs());
        assertEquals(i, temp.getChild(0));
        assertEquals(1, temp.getChild(1).numRecs());
        assertEquals(e, i.getKeyV(0));
        assertEquals(k, temp.getKeyV(0));
        assertEquals(b, temp.getChild(1).getKeyV(0));
    }

    /**
     * test the setChild and getChildmethod
     */
    public void testChild() {
        assertNull(i.getChild(2));

        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);

        INode temp = new INode(k);

        i.setChild(1, temp);

        assertEquals(temp, i.getChild(1));
    }

    /**
     * test the promote method
     */
    public void testPromote() {
        MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(0);
        KVPair e = new KVPair(q, w);
        LNode temp1 = new LNode(e);

        MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(4);
        KVPair b = new KVPair(a, s);
        LNode temp2 = new LNode(b);

        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);
        LNode temp3 = new LNode(k);

        i.setChild(0, temp1);
        i.setChild(1, temp2);

        i.promote(temp1, temp3);

        assertEquals(temp1, i.getChild(0));
        assertEquals(temp3, i.getChild(1));
        assertEquals(temp2, i.getChild(2));

    }

    /**
     * test the setKey method
     */
    public void testSetKey() {
        MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(0);
        KVPair e = new KVPair(q, w);

        i.setKey(1, e);

        assertEquals(e, i.getKeyV(1));
    }

    /**
     * test the isFull Method
     */
    public void testIsFull() {
        MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(2);
        KVPair k = new KVPair(m, v);
        MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(4);
        KVPair b = new KVPair(a, s);
        MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(0);
        KVPair e = new KVPair(q, w);

        i.insert(e);
        i.insert(k);
        i.insert(b);

        assertTrue(i.isFull());
    }
}
