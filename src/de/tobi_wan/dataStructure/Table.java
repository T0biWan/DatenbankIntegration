// Datei: Table.java
// Autor: Tobi Wan
// Datum: 10.06.2016
/*----------------------------------------------------------------------
   Die Klasse enthaelt:
   Konstruktor:
      Table(String, String[])
        
   Getter & Setter:
      String getTableName()
      void setTableName(String)
      int getNumberOfColumns()
      setNumberOfColumns(int)
      int getNumberOfRows()
      String[] getColumnNames()
      void setColumnNames(String...)
      
   Methoden:
      void addRow(String[])
      String[] getRow(int)
      void deleteRow(int)
      ArrayList<String []> getAllRows()
      String toString()
      void printTable()
 ----------------------------------------------------------------------*/
package de.tobi_wan.dataStructure;

import java.util.ArrayList;
import de.tobi_wan.support.StandardOutput;



public class Table {
   // Attribute
   StandardOutput               s         = StandardOutput.defaultSupportMethods();
   private String               tableName;
   private int                  numberOfColumns;
   private String []            columnNames;
   private String []            tableRow;
   private ArrayList<String []> tableRows = new ArrayList<>();

   // Konstruktor
   public Table(String tableName, String... columnNames) {
      setTableName(tableName);
      setColumnNames(columnNames);
      setNumberOfColumns();
      tableRow = new String [getNumberOfColumns()];
   } // Table

   // Getter & Setter
   public String getTableName() {
      return tableName;
   } // getTableName

   public void setTableName(String tableName) {
      this.tableName = tableName;
   } // setTableName

   public int getNumberOfColumns() {
      return numberOfColumns;
   } // getNumberOfColumns

   private void setNumberOfColumns() {
      this.numberOfColumns = columnNames.length;
   } // setNumberOfColumns

   public int getNumberOfRows() {
      return tableRows.size();
   } // getNumberOfRows

   public String [] getColumnNames() {
      return columnNames;
   } // getColumnNames

   public void setColumnNames(String... columnNames) {
      this.columnNames = columnNames;
   } // setColumnNames

   // Methoden
   public void addRow(String [] tableRow) {
      tableRows.add(tableRow);
   } // addRow

   public String [] getRow(int row) {
      return tableRows.get(row);
   } // getRow

   public void deleteRow(int row) {
      tableRows.remove(getRow(row));
   } // deleteRow

   public ArrayList<String []> getAllRows() {
      return tableRows;
   } // getAllRows

   public String toString() {
      String string = "Table:\t\t" + getTableName() + "\nColumns:\t" + getNumberOfColumns() + "\nRows:\t\t" + getNumberOfRows() + "\n";
      string += s.arrayToString(columnNames) + "\n";
      for (int i = 0; i < getNumberOfRows(); i++)
         string += s.arrayToString(getRow(i)) + "\n";
      return string;
   } // toString

   public void printTable() {
      s.println(toString());
   } // printTable

} // class Table