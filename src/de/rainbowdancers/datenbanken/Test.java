package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.sql.SQLException;
import de.rainbowdancers.exceptions.DifferentNumberOfColumnsException;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import tobi_wan.dataStructure.Table;
import tobi_wan.support.StandardOutput;



public class Test {
   // Attribute
   static StandardOutput           s;
   static String                   connectionString;
   static String                   user;
   static String                   password;
   static DatabaseOperationsOracle dbo;
   static IOStreamTableCSV         io;
   static Table                    brands;
   static Table                    clothing;
   static Table                    colors;
   static Table                    onlineShops;
   static Table                    outfits;

   // Methoden
   public static void main(String [] args) {
      initialiseAttributes();
      s.printlnSeparation();
      dbo.printFields();
      s.printlnSeparation();
      dbo.connect();
      s.println();
      CSVToOracleInsertTransaction(brands, "int", "String");
      CSVToOracleInsertTransaction(clothing, "int", "String");
      CSVToOracleInsertTransaction(colors, "int", "String");
      CSVToOracleInsertTransaction(onlineShops, "int", "String");
      CSVToOracleInsertTransaction(outfits, "int", "String", "String", "String", "String", "String");
      dbo.disconnect();
      s.printlnSeparation();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      connectionString = "jdbc:oracle:thin:@dbl43.beuth-hochschule.de:1521:oracle";
      user = "KLATT";
      password = "Student";
      s.printlnSeparation();
      dbo = new DatabaseOperationsOracle(connectionString, user, password);
      io = new IOStreamTableCSV(";");
      brands = new Table("Brands", new String [] { "BrandID", "Brand" });
      clothing = new Table("Clothing", new String [] { "ClothingID", "Category" });
      colors = new Table("Colors", new String [] { "ColorID", "Color" });
      onlineShops = new Table("OnlineShops", new String [] { "ShopID", "Shop" });
      outfits = new Table("Outfits", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" });
   }

   private static void CSVToOracleInsertTransaction(Table table, String... datatypesOfColumns) {
      try {
         io.readCSVIntoTable("data/" + table.getTableName() + ".csv", table);
         s.println("Tabelle " + table.getTableName() + " eingelesen.");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (NotEnoughColumnsException e) {
         e.printStackTrace();
      }
      try {
         dbo.insertTransaction(table, datatypesOfColumns);
         s.println("Inserted Brands succesful.");
      } catch (DifferentNumberOfColumnsException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}