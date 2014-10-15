
// -------------------------------------------------------------------------
/**
 *  This class creates a hash table that stores handles and their position in
 *  the table.
 *  The table is used to store the names of the artist and the name of each
 *  song. Two hash tables will be used (one for songs one for artists).
 *
 *  @author sucram20, jbruzek
 *  @version Sep 12, 2014
 */
public class HashTable
{
    private int size;
    private int numElem;            //number of elements in hash table
    private MemHandle[] hashTable1;
    private String name;

    // ----------------------------------------------------------
    /**
     * Create a new HashTable object.
     * @param m the size
     * @param name the name of the table
     */
    public HashTable(int m, String name) {
        this.name = name;
        size = m;
        hashTable1 = new MemHandle[size];
        
        for (int i = 0; i < m; i++) {
            hashTable1[i] = new MemHandle(0);
        }
    }

    // Use folding on a string, summed 4 bytes at a time
    // ----------------------------------------------------------
    /**
     * convert a string into a long of 4 bytes
     * @param s the string
     * @return the added four-byte chunks of the string s
     */
    public long sfold(String s) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
	        char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
	        long mult = 1;
	        for (int k = 0; k < c.length; k++) {
	            sum += c[k] * mult;
	            mult *= 256;
	        }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
	        sum += c[k] * mult;
	        mult *= 256;
        }

        return (Math.abs(sum));
    }

    // ----------------------------------------------------------
    /**
     * Checks is the hash table is empty.
     * @return true if there are no elements in the table.
     */
    public boolean isEmpty() {
        return numElem == 0;
    }

    // ----------------------------------------------------------
    /**
     * find the home for this key
     * @param k the key in the hash table
     * @return the index in the table where k should go
     */
    public int home(long k) {
        return (int) (k % size);
    }

    // ----------------------------------------------------------
    /**
     * find a spot in the table for the key
     * @param key The key in the hash table
     * @param count the count, ah, ah, ah
     * @return the quadratic probing position
     */
    public int probe(long key, int count) {
        return (home(key) + count * count) % size;
    }

    // ----------------------------------------------------------
    /**
     * insert into the hash table
     * @param k the key being inserted
     * @param handle the handle of the key being inserted
     */
    public void hashInsert(String k, MemHandle handle) {
        long key = sfold(k);
        int h = home(key);
        int pos = h;

        for (int i = 1; hashTable1[pos] != null 
        		&& hashTable1[pos].getKey() >= 0;
            i++) {


            if (hashTable1[pos].getKey() == key) {
                return;
            }
            pos = probe(key, i);
        }

        if (!searchTable(k)) {
			hashTable1[pos] = handle;
			hashTable1[pos].setKey(key);
			numElem++;
			
			if (numElem > size / 2) {
				resize();
			}
        }
    }

    // ----------------------------------------------------------
    /**
     * search the hash table for a string key
     * @param k The key being searched
     * @return true if the key was found in the table
     */
    public boolean searchTable(String k) {
        long key = sfold(k);
        int h = home(key);
        int pos = h;
        for (int i = 1; hashTable1[pos] != null &&
            hashTable1[pos].getKey() != key &&
                hashTable1[pos].getKey() != -1; i++) {
            if (i == size) {
            	return false;
            }
            pos = probe(key, i);
        }
        return (hashTable1[pos] != null && hashTable1[pos].getKey() == key);
    }

    // ----------------------------------------------------------
    /**
     * delete something from the hash table
     * @param k The key that is being removed
     * @return true if the k was successfully removed
     */
    public MemHandle hashDelete(String k) {
        if (!searchTable(k)) {
            return null;
        }

        long key = sfold(k);
        int h = home(key);
        int pos = h;

        for (int i = 1; hashTable1[pos] != null 
        		&& hashTable1[pos].getKey() != key; i++) {
            pos = probe(key, i);
        }
        MemHandle temp = hashTable1[pos];

        hashTable1[pos] = new MemHandle(0);
        hashTable1[pos].setKey(-2);
        numElem--;

        return temp;
    }

    /**
     * is the key a duplicate?
     * @return is it is duplicate
     * @param k the key
     */
    public boolean duplicate(String k) {
        long key = sfold(k);
        int h = home(key);
        int pos = h;
        for (int i = 1; hashTable1[pos] != null &&
            hashTable1[pos].getKey() != key &&
                hashTable1[pos].getKey() != -1; i++) {
            if (i == size) {
            	return true;
            }
            pos = probe(key, i);
        }
        if (!searchTable(k) && (numElem + 1) > size / 2) {
            System.out.println(name + " hash table size doubled.");
        }
        return !(hashTable1[pos] != null && hashTable1[pos].getKey() == key);
    }

    /**
     * resize the table
     */
    public void resize() {
        size = size * 2;
        MemHandle[] resized = new MemHandle[size];
        for (int i = 0; i < size; i++) {
            resized[i] = new MemHandle(0);
        }
        for (int i = 0; i < hashTable1.length; i++) {
            if (hashTable1[i].getKey() > -1) {
                long key = hashTable1[i].getKey();
                int h = home(hashTable1[i].getKey());
                int pos = h;
                for (int j = 1; resized[pos] != null 
                		&& resized[pos].getKey() >= 0; j++) {
                    pos = probe(key, j);
                }
                resized[pos] = hashTable1[i];
                resized[pos].setKey(key);
            }
        }
        hashTable1 = resized;

    }

    /**
     * find all things in the hashTable1
     * @return a pair with a MemHandle array and a int array
     */
    public Pair<MemHandle[], int[]> findAll() {
        MemHandle[] names = new MemHandle[numElem];
        int[] nums = new int[numElem];
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (hashTable1[i].getKey() > -1) {
                names[count] = hashTable1[i];
                nums[count] = i;
                count++;
            }
        }

        return new Pair<MemHandle[], int[]>(names, nums);
    }

    // ----------------------------------------------------------
    /**
     * Get the size
     * @return the size of the hash table
     */
    public int getSize() {
        return size;
    }

    /**
     * get the handle for a position
     * @param pos the position
     * @return the handle at a certain position
     */
    public MemHandle getHandle(int pos) {
        return hashTable1[pos];
    }

}

