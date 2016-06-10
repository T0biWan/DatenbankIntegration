package de.rainbowdancers.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.rainbowdancers.exceptions.DifferentNumberOfColumnsException;
import de.tobi_wan.support.StandardOutput;
import de.tobi_wan.support.Table;



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
         standardOutput.println("Oracle JDBC Driver Registered.");
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

   public void setPreparedStatement(Table table) throws SQLException {
      this.preparedStatement = makePreparedStatement(makePreparedStatementInsertString(table));
   }

   // Methoden
   public void printFields() {
      standardOutput.println("Daten:" + "\nUser:\t\t\t" + getUser() + "\nPassword:\t\t" + getPassword() + "\nConnectionString:\t" + getConnectionString());
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

   public String makePreparedStatementInsertString(Table table) {
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

   public PreparedStatement makePreparedStatement(String preparedStatement) throws SQLException {
      return connection.prepareStatement(preparedStatement);
   }

   public void insertOneTablerowIntoPreparedStatement(String [] tablerow, String... datatypesOfColumns) throws NumberFormatException, SQLException {
      for (int i = 0; i < tablerow.length; i++) {
         switch (datatypesOfColumns[i]) {
            case "int":
               getPreparedStatement().setInt(i + 1, Integer.parseInt(tablerow[i]));
               break;
            case "String":
               getPreparedStatement().setString(i + 1, tablerow[i]);
               break;
         }
      }
   }

   public void insertTransaction(Table table, String... datatypesOfColumns) throws DifferentNumberOfColumnsException, SQLException {
      if (table.getNumberOfColumns() != datatypesOfColumns.length) throw new DifferentNumberOfColumnsException();
      setPreparedStatement(table);
      for (String [] row : table.getAllRows()) {
         insertOneTablerowIntoPreparedStatement(row, datatypesOfColumns);
         getPreparedStatement().execute();
      }
      getPreparedStatement().close();
      // Rollback?
   }

}
