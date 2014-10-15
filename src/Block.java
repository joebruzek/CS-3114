/**
 * block is a piece of data on the free block list
 * @author jbruzek
 * @author sucram20
 * @version 2014.10.14
 */
public class Block {

    private int position = 0;
    private int size = 0;
    
    /**
     * empty constructor
     */
    public Block() {
        //nothing
    }
    
    /**
     * initialize the block with size and position
     * @param size the size
     * @param position the position
     */
    public Block(int size, int position) {
        this.size = size;
        this.position = position;
    }
    
    /**
     * get the position
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * get the size
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * set the position
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * set the size
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

}
