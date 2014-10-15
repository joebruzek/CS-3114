/**
 * Pair holds a pair of values
 * 
 * @author jbruzek, sucram20
 * @version 2014.10.14
 * @param <T>
 *            the first value
 * @param <E>
 *            the second value
 */
public class Pair<T, E> {
    private T first = null;
    private E second = null;

    /**
     * empty constructor
     */
    public Pair() {
        // nothing
    }

    /**
     * initialize a pair
     * 
     * @param first the first value
     * @param second the second value
     */
    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    /**
     * set the first
     * 
     * @param data the data to set
     */
    public void setFirst(T data) {
        first = data;
    }

    /**
     * set the second
     * 
     * @param data the data to set
     */
    public void setSecond(E data) {
        second = data;
    }

    /**
     * get the first
     * 
     * @return the first data
     */
    public T getFirst() {
        return first;
    }

    /**
     * get the second
     * 
     * @return the second data
     */
    public E getSecond() {
        return second;
    }

}
