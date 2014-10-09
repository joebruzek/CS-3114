import junit.framework.TestCase;

/**
 * test the Pair class
 * @author jbruzek, sucram20
 *
 */
public class PairTest extends TestCase {

	private Pair<String, String> pair;
	
	/**
	 * setup the tests
	 */
	public void setUp() {
		pair = new Pair<String, String>();
	}
	
	/**
	 * test the default constructor
	 */
	public void testConstructor1() {
		assertNull(pair.getFirst());
		assertNull(pair.getSecond());
	}
	
	/**
	 * test the constructor
	 */
	public void testConstructor2() {
		Pair<String, String> test = new Pair<String, String>("first", "second");
		
		assertEquals("first", test.getFirst());
		assertEquals("second", test.getSecond());
	}
	
	/**
	 * test the first methods
	 */
	public void testFirst() {
		pair.setFirst("first");
		
		assertEquals("first", pair.getFirst());
	}
	
	/**
	 * test the second methods
	 */
	public void testSecond() {
		pair.setSecond("second");
		
		assertEquals("second", pair.getSecond());
	}
}