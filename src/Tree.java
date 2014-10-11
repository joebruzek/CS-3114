/**
 * a 2-3+ tree. This is gonna store a lot of stuff.
 *
 * @author jbruzek, sucram20
 *
 */
public class Tree {
	TTNode root;

	/**
	 * initialize the tree
	 */
	public Tree() {
		root = null;
	}

	/**
	 * insert a value into the tree
	 * @param k the KVPair to insert
	 */
	public TTNode insert(TTNode node, KVPair k) {
		//base case
		if (node == null) {
			node = new LNode(k);
			return node;
		}
		if (node.isLeaf()) {
			((LNode) node).insert(k);
			return insert(new INode());
		}

		// recursive case, if root's an internal node
		else {
			if (k.key().compareTo((KVPair)node.getKey(0)) <= 0) {
				this.insert(node.getChild(0), k);
			}
			else if (node.getKey(1) == null || k.key().compareTo((KVPair)node.getKey(1)) < 0) {
				this.insert(node.getChild(1), k);
			}
			else {
				this.insert(node.getChild(2), k);
			}
		}

		//handle splitting
		if (!node.isLeaf()) {
			for (int i = 0; i < 3; i++) {
				if (node.getChild(i) != null && node.getChild(i).isFull()) {
					TTNode temp = node.getChild(i).split();
<<<<<<< HEAD

					node.promote(node, temp);
=======
					
					// promote up the node stuff
					((INode) node).promote(node, temp);
>>>>>>> f8e5342a92b29f7bc0d226131492cfe97098c6fd
				}
			}
		}
		
		return null;
	}

	// ----------------------------------------------------------
	/**
	 * Adds key values to the inner nodes
	 * @param node
	 * @param k
	 */
	public void add(INode node, KVPair k)
	{
	    if(node.isFull())
	    {
	        INode temp = node.split();
	    }
	}

	/**
	 * search the tree recursively to see if it contains a value
	 * @param node the root to search from
	 * @param k the key to find
	 * @return k exists in the tree
	 */
	public boolean search(TTNode node, KVPair k) {
		if (node == null) {
			return false;
		}
		if (k.compareTo(node.getKey(0)) == 0) {
			return true;
		}
		if (node.getKey(1) != null && k.compareTo(node.getKey(1)) == 0) {
			return true;
		}
		if (k.compareTo(node.getKey(0)) < 0) {
			return search(node.getChild(0), k);
		}
		else if (node.getKey(1) == null) {
			return search(node.getChild(1), k);
		}
		else if (k.compareTo(node.getKey(1)) < 0) {
			return (search(node.getChild(1), k));
		}
		else {
			return (search(node.getChild(2), k));
		}
	}
	
	/**
	 * print the tree
	 * Calls the recursive tree print method
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
			s.append(node.getKey(i).key());
			s.append(" ");
			s.append(node.getKey(i).value());
			space = " ";
		}
		
		System.out.println(s.toString());
		
		print(node.getChild(0), d + 1);
		print(node.getChild(1), d + 1);
		print(node.getChild(2), d + 1);
	}
}
