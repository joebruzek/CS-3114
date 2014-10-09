
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
    //private Handle[] hashTable;
    private int numElem;            //number of elements in hash table
    private int[] save;
    private MemHandle[] hashTable;
    private String name;

    // ----------------------------------------------------------
    /**
     * Create a new HashTable object.
     * @param M
     */
    public HashTable(int M, String name) {
    	this.name = name;
        size = M;
        hashTable = new MemHandle[size];
        //TODO
        for (int i = 0; i < M; i++) {
            hashTable[i] = new MemHandle(0);
        }
        save = new int[size];
    }

    // Use folding on a string, summed 4 bytes at a time
    // ----------------------------------------------------------
    /**
     * convert a string into a long of 4 bytes
     * @param s
     * @return the added four-byte chunks of the string s
     */
    public long sfold(String s) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
        char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }
        }

        char c[] = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
        sum += c[k] * mult;
        mult *= 256;
        }

        return(Math.abs(sum));
    }

    // ----------------------------------------------------------
    /**
     * Checks is the hash table is empty.
     * @return true if there are no elements in the table.
     */
    public boolean isEmpty() {
        /*for (int i = 0; i < size; i++) {
            if (hashTable[i].getKey() != -1) {
                return false;
            }
        }*/
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
     * @param count
     * @return the quadratic probing position
     */
    public int probe(long key, int count) {
        return (home(key) + count*count) % size;
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
        //TODO

        for (int i=1; hashTable[pos] != null && hashTable[pos].getKey() >= 0;
            i++) {


            if (hashTable[pos].getKey() == key) {
                return;
            }
            pos = probe(key, i);
        }
        
        if (duplicate(k)) {
        	 hashTable[pos] = handle;
             hashTable[pos].setKey(key);
             saveIndex(pos);
             numElem++;
             
             if (numElem > size/2) {
                 resize();
                 System.out.println(name + " hash table size doubled.");
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
        for (int i = 1; hashTable[pos] != null &&
            hashTable[pos].getKey() != key &&
                hashTable[pos].getKey() != -1; i++) {
            //TODO
            pos = probe(key, i);
        }
        return (hashTable[pos] != null && hashTable[pos].getKey() == key);
    }

    // ----------------------------------------------------------
    /**
     * delete something from the hash table
     * @param k The key that is being removed
     * @param handle The handle of the key being removed
     * @return true if the k was successfully removed
     */
    public MemHandle hashDelete(String k) {
        //TODO
        if (!searchTable(k)){
            return null;
        }

        long key = sfold(k);
        int h = home(key);
        int pos = h;
        
        for (int i = 1; hashTable[pos] != null && hashTable[pos].getKey() != key; i++) {
            pos = probe(key, i);
        }
        MemHandle temp = hashTable[pos];

        hashTable[pos] = new MemHandle(0);
        hashTable[pos].setKey(-2);
        numElem--;

        return temp;
    }
    
    /**
     * is the key a duplicate?
     * @return is it is duplicate
     */
    public boolean duplicate(String k) {
    	long key = sfold(k);
        int h = home(key);
        int pos = h;

        for (int i=1; hashTable[pos] != null && hashTable[pos].getKey() != -1;
            i++) {

            if (hashTable[pos].getKey() == key) {
                return false;
            }
            pos = probe(key, i);
        }
        
        return true;
    }

    /**
     * resize the table
     */
    public void resize() {
        if (numElem > (size/2)) {
        	
        	int count = numElem;
        	
        	for (int i = 0; i < save.length; i++) {
        		if (hashTable[save[i]].getKey() == -2) {
        			count--;
        			for (int j = 0; j < save.length - 1; j++) {
        				save[j] = save[j + 1];
        			}
        		}
        	}
        	
            int [] save2 = new int[size];
            size = size*2;
            for (int i = 0; i < save.length; i++) {
                save2[i] = save[i];
            }
            save = new int[size];
            MemHandle[] change = new MemHandle[size];

            for (int i = 0; i < count; i++) {
                change[save2[i]] = hashTable[save2[i]];
            }
            hashTable = new MemHandle[size];
            //TODO
            for (int i = 0; i < size; i++) {
                hashTable[i] = new MemHandle(0);
            }

            for (int i = 0; i < count; i++) {
                long key = change[save2[i]].getKey();
                if (key > -1) {
                	int h = home(key);
                    int pos = h;

                    for (int j=1; hashTable[pos] != null &&
                            hashTable[pos].getKey() > -1; j++) {
                        pos = probe(key, j) % size;
                    }


                    hashTable[pos] = change[save2[i]];
                    hashTable[pos].setKey(key);
                    save[i] = pos;
                }
            }

        }
    }
    
    /**
     * find all things in the hashTable
     * @param index
     * @return a pair with a MemHandle array and a int array
     */
    public Pair<MemHandle[], int[]> findAll() {
    	MemHandle[] names = new MemHandle[numElem];
    	int[] nums = new int[numElem];
    	int count = 0;
    	
    	for (int i = 0; i < size; i++) {
    		if (hashTable[i].getKey() > -1) {
    			names[count] = hashTable[i];
    			nums[count] = i;
    			count++;
    		}
    	}
    	
    	return new Pair<MemHandle[], int[]>(names, nums);
    }

    /**
     * save the index in the save array
     * @param index
     */
    public void saveIndex(int index) {
        save[numElem] = index;
    }

    public int getSize() {
        return size;
    }

    /**
     * get the handle for a position
     * @param pos
     * @return
     */
    public MemHandle getHandle(int pos) {
        return hashTable[pos];
    }

}

