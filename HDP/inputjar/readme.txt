C:\HDP\sbin>start-dfs                                                                   //Start Daemons
C:\HDP\sbin>start-yarn
C:\HDP>bin>hdfs dfs -mkdir /input                                                       //creating directory input in HDFS
C:\HDP\bin>hdfs dfs -copyFromLocal c:/wordcount/myfile.txt /input                       // copy myfile.txt to hdfs
C:\HDP\bin>hdfs dfs -ls /input                                                          //Check content of the copied file.
C:\HDP\bin>hdfs dfs -cat /input/myfile.txt                                              // To View Content Of File

C:\HDP\bin>hadoop fs -rm -r /logFiles/video.mp4                                         //To Delete Directory or file

------------------------------  START  ---------------------------------------------------------------------------------------------
yarn jar ../inputjar/loggerIP.jar /logFiles/abc.log /logFiles/output11                  //Execute Jar


http://hemant-pc:8088/cluster/apps/FINISHED                                             //To see job status


Hits
yarn jar ../inputjar/loggerIPTest.jar /logFiles/abc.log /logFiles/output
yarn jar ../inputjar/loggerIPTest.jar /logFiles/NASAWebLog/NASA.log /logFiles/NASAWebLog/outputNasaIP

Page View
yarn jar ../inputjar/loggerPVTest.jar loganalyzer /logFiles/NASAWebLog/NASA.log /logFiles/NASAWebLog/outputNasaPV 
yarn jar ../inputjar/loggerPVTest.jar loganalyzer /logFiles/abc.log /logFiles/outputPV

