/**
 * Node class is used for the doubly linked list
 * 
 * @author jbruzek sucram20
 * @version 2014.10.14
 * @param <E> the data of the node
 */
public class Node<E> {
	private E data;
	private Node<E> next;
	private Node<E> previous;

	/**
	 * Constructor for the node class
	 * 
	 * @param data the data to add to the node
	 */
	public Node(E data) {
		this.data = data;
	}

	/**
	 * get the contents of the node
	 * 
	 * @return the data
	 */
	public E data() {
		return data;
	}

	/**
	 * set the contents of the data
	 * 
	 * @param data the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * get the next node in the sequence
	 * 
	 * @return the next node
	 */
	public Node<E> next() {
		return next;
	}

	/**
	 * get the previous node in the sequence
	 * 
	 * @return the previous node
	 */
	public Node<E> previous() {
		return previous;
	}

	/**
	 * Join this node with another node. The node passed as a parameter is
	 * joined behind the current node e.g. A.join(B) creates the order A <-> B
	 * 
	 * Return this node, so you can link joins.
	 * 
	 * Throws IllegalStateException if the nodes cannot be joined;
	 * 
	 * @param newNext the new next node
	 * @return this node
	 */
	public Node<E> join(Node<E> newNext) {
		if (newNext == null) {
			return this;
		}

		if (this.next != null || newNext.previous != null) {
			throw new IllegalStateException("The node pointers must be null");
		}

		this.next = newNext;
		next.previous = this;
		return this;
	}

	/**
	 * split this node from the node in front of it. return the node that has
	 * been split from this one.
	 * 
	 * @return the node that has been split
	 */
	public Node<E> split() {
		if (this.next == null) {
			return null;
		}

		Node<E> temp = this.next();
		this.next = null;
		temp.previous = null;
		return temp;
	}

}
