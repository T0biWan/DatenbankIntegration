package de.rainbowdancers.datenbanken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import de.rainbowdancers.exceptions.NotEnoughColumnsException;
import de.tobi_wan.support.IOStreamCSV;
import de.tobi_wan.support.Table;



public class IOStreamTableCSV extends IOStreamCSV {
   // Attribute

   // Konstruktor
   public IOStreamTableCSV(String separator) {
      super(separator);
   }

   // Getter & Setter

   // Methoden
   public Table readCSVIntoTable(String path, Table table) throws IOException, NotEnoughColumnsException {
      List<String> lines = Files.readAllLines(Paths.get(path));
      String testRow[] = lines.get(0).split(getSeparator());
      if (testRow.length > table.getNumberOfColumns()) throw new NotEnoughColumnsException();
      for (String line : lines)
         table.addRow(line.split(getSeparator()));
      return table;
   }
}