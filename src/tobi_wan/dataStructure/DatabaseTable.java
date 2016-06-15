package tobi_wan.dataStructure;

import java.util.HashMap;
import de.rainbowdancers.exceptions.DifferentAmountOfColumnsException;
import de.rainbowdancers.exceptions.NoValidNumberForPrimaryKeyException;



public class DatabaseTable extends Table {
   // Attribute
   private String []                datatypesOfColumns;
   private int                      columnOfPrimaryKey;
   private int []                   columnsOfForeignKeys;
   private String []                foreignKeyReferences;
   private HashMap<Integer, String> foreignKeys;

   // Konstruktor
   public DatabaseTable(String tableName, String [] columnNames, String [] datatypesOfColumns, int columnOfPrimaryKey, int [] columnsOfForeignKeys, String [] foreignKeyReferences)
         throws DifferentAmountOfColumnsException, NoValidNumberForPrimaryKeyException {
      super(tableName, columnNames);
      if (super.getNumberOfColumns() != datatypesOfColumns.length || super.getNumberOfColumns() < columnsOfForeignKeys.length) throw new DifferentAmountOfColumnsException();
      if (columnOfPrimaryKey < 0) throw new NoValidNumberForPrimaryKeyException();
      setDatatypesOfColumns(datatypesOfColumns);
      setColumnOfPrimaryKey(columnOfPrimaryKey);
      setColumnsOfForeignKeys(columnsOfForeignKeys);
      setForeignKeyReferences(foreignKeyReferences);
      setForeignKeys();
   }

   // Getter & Setter
   public void setDatatypesOfColumns(String [] datatypesOfColumns) {
      this.datatypesOfColumns = datatypesOfColumns;
   }

   public String [] getDatatypesOfColumns() {
      return datatypesOfColumns;
   }

   public int getColumnOfPrimaryKey() {
      return columnOfPrimaryKey;
   }

   public void setColumnOfPrimaryKey(int columnOfPrimaryKey) {
      this.columnOfPrimaryKey = columnOfPrimaryKey;
   }

   public int [] getColumnsOfForeignKeys() {
      return columnsOfForeignKeys;
   }

   public void setColumnsOfForeignKeys(int [] columnsOfForeignKeys) {
      this.columnsOfForeignKeys = columnsOfForeignKeys;
   }

   public String [] getForeignKeyReferences() {
      return foreignKeyReferences;
   }

   public void setForeignKeyReferences(String [] foreignKeyReferences) {
      this.foreignKeyReferences = foreignKeyReferences;
   }

   public HashMap<Integer, String> getForeignKeys() {
      return foreignKeys;
   }

   public void setForeignKeys() {
      foreignKeys = new HashMap<>();
      for (int i = 0; i < columnsOfForeignKeys.length; i++) {
         foreignKeys.put(getColumnsOfForeignKeys()[i], getForeignKeyReferences()[i]);
      }
   }

   // Methoden
   public String toString() {
      String string = "Table:\t\t" + getTableName() + "\nColumns:\t" + getNumberOfColumns() + "\nRows:\t\t" + getNumberOfRows() + "\n";
      string += "Primary Key:\t" + super.getColumnNames()[getColumnOfPrimaryKey()] + "\n";
      if (getColumnsOfForeignKeys().length > 0) {
         string += "Foreign Key:\t";
         int i = 0;
         for (int index : columnsOfForeignKeys) {
            if (i > 0)
               string += "\t\t" + super.getColumnNames()[index] + "\n";
            else string += super.getColumnNames()[index] + "\n";
            i++;
         }

      }
      string += s.arrayToString(super.getColumnNames()) + "\n";
      string += s.arrayToString(getDatatypesOfColumns());
      for (int i = 0; i < getNumberOfRows(); i++)
         string += s.arrayToString(getRow(i)) + "\n";
      return string;
   }
}
