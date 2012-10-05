package edu.cs.iit.cs495;
import java.util.HashMap;
import java.util.LinkedList;
public class WordCountJ {
   private int numReducer = 100;
   public WordCountJ(){
   }
   private class Mapper implements Runnable{
      private HashMap<String, Integer> context;
      private String data;
      public Mapper(String input){
         context = new HashMap<String, Integer>();
         data = input;
      }
      private void map(String input) {
         // tokenize
         context.put(input, 1);
      }
      public void run(){
         //tokenize input
         /*
          * for ( word in data)
          *     map( word )
          *     done
          */
      }
   }
   private class Reducer implements Runnable {
      private HashMap<String, Integer> context;
      private HashMap<String, LinkedList<Integer>> intermediate;
      public Reducer(HashMap<String, LinkedList<Integer>> occurences){
         context = new HashMap<String, Integer>();
      }
      private void reduce(String key, LinkedList<Integer> values){
         int sum = 0;
         for (Integer q : values){
            sum += q.intValue();
         }
         context.put(key, sum);
      }
      public void run(){
      }
   }
   public static void main(String [] args){
      System.out.println("Hello World");
   }
}
