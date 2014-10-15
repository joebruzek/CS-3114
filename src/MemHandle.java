/**
 * A handle that points to a location in the memory pool
 *
 * @author jbruzek, sucram20
 * @version 2014.10.14
 */
public class MemHandle {
    private int position;
    private long key;

    /**
     * initialize the handle
     * @param position the position
     */
    public MemHandle(int position) {
        this.position = position;
        key = -1;
    }

    /**
     * Get the position
     * 
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the key
     * 
     * @param k the key
     */
    void setKey(long k) {
        key = k;
    }

    /**
     * Gets the key
     * 
     * @return key
     */
    public long getKey() {
        return key;
    }

    /**
     * compare this MemHandle with another.
     * 
     * @param m the MemHandle to compare to
     * @return -1 if this is less than, 0 if equals, 1 if this is greater than
     */
    public int compareTo(MemHandle m) {
        int value;
        if (position > m.getPosition()) {
            value = 1;
        } 
        else if (position == m.getPosition()) {
            value = 0;
        } 
        else {
            value = -1;
        }

        return value;
    }

}
