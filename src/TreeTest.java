import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;

import junit.framework.TestCase;

/**
 * test the tree class
 * @author jbruzek sucram20
 *
 */
public class TreeTest extends TestCase {

	private Tree tree;

	/**
	 * set up a tree before the tests start
	 */
	@Before
	public void setUp() {
		tree = new Tree();
	}

	// ----------------------------------------------------------
	/**
	 * Just cuz
	 */
	public void printSomething() {
		System.out.println("something");
	}

	// ----------------------------------------------------------
	/**
	 * tests insert for multiple inserts
	 */
	public void testDelete()
	{
		assertNull(tree.getRoot());
		int j = 3;
	    for (int i = 2; i <= 100; i*= 3 , j *= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(new KVPair(m, v));
            MemHandle h = new MemHandle(j);
            MemHandle x = new MemHandle(j + 3);
            tree.insert(new KVPair(h, x));
        }

	    for (int i = 2; i <= 100; i*= 3 , j *= 3) {
	        MemHandle m = new MemHandle(i);
	        MemHandle v = new MemHandle(i + 5);
	        //tree.insert(new KVPair(m, v));
	        tree.delete(tree.getRoot(), new KVPair(m, v));
	    }
	    MemHandle h = new MemHandle(2);
        MemHandle x = new MemHandle(7);
        //tree.insert(new KVPair(h, x));
        tree.delete(tree.getRoot(), new KVPair(h, x));
	    assertFalse(tree.getRoot().isLeaf());
	    assertFalse(tree.getRoot().getChild(0).isLeaf());
	    assertFalse(tree.getRoot().getChild(1).isLeaf());
	    tree.print(tree.getRoot(), tree.depth());

	}

	/**
	 * test insert on empty tree
	 */
	public void testInsert1() {
		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(5);
        tree.insert(new KVPair(m, v));

        assertEquals(m, tree.getRoot().getKeyV(0).key());
        assertEquals(1, tree.getRoot().numRecs());
        assertTrue(tree.getRoot().isLeaf());
	}

	/**
	 * test insert in one split case
	 */
	public void testInsert2() {
		for (int i = 1; i <= 3; i++) {
			MemHandle m = new MemHandle(i);
	        MemHandle v = new MemHandle(i + 4);
	        tree.insert(new KVPair(m, v));
		}

		assertFalse(tree.getRoot().isLeaf());
		TTNode one = tree.getRoot().getChild(0);
		TTNode two = tree.getRoot().getChild(1);
		assertEquals(1, one.numRecs());
		assertEquals(2, two.numRecs());
		assertEquals(1, one.getKeyV(0).key().getPosition());
		assertEquals(5, one.getKeyV(0).value().getPosition());
		assertEquals(2, two.getKeyV(0).key().getPosition());
		assertEquals(6, two.getKeyV(0).value().getPosition());
		assertEquals(3, two.getKeyV(1).key().getPosition());
		assertEquals(7, two.getKeyV(1).value().getPosition());
	}

	/**
	 * test inserting a duplicate into the tree
	 */
	public void testInsert3() {
		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(5);
        tree.insert(new KVPair(m, v));

        assertFalse(tree.insert(new KVPair(m, v)));
        assertEquals(1, tree.getRoot().numRecs());
	}

	/**
	 * Test insert and print for the correct number of nodes
	 */
	public void testInsert4()
	{
	    for (int i = 0; i < 17; i++)
	    {
	        MemHandle m = new MemHandle(i);
	        MemHandle v = new MemHandle(i + 5);
	        tree.insert(new KVPair(m, v));
	    }
	    tree.printTree();
	}

	/**
     * Test insert and print for the correct number of nodes
     */
    public void testInsert5()
    {
        for (int i = 0; i < 16; i++)
        {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(new KVPair(m, v));
        }
        tree.printTree();
    }

	/**
	 * test the print and printTree methods
	 */
	public void testPrint1() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        for (int i = 1; i <= 5; i++) {
        	MemHandle m = new MemHandle(i);
        	MemHandle v = new MemHandle(i + 5);
        	tree.insert(new KVPair(m, v));
        }

        tree.printTree();

