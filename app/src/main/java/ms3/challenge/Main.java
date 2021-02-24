/**
 * Main class, handles running program
 */

package ms3.challenge;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        String inputFile = input.nextLine(); // Get name of input csv file
        inputFile = inputFile.substring(0, inputFile.lastIndexOf('.')); // Remove extension if given
        CSVReader csv = null;
        CSVParser parser = new CSVParserBuilder()
            .withSeparator(',')
            .withIgnoreQuotations(false)
            .build();
        try {
            csv = new CSVReaderBuilder(new FileReader("./src/input/" + inputFile + ".csv")) // Access to input csv
                        .withSkipLines(0)
                        .withCSVParser(parser)
                        .build();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        Record badRecords = new Record("./src/output/" + inputFile + "-bad.csv"); // Access to -bad.csv file
        Record logs = new Record("./src/output/" + inputFile + ".log"); // Access to log file
        Database db = new Database(inputFile, "records"); // Access to database
        String[] nextRecord;
        while((nextRecord = csv.readNext()) != null) { // Read every line in csv file
            totalRecords++;
            // Validate line
            if(validateRecord(nextRecord)) {
                // If valid, add to database
                db.addRecord(nextRecord);
                successfulRecords++;
            } else {
                // If not valid add to -bad.csv
                badRecords.writeRecord(nextRecord);
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
        // for(String r : record)
        //     System.out.println(r);
        if(record.length != 10) // Check if the line does not have 10 cells
            return false;
        for(String entry : record) // For every entry in the record
            if(entry.equals("")) // Check if it is empty
                return false;
        return true;
    }
}