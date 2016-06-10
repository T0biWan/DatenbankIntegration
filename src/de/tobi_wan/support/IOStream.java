package de.tobi_wan.support;

import java.nio.file.Path;
import java.nio.file.Paths;



public class IOStream {
   // Attribute
   private Path path;

   // Getter & Setter
   public Path getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = Paths.get(path);
   }

   // Konstruktor
   public IOStream() {

   }

   // Methoden

}
