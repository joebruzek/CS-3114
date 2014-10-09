import junit.framework.TestCase;

/**
 * A test case for the MemoryManager Class
 *
 * @author jbruzek, sucram20
 */
public class MemoryManagerTest extends TestCase {

    private MemoryManager manager;

    /**
     * initialize the test nodes.
     */
    public void setUp() {
        manager = new MemoryManager(100);
    }

    /**
     * test the constructor
     */
    public void testMemoryManager() {
        assertEquals(100, manager.getSize());
    }
    
//    /**
//     * test the getREcord method
//     */
//    public void testGetRecord() {
//    	byte[] a = {
//    			(byte)'a',
//    			(byte)'b',
//    			(byte)'c',
//    			(byte)'d'
//    	};
//    	MemHandle h = manager.insert(a);
//    	
//    	assertEquals(a, manager.getRecord(h));
//    }

    /**
     * test the insert Method
     */
    public void testInsert() {
        String str = "Hello World";
        char[] buffer = str.toCharArray();
        byte[] b = new byte[buffer.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) buffer[i];
        }

        manager.insert(b);

        assertEquals("(11,89)", manager.getBlocks().toString());
    }

    /**
     * test the insert when the pool needs to expand
     */
    public void testInsert2() {
        MemoryManager test = new MemoryManager(10);

        String str = "12345678901";
        char[] buffer = str.toCharArray();
        byte[] b = new byte[buffer.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) buffer[i];
        }

        MemHandle h;
        h = test.insert(b);

        assertEquals(0, h.getPosition());

        assertEquals("(11,9)", test.getBlocks().toString());
    }

    /**
     * test the increasePool method
     */
    public void testIncreasePool() {
        manager.increasePool();

        assertEquals(200, manager.getSize());

        manager.increasePool();

        assertEquals(300, manager.getSize());
    }
    
    /**
     * test to see if the pool is increased
     */
    public void testIncreased() {
    	manager.increased();
    	assertFalse(manager.getDub());
    	
    }
    
    /**
     * test the get length method
     */
    public void testGetLength() {
    	int length = 51;
    	byte[] pool = manager.getPool();
    	
    	// this conversion method is tested in MemmenTest
    	pool[20] = (byte) (length & 0xFF);
    	pool[21] = (byte) ((length >> 8) & 0xFF);
    	
    	MemHandle h = new MemHandle(20);
    	
    	assertEquals(51, manager.getLength(h));
    }
}

