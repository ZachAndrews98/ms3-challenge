/**
 * Main class, handles running program
 */

package ms3.challenge;

import java.util.Scanner;
import java.io.File;

public class Main {
    private static int totalRecords = 0;
    private static int successfulRecords = 0;
    private static int failedRecords = 0;
    // Regex used to split commas but ignore commas within quotes
    private static final String COMMAREGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    /**
     * Main method, handles execution loop
     */
    public static void main(String args[]) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Input File: ");
        String inputFile = input.nextLine(); // get name of input csv file

        Scanner csv = new Scanner(new File("./src/input/" + inputFile + ".csv")); // Access to input csv
        Record badRecords = new Record("./src/output/" + inputFile + "-bad.csv"); // Access to -bad.csv file
        Record logs = new Record("./src/output/" + inputFile + ".log"); // Access to log file
        Database db = new Database(inputFile, "records"); // Access to database

        while(csv.hasNext()) { // Read every line in csv file
            String line = csv.nextLine(); // Store next line broken into cells
            totalRecords++;
            // Validate line
            if(validateRecord(line.split(COMMAREGEX))) {
                // If valid, add to database
                db.addRecord(line.split(COMMAREGEX));
                successfulRecords++;
            } else {
                // If not valid add to -bad.csv
                badRecords.writeRecord(line);
                failedRecords++;
            }
        }
        String run = "# of records received: " + totalRecords + " | # of records successful: " + successfulRecords + " | # of records failed: " + failedRecords;
        logs.writeRecord(run);

        // Close database and open files
        csv.close();
        db.close();
        input.close();
        logs.close();
        badRecords.close();
    }

    /**
     * Checks if the record has 10 filled entries. Returns true if it is valid.
     * @param record line to check is valid
     * @return true (valid record) or false (invalid record)
     */
    public static boolean validateRecord(String[] record) {
        if(record.length != 10) // Check if the line does not have 10 cells
            return false;
        for(String entry : record) // For every entry in the record
            if(entry.equals("")) // Check if it is empty
                return false;
        return true;
    }
}