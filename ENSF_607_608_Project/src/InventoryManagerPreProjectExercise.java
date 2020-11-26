import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import sharedModel.Item;
import sharedModel.Item_Elec;

// Pre-Project Exercise 

// This program allows you to create and manage a store inventory database.
// It creates a database and datatable, then populates that table with tools from
// items.txt.
public class InventoryManagerPreProjectExercise {

	public Connection jdbc_connection;
	public Statement statement;
	public String databaseName = "607PreProjectEx", tableName = "ToolTable", dataFile = "items.txt";

	// Students should configure these variables for their own MySQL environment
	// If you have not created your first database in mySQL yet, you can leave the
	// "[DATABASE NAME]" blank to get a connection and create one with the
	// createDB() method.
	public String connectionInfo = "jdbc:mysql://localhost:3306/", login = "root",
			password = "craven-stomp-husband-learn";

	public InventoryManagerPreProjectExercise() {
		try {
			// If this throws an error, make sure you have added the mySQL connector JAR to
			// the project
			Class.forName("com.mysql.jdbc.Driver");

			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Use the jdbc connection to create a new database in MySQL.
	// (e.g. if you are connected to "jdbc:mysql://localhost:3306", the database
	// will be created at "jdbc:mysql://localhost:3306/InventoryDB")
	public void createDB() {
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			System.out.println("Created Database " + databaseName);

			statement.executeUpdate("USE " + databaseName); // added code
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create a data table inside of the database to hold tools. NOTE: JJ and NJ
	// have replaced this with our version from our project.
	public void createTable() {
		String sql = "CREATE TABLE " + tableName + "(itemId INTEGER not NULL, " + " supplierId INTEGER not NULL, "
				+ " itemType VARCHAR(1), " + " itemDesc VARCHAR(255), " + " itemPrice DECIMAL(5,2), "
				+ " itemQty INTEGER, " + " powerType VARCHAR(10), " + " V INTEGER, " + " Ph INTEGER, "
				+ " PRIMARY KEY (itemId) )";
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Removes the data table from the database (and all the data held within it!)
	public void removeTable() {
		String sql = "DROP TABLE " + tableName;
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Removed Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method added by students. Replaces professor supplied add import from text
	// method.
	public ArrayList<Object> importFromTxt(String filename) {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			Scanner sc = new Scanner(new FileReader(filename));
			while (sc.hasNext()) {
				String fileInfo[] = sc.nextLine().split(";");
				list.add(new Item_Elec(Integer.parseInt(fileInfo[0]), fileInfo[1].charAt(0), fileInfo[2],
						Integer.parseInt(fileInfo[3]), Double.parseDouble(fileInfo[4]), Integer.parseInt(fileInfo[5]),
						fileInfo[6], Integer.parseInt(fileInfo[7]), Integer.parseInt(fileInfo[8])));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + filename + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Fills the data table with all the tools from the text file 'items.txt' if
	// found. Note we are using our own method as developed for this project rather
	// than
	// the D2L supplied method
	public void initializeItemTable() {
		ArrayList<Object> newItems = this.importFromTxt(dataFile);
		for (Object item : newItems) {
			this.insertItem((Item_Elec) item);
		}
	}

	// Add a tool to the database table
	// note we are using our own method as developed for this project rather than
	// the D2L supplied method

	public void insertItem(Item_Elec item) {
		try {

			String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStat = jdbc_connection.prepareStatement(query);
			pStat.setInt(1, item.getItemID());
			pStat.setInt(2, item.getSupplierID());
			pStat.setString(3, String.valueOf(item.getItemType()));
			pStat.setString(4, item.getItemDesc());
			pStat.setDouble(5, item.getPrice());
			pStat.setInt(6, item.getQty());
			pStat.setString(7, item.getPowerType());
			pStat.setInt(8, item.getVoltage());
			pStat.setInt(9, item.getPhase());
			int rowCount = pStat.executeUpdate();
			// System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + tableName + " and " + item.getItemID());
			e.printStackTrace();
		}
	}

	// This method should search the database table for a tool matching the toolID
	// parameter and return that tool.
	// It should return null if no tools matching that ID are found.
	public Item_Elec queryItem(int itemID) {
		Item_Elec queryRes = null;
		try {
			String query = "SELECT * FROM " + tableName + " WHERE itemId = ?";
			PreparedStatement pStat = jdbc_connection.prepareStatement(query);
			pStat.setInt(1, itemID);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new Item_Elec(results.getInt("itemId"), results.getString("itemType").charAt(0),
						results.getString("itemDesc"), results.getInt("itemQty"), results.getDouble("itemPrice"),
						results.getInt("supplierId"), results.getString("powerType"), results.getInt("V"),
						results.getInt("Ph"));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryItem by ID failed with " + itemID);
			e.printStackTrace();
		}
		return queryRes;
	}

	// Prints all the items in the database to console
	public void printTable() {
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
			ResultSet tools = statement.executeQuery(sql);
			System.out.println("Tools:");
			while (tools.next()) {
				System.out.println(
						tools.getInt("ItemId") + " " + tools.getString("ItemDesc") + " " + tools.getInt("ItemQty") + " "
								+ tools.getDouble("ItemPrice") + " " + tools.getInt("SUPPLIERID"));
			}
			tools.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		InventoryManagerPreProjectExercise inventory = new InventoryManagerPreProjectExercise();

		// You should comment this line out once the first database is created (either
		// here or in MySQL workbench)
		inventory.createDB();

		inventory.createTable();

		System.out.println("\nFilling the table with tools");
		inventory.initializeItemTable();

		System.out.println("Reading all tools from the table:");
		inventory.printTable();

		System.out.println("\nSearching table for tool 1002: should return 'Grommets'");
		int toolID = 1002;
		Item searchResult = inventory.queryItem(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nSearching table for tool 9000: should fail to find a tool");
		toolID = 9000;
		searchResult = inventory.queryItem(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nTrying to remove the table");
		inventory.removeTable();

		try {
			inventory.statement.close();
			inventory.jdbc_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\nThe program is finished running");
		}
	}
}