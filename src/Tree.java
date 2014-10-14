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
        root = null;
        depth = 1;
    }

    /**
     * add a value into the tree
     * calls the recursive insert method
     * @param k the KVPair to insert
     */
    public boolean insert(KVPair k) {
    	// if it's already in the tree
    	if (search(root, k)) return false;
    	
    	if (root == null) {
    		root = new LNode(k);
    		return true;
    	}
    	else if (root.isLeaf()) {
    		((LNode) root).insert(k);
            rootFullCheck();
            return true;
    	}
    	else {
	    	add(root, k);
	    	return true;
    	}
    }


    /**
     * insert a value into the tree
     * @param k the KVPair to insert
     * @param node the current node in the tree
     * @return TTNode the parent of the inserted node
     */
    public TTNode add(TTNode node, KVPair k) {
    	//base case, we never go below the lowest level of INodes
        if (node.getChild(0).isLeaf()) {
        	int i;
            if (k.compareTo(node.getKeyV(0)) < 0) {
                i = 0;
            }
            else if (node.getKeyV(1) == null || k.compareTo(node.getKeyV(1)) < 0) {
                i = 1;
            }
            else {
                i = 2;
            }
            node.getChild(i).insert(k);
        }
        else {
        	if (k.key().compareTo(node.getKeyV(0).key()) < 0) {
                this.add(node.getChild(0), k);
            }
            else if (node.getKeyV(1) == null || k.key().compareTo(node.getKeyV(1).key()) < 0) {
                this.add(node.getChild(1), k);
            }
            else {
                this.add(node.getChild(2), k);
            }
        }
        
        //check to see if we filled any node
        for (int i = 0; i < 4; i++) {
	        if (node.getChild(i) != null && node.getChild(i).isFull()) {
	        	splitUp(node, i);
	        }
        }
        
        return null;
    }
    
    /**
     * split up a node
     * @param node the node that is above the splitting node
     * @param index the index of the child to split
     */
    public void splitUp(TTNode node, int index) {
    	TTNode child = node.getChild(index);
    	if (node == root) {
    		if (child.isLeaf()) {
    			LNode temp = (LNode) child.split();
    			for (int i = 3; i > index + 1; i--) {
    				root.setChild(i, root.getChild(i - 1));
    			}
    			root.insert(temp.getKeyV(0));
    			node.setChild(index + 1, temp);
    		}
    		else {
    			INode temp = (INode) child.split();
    			root.insert(temp.getKeyV(0));
    			((INode) root).promote(child, temp.getChild(1));
    		}
    		
    		rootFullCheck();
    	} else {
    		if (child.isLeaf()) {
    			LNode temp = (LNode) child.split();
    			for (int i = 3; i > index + 1; i--) {
    				node.setChild(i, node.getChild(i - 1));
    			}
    			node.insert(temp.getKeyV(0));
    			node.setChild(index + 1, temp);
    		}
    		else {
    			INode temp = (INode) child.split();
    			node.insert(temp.getKeyV(0));
    			((INode) node).promote(child, temp.getChild(1));
    		}
    	}
    }
    
    /**
     * check if the root is full and split it
     */
    public void rootFullCheck() {
    	if (root.isFull()) {
    		if (root.isLeaf()) {
    			INode newNode = new INode(root.getKeyV(1));
                TTNode temp = root.split();
                newNode.setChild(0, root);
                newNode.setChild(1, temp);
                root = newNode;
                depth++;
    		}
    		else {
    			INode temp = (INode) root.split();
    			temp.promote(root, temp.getChild(1));
    			root = temp;
    			depth++;
    		}
    	}
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
    	System.out.println("Printing 2-3 tree:");
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
	
	/**
	 * go to the first LNode in the tree
	 * @param node the root for this subtree
	 * @return the first LNode, null if tree is empty
	 */
	public LNode getToFirst(TTNode node) {
		//base case
		if (node == null) {
			return null;
		}
		if (node.isLeaf()) {
			return (LNode) node;
		}
		
		return getToFirst(node.getChild(0));
	}
	
	/**
	 * get the number of KVPairs in the leaves of the tree
	 * @return the number of KVPairs in the tree
	 */
	public int numElements() {
		if (root == null) {
			return 0;
		}
		int results = 0;
		LNode temp = getToFirst(root);
		
		do {
			results += temp.numRecs();
			temp = temp.next();
		} while(temp != null);
		
		return results;
	}
	
	/**
	 * find all the values in the tree that match a key
	 * @param k the MemHandle key
	 */
	public MemHandle[] find(MemHandle k) {
		MemHandle[] results = new MemHandle[numElements()];
		int nums = 0;
		
		LNode temp = getToFirst(root);
		
		do {
			for (int i = 0; i < temp.numRecs(); i++) {
				if (temp.getKeyV(i).key().compareTo(k) == 0) {
					results[nums] = temp.getKeyV(i).value();
					nums++;
				}
			}
			temp = temp.next();
		} while (temp != null);
		
		// make the array the size of the results returned
		MemHandle[] finals = new MemHandle[nums];
		for (int i = 0; i < nums; i++) {
			finals[i] = results[i];
		}
		
		return finals;
	}
	
	/**
	 * set root for testing purposes
	 * @param r the new root
	 */
	public void setRoot(TTNode r) {
		root = r;
	}
	
}
