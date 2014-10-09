/**
 * A handle that points to a location in the memory pool
 *
 * Author: jbruzek, sucram20
 */
public class MemHandle {

    private int position;
    private long key;

    /**
     * initialize the handle
     */
    public MemHandle(int position) {
        this.position = position;
        key = -1;
    }

    /**
     * Get the position
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the key
     * @param k
     */
    void setKey(long k) {
        key = k;
    }

    /**
     * Gets the key
     * @return key
     */
    public long getKey() {
        return key;
    }
    
    /**
     * compare this MemHandle with another. 
     * @param m
     * @return -1 if this is less than, 0 if equals, 1 if this is greater than
     */
    public int compareTo(MemHandle m) {
    	int value;
    	if (position > m.getPosition()) {
    		value = 1;
    	} else if (position == m.getPosition()) {
    		value = 0;
    	} else {
    		value = -1;
    	}
    	
    	return value;
    }

}
