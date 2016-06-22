package de.rainbowdancers.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.tobi_wan.dataStructure.DatabaseTable;
import de.tobi_wan.support.StandardOutput;



public class DatabaseOperationsOracle {
   // Attribute
   StandardOutput            standardOutput = StandardOutput.defaultSupportMethods();
   private String            driver         = "oracle.jdbc.driver.OracleDriver";
   private String            user;
   private String            password;
   private String            connectionString;
   private Connection        connection;
   private Statement         statement;
   private ResultSet         resultSet;
   private PreparedStatement preparedStatement;

   // Konstruktor
   public DatabaseOperationsOracle(String connectionString, String user, String password) {
      super();
      this.connection = null;
      this.statement = null;
      this.resultSet = null;
      this.preparedStatement = null;
      setConnectionString(connectionString);
      setUser(user);
      setPassword(password);
      try {
         Class.forName(driver);
      } catch (ClassNotFoundException e) {
         System.out.println("Oracle JDBC Driver not found.");
         e.printStackTrace();
      }
   }

   // Getter & Setter
   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getConnectionString() {
      return connectionString;
   }

   public void setConnectionString(String connectionString) {
      this.connectionString = connectionString;
   }

   public PreparedStatement getPreparedStatement() {
      return preparedStatement;
   }

   public void setPreparedStatement(String preparedStatementString) throws SQLException {
      this.preparedStatement = makePreparedStatement(preparedStatementString);
   }

   // Methoden
   public void printFields() {
      standardOutput.println("User:\t\t\t" + getUser() + "\nPassword:\t\t" + getPassword() + "\nConnectionString:\t" + getConnectionString());
   }

   public boolean connect() {
      try {
         connection = DriverManager.getConnection(getConnectionString(), getUser(), getPassword());
         statement = connection.createStatement();
         standardOutput.println("Established connection.");
      } catch (SQLException e) {
         standardOutput.println("Connection failed.");
         e.printStackTrace();
         return false;
      }
      return true;
   }

   public boolean disconnect() {
      try {
         if (resultSet != null) resultSet.close();
         if (statement != null) statement.close();
         if (connection != null) connection.close();
         standardOutput.println("Disconected succesfully.");
      } catch (SQLException e) {
         standardOutput.println("Disconnection failed.");
         return false;
      }
      return true;
   }

   public String makeCreateTableString(DatabaseTable table) {
      String returnString = "CREATE TABLE " + table.getTableName() + "(";
      for (int i = 0; i < table.getNumberOfColumns(); i++) {
         returnString += table.getColumnNames()[i] + " ";
         if (table.getDatatypesOfColumns()[i] == "int")
            returnString += "NUMBER(38,0)";
         else if (table.getDatatypesOfColumns()[i] == "String") returnString += "VARCHAR2(128 BYTE)";
         if (table.getColumnOfPrimaryKey() == i) returnString += " NOT NULL PRIMARY KEY";
         if (i < table.getNumberOfColumns() - 1) returnString += ", ";
      }
      for (int i = 0; i < table.getColumnsOfForeignKeys().length; i++) {
         String columnName = table.getColumnNames()[table.getColumnsOfForeignKeys()[i]];
         String referenz = table.getForeignKeys().get(table.getColumnsOfForeignKeys()[i]);
         returnString += ", CONSTRAINT fk_" + columnName + " FOREIGN KEY (" + columnName + ") ";
         returnString += "REFERENCES " + referenz;
      }
      returnString += ")";
      return returnString;
   }

   public String makeInsertIntoString(DatabaseTable table) {
      String returnString = "INSERT INTO " + table.getTableName() + " (";
      for (int i = 0; i < table.getNumberOfColumns(); i++) {
         returnString += table.getColumnNames()[i];
         if (i < table.getNumberOfColumns() - 1) returnString += ", ";
      }
      returnString += ") VALUES (";
      for (int i = 0; i < table.getNumberOfColumns(); i++) {
         returnString += "?";
         if (i < table.getNumberOfColumns() - 1) returnString += ", ";
      }
      returnString += ")";
      return returnString;
   }

   public String makeDropTableString(DatabaseTable table) {
      return "DROP TABLE " + table.getTableName() + " CASCADE CONSTRAINTS PURGE";
   }

   public PreparedStatement makePreparedStatement(String preparedStatement) throws SQLException {
      return connection.prepareStatement(preparedStatement);
   }

   public void createTableTransaction(DatabaseTable table) throws SQLException {
      setPreparedStatement(makeCreateTableString(table));
      getPreparedStatement().execute();
      getPreparedStatement().close();
   }

   public void insertTransaction(DatabaseTable table) throws SQLException {
      setPreparedStatement(makeInsertIntoString(table));
      for (int row = 0; row < table.getNumberOfRows(); row++) {
         for (int column = 0; column < table.getNumberOfColumns(); column++) {
            int sqlColumn = column + 1;
            if (table.getDatatypesOfColumns()[column] == "int") {
               getPreparedStatement().setInt(sqlColumn, Integer.parseInt(table.getRow(row)[column]));
            } else if (table.getDatatypesOfColumns()[column] == "String") {
               getPreparedStatement().setString(sqlColumn, table.getRow(row)[column]);
            }
         }
         getPreparedStatement().execute();
      }
      getPreparedStatement().close();
   }

   public void dropTableTransaction(DatabaseTable table) throws SQLException {
      setPreparedStatement(makeDropTableString(table));
      getPreparedStatement().execute();
      getPreparedStatement().close();
   }

}