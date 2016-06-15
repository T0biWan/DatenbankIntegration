package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.sql.SQLException;
import de.rainbowdancers.exceptions.DifferentAmountOfColumnsException;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.Table;
import tobi_wan.support.StandardOutput;



public class Main {
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
      dbo.printFields();
      s.printlnSeparation();
      dbo.connect();
      s.println();

      csvToOracleTable(brands, "data/Brands.csv", 1, "int", "String");
      csvToOracleTable(clothing, "data/Clothing.csv", 1, "int", "String");
      csvToOracleTable(colors, "data/Colors.csv", 1, "int", "String");
      csvToOracleTable(onlineShops, "data/OnlineShops.csv", 1, "int", "String");
      csvToOracleTable(outfits, "data/Outfits.csv", 1, "int", "String", "String", "String", "String", "String");

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

   private static void csvToOracleTable(Table table, String path, int columnNumberOfPrimaryKey, String... datatypesOfColumns) {
      s.println("Table:\t\t" + table.getTableName());
      s.println("Source:\t\t" + path);
      try {
         s.print("Read input:\t");
         io.readCSVIntoTable(path, table);
         s.println("Succesful");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (NotEnoughColumnsException e) {
         e.printStackTrace();
      }
      try {
         s.print("Create table:\t");
         dbo.createTableTransaction(table, columnNumberOfPrimaryKey, datatypesOfColumns);
         s.println("Succesful");
      } catch (SQLException e) {
         e.printStackTrace();
      }
      try {
         s.print("Insert data:\t");
         dbo.insertTransaction(table, datatypesOfColumns);
         s.println("Succesful\n");
      } catch (DifferentAmountOfColumnsException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

}
