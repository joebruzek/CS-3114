import java.io.File;
import java.util.ArrayList;

/**
 * Main SearchTree class for the project. Reads from file and executes commands
 *
 * @author jbruzek, sucram20
 *
 */
public class SearchTree {

    // On my honor:
    //
    // - I have not used source code obtained from another student,
    // or any other unauthorized source, either modified or
    // unmodified.
    //
    // - All source code and documentation used in my program is
    // either my original work, or was derived by me from the
    // source code published in the textbook for this course.
    //
    // - I have not discussed coding details about this project with
    // anyone other than my partner (in the case of a joint
    // submission), instructor, ACM/UPE tutors or the TAs assigned
    // to this course. I understand that I may discuss the concepts
    // of this program with other students, and that another student
    // may help me debug my program so long as neither of us writes
    // anything during the discussion or modifies any computer file
    // during the discussion. I have violated neither the spirit nor
    // letter of this restriction.

    /**
     * the main function for the program. Takes arguments.
     * @param args
     */
    public static void main(String[] args) {

        int hashSize = 0;
        int blockSize = 0;
        File file;
        HashTable artists;
        HashTable songs;
        Tree tree;

        hashSize = Integer.parseInt(args[0]);
        blockSize = Integer.parseInt(args[1]);
        file = new File(args[2]);
        artists = new HashTable(hashSize, "Artist");
        songs = new HashTable(hashSize, "Song");
        tree = new Tree();

        MemoryManager mm = new MemoryManager(blockSize);

        FileReader reader = new FileReader(file);

        executeCommands(reader.getCommands(), mm, artists, songs, tree);
    }