        try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String whatWasPrinted = new String(baos.toByteArray());
        String[] linesOfOutput = whatWasPrinted.split(
                System.getProperty("line.separator"));
        assertEquals(8, linesOfOutput.length);
        assertEquals("Printing 2-3 tree:", linesOfOutput[0]);
        assertEquals("3 8", linesOfOutput[1]);
        assertEquals("  2 7", linesOfOutput[2]);
        assertEquals("    1 6", linesOfOutput[3]);
        assertEquals("    2 7", linesOfOutput[4]);
        assertEquals("  4 9", linesOfOutput[5]);
        assertEquals("    3 8", linesOfOutput[6]);
        assertEquals("    4 9 5 10", linesOfOutput[7]);
	}

	/**
	 * test the print and printTree methods
	 * when the tree is empty
	 */
	public void testPrint2() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        tree.printTree();

        try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String whatWasPrinted = new String(baos.toByteArray());
        String[] linesOfOutput = whatWasPrinted.split(
                System.getProperty("line.separator"));
        assertEquals(1, linesOfOutput.length);
        assertEquals("Printing 2-3 tree:", linesOfOutput[0]);
	}

	/**
	 * test search
	 */
	public void testSearch() {
		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(6);
        KVPair k = new KVPair(m, v);
		tree.setRoot(new LNode(k));

		assertTrue(tree.search(tree.getRoot(), k));
        KVPair x = new KVPair(v, m);
		assertFalse(tree.search(tree.getRoot(), x));

		INode root = new INode();
		root.insert(k);
		LNode temp = new LNode();
		m = new MemHandle(0);
        v = new MemHandle(6);
        k = new KVPair(m, v);
		temp.insert(k);

		root.setChild(0, temp);
		tree.setRoot(root);
		assertTrue(tree.search(tree.getRoot(), k));
        x = new KVPair(v, m);
		assertFalse(tree.search(tree.getRoot(), x));

		m = new MemHandle(0);
        v = new MemHandle(10);
        k = new KVPair(m, v);
		temp.insert(k);
		assertTrue(tree.search(tree.getRoot(), k));
        x = new KVPair(v, m);
		assertFalse(tree.search(tree.getRoot(), x));
	}

	/**
	 * tests searching for a key in the tree
	 */
	public void testSearchTree()
	{
        int j= 3;
	    for (int i = 2; i <= 100; i*= 3 , j *= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(new KVPair(m, v));
            MemHandle h = new MemHandle(j);
            MemHandle x = new MemHandle(j + 3);
            tree.insert(new KVPair(h, x));
        }
	    for (int i = 2; i <= 100; i*= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            assertTrue(tree.search(tree.getRoot(), new KVPair(m, v)));
        }
	    MemHandle a = new MemHandle(0);
        MemHandle b = new MemHandle(7);
        KVPair pair = new KVPair(a, b);
        assertFalse(tree.search(tree.getRoot(), pair));

	}

	/**
	 * test the getToFirst method
	 */
	public void testGetToFirst() {
		assertEquals(tree.getRoot(), tree.getToFirst(tree.getRoot()));
		MemHandle m = new MemHandle(0);
        MemHandle v = new MemHandle(1);
	    tree.insert(new KVPair(m, v));

	    assertEquals(tree.getRoot(), tree.getToFirst(tree.getRoot()));

	    MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(2);
	    tree.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(0);
        MemHandle r = new MemHandle(3);
	    tree.insert(new KVPair(e, r));

	    assertEquals(1, tree.getToFirst(tree.getRoot()).getKeyV(0).value().getPosition());
	    //damn that was a long assertion statement
	    assertEquals(1, tree.getToFirst(tree.getRoot()).numRecs());
	}

	/**
	 * test the numElements method
	 */
	public void testNumElements() {
		assertEquals(0, tree.numElements());

		MemHandle m = new MemHandle(0);
        MemHandle v = new MemHandle(1);
	    tree.insert(new KVPair(m, v));
		MemHandle q = new MemHandle(0);
        MemHandle w = new MemHandle(2);
	    tree.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(0);
        MemHandle r = new MemHandle(3);
	    tree.insert(new KVPair(e, r));

	    assertEquals(3, tree.numElements());
	}

	/**
	 * test the find method
	 */
	public void testFind() {
		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(10);
	    tree.insert(new KVPair(m, v));
		MemHandle q = new MemHandle(2);
        MemHandle w = new MemHandle(20);
	    tree.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(3);
        MemHandle r = new MemHandle(30);
	    tree.insert(new KVPair(e, r));
	    MemHandle a = new MemHandle(3);
        MemHandle s = new MemHandle(300);
	    tree.insert(new KVPair(a, s));

	    assertEquals(0, tree.find(new MemHandle(50)).length);
	    assertEquals(1, tree.find(new MemHandle(1)).length);
	    assertEquals(10, tree.find(new MemHandle(1))[0].getPosition());

	    MemHandle[] found = tree.find(new MemHandle(3));
	    assertEquals(2, found.length);
	    assertEquals(30, found[0].getPosition());
	    assertEquals(300, found[1].getPosition());
	}

	/**
	 * test the rootfullcheck method
	 */
	public void testRootFullCheck() {
		INode root = new INode();
		for (int i = 0; i < 3; i++) {
			MemHandle m = new MemHandle(i);
			MemHandle v = new MemHandle(i + 5);
			root.insert(new KVPair(m, v));
		}

		tree.setRoot(root);
		tree.rootFullCheck();
		assertEquals(2, tree.depth());

		LNode rooty = new LNode();
		for (int i = 0; i < 3; i++) {
			MemHandle m = new MemHandle(i);
			MemHandle v = new MemHandle(i + 5);
			rooty.insert(new KVPair(m, v));
		}

		tree.setRoot(rooty);
		tree.rootFullCheck();
		// three because we never reset the depth after last time
		assertEquals(3, tree.depth());

	}

	/**
	 * test the root cases of SplitUp
	 * when root is above leaf nodes
	 */
	public void testSplitUp1() {
		MemHandle a = new MemHandle(5);
        MemHandle s = new MemHandle(5);
		INode root = new INode(new KVPair(a, s));
		LNode one = new LNode();
		LNode two = new LNode();

		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(10);
	    one.insert(new KVPair(m, v));
		MemHandle q = new MemHandle(2);
        MemHandle w = new MemHandle(20);
	    one.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(3);
        MemHandle r = new MemHandle(30);
	    one.insert(new KVPair(e, r));
        two.insert(new KVPair(a, s));
        one.setNext(two);
        two.setPrevious(one);

        root.setChild(0, one);
        root.setChild(1, two);

        tree.setRoot(root);

        tree.splitUp(tree.getRoot(), 0);

        assertEquals(2, tree.getRoot().numRecs());
        assertEquals(1, one.numRecs());
        assertEquals(1, two.numRecs());
        assertEquals(two, one.next().next());
        assertEquals(2, one.next().numRecs());
	}

	/**
	 * test the root cases of SplitUp
	 * when root is above Internal nodes
	 */
	public void testSplitUp2() {
		MemHandle a = new MemHandle(5);
        MemHandle s = new MemHandle(5);
		INode root = new INode(new KVPair(a, s));
		INode one = new INode();
		INode two = new INode();

		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(10);
	    one.insert(new KVPair(m, v));
		MemHandle q = new MemHandle(2);
        MemHandle w = new MemHandle(20);
	    one.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(3);
        MemHandle r = new MemHandle(30);
	    one.insert(new KVPair(e, r));
        two.insert(new KVPair(a, s));

        root.setChild(0, one);
        root.setChild(1, two);

        tree.setRoot(root);

        tree.splitUp(tree.getRoot(), 0);

        assertEquals(2, tree.getRoot().numRecs());
        assertEquals(1, one.numRecs());
        assertEquals(1, two.numRecs());
        assertEquals(one, tree.getRoot().getChild(0));
        assertEquals(two, tree.getRoot().getChild(2));
	}

	/**
	 * test the exists method
	 */
	public void testExists() {
		assertFalse(tree.exists(new MemHandle(3)));

		MemHandle a = new MemHandle(5);
        MemHandle s = new MemHandle(5);
		INode root = new INode(new KVPair(a, s));
		LNode one = new LNode();
		LNode two = new LNode();

		MemHandle m = new MemHandle(1);
        MemHandle v = new MemHandle(10);
	    one.insert(new KVPair(m, v));
		MemHandle q = new MemHandle(2);
        MemHandle w = new MemHandle(20);
	    one.insert(new KVPair(q, w));
	    MemHandle e = new MemHandle(3);
        MemHandle r = new MemHandle(30);
	    one.insert(new KVPair(e, r));
        two.insert(new KVPair(a, s));
        one.setNext(two);
        two.setPrevious(one);

        root.setChild(0, one);
        root.setChild(1, two);

        tree.setRoot(root);

        assertTrue(tree.exists(r));
	}
}
