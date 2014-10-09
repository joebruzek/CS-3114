/**
 * an inner node for our 2-3+ tree. Implements the TTNode interface
 * @author jbruzek, sucram20
 *
 */
public class INode implements TTNode<KVPair> {
	
	private TTNode<KVPair> left;
	private TTNode<KVPair> center;
	private TTNode<KVPair> right;
	private TTNode<KVPair> parent;
	private int recs = 0;
	private KVPair leftVal;
	private KVPair rightVal;
	
	/**
	 * constructor for the inner node class
	 */
	public INode() {
		leftVal = null;
		rightVal = null;
		parent = null;
		left = null;
		right = null;
		center = null;
	}
	
	/**
	 * set the parent of this node
	 * @param p the new parent
	 */
	public void setParent(TTNode<KVPair> p) {
		parent = p;
	}
	
	/**
	 * get the parent node
	 * @return the parent
	 */
	public TTNode<KVPair> parent() {
		return parent;
	}
	
	/**
	 * set the left child
	 * @param l the new left child
	 */
	public void setLeft(TTNode<KVPair> l) {
		left = l;
	}
	
	/**
	 * get the left child
	 * @return the left child
	 */
	public TTNode<KVPair> left() {
		return left;
	}
	
	/**
	 * set the right child
	 * @param r the new right child
	 */
	public void setRight(TTNode<KVPair> r) {
		right = r;
	}
	
	/**
	 * get the right child
	 * @return the right child
	 */
	public TTNode<KVPair> right() {
		return right;
	}
	
	/**
	 * set the center child
	 * @param c the new center child
	 */
	public void setCenter(TTNode<KVPair> c) {
		center = c;
	}
	
	/**
	 * get the center child
	 * @return center
	 */
	public TTNode<KVPair> center() {
		return center;
	}

	/**
	 * is this a leaf?
	 * Always returns false because this is an
	 * (•_•)
	 * ( •_•)>⌐■-■
	 * (⌐■_■)
	 * Inner node
	 * 
	 * @return false
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}

	
	/**
	 * number of records this node has
	 * @return the number of records
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
