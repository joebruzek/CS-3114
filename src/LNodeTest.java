import org.junit.Before;

import junit.framework.TestCase;

/**
 * test the LNode class
 * @author jbruzek sucram20
 *
 */
public class LNodeTest extends TestCase {

	private LNode l;
	
	/**
	 * set up the tests
	 */
	@Before
	public void setUp() {
		l = new LNode();
	}
	
	/**
	 * test the default constructor and the second constructor
	 * implicitely test numRecs and getKeyV
	 */
	public void testConstructors() {
		assertEquals(0, l.numRecs());
		
		MemHandle m = new MemHandle(1);
		MemHandle v = new MemHandle(2);
		KVPair k = new KVPair(m, v);
		
		l = new LNode(k);
		
		assertEquals(1, l.numRecs());
		assertEquals(k, l.getKeyV(0));
	}
	
	/**
	 * test the get and set next methods
	 */
	public void testNext() {
		LNode temp = new LNode();
		l.setNext(temp);
		
		assertEquals(temp, l.next());
	}
	
	/**
	 * test the isLeaf method
	 */
	public void testIsLeaf() {
		assertTrue(l.isLeaf());
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
		
		l.insert(k);
		l.insert(b);
		
		assertEquals(1, l.search(b));
		assertEquals(0, l.search(k));
	}
	
	/**
	 * test the search method when the KVPair does notexists
	 */
	public void testSearch2() {
		MemHandle m = new MemHandle(1);
		MemHandle v = new MemHandle(2);
		KVPair k = new KVPair(m, v);
		
		assertEquals(-1, l.search(k));
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
		
		l.insert(k);
		l.insert(b);
		
		MemHandle q = new MemHandle(0);
		MemHandle w = new MemHandle(0);
		KVPair e = new KVPair(q, w);
		
		l.insert(e);
		
		assertEquals(0, l.search(e));
		assertEquals(k, l.getKeyV(1));
		assertEquals(b, l.getKeyV(2));
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
		
		l.insert(e);
		l.insert(k);
		l.insert(b);
		
		TTNode temp = l.split();
		
		assertEquals(1, l.numRecs());
		assertEquals(2, temp.numRecs());
		assertEquals(e, l.getKeyV(0));
		assertEquals(k, temp.getKeyV(0));
		assertEquals(b, temp.getKeyV(1));
		assertEquals(temp, l.next());
	}
	
	/**
	 * test the setChild  and getChildmethod
	 */
	public void testChild() {
		assertNull(l.getChild(1));
		
		try {
			l.setChild(1, new LNode());
			fail("Should throw an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals("Leaf nodes have no children", e.getMessage());
		}
	}
	
	/**
	 * test the setKey method
	 */
	public void testSetKey() {
		MemHandle q = new MemHandle(0);
		MemHandle w = new MemHandle(0);
		KVPair e = new KVPair(q, w);
		
		l.setKey(1, e);
		
		assertEquals(e, l.getKeyV(1));
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
		
		l.insert(e);
		l.insert(k);
		l.insert(b);
		
		assertTrue(l.isFull());
	}
	
	/**
	 * test the getKey method
	 */
	public void testGetKey() {
		try {
			l.getKey(2);
			fail("Should throw an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals("LNodes only store KVPairs.", e.getMessage());
		}
	}
}






















