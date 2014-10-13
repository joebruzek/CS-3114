/**
 * Leaf node implementation for the 2-3+ tree
 * @author jbruzek, sucram20
 *
 */
public class LNode implements TTNode {

	private LNode next;
	private LNode previous;
	private int recs;
	private KVPair[] keys;

	/**
	 * constructor for the leaf node
	 */
	public LNode() {
		this.recs = 0;
		this.keys = new KVPair[3];
		next = null;
		previous = null;
	}

	/**
	 * make a LNode with a left value
	 * @param l the KVPair
	 */
	public LNode(KVPair l) {
		this.recs = 1;
		this.keys = new KVPair[3];
		next = null;
		setKey(0, l);
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
	 * set the previous node
	 * @param p the previous
	 */
	public void setPrevious(LNode p) {
		previous = p;
	}
	
	/**
	 * get the previous node
	 * @return the previous
	 */
	public LNode previous() {
		return previous;
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
	public int search(KVPair k) {
		for (int i = 0; i < this.numRecs(); ++i) {
			if (this.getKeyV(i) == null) {
				return -1;
			}
			int cmp = this.getKeyV(i).compareTo(k);
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
		while (index < this.numRecs() && this.getKeyV(index).compareTo(k) < 0)
			++index;

		// move space for the new key
		for (int i = this.numRecs() - 1; i >= index; --i) {
			this.setKey(i + 1, this.getKeyV(i));
		}

		this.setKey(index, k);
		this.recs++;
	}

	/**
	 * split the node into two, when it has more than 2 recs
	 *
	 * @return the new node that has been split from this one
	 */
	@Override
	public LNode split() {
		LNode node = new LNode();
		node.insert(this.keys[1]);
		this.keys[1] = null;
		node.insert(this.keys[2]);
		this.keys[2] = null;
		this.recs = 1;
		node.recs = 2;

		if (this.next != null) {
			LNode temp = this.next();
			this.setNext(node);
			node.setPrevious(this);
			node.setNext(temp);
			temp.setPrevious(node);
		} else {
			this.setNext(node);
			node.setPrevious(this);
		}

		return node;
	}

	/**
	 * set the Child.
	 * Throws an UnsupportedOperationException
	 * @param i the index
	 * @param c the child
	 */
	@Override
	public void setChild(int i, TTNode c) {
		throw new UnsupportedOperationException("Leaf nodes have no children");
	}

	/**
	 * get the child
	 * @param i the index
	 * @return the child
	 */
	@Override
	public TTNode getChild(int i) {
		return null;
	}

	/**
	 * get the number of records in this node
	 * @return the number of recs
	 */
	@Override
	public int numRecs() {
		return recs;
	}

	/**
	 * set the number of recs in this node
	 * @param r the number of recs
	 */
	@Override
	public void setRecs(int r) {
		recs = r;

	}

	/**
	 * set the E at the index
	 * @param index the index to insert at
	 * @param value the E to insert
	 */
	public void setKey(int index, KVPair value) {
		this.keys[index] = value;
	}

	/**
	 * is the number of recs too damn high?
	 * @return too many recs
	 */
	@Override
	public boolean isFull() {
		return (this.numRecs() == 3);
	}

	/**
	 * get the KVPair at the index
	 * @param i the index to get
	 * @return the KVPair
	 */
	@Override
	public KVPair getKeyV(int i) {
		return keys[i];
	}

	/**
	 * get a memhandle
	 * throws UnsupportedOperationException
	 * @param i the index
	 * @return NOTHING
	 */
	@Override
	public MemHandle getKey(int i) {
		throw new UnsupportedOperationException("LNodes only store KVPairs.");
	}
}
