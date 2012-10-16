package edu.cs.iit.cs495;
import java.util.Arrays;

public class Launcher {
   public static void main (String[] args){
      try{
         if (args[0].equalsIgnoreCase("WordCountJ"))
            WordCountJ.main(Arrays.copyOfRange(args,1, args.length));
         else if (args[0].equalsIgnoreCase("WordCountMR"))
            WordCountMR.main(Arrays.copyOfRange(args, 1, args.length));
      } catch (Exception e) {
         System.out.println("Failed to run specified program");
         e.printStackTrace();
      }
   }
}

