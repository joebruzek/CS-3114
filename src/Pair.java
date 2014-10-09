/**
 * Pair holds a pair of values
 * 
 * @author jbruzek, sucram20
 * @param <T> the first value
 * @param <E> the second value
 */
public class Pair<T, E> {
	
	private T first = null;
	private E second = null;
	
	/**
	 * empty constructor
	 */
	public Pair() {
		//nothing
	}
	
	/**
	 * initialize a pair
	 * @param first
	 * @param second
	 */
	public Pair(T first, E second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * set the first
	 * @param data
	 */
	public void setFirst(T data) {
		first = data;
	}
	
	/**
	 * set the second
	 * @param data
	 */
	public void setSecond(E data) {
		second = data;
	}
	
	/**
	 * get the first
	 * @return
	 */
	public T getFirst() {
		return first;
	}
	
	/**
	 * get the second
	 * @return
	 */
	public E getSecond() {
		return second;
	}

}
