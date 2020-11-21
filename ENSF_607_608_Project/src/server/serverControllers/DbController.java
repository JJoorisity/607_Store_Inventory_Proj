package server.serverControllers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sharedModel.*;
import java.sql.*;

public class DbController implements DatabaseConstants, DatabaseTables {

	// Attributes
	private Connection conn;	// Object of type connection from the JDBC class that deals with connecting to the database
	private Statement stmt; 	// object of type statement from JDBC class that enables the creation "Query statements"
	private ResultSet rs;		// object of type ResultSet from the JDBC class that stores the result of the query
	private DbControllerHelper helper;

	public DbController() {
		helper = new DbControllerHelper();
	}
	
	public void initializeConnection() {
		try {
			// Register JDBC driver
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Problem");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTable() {

		String sqlSupp = "CREATE TABLE " + SUPPLIERS +
				"(supplierId INTEGER not NULL, " +
				" supplierType VARCHAR(1), " +
				" supplierName VARCHAR(255), " +
				" salesContact VARCHAR(255), " +
				" address VARCHAR(255), " +
				" importTax DECIMAL(5,2), " +
				" PRIMARY KEY (supplierId))";
		
		String sqlItems = "CREATE TABLE " + ITEMS +
				"(itemId INTEGER not NULL, " +
				" supplierId INTEGER not NULL, " +
				" itemType VARCHAR(1), " +
				" itemDesc VARCHAR(255), " +
				" itemPrice DECIMAL(5,2), " +
				" itemQty INTEGER, " +
				" powerType VARCHAR(10), " +
				" V INTEGER, " +
				" Ph INTEGER, " +
				" PRIMARY KEY (itemId), " +
				"  CONSTRAINT FK_SuppItem FOREIGN KEY (supplierId) REFERENCES Suppliers(supplierId))";
		
		String sqlOrder = "CREATE TABLE " + ORDERS +
				"(orderId INTEGER not NULL, " +
				" orderDate DATE, " +
				" PRIMARY KEY (orderId))";
		
				
		String sqlOrderLine = "CREATE TABLE " + ORDER_LINES +
		"( itemId INTEGER not NULL, " +
		" orderId INTEGER not NULL, " +
		" orderQty INTEGER, " +
		" CONSTRAINT FK_LineItem FOREIGN KEY (itemId) REFERENCES Items(itemId), " +
		" CONSTRAINT FK_LineOrder FOREIGN KEY (orderId) REFERENCES Orders(orderId))";

		String sqlCustomer = "CREATE TABLE " + CUSTOMERS + 
		"(customerId INTEGER not NULL, " + 
		" fName VARCHAR(20), " +
		" lName VARCHAR(20), " + 
		" address VARCHAR(50), " + 
		" postalCode VARCHAR(7), " +
		" phoneNumber VARCHAR(12), " + 
		" customerType VARCHAR(1), " +
		" PRIMARY KEY (customerId))";

		String sqlPurchase = "CREATE TABLE " + PURCHASES + "(customerId INTEGER not NULL, "
		+ " itemId INTEGER not NULL, " + 
		" CONSTRAINT FK_PurchaseCust FOREIGN KEY (customerId) REFERENCES Customers(customerId), " +
		" CONSTRAINT FK_PurchaseItem FOREIGN KEY (itemId) REFERENCES Items(itemId))";
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlSupp);
			stmt.executeUpdate(sqlItems);
			stmt.executeUpdate(sqlCustomer);
			stmt.executeUpdate(sqlPurchase);
			stmt.executeUpdate(sqlOrder);
			stmt.executeUpdate(sqlOrderLine);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created table in given database...");
	}
	
	public void initializeItemTable() {
		ArrayList<Item> newItems = helper.getItemsFromTxt();
		for (Item item: newItems) {
			this.insert(ITEMS, item.sqlInsert());
		}
	}
	
	public void initializeSupplierTable() {
		ArrayList<Supplier> newSuppliers = helper.getSuppliersFromTxt();
		for (Supplier supplier: newSuppliers) {
			this.insert(ITEMS, supplier.sqlInsert());
		}
	}
	
	public void initializeCustomerTable() {
		ArrayList<Item> newItems = helper.getItemsFromTxt();
		for (Item item: newItems) {
			this.insert(ITEMS, item.sqlInsert());
		}
	}
	
	public void insert(String tableName, String sqlString) {
		try {
			String query = helper.insert();
			query = query.replace("$tablename", tableName);
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, sqlString);
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + tableName + " and " + sqlString);
		}
	}
	
	public void updateOrderLine(String sqlString) {
		String[] res = sqlString.split(",");
		try {
			String query = helper.updateOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, res[2]);
			pStat.setString(2, res[0]);
			pStat.setString(3, res[1]);
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("updateOrderLine failed with " + sqlString);
		}
	}
	
	public OrderLine queryOrderLine(int itemId, int orderId) {
		try {
			String query = helper.queryOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, itemId);
			pStat.setInt(2, orderId);
			ResultSet results = pStat.executeQuery();
			pStat.close();
			
			if(results.next()) {
				return new OrderLine(results.getInt("itemId"),
								results.getInt("orderId"));
			}
		} catch (SQLException e) {
			System.err.println("queryOrderLine failed with " + itemId + " and " + orderId);
		}
		return null;
	}
	
	public Order queryOrder(int orderId) {
		try {
			String query = helper.queryOrder();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, orderId);
			ResultSet results = pStat.executeQuery();
			pStat.close();
			
			if(results.next()) {
				return new Order(results.getInt("orderId"));
			}
		} catch (SQLException e) {
			System.err.println("queryOrder failed with " + orderId);
		}
		return null;
	}

	public static void main(String[] args0) {
		DbController myApp = new DbController();
		myApp.initializeConnection();
		// myApp.createTable();
		// myApp.insertUser();
		// myApp.insertUserPreparedStatment(1, "sam", "Smith");
		// myApp.close();
	}

}
