package server.serverControllers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class DbController implements IDBCredentials {

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

	private void insertUserPreparedStatment(int i, String fName, String lName) {
		// TODO Auto-generated method stub
		try {
			String query = "INSERT INTO STUDENT (ID, first, last) values (?,?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, i);
			pStat.setString(2, fName);
			pStat.setString(3, lName);
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
