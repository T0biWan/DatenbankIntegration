package de.tobi_wan.support;

import java.util.ArrayList;



public class Table { // Attribute
   StandardOutput              s    = StandardOutput.defaultSupportMethods();
   private String              tableName;
   private int                 numberOfColumns;
   private String []           columnNames;
   private String []           tableRows;
   public ArrayList<String []> data = new ArrayList<>();

   // Getter & Setter
   public String getTableName() {
      return tableName;
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }

   public int getNumberOfColumns() {
      return numberOfColumns;
   }

   public void setNumberOfColumns(int numberOfColumns) {
      this.numberOfColumns = numberOfColumns;
   }

   public int getNumberOfRows() {
      return data.size();
   }

   public String [] getColumnNames() {
      return columnNames;
   }

   public void setColumnNames(String [] columnNames) {
      this.columnNames = columnNames;
   }

   // Konstruktor
   public Table(String tableName, String [] columnNames) {
      setTableName(tableName);
      setColumnNames(columnNames);
      setNumberOfColumns(columnNames.length);
      tableRows = new String [getNumberOfColumns()];
   }

   // Methoden
   public void addRow(String [] tableRow) {
      data.add(tableRow);
   }

   public String [] getRow(int row) {
      return data.get(row);
   }

   public void deleteRow(int row) {
      data.remove(getRow(row));
   }

   public ArrayList<String []> getAllRows() {
      return data;
   }

   public String toString() {
      String string = "Table:\nName:\t\t" + getTableName() + "\nColumns:\t" + getNumberOfColumns() + "\nRows:\t\t" + getNumberOfRows() + "\n";
      string += s.arrayToString(columnNames) + "\n";
      for (int i = 0; i < getNumberOfRows(); i++)
         string += s.arrayToString(getRow(i)) + "\n";
      return string;
   }
}