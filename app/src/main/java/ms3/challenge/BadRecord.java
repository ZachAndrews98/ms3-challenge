package ms3.challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadRecord {
    private File badRecords;
    private FileWriter recordWriter;

    public BadRecord(String fileRecord) {
        try {
            this.badRecords = new File(fileRecord);
            this.badRecords.createNewFile();
            this.recordWriter = new FileWriter(this.badRecords, true);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public void writeBadRecord(String record) {
        try {
            this.recordWriter.write(record);
            this.recordWriter.write("\n");
            // this.recordWriter.close();
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.recordWriter.close();
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
