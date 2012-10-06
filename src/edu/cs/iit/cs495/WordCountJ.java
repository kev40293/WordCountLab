package edu.cs.iit.cs495;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
public class WordCountJ {
   private class Mapper implements Runnable {
      private HashMap<String, Integer> context;
      private File data;
      private int reducers;
      public Mapper(File input,
            HashMap<String,Integer> output){
         context = output;
         data = input;
      }
      private void map(String word) {
         // replace with better calculation?
         int bucket = word.hashCode() % reducers;
         if (context.containsKey(word))
            context.put(word, new Integer(1));
         else
            context.put(word, new Integer(context.get(word).intValue() + 1));
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
      public Reducer(HashMap<String, LinkedList<Integer>> input,
            HashMap<String, Integer> output) {
         context = output;
         intermediate = input;
      }
      private void reduce(String key){
         int sum = 0;
         for (Integer q : intermediate.get(key)){
            sum += q.intValue();
         }
         context.put(key, sum);
      }
      public void run(){
         /*
          * for ( key in map )
          * do reduce (key)
          * done
          *
          * sort context
          * return context;
          */
      }
   }
   private int numReducers = 100;
   private Mapper[] mappers;
   private HashMap<String, Integer>[][] mapout;
   public WordCountJ(String [] filelist){
      mappers = new Mapper[filelist.length];
      mapout = new HashMap<String, Integer>[filelist.length][numReducers];
      for (int i =0; i < filelist.length; i++){
         mappers[i] = new Mapper(new File(filelist[i]), numReducers, mapout[i]);
      }
      /*
       * for x in files
       * create mapper thread, input = x;
       * map_thread.run()
       * done
       *
       * partition into n maps for reduce
       *
       * for n in reduce_maps
       * thread reduce (n )
       * done
       *
       * output = sort . merge reducecontex
       *
       *
       * print output
       *
       */
   }

   public static void main(String [] args){
      System.out.println("Hello World");
   }
}
