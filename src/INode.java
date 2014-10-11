
// -------------------------------------------------------------------------
/**
 *  implements each inner node in the tree
 *
 *  @author jbruzek sucram20
 *  @version Oct 10, 2014
 */
public class INode implements TTNode
{
	private MemHandle[] keys;
	private int recs;
	private TTNode[] children;
	
    // ----------------------------------------------------------
    /**
     * New inner node object
     */
    public INode()
    {
        keys = new MemHandle[3];
        recs = 0;
        children = new TTNode[4];
    }
    // ----------------------------------------------------------
    /**
     * Create a new INode object with a left value.
     * @param data
     */
    public INode(MemHandle data)
    {
        this.keys = new MemHandle[3];
        this.keys[0] = data;
        this.recs = 1;
    }

    /**
     * search the INode for a MemHandle
     * @param k the Memhandle to search for
     * @return the index
     */
    public int search(MemHandle k)
    {
        for (int i = 0; i < this.numRecs(); i++)
        {
            if (this.getKey(i) == null) {
                return -1;
            }
            if( this.getKey(i).compareTo(k) == 0) return i;
        }
        return -1;
    }

    /**
     * split the node
     * @return the node split from this one
     */
    @Override
    public TTNode split()
    {
        MemHandle mid;
        MemHandle max;
        MemHandle min;
        min = this.getKey(0);
        mid = this.getKey(1);
        max = this.getKey(2);
        INode newNode = new INode(mid);
        INode rightSplit = new INode(max);

        this.setKey(1, null);
        this.setKey(2, null);
        return newNode;
    }
    
    /**
     * set the key at an index
     * @param i the index
     * @param k the MemHandle
     */
    public void setKey(int i, MemHandle k) {
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
     * insert a MemHandle into the node
     * @param k the memHandle
     */
    public void insert(MemHandle k)
    {
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
     * promote values up from a split child
     * @param node the node that is already a child
     * @param newNode the newly split node
     */
    public void promote(TTNode node, TTNode newNode)
    {
//        this.insert((MemHandle) node.getKey(0));
//        if (this.getChild(0) == node)
//        {
//            if (this.getChild(1) != null)
//            {
//                TTNode<MemHandle> temp = this.getChild(1);
//                this.setChild(1, newNode);
//                promote(this.getChild(1), temp);
//
//            }
//        }
//        else if (this.getChild(1) == node)
//        {
//
//        }
//        else
//        {
//
//        }
        int index = 0;
        while (index < this.children.length && this.getChild(index).getKey(index).compareTo(node.getKey(0)) != 0)
            index++;
        for (int i = this.children.length - 1; i > index; i--)
        {
            this.setChild(i, this.getChild(i - 1));
        }
        this.setChild(index + 1, newNode);
        this.insert(newNode.getKey(0));
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
		throw new UnsupportedOperationException("Inner nodes only hold MemHandles");
	}
	
	/**
	 * get the MemHandle at an index
	 * @param i the index
	 * @return the MemHandle
	 */
	@Override
	public MemHandle getKey(int i) {
		return keys[i];
	}

}
