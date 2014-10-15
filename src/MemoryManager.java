/**
 * Memory Manager does stuff
 *
 * @author jbruzek, sucram20
 * @version 2014.10.14
 */
public class MemoryManager implements MemManager {
    private byte[] pool;
    private int size;
    private int initialSize;
    private FreeBlockList blocks;
    private boolean dub = false;

    /**
     * Initilize the memory manager
     * @param poolsize the size of the mem pool
     */
    public MemoryManager(int poolsize) {
        size = poolsize;
        initialSize = size;
        blocks = new FreeBlockList(poolsize);
        pool = new byte[size];
    }

    /**
     * insert the info into the memory pool
     * 
     * @param info a byte array to isert
     * @return a handle pointing to the location in memory
     */
    public MemHandle insert(byte[] info) {
        int position = blocks.insertItem(info.length);

        if (position == -1) {
            increasePool();
            return insert(info);
        }

        MemHandle handle = new MemHandle(position);

        for (int i = 0; i < info.length; i++, position++) {
            pool[position] = info[i];
        }

        return handle;
    }

    /**
     * remove info from the memory pool
     * 
     * @param h
     *            the handle for the data
     */
    public void release(MemHandle h) {
        int position = h.getPosition();
        int sizel = getLength(h);

        blocks.removeItem(position, sizel + 2);
    }

    /**
     * get info from the memory pool
     * 
     * @param h
     *            the handle for the data
     * @return the data
     */
    public byte[] getRecord(MemHandle h) {
        int position = h.getPosition();
        int size1 = getLength(h);
        byte[] record = new byte[size1];

        for (int i = 0; i < size1; i++, position++) {
            record[i] = pool[position + 2];
        }

        return record;
    }

    /**
     * increase the size of the memory pool
     */
    public void increasePool() {
        byte[] newPool = new byte[size + initialSize];
        for (int i = 0; i < size; i++) {
            newPool[i] = pool[i];
        }
        size += initialSize;
        pool = newPool;
        System.out.println("Memory pool expanded to be " + size + " bytes.");
        dub = true;
    }

    /**
     * set increased to false
     */
    public void increased() {
        dub = false;
    }

    /**
     * get the freeBlockList
     * 
     * @return the freeblocklist
     */
    public FreeBlockList getBlocks() {
        return blocks;
    }

    /**
     * get dub
     * 
     * @return dub
     */
    public boolean getDub() {
        return dub;
    }

    /**
     * get names from an array of handles
     * 
     * @param handles handles array
     * @return the names in an array
     */
    public String[] getNames(MemHandle[] handles) {
        int count = 0;
        for (int i = 0; i < handles.length; i++) {
            if (handles[i] == null) {
                count++;
            }
        }
        String[] names = new String[handles.length - count];
        for (int i = 0; i < names.length; i++) {
            names[i] = getString(getRecord(handles[i]), handles[i]);
        }
        return names;
    }

    /**
     * get the length of the string from the first two bytes of the byte array
     * 
     * @param h the memhandle to get
     * @return the length
     */
    public int getLength(MemHandle h) {
        int position = h.getPosition();
        byte[] bytes = { pool[position], pool[position + 1] };
        return (int) ((bytes[1] << 8) | (bytes[0]));
    }

    /**
     * get the string value from a byte array
     * 
     * @param b the byte array
     * @param h the memhandle
     * @return the string value of the byte array
     */
    public String getString(byte[] b, MemHandle h) {
        int length = getLength(h);
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) b[i];
        }
        return new String(chars);
    }

    /**
     *
     * These methods are for testing purposes.
     *
     */

    /**
     * get the memory pool
     * 
     * @return the pool
     */
    public byte[] getPool() {
        return pool;
    }

    /**
     * get the size
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }
}