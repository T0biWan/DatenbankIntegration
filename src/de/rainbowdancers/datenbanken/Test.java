package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.sql.SQLException;
import de.rainbowdancers.exceptions.DifferentNumberOfColumnsException;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import de.tobi_wan.support.StandardOutput;
import de.tobi_wan.support.Table;



public class Test {

   public static void main(String [] args) {
      // Attribute
      StandardOutput s = new StandardOutput("*", 80);
      String connectionString = "jdbc:oracle:thin:@dbl43.beuth-hochschule.de:1521:oracle";
      String user = "KLATT";
      String password = "Student";
      s.printlnSeparation();
      DatabaseOperationsOracle dbo = new DatabaseOperationsOracle(connectionString, user, password);
      IOStreamTableCSV io = new IOStreamTableCSV(";");
      TranslateTableToSQL CSVToSQL = new TranslateTableToSQL();
      Table brands = new Table("Brands", new String [] { "BrandID", "Brand" });
      Table clothing = new Table("Clothing", new String [] { "ClothingID", "Category" });
      Table colors = new Table("Colors", new String [] { "ColorID", "Color" });
      Table onlineShops = new Table("OnlineShops", new String [] { "ShopID", "Shop" });
      Table outfits = new Table("Outfits", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" });

      dbo.printFields();
      s.printlnSeparation();
      dbo.connect();
      s.println();
      try {
         io.readCSVIntoTable("data/Brands.csv", brands);
         io.readCSVIntoTable("data/Clothing.csv", clothing);
         io.readCSVIntoTable("data/Colors.csv", colors);
         io.readCSVIntoTable("data/OnlineShops.csv", onlineShops);
         io.readCSVIntoTable("data/Outfits.csv", outfits);
         s.println("Tabelle Brands eingelesen.");
         s.println("Tabelle Clothing eingelesen.");
         s.println("Tabelle Colors eingelesen.");
         s.println("Tabelle OnlineShops eingelesen.");
         s.println("Tabelle Outfits eingelesen.");
         s.println();
         dbo.insertTransaction(brands, "int", "String");
         dbo.insertTransaction(clothing, "int", "String");
         dbo.insertTransaction(colors, "int", "String");
         dbo.insertTransaction(onlineShops, "int", "String");
         dbo.insertTransaction(outfits, "int", "String", "String", "String", "String", "String");
         s.println("Inserted Brands succesful.");
         s.println("Inserted Clothing succesful.");
         s.println("Inserted Colors succesful.");
         s.println("Inserted OnlineShops succesful.");
         s.println("Inserted Outfits succesful.");
         s.println();
      } catch (IOException | NotEnoughColumnsException | SQLException | DifferentNumberOfColumnsException e) {
         e.printStackTrace();
      }
      dbo.disconnect();
      s.printlnSeparation();
   }
   // Ggf. Tabelle mit SQL in DB erzeugen

}