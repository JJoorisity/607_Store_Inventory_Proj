package server.serverControllers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import sharedModel.*;

import java.io.IOException;
import java.sql.*;

/**
 * Controller used to interface between the shop model controller and shop application
 * to the mySQL database. Contains the following mySQL commands:
 * - initialize connection,
 * - create tables,
 * - drop tables,
 * - query, update, and delete among tables.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class DbController implements DatabaseConstants, DatabaseTables {

	
	private Connection conn; 	// Object of type connection from the JDBC class that deals with connecting to
									// the database
	private Statement stmt; 	// object of type statement from JDBC class that enables the creation "Query
									// statements"
	private ResultSet rs; 		// object of type ResultSet from the JDBC class that stores the result of the
									// query
	private DbControllerHelper helper;

	/**
	 * Default constructor to initialize connection with DbControllerHelper class.
	 */
	public DbController() {
		helper = new DbControllerHelper();
	}

	/**
	 * Initialize the connection to the shop database.
	 */
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

	/**
	 * Close all connections to the database.
	 */
	public void close() {
		try {
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drops all tables in the shop database, for hard reset.
	 */
	public void resetDatabase() {
		String sqlDropTables = "DROP TABLES PURCHASES, ITEMS, SUPPLIERS, ORDER_LINES, ORDERS, CUSTOMERS";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlDropTables);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("All tables deleted...");
	}

	/**
	 * Creates all necessary table schemas in the database for the shop application.
	 */
	public void createTable() {
		String sqlSupp = "CREATE TABLE " + SUPPLIERS + "(supplierId INTEGER not NULL, " + " supplierType VARCHAR(1), "
				+ " supplierName VARCHAR(255), " + " salesContact VARCHAR(255), " + " address VARCHAR(255), "
				+ " importTax DECIMAL(5,2), " + " PRIMARY KEY (supplierId))";

		String sqlItems = "CREATE TABLE " + ITEMS + "(itemId INTEGER not NULL, " + " supplierId INTEGER not NULL, "
				+ " itemType VARCHAR(1), " + " itemDesc VARCHAR(255), " + " itemPrice DECIMAL(5,2), "
				+ " itemQty INTEGER, " + " powerType VARCHAR(10), " + " V INTEGER, " + " Ph INTEGER, "
				+ " PRIMARY KEY (itemId), "
				+ "  CONSTRAINT FK_SuppItem FOREIGN KEY (supplierId) REFERENCES Suppliers(supplierId) ON UPDATE CASCADE ON DELETE CASCADE)";

		String sqlOrder = "CREATE TABLE " + ORDERS + "(orderId INTEGER not NULL, " + " orderDate DATE, "
				+ " PRIMARY KEY (orderId))";

		String sqlOrderLine = "CREATE TABLE " + ORDER_LINES + "( itemId INTEGER not NULL, "
				+ " orderId INTEGER not NULL, " + " orderQty INTEGER, "
				+ " CONSTRAINT FK_LineItem FOREIGN KEY (itemId) REFERENCES Items(itemId) ON UPDATE CASCADE ON DELETE CASCADE, "
				+ " CONSTRAINT FK_LineOrder FOREIGN KEY (orderId) REFERENCES Orders(orderId) ON UPDATE CASCADE ON DELETE CASCADE)";

		String sqlCustomer = "CREATE TABLE " + CUSTOMERS + "(customerId INTEGER not NULL, " + " fName VARCHAR(20), "
				+ " lName VARCHAR(20), " + " address VARCHAR(50), " + " postalCode VARCHAR(7), "
				+ " phoneNumber VARCHAR(12), " + " customerType VARCHAR(1), " + " PRIMARY KEY (customerId))";

		String sqlPurchase = "CREATE TABLE " + PURCHASES + "(customerId INTEGER not NULL, "
				+ " itemId INTEGER not NULL, "
				+ " CONSTRAINT FK_PurchaseCust FOREIGN KEY (customerId) REFERENCES Customers(customerId) ON UPDATE CASCADE ON DELETE CASCADE, "
				+ " CONSTRAINT FK_PurchaseItem FOREIGN KEY (itemId) REFERENCES Items(itemId) ON UPDATE CASCADE ON DELETE CASCADE)";

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlSupp);
			stmt.executeUpdate(sqlItems);
			stmt.executeUpdate(sqlCustomer);
			stmt.executeUpdate(sqlPurchase);
			stmt.executeUpdate(sqlOrder);
			stmt.executeUpdate(sqlOrderLine);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Created table in given database...");
	}

	/**
	 * Initialize the item data by reading in from a text file.
	 */
	public void initializeItemTable() {
		ArrayList<Object> newItems = helper.importFromTxt(ITEMFILE);
		for (Object item : newItems) {
			this.insertItem((Item_Elec) item);
		}
	}

	/**
	 * Initialize the supplier data by reading in from a text file.
	 */
	public void initializeSupplierTable() {
		ArrayList<Object> newSuppliers = helper.importFromTxt(SUPPLIERFILE);
		for (Object s : newSuppliers) {
			this.insertSupplier((Int_Supplier) s);
		}
	}

	/**
	 * Initialize the customer data by reading in from a text file.
	 */
	public void initializeCustomerTable() {
		ArrayList<Object> newCustomers = helper.importFromTxt(CUSTOMERFILE);
		for (Object c : newCustomers) {
			this.insertCustomer((Customer) c);
		}
	}

	/**
	 * Inserts a new item into the database.
	 * Uses the lowest class of item; a non-electric item will have null values
	 * for the electric specific attributes.
	 * @param item (Item_Elec) electric item used to update the database.
	 */
	public void insertItem(Item_Elec item) {
		try {
			String query = helper.insertItem();
			PreparedStatement pStat = conn.prepareStatement(query);
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
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + ITEMS + " and " + item.getItemID());
			e.printStackTrace();
		}
	}

	/**
	 * Query an item by its item ID.
	 * @param itemID (int) the ID being searched in mySQL.
	 * @return (Item_Elec) the returned searched item attributes are stored in Item_Elec object.
	 */
	public Item_Elec queryItem(int itemID) {
		Item_Elec queryRes = null;
		try {
			String query = helper.queryItemId();
			PreparedStatement pStat = conn.prepareStatement(query);
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

	/**
	 * Query by item name/description to find all results that contain the search parameter.
	 * @param itemDesc (String) name or description of item being searched.
	 * @return (LinkedHashSet<Item_Elec>) the returned searched items are stored in Item_Elec objects.
	 */
	public LinkedHashSet<Item_Elec> queryItem(String itemDesc) {
		itemDesc = "%" + itemDesc + "%";
		LinkedHashSet<Item_Elec> queryRes = new LinkedHashSet<Item_Elec>();
		try {
			String query = helper.queryItemDesc();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, itemDesc);
			ResultSet results = pStat.executeQuery();
			while (results.next()) {
				queryRes.add(new Item_Elec(results.getInt("itemId"), results.getString("itemType").charAt(0),
						results.getString("itemDesc"), results.getInt("itemQty"), results.getDouble("itemPrice"),
						results.getInt("supplierId"), results.getString("powerType"), results.getInt("V"),
						results.getInt("Ph")));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("queryItem by Desc failed with " + itemDesc);
			e.printStackTrace();
		}
		return queryRes;
	}

	/**
	 * Query all items stored in the database.
	 * @return (LinkedHashSet<Item_Elec>) the returned searched items are stored in Item_Elec objects.
	 */
	public LinkedHashSet<Item_Elec> queryItem() {
		LinkedHashSet<Item_Elec> queryRes = new LinkedHashSet<Item_Elec>();
		try {
			String query = helper.queryItemAll();
			PreparedStatement pStat = conn.prepareStatement(query);
			ResultSet results = pStat.executeQuery();
			while (results.next()) {
				queryRes.add(new Item_Elec(results.getInt("itemId"), results.getString("itemType").charAt(0),
						results.getString("itemDesc"), results.getInt("itemQty"), results.getDouble("itemPrice"),
						results.getInt("supplierId"), results.getString("powerType"), results.getInt("V"),
						results.getInt("Ph")));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("queryItem all failed.");
			e.printStackTrace();
		}
		return queryRes;
	}

	/**
	 * Update the item quantity after a purchase.
	 * @param itemID (int) the ID of the item purchased.
	 * @param qty (int) the quantity of the item to be reduced.
	 */
	public void updateItem(int itemID, int qty) {
		try {
			String query = helper.updateItem();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, qty);
			pStat.setInt(2, itemID);

			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("updateItem failed with " + itemID + " and " + qty);
		}

	}

//	public void removeItem(Item item) {
//	}

	/**
	 * Insert a new supplier into the database.
	 * Uses the lowest class of supplier; a local supplier will have null values
	 * for the international attributes.
	 * @param supplier (Int_Supplier) international supplier used to update the database.
	 */
	public void insertSupplier(Int_Supplier supplier) {
		try {
			String query = helper.insertSupplier();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, supplier.getSupplierID());
			pStat.setString(2, String.valueOf(supplier.getSupplierType()));
			pStat.setString(3, supplier.getCompanyName());
			pStat.setString(4, supplier.getSalesContact());
			pStat.setString(5, supplier.getAddress());
			pStat.setDouble(6, supplier.getImportTax());
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + SUPPLIERS + " and " + supplier.getSupplierID());
			e.printStackTrace();
		}
	}
	
	/**
	 * Query supplier by the supplier ID.
	 * Local suppliers are stored in an international supplier with null information for the
	 * international attributes.
	 * @param supplierID (int) the ID being searched.
	 * @return (Int_supplier) returns an international supplier matching the ID.
	 */
	public Int_Supplier querySupplier(int supplierID) {
		Int_Supplier queryRes = null;
		try {
			String query = helper.querySupplier();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, supplierID);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new Int_Supplier(results.getInt("supplierID"), results.getString("supplierType").charAt(0),
						results.getString("supplierName"), results.getString("salesContact"),
						results.getString("address"), results.getDouble("importTax"));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("querySupplier failed with " + supplierID);
		}
		return queryRes;
	}

	/**
	 * Insert a new customer into the database.
	 * @param customer (Customer) used to update the database.
	 */
	public void insertCustomer(Customer customer) {
		try {
			String query = helper.insertCustomer();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, customer.getCustomerId());
			pStat.setString(2, customer.getFirstName());
			pStat.setString(3, customer.getLastName());
			pStat.setString(4, customer.getAddress());
			pStat.setString(5, customer.getPostalCode());
			pStat.setString(6, customer.getPhoneNum());
			pStat.setString(7, String.valueOf(customer.getCustomerType()));
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + CUSTOMERS + " and " + customer.getCustomerId());
			e.printStackTrace();
		}
	}

	/**
	 * Query a customer by the customer's ID.
	 * @param customerID (int) the ID to query.
	 * @return (Customer) the query results stored as a Customer object.
	 */
	public Customer queryCustomer(int customerID) {
		Customer queryRes = null;
		try {
			String query = helper.queryCustomer();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, customerID);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new Customer(results.getInt("customerId"), results.getString("fName"),
						results.getString("lName"), results.getString("address"), results.getString("postalCode"),
						results.getString("phoneNumber"), results.getString("customerType").charAt(0));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryCustomer failed with " + customerID);
		}
		return queryRes;
	}

	/**
	 * Query customers by the customer type: residential (R) or commercial (C).
	 * @param type (char) the type being queried.
	 * @return (LinkedHashSet<Customer>) the results are stored as a list of Customer objects.
	 */
	public LinkedHashSet<Customer> queryCustomer(char type) {
		LinkedHashSet<Customer> queryRes = new LinkedHashSet<Customer>();
		try {
			String query = helper.queryCustomerType();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, String.valueOf(type));
			ResultSet results = pStat.executeQuery();

			while (results.next()) {
				queryRes.add(new Customer(results.getInt("customerId"), results.getString("fName"),
						results.getString("lName"), results.getString("address"), results.getString("postalCode"),
						results.getString("phoneNumber"), results.getString("customerType").charAt(0)));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("queryCustomer failed with " + type);
		}
		return queryRes;
	}

	/**
	 * Query customers by the customer last name.
	 * @param name (String) the last name or text being searched in the database's customer last name.
	 * @return (LinkedHashSet<Customer>) the results are stored as a list of Customer objects.
	 */
	public LinkedHashSet<Customer> queryCustomer(String name) {
		LinkedHashSet<Customer> queryRes = new LinkedHashSet<Customer>();
		try {
			String query = helper.queryCustomerName();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, name);
			ResultSet results = pStat.executeQuery();

			while (results.next()) {
				queryRes.add(new Customer(results.getInt("customerId"), results.getString("fName"),
						results.getString("lName"), results.getString("address"), results.getString("postalCode"),
						results.getString("phoneNumber"), results.getString("customerType").charAt(0)));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("queryCustomer failed with " + name);
		}
		return queryRes;
	}

	/**
	 * Update a customer's attributes in the database.
	 * @param customer (Customer) the new customer information to update.
	 */
	public void updateCustomer(Customer customer) {
		try {
			String query = helper.updateCustomer();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, customer.getFirstName());
			pStat.setString(2, customer.getLastName());
			pStat.setString(3, customer.getAddress());
			pStat.setString(4, customer.getPostalCode());
			pStat.setString(5, customer.getPhoneNum());
			pStat.setString(6, String.valueOf(customer.getCustomerType()));
			pStat.setInt(7, customer.getCustomerId());
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("updateCustomer failed with " + customer + e);
			
		}
	}

	/**
	 * Delete a customer from the database.
	 * @param customer (Customer) the customer to be removed.
	 */
	public void removeCustomer(Customer customer) {
		try {
			String query = helper.removeCustomer();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, customer.getCustomerId());
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("removeCustomer failed with " + customer);
		}
	}

	/**
	 * Query an order by the order ID.
	 * @param orderId (int) the order ID of the order being searched.
	 * @param mc (ModelController) access to the model to store items from order lines.
	 * @return (Order) the order searched.
	 */
	public Order queryOrder(int orderId, ModelController mc) {
		Order queryRes = null;
		try {
			String query = helper.queryOrder();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, orderId);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new Order(results.getInt("orderId"));
				pStat.close();
				queryRes.setOrderLines(this.queryAllOrderLines(queryRes.getOrderID(), mc));
			}
			
		} catch (SQLException e) {
			System.err.println("queryOrder failed with " + orderId);
		}
		return queryRes;
	}
	
	/**
	 * Insert a new order into the database.
	 * @param order (Order) the new order being added.
	 */
	public void insertOrder(Order order) {
		try {
			String query = helper.insertOrder();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, order.getOrderID());
			pStat.setDate(2, Date.valueOf(order.getDate()));
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + CUSTOMERS + " and " + order.getOrderID());
			e.printStackTrace();
		}
	}

	/**
	 * Insert a new OrderLine into the database
	 * @param ol (OrderLine) the order line being added.
	 * @param orderID (int) the order ID linked to this order line.
	 */
	public void insertOrderLine(OrderLine ol, int orderID) {
		try {
			String query = helper.insertOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, ol.getItemId());
			pStat.setInt(2, orderID);
			pStat.setInt(3, ol.getOrderQty());
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insert failed with " + ORDER_LINES + " and " + orderID + " and " + ol.getItemId());
			e.printStackTrace();
		}

	}

	/**
	 * Adjust the quantity of an order line if additional items are purchased in the same day.
	 * @param ol (OrderLine) the order line being updated.
	 * @param qty (int) the quantity of items to add to the order line.
	 * @param orderID (int) the order ID of the order line being updated.
	 */
	public void updateOrderLine(OrderLine ol, int qty, int orderID) {
		try {
			String query = helper.updateOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(2, ol.getItemId());
			pStat.setInt(3, orderID);
			pStat.setInt(1, ol.getOrderQty() + qty);
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("updateOrderLine failed with " + ol.getItemId() + " and " + orderID);
		}
	}

	/**
	 * Query a specific order line.
	 * @param itemId (int) the item ID of the specified order line.
	 * @param orderId (int) the order ID of the specified order line.
	 * @return (OrderLine) returns the searched order line.
	 */
	public OrderLine queryOrderLine(int itemId, int orderId) {
		OrderLine queryRes = null;
		try {
			String query = helper.queryOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, itemId);
			pStat.setInt(2, orderId);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				Item_Elec tempItem = this.queryItem(results.getInt("itemId"));
				Int_Supplier tempSupp = this.querySupplier(tempItem.getSupplierID());
				queryRes = new OrderLine(results.getInt("itemId"), results.getInt("orderQty"),
						tempSupp.getCompanyName());
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryOrderLine failed with " + itemId + " and " + orderId);
		}
		return queryRes;
	}
	
	/**
	 * Query all order lines for a specific order.
	 * @param orderId (int) the order ID being searched.
	 * @param mc (ModelController) access to the current model controller to update the local
	 * inventory with the items from each order line.
	 * @return (LinkedHashSet<OrderLine>) a list of all order lines matching the order ID.
	 */
	private LinkedHashSet<OrderLine> queryAllOrderLines(int orderId, ModelController mc) {
		LinkedHashSet<OrderLine> queryRes = new LinkedHashSet<OrderLine>();
		try {
			String query = helper.queryAllOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, orderId);
			ResultSet results = pStat.executeQuery();

			mc.getShop().getInventory().clearItems();
			while (results.next()) {
				Item_Elec tempItem = this.queryItem(results.getInt("itemId"));
				mc.getShop().getInventory().addItems(tempItem);;
				Int_Supplier tempSupp = this.querySupplier(tempItem.getSupplierID());
				queryRes.add(
						new OrderLine(results.getInt("itemId"), results.getInt("orderQty"), tempSupp.getCompanyName()));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("queryAllOrderLine failed with " + orderId);
		}
		return queryRes;
	}

	/**
	 * Insert a new purchase line into database.
	 * @param itemID (int) item ID assigned to the purchase.
	 * @param customerID (int) the customer ID assigned to the purchase.
	 */
	public void insertPurchases(int itemID, int customerID) {
		try {
			String query = helper.insertPurchases();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, customerID);
			pStat.setInt(2, itemID);
			int rowCount = pStat.executeUpdate();
			System.out.println("row Count = " + rowCount);
			pStat.close();
		} catch (SQLException e) {
			System.err.println("insertPurchases failed with " + customerID + " and " + itemID);
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws IOException {
		DbController myDB = new DbController();
		myDB.initializeConnection();
		myDB.resetDatabase();
		myDB.createTable();
		myDB.initializeCustomerTable();
		myDB.initializeSupplierTable();
		myDB.initializeItemTable();
	}
}
