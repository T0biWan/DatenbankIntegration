package de.rainbowdancers.datenbanken;

import de.rainbowdancers.exceptions.DifferentAmountOfColumnsException;
import de.rainbowdancers.exceptions.NoValidNumberForPrimaryKeyException;
import tobi_wan.dataStructure.DatabaseTable;
import tobi_wan.support.StandardOutput;



public class Test {
   // Attribute
   static StandardOutput           s;
   static DatabaseTable            table;
   static DatabaseOperationsOracle dboo;

   // Methoden
   public static void main(String [] args) {
      initialiseAttributes();
      s.println(table.toString());
      s.println();
      s.println(dboo.makeCreateTableString(table));
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      // (String tableName, String [] columnNames, String [] datatypesOfColumns,
      // int columnOfPrimaryKey, int [] columnsOfForeignKeys))
      try {
         table = new DatabaseTable("Outfit", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" },
               new String [] { "int", "String", "String", "String", "String", "String" }, 0, new int [] { 2, 3, 4, 5 },
               new String [] { "Clothing(ClothingID)", "Clothing(ClothingID)", "Brands(BrandID)", "Colors(ColorID)" });
      } catch (DifferentAmountOfColumnsException e) {
         e.printStackTrace();
      } catch (NoValidNumberForPrimaryKeyException e) {
         e.printStackTrace();
      }
      dboo = new DatabaseOperationsOracle("jdbc:oracle:thin:@dbl43.beuth-hochschule.de:1521:oracle", "KLATT", "Student");
   }

}