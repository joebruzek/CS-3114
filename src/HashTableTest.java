import junit.framework.TestCase;

/**
 * test the HashTable class
 * @author jbruzek, sucram20
 *
 */
public class HashTableTest extends TestCase
{
    private HashTable table;
    private MemHandle a;
    private MemHandle b;
    private MemHandle c;

    /**
     * set up the hash table for the tests
     */
    public void setUp()
    {
        table = new HashTable(10, "this");
        a = new MemHandle(1);
        b = new MemHandle(2);
        c = new MemHandle(3);
        table.hashInsert("a", a);
        table.hashInsert("b", b);
        table.hashInsert("c", c);
    }

    /**
     * test the insert method
     */
    public void testInsert() {
        MemHandle d = new MemHandle(4);
        table.hashInsert("Dell", d);
        assertTrue(table.searchTable("Dell"));
    }

    /**
     * test the delete method
     */
    public void testDelete() {
        table.hashDelete("a");
        assertFalse(table.searchTable("a"));
    }

    /**
     * test the resize method
     */
    public void testResize() {
        MemHandle d = new MemHandle(4);
        MemHandle e = new MemHandle(5);
        MemHandle f = new MemHandle(6);
        table.hashInsert("d", d);
        table.hashInsert("e", e);
        assertEquals(10, table.getSize());
        table.hashInsert("v", f);
        assertEquals(20, table.getSize());

    }
}
