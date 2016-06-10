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
      DatabaseOperationsOracle dbo = new DatabaseOperationsOracle(connectionString, user, password);
      IOStreamTableCSV io = new IOStreamTableCSV(";");
      TranslateTableToSQL CSVToSQL = new TranslateTableToSQL();
      Table brands = new Table("Brands", new String [] { "BrandID", "Brand" });
      Table clothing = new Table("Clothing", new String [] { "ClothingID", "Category" });
      Table colors = new Table("Colors", new String [] { "ColorID", "Color" });
      Table onlineShops = new Table("OnlineShops", new String [] { "ShopID", "Shop" });
      Table outfits = new Table("Outfits", new String [] { "OutfitID", "Actor", "Category", "Subcategory", "Brand", "Color" });

      Table test = new Table("Test", new String [] { "ID", "VALUE" });

      s.printlnSeparation();
      dbo.printFields();
      s.printlnSeparation();
      dbo.connect();

      try {
         io.readCSVIntoTable("data/Brands.csv", brands);
         io.readCSVIntoTable("data/Clothing.csv", clothing);
         io.readCSVIntoTable("data/Colors.csv", colors);
         io.readCSVIntoTable("data/OnlineShops.csv", onlineShops);
         io.readCSVIntoTable("data/Outfits.csv", outfits);
         io.readCSVIntoTable("data/test.csv", test);
         s.println("Tabellen eingelesen.");

         dbo.insertTransaction(test, "int", "String");
         // dbo.setPreparedStatement(test);
         // PreparedStatement a = dbo.getPreparedStatement();
         // s.println(test.toString());
         // a.setInt(1, Integer.parseInt(test.getRow(0)[0]));
         // a.setString(2, "TEST");
         // a.execute();

      } catch (IOException | NotEnoughColumnsException | SQLException | DifferentNumberOfColumnsException e) {
         e.printStackTrace();
      }

      dbo.disconnect();
   }
   // Ggf. Tabelle mit SQL in DB erzeugen

}