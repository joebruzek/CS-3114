import junit.framework.TestCase;

import org.junit.Before;

/**
 * test the KVPair class
 * @author jbruzek sucram20
 *
 */
public class KVPairTest extends TestCase {

	private KVPair p;
	MemHandle m;
	MemHandle v;
	
	/**
	 * set up the tests
	 */
	@Before
	public void setUp() {
		m = new MemHandle(1);
		v = new MemHandle(2);
		p = new KVPair(m, v);
	}
	
	/**
	 * test the toString
	 */
	public void testToString() {
		String ms = m.toString();
		String vs = v.toString();
		assertEquals(ms + " " + vs, p.toString());
	}
	
	/**
	 * test key
	 */
	public void testKey() {
		assertEquals(m, p.key());
	}
	
	/**
	 * test the value
	 */
	public void testValue() {
		assertEquals(v, p.value());
	}
	
	/**
	 * test compare to when the same
	 */
	public void testCompareTo1() {
		MemHandle a = new MemHandle(1);
		MemHandle b = new MemHandle(2);
		KVPair s = new KVPair(a, b);
		
		assertEquals(0, p.compareTo(s));
		assertEquals(0, p.compareTo(a));
	}
	
	/**
	 * test compare to when less
	 */
	public void testCompareTo2() {
		MemHandle a = new MemHandle(2);
		MemHandle b = new MemHandle(2);
		KVPair s = new KVPair(a, b);
		
		assertEquals(-1, p.compareTo(s));
		assertEquals(-1, p.compareTo(a));
	}
	
	/**
	 * test compare to when greater
	 */
	public void testCompareTo3() {
		MemHandle a = new MemHandle(0);
		MemHandle b = new MemHandle(2);
		KVPair s = new KVPair(a, b);
		
		assertEquals(1, p.compareTo(s));
		assertEquals(1, p.compareTo(a));
	}
}
