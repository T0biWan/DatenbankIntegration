package de.rainbowdancers.datenbanken;

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
      System.out.println();
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

}
