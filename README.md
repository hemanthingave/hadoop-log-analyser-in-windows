# log analysis using hadoop

This project entails access log analysis using hadoop framework in windows environment


Framework and technology:
-----------------

* Hadoop 

Prerequisites:
-----------------

* Microsoft Windows SDK v7.1.
The Windows SDK  provides tools like headers, libraries, code samples and compilers a new help system that developers can use to create applications that run on Microsoft Windows.

* Cygwin.
Cygwin is an Open Source tools which provide functionality similar to a Linux distribution environment on Windows. The substantial POSIX API functionality provided by a DLL (cygwin1.dll).

* Maven 3.1.1.
Apache Maven is a software project comprehension and management  tool. Supported the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information. Maven is a build automation tool used build  Java projects in .jar. 

* Protocol Buffers 2.5.0 
Protocol Buffers are used for serializing structured data. They are provides interface for developing programs to communicate with each other over a wire or for storing data. The method that describes the structure of some data and a program that generates from that description source code in various programming languages for generating or parsing a stream of bytes that represents the structured data.


How to use
-----------------

* Set Environment Variables
Setup the Environment Variables as JAVA_HOME, M2_HOME and Platform. Variable name are case sensitive. The value for Platform variable will be either x64 or Win32 for building on a 64-bit or 32-bit os. JDK installation path should not contains any space for the JAVA_HOME environment variable. After Edit Path Variable sited at installation directory to add bin directory of Cygwin  (say C:\cygwin64\bin),  bin directory of Maven (say C:\maven\bin) and installation path of Protocol Buffers (say c:\protobuf). 
 
* Download hadoop and place it in the home directory.
After that download hadoop-2.x-src.tar.gz and extract compress file to a folder having shorten path (say c:\hdfs) to avoid runtime problem due to maximum path length limitation in Windows.

* Create Windows binary tar distribution for native support
The native support for Hadoop is provided by mvn package. To setup environment Select Start → All Programs → Microsoft Windows SDK v7.1 and open Windows SDK 7.1 Command Prompt. Change directory to Hadoop source code folder (say c:\hdfs). Execute mvn package with options -Pdist,native-win -DskipTests -Dtar to create Windows binary tar distribution. After process completion native distribution hadoop-2.x.tar.gz will be created inside C:\hdfs\hadoop-dist\target\hadoop-2.x directory.

* Install Hadoop
Download the apache hadoop from source distribution or mirror. Extract hadoop-2.x.tar.gz to a folder (say c:\hadoop). Add Environment Variable HADOOP_HOME and edit Path Variable to add bin directory of HADOOP_HOME (say C:\hadoop\bin).

* Configure Hadoop
following changes are mandatory to  configure Hadoop. 
•	File: C:\hadoop\etc\hadoop\core-site.xml- It stores name and URL of the default file system. The uri's authority is used to determine the port, host  etc. for a filesystem. We set its value as hdfs://localhost:9000  
•	File: C:\hadoop\etc\hadoop\hdfs-site.xml- dfs.replication: Specified Default block replication factor. The replications factor can be specified when the file is created and when replication is not specified the default value is used.  dfs.namenode.name.dir: This specify directory where name node store the name table(fsimage) on the local filesystem DFS, dfs.datanode.data.dir: This specify directory where data node in DFS on the local filesystem store its blocks.
•	File C:\hadoop\etc\hadoop\yarn-site.xml- yarn.nodemanager.aux-services:
This is auxiliary service name. Its Default value is omapreduce_shuffle yarn.nodemanager.aux-services.mapreduce.shuffle.class: This is auxiliary service class to use. The Default value is org.apache.hadoop.mapred.ShuffleHandler yarn.application.classpath: This specify classpath for YARN applications.
•	File: C:\hadoop\etc\hadoop\mapred-site.xml- mapreduce.framework.name:
The runtime framework for executing MapReduce jobs. Can be one of local, classic or yarn.

* Format the namenode
Namenode should be formatted for the first time only on setup node.

* Start the cluster
Start HDFS: The Namenode and Datanode  can be started by executing command ‘\sbin>start-dfs’. Fig 3.4 Shows Namenode and Datanode
  
* Namenode and Datanode
Start MapReduce aka YARN: YARN split up the two major functionalities of the JobTracker, resource management and job scheduling/monitoring, into separate daemons as Resource Manager and Node Manager.

* Verify Installation
Finally we shows Resource Manager and Node Manager at http://localhost:8042 and Namenode at http://localhost:50070.


