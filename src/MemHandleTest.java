import junit.framework.TestCase;

/**
 * A test case for the MemHandle Class
 *
 * @author jbruzek, sucram20
 */
public class MemHandleTest extends TestCase {

	private MemHandle handle;

	/**
	 * initialize the test nodes.
	 */
	public void setUp() {
		handle = new MemHandle(30);
	}

	/**
	 * test the getPosition method
	 */
	public void testGetPosition() {
		assertEquals(30, handle.getPosition());
	}
}
