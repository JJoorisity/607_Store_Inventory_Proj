package server.serverControllers;

public interface DatabaseConstants {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/607toolshop"; // change to our DB Name

	// Database credentials
	static final String USERNAME = "root";
	static final String PASSWORD = "craven-stomp-husband-learn";

	// Database Tables
	static final String CUSTOMERS = "Customers";
	static final String ITEMS = "Items";
	static final String SUPPLIERS = "Suppliers";
	static final String PURCHASES = "Purchases";
	static final String ORDERS = "Orders";
	static final String ORDER_LINES = "Order_Lines";
	
	// Text files
	static final String ITEMFILE = "items.txt";
	static final String SUPPLIERFILE = "suppliers.txt";
	static final String CUSTOMERFILE = "customers.txt";
}
