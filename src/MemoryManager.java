/**
 * Memory Manager does stuff
 *
 * @author: jbruzek, sucram20
 */
public class MemoryManager implements MemManager {

    private byte[] pool;
    private int size;
    private int initialSize;
    private FreeBlockList blocks;
    private boolean dub = false;

    /**
     * Initilize the memory manager
     */
    public MemoryManager(int poolsize) {
        size = poolsize;
        initialSize = size;
        blocks = new FreeBlockList(poolsize);
        pool = new byte[size];
    }

    /**
     * insert the info into the memory pool
     * @param info
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
     * @param h the handle for the data
     */
    public void release(MemHandle h) {
        int position = h.getPosition();
        int size = getLength(h);

        blocks.removeItem(position, size + 2);
    }

    /**
     * get info from the memory pool
     * @param h the handle for the data
     * @return the data
     */
    public byte[] getRecord(MemHandle h) {
        int position = h.getPosition();
        int size = getLength(h);
        byte[] record = new byte[size];

        for (int i = 0; i < size; i++, position++) {
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
        dub = true;
    }
    
    public void increased() {
    	if (dub) {
    		System.out.println("Memory pool expanded to be " + size + " bytes.");
    	}
    	dub = false;
    }

    /**
     * get the freeBlockList
     * @return
     */
    public FreeBlockList getBlocks() {
        return blocks;
    }
    
    /**
     * get dub
     * @return dub
     */
    public boolean getDub() {
    	return dub;
    }
    
    /**
     * get names from an array of handles
     * @param handles
     * @return
     */
    public String[] getNames(MemHandle[] handles) {
    	String[] names = new String[handles.length];
    	for (int i = 0; i < handles.length; i++) {
    		names[i] = getString(getRecord(handles[i]), handles[i]);
    	}
    	return names;
    }
    
    /**
     * get the length of the string from the first two bytes of the byte array
     * @param data
     * @return
     */
    public int getLength(MemHandle h)
    {
    	int position = h.getPosition();
        byte[] bytes = {
        		pool[position],
        		pool[position + 1]
        };
       return (int)((bytes[1]<<8) | (bytes[0]));
    }
    
    /**
     * get the string value from a byte array
     * @param b
     * @param h
     * @return
     */
    public String getString(byte[] b, MemHandle h) {
    	int length = getLength(h);
    	char[] chars = new char[length];
    	for (int i = 0; i < length; i++) {
    		chars[i] = (char)b[i];
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
     * @return the pool
     */
    public byte[] getPool() {
        return pool;
    }

    /**
     * get the size
     * @return the size
     */
    public int getSize() {
        return size;
    }
}