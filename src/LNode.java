/**
 * Leaf node implementation for the 2-3+ tree
 * @author jbruzek, sucram20
 *
 */
public class LNode implements TTNode<KVPair> {
	
	private LNode next;
	private int recs = 0;
	private KVPair leftVal;
	private KVPair rightVal;
	
	/**
	 * constructor for the leaf node
	 */
	public LNode() {
		leftVal = null;
		rightVal = null;
		next = null;
	}
	
	/**
	 * make a LNode with a left value
	 * @param l the KVPair
	 */
	public LNode(KVPair l) {
		leftVal = l;
		rightVal = null;
		next = null;
	}
	
	/**
	 * set the next LNode
	 * @param the next LNode
	 */
	public void setNext(LNode n) {
		next = n;
	}
	
	/**
	 * get the next node
	 * @return the next node
	 */
	public LNode next() {
		return next;
	}

	/**
	 * is this a leaf?
	 * Always returns true, this is literally a leaf.
	 * @return true
	 */
	@Override
	public boolean isLeaf() {
		return true;
	}

	/**
	 * get the number of records in this node
	 * @return the number of records.
	 */
	@Override
	public int numrecs() {
		return recs;
	}

	/**
	 * get the left KVPair
	 * @return the left val
	 */
	@Override
	public KVPair leftVal() {
		return leftVal;
	}

	/**
	 * get the right KVPair
	 * @return the right val
	 */
	@Override
	public KVPair rightVal() {
		return rightVal;
	}

}
