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
	public void insert(TTNode node, KVPair k) {
		//base case
		if (node == null) {
			node = new LNode(k);
		}
		if (node.isLeaf()) {
			((LNode) node).insert(k);
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

					node.promote(node, temp);
				}
			}
		}
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
}
