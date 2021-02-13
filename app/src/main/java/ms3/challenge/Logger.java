package ms3.challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private File logFile;
    private FileWriter logWriter;

    public Logger(String file) {
        try {
            this.logFile = new File(file);
            this.logFile.createNewFile();
            this.logWriter = new FileWriter(this.logFile, true);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public void logRun(String run) {
        System.out.println(run);
        try {
            this.logWriter.write(run);
            this.logWriter.write("\n");
            // this.logWriter.close();
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.logWriter.close();
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
