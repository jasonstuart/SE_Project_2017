# SE_Project_2017_Group_10
## WitsCABS Project 10

### Documentation
Please Note that all Documentation is currently sitting in the Documentation sub-folder. Inside this folder is a folder called "Current Document" which is the latest current document. i.e. the Document path to the latest documentation is Documents/Current Document/WitsCABS_Documentation.pdf

### Compile and Run Android Application
Note: The app/project data is located at WitsCABS_Code/WitsCabs_Android/.
The Android application has been developed by using IntelliJ Idea as an IDE
with Java as a programming language. Java has been chosen as it is the most
familiar programming language between the group as well as the most used
among android developers. Before installing the .apk file, one has to install
the latest version of the Java jdk (1.8) and the android sdk (API 23). In order
to install the app on a mobile device, one would be required to install IntelliJ
Idea or Android Studio. We will assume that we're using IntelliJ Idea in this
description. The first thing to do would to navigate to the project structure.
In the project structure, choose the jdk and sdk you downloaded on your
system. When using a mobile device to run the application, make sure that
developer options are enabled. Do this by clicking 10 times on the build
number under my device info in the settings of the android device. Once
this is completed, enable USB Debugging in the developer options. You are
now ready to install the WitsCabs app. Simply click on the \Run" tab on
the top of the screen and choose \Run Application" with the mobile device
connected to the pc running IntelliJ Idea via a usb cable. When prompted
to choose which device to run the application on, choose the name of the
android device being used and the application .apk file will be installed on
the device. Once this is completed, all that is needed is a Wi-Fi or cellular
connection on the device for the app to work. Note: once installed, the
application can be run from the android device without having to install it
again.

### Compile and Run Call-Center Application
Note: The app/project data is located at WitsCABS_Code/CallCenter/.
The Desktop Application to be used in the call center will be a Java program
(as previously mentioned). So in order to compile it an IDE that supports
Java will be required eg Eclipse or Netbeans. For the sake of this document
it is assumed that Netbeans will be used. Further it is required that you
have the latest JDK and JRE software installed from Oracle.
Netbeans has a built in compiler that is easy to use. On the ribbon at the top
of screen there are two hammers, one hammer has a brush. These hammers
are used to call \build" and \clean and build". Clicking either one of them
will compile the code. The difference between the two is that clean and build
completely recompiles the code, deleting former versions.
After compiling a .jar file should be present in the dist folder of the Java
project. This .jar file can be transferred to computers in the call center and
will allow the application to run without the need for an IDE.It will require
the use of the command prompt.
To run the code using an IDE there are multiple options, you can right
click the main class and select run file, or click the green play button in the
top ribbon. Either of these options will run the code if the code has been
compiled or compile and then run the code. To run from the command line
without the IDE installed, navigate to the file in the command prompt using
the cd command and then type: java -jar CallCenter.jar

### Compile and Run Backend-Server
Note: The project folder is located at WitsCABS_Code/WitsCabs_Backend/.
The distributable is located at WitsCABS_Code/WitsCabs_Backend/dist/
The SQL Backup is located at WitsCABS_Code/WitsCabs_Backend/MySQL Server/
Similarly to the Call Center Desktop Application, the server has also been
constructed using Java as the programming language in order to more easily
maintain compatibility with all the other programs. So in order to compile
the code, you can follow a similar approach to compiling and running the call-Center application described above.
Note it is required that you have installed the latest java JDK as
well as JRE software. There is however an extra step required to setup the
database to work with the server. One needs to install mySQL and install it
to the system using the default settings. When setting up user details, set
the root password to \jason". Keep the port number the default. Further-
more, make sure to restore the current database from the repository under
the folder heading MySQL Server (in the server project folder) to the server
running on your machine using the command: mysql -u root -p WitsCABS
< WitsCABS.sql where this command is run in the same directory as the
sql backup is located. Finally, to make sure that you can receive connec-
tions from client programs, make sure to give them your current computer
ip address to input into their program code for testing. This can be done
using your command prompt by typing ipconfig. Note: it is critical to be
on a network other than the Wits network as the proxy service and network
policies interfere with the correct running of the server. Remember to also
input this ip address into both the android and call-center app, or you will
not be able to successfully link each piece of software together. No ip address
or port number configuration is needed for the database if the instructions
above have been followed. To run the server, follow the similar setup as for
the desktop application, i.e. navigate to the .jar file in the command prompt
using cd and then type: java -jar WitsCABS Backend.jar