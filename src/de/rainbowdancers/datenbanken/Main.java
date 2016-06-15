package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.sql.SQLException;
import de.rainbowdancers.exceptions.DifferentAmountOfColumnsException;
import de.rainbowdancers.exceptions.NoValidNumberForPrimaryKeyException;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.DatabaseTable;
import tobi_wan.support.StandardOutput;



public class Main {
   // Attribute
   static StandardOutput           s;
   static String                   connectionString;
   static String                   user;
   static String                   password;
   static DatabaseOperationsOracle dbo;
   static IOStreamTableCSV         io;
   static DatabaseTable            brands;
   static DatabaseTable            clothing;
   static DatabaseTable            colors;
   static DatabaseTable            onlineShops;
   static DatabaseTable            outfits;

   // Methoden
   public static void main(String [] args) {
      initialiseAttributes();
      dbo.printFields();
      s.printlnSeparation();
      dbo.connect();
      s.println();

      csvToOracleTable(brands, "data/Brands.csv");
      csvToOracleTable(clothing, "data/Clothing.csv");
      csvToOracleTable(colors, "data/Colors.csv");
      csvToOracleTable(onlineShops, "data/OnlineShops.csv");
      csvToOracleTable(outfits, "data/Outfits.csv");

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
      try {
         brands = new DatabaseTable("Brands", new String [] { "BrandID", "Brand" }, new String [] { "int", "String" }, 0, new int [] {}, new String [] {});
         clothing = new DatabaseTable("Clothing", new String [] { "ClothingID", "Category" }, new String [] { "int", "String" }, 0, new int [] {}, new String [] {});
         colors = new DatabaseTable("Colors", new String [] { "ColorID", "Color" }, new String [] { "int", "String" }, 0, new int [] {}, new String [] {});
         onlineShops = new DatabaseTable("OnlineShops", new String [] { "ShopID", "Shop" }, new String [] { "int", "String" }, 0, new int [] {}, new String [] {});
         outfits = new DatabaseTable("Outfits", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" }, new String [] { "int", "String", "int", "int", "int", "int" }, 0,
               new int [] { 2, 3, 4, 5 }, new String [] { "Clothing(ClothingID)", "Clothing(ClothingID)", "Brands(BrandID)", "Colors(ColorID)" });
      } catch (DifferentAmountOfColumnsException e) {
         e.printStackTrace();
      } catch (NoValidNumberForPrimaryKeyException e) {
         e.printStackTrace();
      }

   }

   private static void csvToOracleTable(DatabaseTable table, String path) {
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
         dbo.createTableTransaction(table);
         s.println("Succesful");
      } catch (SQLException e) {
         e.printStackTrace();
      }
      try {
         s.print("Insert data:\t");
         dbo.insertTransaction(table);
         s.println("Succesful\n");
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

}
