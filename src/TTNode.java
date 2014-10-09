/**
 * Node for the 2-3+ tree
 * Leaf Nodes and Inner nodes implement this interface
 * 
 * @author jbruzek sucram20
 */
public interface TTNode<KVPair> {
	/**
	 * is this node a leaf?
	 * @return leaf or nah
	 */
	public boolean isLeaf();
	
	/**
	 * number of records in this node
	 * @return the number of records
	 */
	public int numrecs();
	
	/**
	 * get the left value
	 * @return the left value
	 */
	public KVPair leftVal();
	
	/**
	 * get the right value
	 * @return the right value
	 */
	public KVPair rightVal();
	
	/**
	 * get the left node
	 * @return the left
	 */
	public TTNode<KVPair> left();
	
	/**
	 * get the center node
	 * @return the center
	 */
	public TTNode<KVPair> center();
	
	/**
	 * get teh right child
	 * @return the right
	 */
	public TTNode<KVPair> right();
}