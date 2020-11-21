package server.serverControllers;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// Pre-Project Exercise 

// This program allows you to create and manage a store inventory database.
// It creates a database and data table, then populates that table with tools from
// items.txt.
public class DbControllerHelper implements DatabaseConstants {
	
	public DbControllerHelper() {
	}

	// Fills the data table with all the tools from the text file 'items.txt' if found
	public void fillItemTable()
	{
		try{
			Scanner sc = new Scanner(new FileReader(ITEMFILE));
			while(sc.hasNext())
			{
				String itemInfo[] = sc.nextLine().split(";");
				addItem( new Item( Integer.parseInt(itemInfo[0]),
						                            itemInfo[1],
						           Integer.parseInt(itemInfo[2]),
						         Double.parseDouble(itemInfo[3]),
						           Integer.parseInt(itemInfo[4]),
						           					itemInfo[5],
						           Integer.parseInt(itemInfo[6]),
						           Integer.parseInt(itemInfo[7])));
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + ITEMFILE + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add a tool to the database table
	public void addItem(Item item)
	{
		return ("INSERT INTO " + ITEMS +
				" VALUES ( " + item.sqlInsert());
	}

	// This method should search the database table for a tool matching the toolID parameter and return that tool.
	// It should return null if no tools matching that ID are found.
	public Tool searchTool(int toolID)
	{
		// TO DO**
		String sql = "SELECT * FROM " + tableName + " WHERE ID=" + toolID;
		ResultSet tool;
		try {
			statement = jdbc_connection.createStatement();
			tool = statement.executeQuery(sql);
			if(tool.next())
			{
				return new Tool(tool.getInt("ID"),
								tool.getString("TOOLNAME"), 
								tool.getInt("QUANTITY"), 
								tool.getDouble("PRICE"), 
								tool.getInt("SUPPLIERID"));
			}
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	// Prints all the items in the database to console
	public void printTable()
	{
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
			ResultSet tools = statement.executeQuery(sql);
			System.out.println("Tools:");
			while(tools.next())
			{
				System.out.println(tools.getInt("ID") + " " + 
								   tools.getString("TOOLNAME") + " " + 
								   tools.getInt("QUANTITY") + " " + 
								   tools.getDouble("PRICE") + " " + 
								   tools.getInt("SUPPLIERID"));
			}
			tools.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		InventoryManager inventory = new InventoryManager();
		
		// You should comment this line out once the first database is created (either here or in MySQL workbench)
		inventory.createDB();

		inventory.createTable();
		
		System.out.println("\nFilling the table with tools");
		inventory.fillTable();

		System.out.println("Reading all tools from the table:");
		inventory.printTable();

		System.out.println("\nSearching table for tool 1002: should return 'Grommets'");
		int toolID = 1002;
		Tool searchResult = inventory.searchTool(toolID);
		if(searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nSearching table for tool 9000: should fail to find a tool");
		toolID = 9000;
		searchResult = inventory.searchTool(toolID);
		if(searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());
		
		System.out.println("\nTrying to remove the table");
		inventory.removeTable();
		
		try {
			inventory.statement.close();
			inventory.jdbc_connection.close();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally
		{
			System.out.println("\nThe program is finished running");
		}
	}
}