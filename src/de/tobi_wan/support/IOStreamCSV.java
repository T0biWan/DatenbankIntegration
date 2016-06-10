package de.tobi_wan.support;

import java.io.IOException;
import java.util.ArrayList;



public class IOStreamCSV extends IOStream {
   // Attribute
   String separator;

   // Getter & Setter
   public String getSeparator() {
      return separator;
   }

   public void setSeparator(String separator) {
      this.separator = separator;
   }

   // Konstruktor
   public IOStreamCSV(String separator) {
      super();
      setSeparator(separator);
   }

   // Methoden
   public void writeCSV(String path, ArrayList<String> data) throws IOException {

   }

   public ArrayList<String> readCSV() throws IOException {
      return null;
   }
}
