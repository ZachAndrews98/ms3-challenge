/**
 * Database class, responsible for connecting to and interacting with the database
 */

package ms3.challenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
    private Connection connection = null;
    private Statement statement = null;
    private String tableName = "";

    /**
     * Database connection constructor
     * @param dbName name of the database file
     * @param tableName name of the table to write to
     */
    public Database(String dbName, String tableName){
        this.tableName = tableName;
        try {
            // Create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:./src/output/"+dbName+".db");
            this.statement = this.connection.createStatement();
            this.statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists " + tableName);
            statement.executeUpdate(
                "create table " + tableName + 
                "(A text, B text, C text, D text, E text, F text, G text, H text, I text, J text)"
            );
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Write a record to the database
     * @param record record to write to the database
     */
    public void addRecord(String[] record) {
        // SQLite insert statement
        String sql = "insert into " + this.tableName + " values (?,?,?,?,?,?,?,?,?,?)";
        try{
            // Make prepared statement
            PreparedStatement sqlStatement = this.connection.prepareStatement(sql);
            for(int i = 0; i < record.length; i++) // Add each record to PreparedStatement
                sqlStatement.setString(i+1, record[i]);
            sqlStatement.executeUpdate(); // Execute statement
        } catch(SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    /**
     * Close the database
     */
    public void close() {
        try {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e){
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
}