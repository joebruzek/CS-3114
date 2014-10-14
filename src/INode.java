
// -------------------------------------------------------------------------
/**
 *  implements each inner node in the tree
 *
 *  @author jbruzek sucram20
 *  @version Oct 10, 2014
 */
public class INode implements TTNode
{
	private KVPair[] keys;
	private int recs;
	private TTNode[] children;

    // ----------------------------------------------------------
    /**
     * New inner node object
     */
    public INode()
    {
        keys = new KVPair[3];
        recs = 0;
        children = new TTNode[4];
    }
    // ----------------------------------------------------------
    /**
     * Create a new INode object with a left value.
     * @param data
     */
    public INode(KVPair data)
    {
        this.keys = new KVPair[3];
        this.keys[0] = data;
        this.recs = 1;
        children = new TTNode[4];
    }

    /**
     * search the INode for a KVPair
     * @param k the Memhandle to search for
     * @return the index
     */
    public int search(KVPair k)
    {
        for (int i = 0; i < this.numRecs(); i++)
        {
            if (this.getKeyV(i) == null) {
                return -1;
            }
            if( this.getKeyV(i).compareTo(k) == 0) return i;
        }
        return -1;
    }

    /**
     * split the node
     * @return the node split from this one
     */
    @Override
    public INode split()
    {
        KVPair mid;
        KVPair max;
        mid = this.getKeyV(1);
        max = this.getKeyV(2);
        INode newNode = new INode(mid);
        INode rightSplit = new INode(max);
        this.setKey(1, null);
        this.setKey(2, null);
        this.recs = 1;
        for (int i = 0; i < 2; i++)
        {
            rightSplit.setChild(i, this.getChild(i + 2));
            this.setChild(i + 2, null);
        }
        newNode.setChild(0, this);
        newNode.setChild(1, rightSplit);
        return newNode;
    }

    /**
     * set the key at an index
     * @param i the index
     * @param k the KVPair
     */
    public void setKey(int i, KVPair k) {
    	keys[i] = k;
    }

    /**
     * is this a leaf?
     * @return false, this is an inner node
     */
    @Override
    public boolean isLeaf()
    {
        return false;
    }

    /**
     * insert a KVPair into the node
     * @param k the memHandle
     */
    public void insert(KVPair k)
    {
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
     * promote values up from a split child
     * @param node the node that is already a child
     * @param newNode the newly split node
     */
    public void promote(TTNode node, TTNode newNode)
    {
    	//find the child index of node
        int index = 0;
        while (index < this.children.length && this.getChild(index).getKeyV(0).compareTo(node.getKeyV(0)) != 0)
            index++;
        
        for (int i = this.children.length - 1; i > index; i--)
        {
            this.setChild(i, this.getChild(i - 1));
        }
        this.setChild(index + 1, newNode);
    }

    /**
     * get the number of recs
     * @return the number of recs
     */
	@Override
	public int numRecs() {
		return recs;
	}

	/**
	 * set the number of recs
	 * @param r the recs
	 */
	@Override
	public void setRecs(int r) {
		recs = r;

	}

	/**
	 * set the child at an index
	 * @param i the index
	 * @param c the new node
	 */
	@Override
	public void setChild(int i, TTNode c) {
		children[i] = c;
	}

	/**
	 * get the child at an index
	 * @param i the index
	 * @return the node
	 */
	@Override
	public TTNode getChild(int i) {
		return children[i];
	}

	/**
	 * is this node full
	 * @return if there are too many recs
	 */
	@Override
	public boolean isFull() {
		return recs == 3;
	}

	/**
	 * get a KVPair
	 * throws UnsupportedOperationException
	 * @param i the index
	 * @return the KVPair
	 */
	@Override
	public KVPair getKeyV(int i) {
		return keys[i];
	}

}
