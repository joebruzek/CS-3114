/**
 * block is a piece of data on the free block list
 * @author jbruzek
 * @author sucram20
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
	 * @param size
	 * @param position
	 */
	public Block(int size, int position) {
		this.size = size;
		this.position = position;
	}
	
	/**
     * get the position
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * get the size
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * set the position
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * set the size
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

}
