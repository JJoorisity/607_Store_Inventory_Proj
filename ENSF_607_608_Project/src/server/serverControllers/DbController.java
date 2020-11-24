package server.serverControllers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import sharedModel.*;
import java.sql.*;

public class DbController implements DatabaseConstants, DatabaseTables {

	// Attributes
	private Connection conn; // Object of type connection from the JDBC class that deals with connecting to
								// the database
	private Statement stmt; // object of type statement from JDBC class that enables the creation "Query
							// statements"
	private ResultSet rs; // object of type ResultSet from the JDBC class that stores the result of the
							// query
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

	public void resetDatabase() {
		String sqlDropTables = "DROP TABLES PURCHASES, ITEMS, SUPPLIERS, ORDER_LINES, ORDERS, CUSTOMERS";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlDropTables);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All tables deleted...");
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created table in given database...");
	}

	public void initializeItemTable() {
		ArrayList<Object> newItems = helper.importFromTxt(ITEMFILE);
		for (Object item : newItems) {
			this.insertItem((Item_Elec) item);
		}
	}

	public void initializeSupplierTable() {
		ArrayList<Object> newSuppliers = helper.importFromTxt(SUPPLIERFILE);
		for (Object s : newSuppliers) {
			this.insertSupplier((Int_Supplier) s);
		}
	}

	public void initializeCustomerTable() {
		ArrayList<Object> newCustomers = helper.importFromTxt(CUSTOMERFILE);
		for (Object c : newCustomers) {
			this.insertCustomer((Customer) c);
		}
	}

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

	public LinkedHashSet<Item_Elec> queryItem(String itemDesc) {
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
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryItem by Desc failed with " + itemDesc);
			e.printStackTrace();
		}
		return queryRes;
	}

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
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryItem all failed.");
			e.printStackTrace();
		}
		return queryRes;
	}

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
//		try {
//			String query = helper.removeItem();
//			PreparedStatement pStat = conn.prepareStatement(query);
//			pStat.setInt(1, item.getItemID());
//			int rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
//			pStat.close();
//		} catch (SQLException e) {
//			System.err.println("removeItem failed with " + item);
//		}
//	}

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

	public LinkedHashSet<Customer> queryCustomer(char type) {
		LinkedHashSet<Customer> queryRes = new LinkedHashSet<Customer>();
		try {
			String query = helper.queryCustomerType();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, type);
			ResultSet results = pStat.executeQuery();

			while (results.next()) {
				queryRes.add(new Customer(results.getInt("customerId"), results.getString("fName"),
						results.getString("lName"), results.getString("address"), results.getString("postalCode"),
						results.getString("phoneNumber"), results.getString("customerType").charAt(0)));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryCustomer failed with " + type);
		}
		return queryRes;
	}

	public LinkedHashSet<Customer> queryCustomer(String name) {
		LinkedHashSet<Customer> queryRes = new LinkedHashSet<Customer>();
		try {
			String query = helper.queryCustomerType();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setString(1, name);
			ResultSet results = pStat.executeQuery();

			while (results.next()) {
				queryRes.add(new Customer(results.getInt("customerId"), results.getString("fName"),
						results.getString("lName"), results.getString("address"), results.getString("postalCode"),
						results.getString("phoneNumber"), results.getString("customerType").charAt(0)));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryCustomer failed with " + name);
		}
		return queryRes;
	}

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
			System.err.println("updateCustomer failed with " + customer);
		}
	}

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

	public OrderLine queryOrderLine(int itemId, int orderId) {
		OrderLine queryRes = null;
		try {
			String query = helper.queryOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, itemId);
			pStat.setInt(2, orderId);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new OrderLine(results.getInt("itemId"), results.getInt("orderQty"));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryOrderLine failed with " + itemId + " and " + orderId);
		}
		return queryRes;
	}

	public Order queryOrder(int orderId) {
		Order queryRes = null;
		try {
			String query = helper.queryOrder();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, orderId);
			ResultSet results = pStat.executeQuery();

			if (results.next()) {
				queryRes = new Order(results.getInt("orderId"));
				pStat.close();
			}
			queryRes.setOrderLines(this.queryAllOrderLines(queryRes.getOrderID()));
		} catch (SQLException e) {
			System.err.println("queryOrder failed with " + orderId);
		}
		return queryRes;
	}

	private LinkedHashSet<OrderLine> queryAllOrderLines(int orderId) {
		LinkedHashSet<OrderLine> queryRes = new LinkedHashSet<OrderLine>();
		try {
			String query = helper.queryAllOrderLine();
			PreparedStatement pStat = conn.prepareStatement(query);
			pStat.setInt(1, orderId);
			ResultSet results = pStat.executeQuery();

			while (results.next()) {
				queryRes.add(new OrderLine(results.getInt("itemId"), results.getInt("orderQty")));
				pStat.close();
			}
		} catch (SQLException e) {
			System.err.println("queryAllOrderLine failed with " + orderId);
		}
		return queryRes;
	}

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

	public static void main(String[] args0) {
		DbController myApp = new DbController();
		myApp.initializeConnection();
		myApp.resetDatabase();
		myApp.createTable();
		myApp.initializeSupplierTable();
		myApp.initializeItemTable();
		myApp.initializeCustomerTable();
		myApp.close();
	}

}
