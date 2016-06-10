package de.rainbowdancers.datenbanken;

import java.util.ArrayList;
import tobi_wan.support.StandardOutput;



public class Table {
   // Attribute
   StandardOutput              s    = StandardOutput.defaultSupportMethods();
   private String              tableName;
   private int                 numberOfColumns;
   private String []           tableRows;
   public ArrayList<String []> data = new ArrayList<>();

   // Getter & Setter
   public String getTableName() {
      return tableName;
   }

   public int getNumberOfColumns() {
      return numberOfColumns;
   }

   public int getNumberOfRows() {
      return data.size();
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }

   public void setNumberOfColumns(int numberOfColumns) {
      this.numberOfColumns = numberOfColumns;
   }

   // Konstruktor
   public Table(String tableName, int numberOfColumns) {
      setTableName(tableName);
      setNumberOfColumns(numberOfColumns);
      tableRows = new String [getNumberOfColumns()];
   }

   // Methoden
   public void addRow(String [] tableRow) {
      data.add(tableRow);
   }

   public String [] getRow(int row) {
      return data.get(row);
   }

   public String toString() {
      String string = "Table:\nName:\t\t" + getTableName() + "\nColumns:\t" + getNumberOfColumns() + "\nRows:\t\t" + getNumberOfRows() + "\n";
      for (int i = 0; i < getNumberOfRows(); i++)
         string += s.arrayToString(getRow(i)) + "\n";
      return string;
   }
}
