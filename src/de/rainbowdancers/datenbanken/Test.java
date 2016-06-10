package de.rainbowdancers.datenbanken;

import java.io.IOException;
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
      DatabaseOperationsOracle dbo = new DatabaseOperationsOracle(connectionString, user, password);
      IOStreamTableCSV io = new IOStreamTableCSV(";");
      TranslateTableToSQL CSVToSQL = new TranslateTableToSQL();
      Table brands = new Table("Brands", new String [] { "BrandID", "Brand" });
      Table clothing = new Table("Clothing", new String [] { "ClothingID", "Category" });
      Table colors = new Table("Colors", new String [] { "ColorID", "Color" });
      Table onlineShops = new Table("OnlineShops", new String [] { "ShopID", "Shop" });
      Table outfits = new Table("Outfits", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" });

      s.printlnSeparation();
      dbo.printFields();
      s.printlnSeparation();
      // dbo.connect();
      try {
         io.readCSVIntoTable("data/Brands.csv", brands);
         io.readCSVIntoTable("data/Clothing.csv", clothing);
         io.readCSVIntoTable("data/Colors.csv", colors);
         io.readCSVIntoTable("data/OnlineShops.csv", onlineShops);
         io.readCSVIntoTable("data/Outfits.csv", outfits);
         s.println("Tabellen eingelesen.");
         s.println(brands.toString());
         s.println(dbo.makeInsertPreparedStatement(brands.getTableName(), brands.getColumnNames()));
      } catch (IOException e) {
         e.printStackTrace();
      } catch (NotEnoughColumnsException e) {
         e.printStackTrace();
      }
      // Tabellen in SQL uebersetzen
      // Ggf. Tabelle mit SQL in DB erzeugen
      // SQL Transaktionen anweisen
      // dbo.disconnect();
   }

}