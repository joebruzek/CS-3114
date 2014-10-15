import junit.framework.TestCase;

/**
 * A test case for the FreeBlockList Class
 *
 * @author jbruzek, sucram20
 * @version 2014.10.14
 */
public class FreeBlockListTest extends TestCase {
	
	private FreeBlockList blocks;

	/**
	 * initialize the list
	 */
	public void setUp() {
		blocks = new FreeBlockList(100);

	}

	/**
	 * test the constructor
	 */
	public void testFreeBlockList() {
		blocks.head();

		assertEquals(100, blocks.getSize());
		assertEquals(1, blocks.size());
		assertEquals(100, blocks.next().getSize());
	}

	/**
	 * Test insert item with space available
	 */
	public void testInsertItem() {
		blocks.insertItem(40);
		blocks.insertItem(50);
		
		blocks.head();

		assertEquals(10, blocks.next().getSize());
	}
	
	/**
	 * Test insert item with space available
	 */
	public void testInsertItem1() {
		blocks.insertItem(40);
		
		blocks.head();

		assertEquals(60, blocks.next().getSize());
	}

	/**
	 * Test insert item with NO space available
	 *
	 * implicitely tests the expandList method
	 */
	public void testInsertItem2() {
		blocks.insertItem(150);

		assertEquals(200, blocks.getSize());
		blocks.insertItem(150);
		blocks.head();
		assertEquals(50, blocks.next().getSize());
		assertEquals(1, blocks.size());
	}

	/**
	 * Test insert item with exactly enough space
	 */
	public void testInsertItem3() {
		int pos = blocks.insertItem(100);
		blocks.head();

		assertEquals(0, pos);
		assertEquals(0, blocks.size());
	}

	/**
	 * Test the find fit method when space is available
	 */
	public void testFindFit() {
		blocks.insertItem(100);
		blocks.removeItem(5, 5);
		blocks.removeItem(50, 20);

		Node<Block> test = blocks.findFit(4);
		assertEquals(5, test.data().getSize());
	}
	
	/**
	 * Test the find fit method when space is available
	 */
	public void testFindFit1() {
		Node<Block> test = blocks.findFit(4);
		assertEquals(100, test.data().getSize());
	}

	/**
	 * Test the find fit method when NO space is available
	 */
	public void testFindFit2() {
		blocks.insertItem(100);

		Node<Block> test = blocks.findFit(4);
		assertNull(test);
	}

	/**
	 * Test the find fit method
	 */
	public void testRemove() {
	    blocks.insertItem(100);
	    blocks.removeItem(50, 5);
	    blocks.removeItem(5, 20);
	    blocks.removeItem(25, 25);
	    
	    blocks.head();
	    //assertEquals(50, blocks.next().getSize());
	    assertEquals(1, blocks.size());
	}

	/**
	 * Test the find fit method
	 */
	public void testRemove2() {
        blocks.insertItem(100);
        blocks.removeItem(50, 5);
        blocks.removeItem(5, 20);
        blocks.removeItem(25, 20);

        blocks.head();
        assertEquals(40, blocks.next().getSize());
        assertEquals(5, blocks.next().getSize());
        assertEquals(2, blocks.size());
    }

	/**
	 * Test the find fit method
	 */
	public void testRemove3() {
        blocks.insertItem(100);
        blocks.removeItem(50, 5);
        blocks.removeItem(5, 20);
        blocks.removeItem(30, 15);

        blocks.head();
        assertEquals(20, blocks.next().getSize());
        assertEquals(15, blocks.next().getSize());
        assertEquals(5, blocks.next().getSize());
        assertEquals(3, blocks.size());
    }
	
	/**
	 * Test the find fit method
	 */
	public void testRemove4() {
        blocks.insertItem(100);
        blocks.removeItem(50, 5);
        blocks.removeItem(45, 5);

        blocks.head();
        assertEquals(10, blocks.next().getSize());
        assertEquals(1, blocks.size());
    }

	/**
     * test the toString method
     */
    public void testToString() {
        assertEquals("(0,100)", blocks.toString());

        blocks.insertItem(50);
        assertEquals("(50,50)", blocks.toString());

        blocks.removeItem(10, 10);
        assertEquals("(10,10) -> (50,50)", blocks.toString());
    }

    /**
     * test expanding the list
     */
    public void testExpandList() {
        blocks.expandList();

        assertEquals(200, blocks.getSize());

        assertEquals("(0,200)", blocks.toString());
    }
    
    /**
     * test expanding the list
     */
    public void testExpandList2() {
    	blocks.expandList();
    	
    	assertEquals(200, blocks.getSize());
    }
    
    /**
     * test expanding the list
     */
    public void testExpandList3() {
    	blocks.insertItem(100);
    	blocks.removeItem(50, 5);
    	blocks.expandList();
    	
    	assertEquals(200, blocks.getSize());
    }

}





