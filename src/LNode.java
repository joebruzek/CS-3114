/**
 * Leaf node implementation for the 2-3+ tree
 * @author jbruzek, sucram20
 *
 */
public class LNode extends TTNode<KVPair> {
	
	private LNode next;
	
	/**
	 * constructor for the leaf node
	 */
	public LNode() {
		this.keys = new KVPair[3];
		next = null;
	}
	
	/**
	 * make a LNode with a left value
	 * @param l the KVPair
	 */
	public LNode(KVPair l) {
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
	 * search for the KVPair and return the index
	 * @param k the KVPair to search for
	 * @return the index, -1 if the KVPair doesn't exist
	 */
	@Override
	public int search(KVPair k) {
		for (int i = 0; i < this.numRecs(); ++i) {
			if (this.getKey(i) == null) {
				return -1;
			}
			int cmp = this.getKey(i).compareTo(k);
			if (cmp == 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * insert a KVPair into the node
	 * @param k the E to insert
	 */
	public void insert(KVPair k) {
		//find the index to insert into
		int index = 0;
		while (index < this.numRecs() && this.getKey(index).compareTo(k) < 0)
			++index;
		
		// move space for the new key
		for (int i = this.numRecs() - 1; i >= index; --i) {
			this.setKey(i + 1, this.getKey(i));
		}
		
		this.setKey(index, k);
		this.recs++;
	}

	/**
	 * get the left child
	 * @return null because leaves have no children
	 */
	@Override
	public TTNode<KVPair> left() {
		return null;
	}

	/**
	 * get the center child
	 * @return null because leaves have no children
	 */
	@Override
	public TTNode<KVPair> center() {
		return null;
	}

	/**
	 * get the right child
	 * @return null because leaves have no children
	 */
	@Override
	public TTNode<KVPair> right() {
		return null;
	}

	/**
	 * split the node into two, when it has more than 2 recs
	 * 
	 * @return the new node that has been split from this one
	 */
	@Override
	public TTNode<KVPair> split() {
		LNode node = new LNode();
		node.insert(this.keys[1]);
		this.keys[1] = null;
		node.insert(this.keys[2]);
		this.keys[2] = null;
		this.recs = 1;
		node.recs = 2;
		
		if (this.next != null) {
			LNode temp = this.next();
			this.next = node;
			node.setNext(temp);
		} else {
			this.next = node;
		}
		
		return node;
	}

}
