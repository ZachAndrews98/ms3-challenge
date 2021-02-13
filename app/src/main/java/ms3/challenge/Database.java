package ms3.challenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
    private Connection connection = null;
    private Statement statement = null;
    private String tableName = "";

    public Database(String dbName, String tableName){
        this.tableName = tableName;
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
            this.statement = this.connection.createStatement();
            this.statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists " + tableName);
            statement.executeUpdate(
                "create table " + tableName + "(A text, B text, C text, D text, E text, F text, G text, H text, I text, J text)"
            );
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public void addRecord(String[] record) {
        String sql = "insert into " + this.tableName + " values (?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement sqlStatement = this.connection.prepareStatement(sql);
            for(int i = 0; i < record.length; i++)
                sqlStatement.setString(i+1, record[i]);
            sqlStatement.executeUpdate();
            // this.statement.executeUpdate("insert into " + this.tableName + " values (?,?,?,?,?,?,?,?,?,?)")
        } catch(SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

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