package edu.cs.iit.cs495;
import java.util.*;
import java.util.regex.*;
import java.io.*;
public class WordCountJ {
   private class Mapper implements Runnable {
      private HashMap<String, Integer> context;
      private File data;
      //private int reducers;
      public Mapper(File input,
            HashMap<String,Integer> output){
         context = output;
         data = input;
      }
      private void map(String word) {
         // replace with better calculation?
         //int bucket = word.hashCode() % reducers;
         StringTokenizer st = new StringTokenizer(word);
         Pattern w = Pattern.compile("(\\w)+");
         while (st.hasMoreTokens()) {
            Matcher m = w.matcher(st.nextToken());
            if (m.matches()){
               if (context.containsKey(m.group(0)))
                  context.put(m.group(0), new Integer(context.get(m.group(0)).intValue() + 1));
               else
                  context.put(m.group(0), new Integer(1));
            }
         }
      }
      public void run(){
         //tokenize input
         try {
            BufferedReader readin = new BufferedReader(new FileReader(data));
            String nextline = readin.readLine();
            while (nextline != null){
               map(nextline);
               nextline = readin.readLine();
            }
         }catch (Exception e) {
            System.out.println("Mapper failed");
         }

         /*
          * for ( word in data)
          *     map( word )
          *     done
          *
          *     partition into linked list of size n reducers
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
   private LinkedList<HashMap<String, Integer>> mapout;
   public WordCountJ(String [] filelist){
      Thread [] mapthreads = new Thread[1];
      //mappers = new Mapper[filelist.length];
      mapout = new LinkedList<HashMap<String, Integer>>();
      for (int i =0; i < filelist.length; i++){
         mapout.addFirst(new HashMap<String, Integer>());
         mapthreads[i] =  new Thread(new Mapper(new File(filelist[i]), mapout.peek()));
         mapthreads[i].start();
      }
      try {
         mapthreads[0].join();
         for (String k : mapout.peek().keySet()){
            System.out.println(k + " : " + mapout.peek().get(k).intValue());
         }
      } catch (Exception e) {
         System.out.println("Print result failed");
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
      String [] infiles = {"input/input_00.txt" };
      WordCountJ hi = new WordCountJ(infiles);
   }
}
