searchInRdfGraph
================

Searching in web semantic

This project allows to make research on RDF/OWL graphs

You can execute the project either within an IDE as Eclipse (make sure to import it as a MAVEN project)
or in command line

In command line.

For a correct use, follow these steps

1- download the latest version of maven  : http://maven.apache.org/download.cgi
   and unzip it into a desired folder (exemple: c:\apache-maven-3.0.4)
  
2- configure environnement variables (we'll illustrate windows configuration)

    make sure you already have JDK installed and set them as global variables
    
    add variables
    •	JAVA_HOME = C:\Program Files\Java\jdk1.7.0_03\
    •	M2_HOME = C:\apache-maven-3.0.4\
    
    add to PATH
    •	path = C:\apache-maven-3.0.4\bin;

3- execution

    • Move to a desired folder and clone the project
    • with the prompt, move to the folder (where you'll find pom.xml and src folder) and enter command
            $mvn package
    • a folder target will be created, then move to target (cd target) and enter cammand 
            $java -jar rdfSearch-x.x.x-jar-with-dependencies.jar
    • select a RDF/OWL file by selecting the open menuitem of the menu File
    • enter key words to make your research
    • click on the refresh button to create the result graph on a new tab
    
Enjoy searching on RDF graphs