    /**
     * execute all the commands grabbed from the filereader
     * @param commands
     * @param mm
     * @param artists
     * @param songs
     */
    public static void executeCommands(ArrayList<String> commands, MemoryManager mm, HashTable artists, HashTable songs, Tree tree) {
        // run every command in the commands list
        for (int i = 0; i < commands.size(); i++) {
            String command = commands.get(i);
            String[] tokens = command.split(" ");

            //get the command (key) and the parameter (value)
            String key = command.substring(0, tokens[0].length());
            String value = command.substring(key.length() + 1, command.length());

            //process the command
            switch(key) {
                case "insert":
                    Pair<String, String> sPair = toPair(value);
                    Pair<byte[], byte[]> bPair = toByteArray(sPair);
                    MemHandle h1;
                    h1 = mm.insert(bPair.getFirst());
                    MemHandle h2;
                    h2 = mm.insert(bPair.getSecond());
                    if (artists.duplicate(sPair.getFirst())) {
                        artists.hashInsert(sPair.getFirst(), h1);
                        System.out.println("|" + sPair.getFirst() + "| is added to the artist database.");
                    } else {
                        System.out.println("|" + sPair.getFirst() + "| duplicates a record already in the artist database.");
                    }
                    if (songs.duplicate(sPair.getSecond())) {
                        songs.hashInsert(sPair.getSecond(), h2);
                        mm.increased();
                        System.out.println("|" + sPair.getSecond() + "| is added to the song database.");
                    } else {
                        System.out.println("|" + sPair.getSecond() + "| duplicates a record already in the song database.");
                    }
                    KVPair p1 = new KVPair(h1, h2);
                    KVPair p2 = new KVPair(h2, h1);
                    if (tree.insert(p1))
                    {
                        System.out.println("The KVPair (|" + sPair.getFirst() + "|,|" + sPair.getSecond()
                            + "|),(" + h1.getPosition() + "," + h2.getPosition() + ") is added to the tree.");
                    }
                    else
                    {
                        System.out.println("The KVPair (|" + sPair.getFirst() + "|,|" + sPair.getSecond()
                            + "|),(" + h1.getPosition() + "," + h2.getPosition() + ") duplicates a record already in the tree.");
                    }
                    if (tree.insert(p2))
                    {
                        System.out.println("The KVPair (|" + sPair.getSecond() + "|,|" + sPair.getFirst()
                    		+ "|),(" + h2.getPosition() + "," + h1.getPosition() + ") is added to the tree.");
                    }
                    else
                    {
                        System.out.println("The KVPair (|" + sPair.getSecond() + "|,|" + sPair.getFirst()
                            + "|),(" + h2.getPosition() + "," + h1.getPosition() + ") duplicates a record already in the tree.");
                    }

                    break;
                case "remove":
                    String[] words = value.split(" ");
                    String type = words[0];
                    String name = value.substring(type.length() + 1, value.length());

                    switch(type) {
                        case "artist":
                            if (artists.searchTable(name)) {
                                MemHandle rh = artists.hashDelete(name);
                                mm.release(rh);
                                System.out.println("|" + name + "| is removed from the artist database.");
                            } else {
                                System.out.println("|" + name + "| does not exist in the artist database.");
                            }
                            break;
                        case "song":
                            if (songs.searchTable(name)) {
                                MemHandle rh = songs.hashDelete(name);
                                mm.release(rh);
                                System.out.println("|" + name + "| is removed from the song database.");
                            } else {
                                System.out.println("|" + name + "| does not exist in the song database.");
                            }
                            break;
                        default:
                            //nothing
                    }
                    break;
                case "print":
                    switch(value) {
                    	case "tree":
                    		tree.printTree();
                    		break;
                        case "blocks":
                            System.out.println(mm.getBlocks());
                            break;
                        case "artist":
                            Pair<MemHandle[], int[]> pair = artists.findAll();
                            String[] names = mm.getNames(pair.getFirst());
                            for (int j = 0; j < names.length; j++) {
                                System.out.println("|" + names[j] + "| " + pair.getSecond()[j]);
                            }
                            System.out.println("total artists: " + names.length);
                            break;
                        case "song":
                            Pair<MemHandle[], int[]> pair2 = songs.findAll();
                            String[] names2 = mm.getNames(pair2.getFirst());
                            for (int j = 0; j < names2.length; j++) {
                                System.out.println("|" + names2[j] + "| " + pair2.getSecond()[j]);
                            }
                            System.out.println("total songs: " + names2.length);
                            break;
                        default:
                            //nothing
                    }
                    break;
                case "list":
                	String[] words1 = value.split(" ");
                	String type1 = words1[0];
                	String name1 = value.substring(type1.length() + 1, value.length());
                	switch(type1) {
	                	case "artist":
	                		//find the MemHandle for the artist name
	                		Pair<MemHandle[], int[]> pair = artists.findAll();
	                		if (pair.getFirst().length == 0) {
	                			System.out.println("|" + name1 + "| does not exist in the artist database.");
	                		}
	                		String[] names = mm.getNames(pair.getFirst());
	                		MemHandle match = null;
	                		for (int j = 0; j < names.length; j++) {
                                if(names[j].equals(name1)) {
                                	match = pair.getFirst()[j];
                                }
                            }
	                		if (match == null) {
	                			// this should never happen I don't think
	                			System.out.println("There's something wrong with list artist");
	                		} else {
	                			MemHandle[] handles = tree.find(match);
	                			String[] theNames = mm.getNames(handles);
	                			for (int j = 0; j < theNames.length; j++) {
	                				System.out.println("|" + theNames[j] + "|");
	                			}
	                		}
	                		break;
	                	case "song":
	                		//find the MemHandle for the artist name
	                		Pair<MemHandle[], int[]> pair1 = songs.findAll();
	                		if (pair1.getFirst().length == 0) {
	                			System.out.println("|" + name1 + "| does not exist in the artist database.");
	                		}
	                		String[] names1 = mm.getNames(pair1.getFirst());
	                		MemHandle match1 = null;
	                		for (int j = 0; j < names1.length; j++) {
                                if(names1[j].equals(name1)) {
                                	match1 = pair1.getFirst()[j];
                                }
                            }
	                		if (match1 == null) {
	                			// this should never happen I don't think
	                			System.out.println("There's something wrong with list artist");
	                		} else {
	                			MemHandle[] handles = tree.find(match1);
	                			String[] theNames = mm.getNames(handles);
	                			for (int j = 0; j < theNames.length; j++) {
	                				System.out.println("|" + theNames[j] + "|");
	                			}
	                		}
	                		break;
                	}
                default:
                    //nothing was there
            }
        }
    }

    /**
     * get a pair of strings from a string with a <SEP> marker
     * @param str
     * @return
     */
    public static Pair<String, String> toPair(String str) {
        Pair<String, String> pair = new Pair<String, String>();
        String[] tokens = str.split("<SEP>");
        String first = tokens[0];
        String second = tokens[1];

        pair.setFirst(first);
        pair.setSecond(second);

        return pair;
    }

    /**
     * get a byte array pair from a String pair
     * @param str
     * @return
     */
    public static Pair<byte[], byte[]> toByteArray(Pair<String, String> str) {
        Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>();

        //Set the Artist
        char[] s = str.getFirst().toCharArray();
        byte[] bytes = getBytes(str.getFirst());

        for (int i = 0; i < s.length; i++) {
            bytes[i + 2] = (byte) s[i];
        }

        pair.setFirst(bytes);

        //Set the Song title
        char[] f = str.getSecond().toCharArray();
        byte[] bites = getBytes(str.getSecond());

        for (int i = 0; i < f.length; i++) {
            bites[i + 2] = (byte) f[i];
        }

        pair.setSecond(bites);

        return pair;
    }

    /**
     * get the byte array for a string, encoding the length into the first two bytes
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        int length = str.length();
        byte[] data = new byte[length + 2];
        data[0] = (byte) (length & 0xFF);
        data[1] = (byte) ((length >> 8) & 0xFF);

        return data;
    }

    /**
     * get the length from the first two bytes of the byte array
     * @param data
     * @return
     */
    public static int getLength(byte[] data)
    {
       return (int)((data[1]<<8) | (data[0]));
    }

}