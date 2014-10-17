import junit.framework.TestCase;

/**
 * test the Block class
 * @author jbruzek
 * @author sucram20
 * @version 2014.10.14
 */
public class BlockTest extends TestCase {

    private Block b;
    
    /**
     * setup before the tests run
     */
    public void setUp() {
        b = new Block();
    }
    
    /**
     * test the constructor
     */
    public void testBlock() {
        Block block = new Block(1, 14);
        
        assertEquals(1, block.getSize());
        assertEquals(14, block.getPosition());
    }
    
    /**
     * test the size getter/setter
     */
    public void testSize() {
        b.setSize(29328);
        
        assertEquals(29328, b.getSize());
    }
    
    /**
     * test the position getter/setter
     */
    public void testPosition() {
        b.setPosition(5);
        
        assertEquals(5, b.getPosition());
    }
    
    /**
     * test compare to
     */
    public void testCompareTo() {
    	Block b = new Block(1, 1);
    	Block one = new Block(1, 0);
    	Block two = new Block(1, 1);
    	Block three = new Block(1, 2);
    	Block four = new Block(0, 1);
    	Block five = new Block(2, 1);
    	
    	assertEquals(1, b.compareTo(one));
    	assertEquals(1, b.compareTo(two));
    	assertEquals(1, b.compareTo(three));
    	assertEquals(1, b.compareTo(four));
    	assertEquals(1, b.compareTo(five));
    }
}
