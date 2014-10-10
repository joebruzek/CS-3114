/**
 * a 2-3+ tree. This is gonna store a lot of stuff.
 * 
 * @author jbruzek, sucram20
 *
 */
public class Tree {
	TTNode<KVPair> root;
	
	/**
	 * initialize the tree
	 */
	public Tree() {
		root = null;
	}
	
	/**
	 * insert a value into the tree
	 */
	public TTNode<KVPair> insert(TTNode<KVPair> node, KVPair k) {
		if (node == null) {
			root = new LNode(k);
			return root;
		}
		if (node.isLeaf()) {
			((LNode) node).insert(k);
		}
		return null;
	}
	
	/**
	 * search the tree recursively to see if it contains a value
	 * @param node the root to search from
	 * @param k the key to find
	 * @return k exists in the tree
	 */
	public boolean search(TTNode<KVPair> node, KVPair k) {
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
			return search(node.left(), k);
		}
		else if (node.getKey(1) == null) {
			return search(node.center(), k);
		}
		else if (k.compareTo(node.getKey(1)) < 0) {
			return (search(node.center(), k));
		}
		else {
			return (search(node.right(), k));
		}
	}
}
