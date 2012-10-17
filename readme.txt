ant assumes that the hadoop base directory is located at
../hadoop relative to the build.xml

so unpack hadoop in the same directory that this project is in
compile with ant

the prebuilt jar is located at ./output/proj3_wc.jar

run with

for threaded:
java -jar output/proj3_wc.jar WordCountJ <input directory> <thread pool size>

for hadoop:
in hadoop dir
bin/hadoop jar path/to/proj/output/proj3_wc.jar WordCountMR <inputdir> <outdir>


Source files

WordCountJ
        this is the threaded java implementation of wordcount
        it has a pool of threads that map each file for words
        then it merges each files count into one unified count

WordCountMR
        this is the hadoop map reduce class. It contains the map
        and the reduce classes as well as the main function for 
        hadoop job configuration and launching

Launcher
        this is the main class file of the jar file. all it does is take
        the arguments to determine which program to run and passes the rest of
        the arguments to that program.
