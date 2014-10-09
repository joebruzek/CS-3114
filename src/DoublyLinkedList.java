/**
 * A doubly linked list of nodes
 *
 * @author jbruzek, sucram20
 * @param <T> the data type stored in the nodes
 */
public class DoublyLinkedList<T>
{
    private Node<T> head = null;
    private Node<T> tail = null;
    private int     size = 0;
    private Node<T> current;


    /**
     * Create a new DoublyLinkedList object.
     */
    public DoublyLinkedList() {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        head.join(tail);
        current = new Node<T>(null);
    }


    /**
     * add a node to the end of the list
     *
     * @param data
     *            the data to be added
     */
    public void add(T data) {
        Node<T> temp = new Node<T>(data);
        Node<T> temp2 = tail.previous();
        temp.join(temp2.split());
        temp2.join(temp);
        current = temp;
        size++;
    }
    
    /**
     * add a node in front of current
     * current becomes the added node
     * @param data the data to be added
     */
    public void addHere(T data) {
    	Node<T> temp = new Node<T>(data);
    	temp.join(current.split());
    	current.join(temp);
    	current = temp;
    	size++;
    }
    
    /**
     * remove the current node
     * current gets set to the node in front of current
     */
    public void remove() {
    	Node<T> temp = current.previous();
    	temp.join(temp.split().split());
    	current = temp;
    	size--;
    }


    /**
     * clear the list
     */
    public void clear() {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        head.join(tail);
        current = new Node<T>(null);
        size = 0;
    }


    /**
     * return the size of the list
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }


    /**
     * set the current to the front of the list
     */
    public void head() {
        current = head;
    }


    /**
     * set the current to the tail of the list
     */
    public void tail() {
        current = tail;
    }


    /**
     * return the front item of the list
     *
     * @return the front item
     */
    public T frontItem() {
        return head.next().data();
    }


    /**
     * moves the current to the next item returns the next item
     *
     * @return the next item
     * @precondition has a next
     */
    public T next() {
        current = current.next();
        return current.data();
    }


    /**
     * moves the current to the previous item returns the previous item
     *
     * @return the previous item
     * @precondition has a previous
     */
    public T previous() {
        current = current.previous();
        return current.data();
    }
    
    /**
     * get the current node
     */
    public Node<T> current() {
    	return current;
    }
    
    /**
     * update the current node
     * @param data, the new data of current
     */
    public void updateCurrent(T data) {
    	current.setData(data);
    }
    
    /**
     * set the current to a node
     * @param node the node to set current to
     */
    public void setCurrent(Node<T> node) {
    	current = node;
    }


    /**
     * determines if there is a next node
     *
     * @return if there is a next
     */
    public boolean hasNext() {
        return current.next() != tail;
    }


    /**
     * determines if there is a previous node
     *
     * @return if there is a previous node
     */
    public boolean hasPrevious() {
        return current.previous() != head;
    }
}