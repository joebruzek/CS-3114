/**
 * FreeBlockList extends DoublyLinkedList to create a list of Free Blocks
 * @author jbruzek
 * @author sucram20
 */
public class FreeBlockList extends DoublyLinkedList<Block>{
	
	private int size;
	private int initialSize;
	
	/**
	 * create a FreeBlockList
	 */
	public FreeBlockList(int size) {
		super();
		this.size = size;
		initialSize = size;
		head();
		addHere(new Block(size, 0));
	}
	
	/**
     * Insert something into the memory pool, removing space from
     * a node in the free block list
     * 
     * the name is a little counterintuitive from a FreeBlockList perspective
     * this is so that it makes more sense when called from the Memory Manager
     * 
     * @param size the size of the block to insert
     * @return the position of the inserted block, -1 if no position is found
     */
    public int insertItem(int size) {
        setCurrent(findFit(size));
        int position;

        if (current() == null) {
            expandList();
            return -1;
        }

        Block b = current().data();
        
        // if it fits exactly, just remove it
        if (b.getSize() == size) {
            remove();
            position = b.getPosition();
        } else { //else squeeze it in
        	Block block = new Block(b.getSize() - size, b.getPosition() + size);
            updateCurrent(block);
            position = block.getPosition() - size;
        }

        return position;
    }
    
    /**
     * remove a node from the list
     * @param position
     * @param size
     */
    public void removeItem(int position, int size) {
        head();
        
        if (size() == 0) {
        	tail();
        } else {
	        while(next().getPosition() < position && hasNext()) {
	        	/* this just moves the current to the right position
	        	 * 
	        	 * ---[A NODE]-----(position)---[CURRENT]--
	        	 */
	        }
        }
        
        Block previous  = current().previous().data();
        Block next = current().data();
        
        if (previous != null && previous.getPosition() + previous.getSize() == position) {
        	previous();
        	Block b = new Block(previous.getSize() + size, previous.getPosition());
        	updateCurrent(b);
        	next();

        }
        else if(next != null && position + size == next.getPosition()) {
        	Block b = new Block(size + next.getSize(), position);
        	updateCurrent(b);
        }
        else {
        	previous();
        	Block b = new Block(size, position);
        	addHere(b);
        	next();
        }

        previous  = current().previous().data();
        if (next != null && previous != null && next.getPosition() == previous.getPosition() + previous.getSize()) {
            Block b = new Block(previous.getSize() + next.getSize(), previous.getPosition());
            remove();
            remove();
            addHere(b);
        }
    }
	
	/**
	 * find the node that fits based on the best fit algorithm
	 * @param size the size of the space to find
	 * @return the node, null if no node is found
	 */
	public Node<Block> findFit(int size) {
		Node<Block> dest = null;
		Block temp = null;
        head();
        int space = this.size;

        for (int i = 0; i < size(); i++) {
        	temp = next();
        	if ((temp.getSize() >= size) && (temp.getSize() <= space)) {
        		dest = current();
        		space = temp.getSize();
        	}
        }

        return dest;
	}
	
	/**
	 * expand the list by the initialSize
	 */
	public void expandList() {
		tail();
		Block last = previous();
		
		if (size() == 0) {
			add(new Block(initialSize, size));
		} else if (last.getPosition() + last.getSize() == size) {
            last.setSize(last.getSize() + initialSize);
            updateCurrent(last);
        } else {
        	add(new Block(initialSize, size));
        }
        
        size += initialSize;
    }
	
	/**
     * make a string representation of the free block list
     * @return
     */
    public String toString() {
    	head();
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for(int i = 0; i < size(); i++) {
        	Block b = next();
        	sb.append(prefix);
            prefix = " -> ";
            sb.append("(");
            sb.append(b.getPosition());
            sb.append(",");
            sb.append(b.getSize());
            sb.append(")");
        }
        return sb.toString();
    }
    
    /**
     * get the size of the freeBlockList
     * @return the size
     */
    public int getSize() {
    	return size;
    }
}
