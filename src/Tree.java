/**
 * a 2-3+ tree. This is gonna store a lot of stuff.
 *
 * @author jbruzek, sucram20
 *
 */
public class Tree {
    private TTNode root;
    private int depth;

    /**
     * initialize the tree
     */
    public Tree() {
        root = new LNode();
        depth = 1;
    }

    /**
     * add a value into the tree
     * calls the insert method
     * @param k the KVPair to insert
     */
    public boolean insert(KVPair k) {
        if (search(root, k)) return false;
    	add(root, null, k);
    	return true;
    }


    /**
     * insert a value into the tree
     * @param k the KVPair to insert
     * @param node the current node in the tree
     * @param promote the promoted node, null if no promotion
     * @return TTNode the parent of the inserted node
     */
    public TTNode add(TTNode node, TTNode promote, KVPair k) {
        //base case
        if (root.isLeaf())
        {
            ((LNode) root).insert(k);
            if (root.isFull())
            {
                //if insert into root causes overflow split and create new root
                INode newNode = new INode(root.getKeyV(1));
                LNode temp = ((LNode) root).split();
                newNode.setChild(0, root);
                newNode.setChild(1, temp);
                root = newNode;
                depth++;
                return root;
            }
            return root;
        }
        else
        {
            if (root.isFull())
            {
                //if insert into root causes overflow split and create new root
                INode newNode = new INode(root.getKeyV(1));
                INode temp = (INode) root.split();
                newNode.setChild(0, root);
                newNode.setChild(1, temp);
                root = newNode;
                depth++;
                return root;
            }
          //Deals with promoting a node
            if (promote != null && promote.getChild(0) == root)
            {
                root = promote;
                return root;
            }
            else if (promote != null && node.getChild(0) == promote.getChild(0))
            {
                ((INode) node).promote(node.getChild(0), promote.getChild(1));
                ((INode) node).insert(promote.getKeyV(0));
                if (node.isFull())
                {
                    //the returned node points to the two split children
                    INode p = (INode) node.split();
                    add(root, p, p.getKeyV(0));
                }
                return node;
            }
            else if (promote != null && node.getChild(1) == promote.getChild(0))
            {
                ((INode) node).promote(node.getChild(1), promote.getChild(1));
                ((INode) node).insert(promote.getKeyV(0));
                if (node.isFull())
                {
                    //the returned node points to the two split children
                    INode p = (INode) node.split();
                    add(root, p, p.getKeyV(0));
                }
                return node;
            }
            else if (promote != null && node.getChild(2) == promote.getChild(0))
            {
                ((INode) node).promote(node.getChild(2), promote.getChild(1));
                ((INode) node).insert(promote.getKeyV(0));
                if (node.isFull())
                {
                    //the returned node points to the two split children
                    INode p = (INode) node.split();
                    add(root, p, p.getKeyV(0));
                }
                return node;
            }

            if (node.getChild(0).isLeaf())
            {
                //insert the key into a leaf node by recursing to the second to last level
                int i;
                if (k.compareTo(node.getKeyV(0)) < 0)
                {
                    i = 0;
                }
                else if (node.getKeyV(1) == null || k.compareTo(node.getKeyV(1)) < 0)
                {
                    i = 1;
                }
                else
                {
                    i = 2;
                }
                ((LNode) node.getChild(i)).insert(k);
                if (node.getChild(i).isFull())
                {
                    LNode temp = ((LNode) node.getChild(i)).split();
                    ((INode) node).insert(temp.getKeyV(0));
                    node.setChild(i + 1, temp);
                    if (node.isFull())
                    {
                        //the returned node points to the two split children
                        INode p = ((INode) node).split();
                        add(root, p, p.getKeyV(0));
                    }
                }
                return node;
            }


            if (k.key().compareTo(node.getKeyV(0).key()) < 0) {
                this.add(node.getChild(0), promote, k);
            }
            else if (node.getKeyV(1) == null || k.key().compareTo(node.getKeyV(1).key()) < 0) {
                this.add(node.getChild(1), promote, k);
            }
            else {
                this.add(node.getChild(2),promote, k);
            }
        }
        return node;
    }

    /**
     * search the tree recursively to see if it contains a value
     * @param node the root to search from
     * @param k the key to find
     * @return k exists in the tree
     */
    public boolean search(TTNode node, KVPair k) {
        if (node == null || k.key() == null) {
            return false;
        }
        if (root.getKeyV(0) == null) return false;
        if (k.compareTo(node.getKeyV(0)) == 0) {
            return true;
        }
        if (node.getKeyV(1) != null && k.compareTo(node.getKeyV(1)) == 0) {
            return true;
        }
        if (k.compareTo(node.getKeyV(0)) < 0) {
            return search(node.getChild(0), k);
        }
        else if (node.getKeyV(1) == null) {
            return search(node.getChild(1), k);
        }
        else if (k.compareTo(node.getKeyV(1)) < 0) {
            return (search(node.getChild(1), k));
        }
        else {
            return (search(node.getChild(2), k));
        }
    }

    /**
     * print the tree
     */
    public void printTree() {
    	print(root, 0);
    }


	/**
	 * recursive print method
	 * @param node the node you're on
	 * @param d the depth of the node
	 */
	public void print(TTNode node, int d) {
		//base case
		if (node == null) {
			return;
		}

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < d * 2; i++) {
			s.append(" ");
		}
		String space = "";
		for (int i = 0; i < node.numRecs(); i++) {
			s.append(space);
			s.append(node.getKeyV(i).key().getPosition());
			s.append(" ");
			s.append(node.getKeyV(i).value().getPosition());
			space = " ";
		}

		System.out.println(s.toString());

		print(node.getChild(0), d + 1);
		print(node.getChild(1), d + 1);
		print(node.getChild(2), d + 1);
	}

	/**
	 * Gets the root node of the tree
	 * @return the root node
	 */
	public TTNode getRoot()
	{
	    return this.root;
	}

	/**
	 * Gets the height of the tree
	 * @return returns the height
	 */
	public int depth()
	{
	    return depth;
	}
}
