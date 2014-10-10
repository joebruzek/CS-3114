/**
 * Node for the 2-3+ tree
 * Leaf Nodes and Inner nodes implement this interface
 * 
 * @author jbruzek sucram20
 * @param E the value stored in the node
 */
abstract class TTNode<E> {
	protected E[] keys;
	protected int recs;
	protected TTNode<E> parent;
	
	protected TTNode() {
		this.recs = 0;
		parent = null;
	}
	
	/**
	 * is this node a leaf?
	 * @return leaf or nah
	 */
	public abstract boolean isLeaf();
	
	/**
	 * number of records in this node
	 * @return the number of records
	 */
	public int numRecs() {
		return this.recs;
	}
	
	/**
	 * set the recs
	 * @param the new recs number
	 */
	public void setRecs(int r) {
		this.recs = r;
	}
	
	/**
	 * get the value at the index
	 * @param index the index
	 * @return the E
	 */
	public E getValue(int index) {
		return (E)this.keys[index];
	}
	
	/**
	 * set the E at the index
	 * @param index the index to insert at
	 * @param value the E to insert
	 */
	public void setKey(int index, E value) {
		this.keys[index] = value;
	}
	
	/**
	 * search the node by key
	 * @param k the E key to search
	 * @return the index of the E
	 */
	public abstract int search(E k);
	
	/**
	 * split the node when it needs to be split
	 * @return a TTNode
	 */
	public abstract TTNode<E> split();
	
	/**
	 * get the parent node
	 * @return the parents
	 */
	public TTNode<E> parent() {
		return this.parent;
	}
	
	/**
	 * set the parent node
	 * @param p the new parent
	 */
	public void setParent(TTNode<E> p) {
		this.parent = p;
	}
	
	/**
	 * get the left node
	 * @return the left
	 */
	public abstract TTNode<E> left();
	
	/**
	 * get the center node
	 * @return the center
	 */
	public abstract TTNode<E> center();
	
	/**
	 * get the right child
	 * @return the right
	 */
	public abstract TTNode<E> right();
}