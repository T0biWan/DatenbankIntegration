package de.rainbowdancers.datenbanken;

import java.io.IOException;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import de.tobi_wan.support.StandardOutput;
import de.tobi_wan.support.Table;



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
         io.readCSVIntoTable("data/Brands.csv", brands);
         io.readCSVIntoTable("data/Clothing.csv", clothing);
         io.readCSVIntoTable("data/Colors.csv", colors);
         io.readCSVIntoTable("data/OnlineShops.csv", onlineShops);
         io.readCSVIntoTable("data/Outfit.csv", outfits);

         io.readCSVIntoTable("data/Outfit.csv", brands);
         s.println("Tabellen eingelesen");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (NotEnoughColumnsException e) {
         e.printStackTrace();
      }
      // Tabellen in SQL uebersetzen
      // Ggf. Tabelle erzeugen
      // SQL Transaktionen anweisen
      // dbo.disconnect();
   }

}