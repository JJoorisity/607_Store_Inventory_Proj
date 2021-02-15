# 607_Store_Inventory_Proj

## About
This project was a combined project between Database design and software architecture. The task was to
design and implement a store inventory and client management system, using the MVC design pattern, Java, MySQL, and JDBC. 
Client and Server architecture was implemented; the project runs locally using sockets.

The architechture of the program was drawn in UML. These diagrams can be found in the "UMLDiagrams" folder. 
The relational diagram, outlining the MySQL database design, can be found in "ToolShop_Relational_Diagram.pdf".

## Project Objectives
1. Conceptual database design based on project narrative
2. Relational database design through relational model mapping
3. Implementation and querying of a database
4. Communicating to the MySQL database from Java
5. Client-Server local architecture
6. Model, View, Controller architecture design.
7. Java Graphical User Interface event handling for front end design

## Requirements
- Java 11
- MySQL Workbench
- JDBC

## Running the Program
To run this program, follow these steps:

1. Clone the repository
2. Populate the required database credentials in DatabaseConstants.java located in "ENSF607Proj_Server/src/server/serverControllers/DatabaseConstants.java"
3. Run the DbController main method to initialize the MySQL database tables, located in "ENSF607Proj_Server/src/server/serverControllers/DbController.java"
4. Run ServerController.java located in "ENSF607Proj_Server/src/server/serverControllers/ServerController.java" to run the local server
5. Run ClientController.java located in "ENSF607Proj_Client/src/client/clientControllers/ClientController.java" to launch the GUI
6. You can now use the GUI to perform actions on the database (The server operates on a thread pool and can handle multiple clients).
