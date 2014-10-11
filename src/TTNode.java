/**
 * Node for the 2-3+ tree
 * Leaf Nodes and Inner nodes implement this interface
 * 
 * @author jbruzek sucram20
 */
public interface TTNode {
	
	/**
	 * is this node a leaf?
	 * @return leaf or nah
	 */
	public abstract boolean isLeaf();
	
	/**
	 * number of records in this node
	 * @return the number of records
	 */
	public int numRecs();
	
	/**
	 * set the recs
	 * @param the new recs number
	 */
	public void setRecs(int r);
	
	/**
	 * split the node when it needs to be split
	 * @return a TTNode
	 */
	public TTNode split();

	/**
	 * set the child at an index
	 * @param i the index to set the child at
	 * @param c the new child
	 */
	public void setChild(int i, TTNode c);
	
	/**
	 * get the child at an index
	 * @param i the index to get from
	 * @return the child at the index
	 */
	public TTNode getChild(int i);
	
	/**
	 * is the number of recs too damn high?
	 * @return too many recs
	 */
	public boolean isFull();
	
	/**
	 * get the key at an index. KVPair
	 * @param i the index
	 * @return the KVPair
	 */
	public KVPair getKeyV(int i);
	
	/**
	 * get the key at an index. MemHandle
	 * @param i the index
	 * @return the Memhandle
	 */
	public MemHandle getKey(int i);
	
	/**
     * insert a KVPair into the node
     * @param k the memHandle
     */
    public void insert(KVPair k);
}