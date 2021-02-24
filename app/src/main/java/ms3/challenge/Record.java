/**
 * Record class, allows for connection to and creation of output files
 */

package ms3.challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Record {
    private FileWriter writer;

    /**
     * Record Constructor
     * @param file path to the file to open
     */
    public Record(String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile(); // Create the file if it does not exist
            this.writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the supplied record to the file
     * @param record string to write to the file
     */
    public void writeRecord(String[] record) {
        try {
            String line = "";
            for(String item : record) {
                line += item + ",";
            }
            this.writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(String record) {
        try {
            this.writer.write(record + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the file when done writing to it
     */
    public void close() {
        try {
            this.writer.close();
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
