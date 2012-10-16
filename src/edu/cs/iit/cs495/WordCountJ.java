package edu.cs.iit.cs495;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.util.concurrent.*;
public class WordCountJ {
   private class Counter implements Runnable {
      private HashMap<String, Integer> context;
      private File data;
      //private int reducers;
      public Counter(File input, HashMap<String,Integer> output){
         context = output;
         data = input;
      }

      public void run(){
         //tokenize input
         try {
            BufferedReader readin = new BufferedReader(new FileReader(data));
            String nextline = readin.readLine();
            while (nextline != null){
               StringTokenizer st = new StringTokenizer(nextline);
               Pattern w = Pattern.compile("\\b([\\w\\'\\-]+)\\b");
               while (st.hasMoreTokens()) {
                  Matcher m = w.matcher(st.nextToken());
                  
                  if (m.matches()){
                      String s = m.group(1).toLowerCase();
                     if (context.containsKey(s))
                        context.put(s, new Integer(context.get(s).intValue() + 1));
                     else
                        context.put(s, new Integer(1));
                  }
               }
               nextline = readin.readLine();
            }
         }catch (Exception e) {
            System.out.println("Counter failed");
         }
         try {
         } finally {
            latch.countDown();
         }
      }
   }

   private LinkedList<HashMap<String, Integer>> mapout;
   private CountDownLatch latch;
   public WordCountJ(String [] filelist, int threads){
      Thread [] mapthreads = new Thread[filelist.length];
      ExecutorService pool = Executors.newFixedThreadPool(threads);
      latch = new CountDownLatch(filelist.length);
      mapout = new LinkedList<HashMap<String, Integer>>();
      for (int i =0; i < filelist.length; i++){
         mapout.addFirst(new HashMap<String, Integer>());
         mapthreads[i] =  new Thread(new Counter(new File(filelist[i]), mapout.peek()));
         //mapthreads[i].start();
         pool.submit(mapthreads[i]);
      }
      try {
         latch.await();
         pool.shutdownNow();
         LinkedList<HashMap<String, Integer>> counts = new LinkedList<HashMap<String, Integer>>();
        for (int i =0; i < filelist.length; i++){
          mapthreads[i].join();
          HashMap<String, Integer> h = mapout.removeLast();
          //System.out.println("File name: " + filelist[i]);
          counts.add(h);
        }
        HashMap<String, Integer> first = counts.removeFirst();
        for (HashMap<String, Integer> map : counts){
           merge(first, map);
        }
        String[] keys = first.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (int s = 0; s < keys.length; s++) {
          System.out.println(keys[s] + "\t" + first.get(keys[s]).intValue());
        }

      } catch (Exception e) {
         System.out.println("Print result failed");
         e.printStackTrace();
      }
   }

   public void merge(HashMap<String,Integer> base, HashMap<String,Integer> diff) {
      for (String key : diff.keySet()){
         Integer value = base.get(key);
         if (value == null)
                base.put(key, diff.get(key));
         else
                base.put(key, new Integer(value.intValue() + diff.get(key).intValue()));
      }
   }


   public static void main(String [] args){
      File indir = new File(args[0]);
      String [] infiles = indir.list();
      for (int i = 0; i < infiles.length; i++){
         infiles[i] = args[0] + "/" + infiles[i];
      }
      int threads = 1;
      try {
         threads = Integer.parseInt(args[1]);
      } catch (Exception e) {
      } finally {
         new WordCountJ(infiles, threads);
      }
   }
}
