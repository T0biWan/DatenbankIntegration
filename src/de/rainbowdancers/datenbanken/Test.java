package de.rainbowdancers.datenbanken;

import java.io.IOException;
import tobi_wan.support.StandardOutput;



public class Test {

   public static void main(String [] args) {
      // Attribute
      StandardOutput s = StandardOutput.defaultSupportMethods();
      String connectionString = "jdbc:oracle:thin:@dbl43.beuth-hochschule.de:1521:oracle";
      String user = "KLATT";
      String password = "Student";
      DatabaseOperationsOracle dbo = new DatabaseOperationsOracle(connectionString, user, password);
      IOStreamTableCSV io = new IOStreamTableCSV(";");
      TranslateTableToSQL CSVToSQL = new TranslateTableToSQL();
      Table brands = new Table("Brands", 2);
      Table clothing = new Table("Clothing", 2);
      Table colors = new Table("Colors", 2);
      Table onlineShops = new Table("OnlineShops", 2);
      Table outfits = new Table("Outfits", 6);

      dbo.printFields();
      // dbo.connect();
      try {
         io.readCSVIntoTable("src/de/rainbowdancers/data/Brands.csv", brands);
         io.readCSVIntoTable("src/de/rainbowdancers/data/Clothing.csv", clothing);
         io.readCSVIntoTable("src/de/rainbowdancers/data/Colors.csv", colors);
         io.readCSVIntoTable("src/de/rainbowdancers/data/OnlineShops.csv", onlineShops);
         io.readCSVIntoTable("src/de/rainbowdancers/data/Outfit.csv", outfits);
         s.println("Tabellen eingelesen");
      } catch (IOException e) {
         e.printStackTrace();
      }
      // Tabellen in SQL uebersetzen
      // Ggf. Tabelle erzeugen
      // SQL Transaktionen anweisen
      // dbo.disconnect();
   }

}
