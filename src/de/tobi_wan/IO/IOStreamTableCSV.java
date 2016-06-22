package de.tobi_wan.IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import de.rainbowdancers.exceptions.DifferentAmountOfColumnsException;
import de.tobi_wan.dataStructure.Table;



public class IOStreamTableCSV extends IOStreamCSV {
   // Attribute

   // Konstruktor
   public IOStreamTableCSV(String separator) {
      super(separator);
   }

   // Getter & Setter

   // Methoden
   public Table readCSVIntoTable(String path, Table table) throws IOException, DifferentAmountOfColumnsException {
      List<String> lines = Files.readAllLines(Paths.get(path));
      String testRow[] = lines.get(0).split(getSeparator());
      if (testRow.length > table.getNumberOfColumns()) throw new DifferentAmountOfColumnsException();
      for (String line : lines)
         table.addRow(line.split(getSeparator()));
      return table;
   }
}