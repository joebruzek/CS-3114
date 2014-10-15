import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads commends from a file.
 *
 * @author jbruzek, sucram20
 * @version 2014.10.14
 */
public class FileReader {

    private File file;
    private ArrayList<String> commands;

    /**
     * initialize the filereader
     * 
     * @param file
     *            the file to read from
     */
    public FileReader(File file) {
        this.file = file;
        commands = new ArrayList<String>();

        readFile();
    }

    /**
     * read the file and add the commands to the commands list
     */
    public void readFile() {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNext()) {
            commands.add(sc.nextLine());
        }
    }

    /**
     * get a command at the specified index
     * 
     * @param index the index
     * @return the command
     */
    public String getCommand(int index) {
        return commands.get(index);
    }

    /**
     * get the list of commands
     * 
     * @return the commands
     */
    public ArrayList<String> getCommands() {
        return commands;
    }
}
