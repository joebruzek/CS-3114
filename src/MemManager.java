// Memory Manager abstract class
/**
 * the MemManager interface
 * 
 * @author jbruzek sucram20
 * @version 2014.10.14
 */
interface MemManager {
	/**
	 * insert into the memory manager
	 * 
	 * @param info
	 *            the byte array
	 * @return the handle of the locatiomn
	 */
	public MemHandle insert(byte[] info);

	/**
	 * release something from teh mem pool
	 * 
	 * @param h
	 *            the handle to be released
	 */
	public void release(MemHandle h);

	/**
	 * get the record from a handle
	 * 
	 * @param h
	 *            the handle
	 * @return a byte array
	 */
	public byte[] getRecord(MemHandle h);
}
