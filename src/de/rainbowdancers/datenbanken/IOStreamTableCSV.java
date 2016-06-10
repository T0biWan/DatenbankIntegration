package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import tobi_wan.support.IOStreamCSV;



public class IOStreamTableCSV extends IOStreamCSV {
   // Attribute

   // Konstruktor
   public IOStreamTableCSV(String separator) {
      super(separator);
   }

   // Getter & Setter

   // Methoden
   public Table readCSVIntoTable(String path, Table table) throws IOException {
      // Sicherheitsabfrage
      List<String> lines = Files.readAllLines(Paths.get(path));
      for (String line : lines)
         table.addRow(line.split(getSeparator()));
      return table;
   }
}
