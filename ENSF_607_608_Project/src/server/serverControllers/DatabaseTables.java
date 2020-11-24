package server.serverControllers;

/**
 * Hold the local mySQL 607toolshop database table information and import file locations.
 * @author NJack & JJoorisity
 * @version	1.0
 * @since 2020-11-26
 */
public interface DatabaseTables {
	// Database Tables
	static final String CUSTOMERS = "Customers";
	static final String ITEMS = "Items";
	static final String SUPPLIERS = "Suppliers";
	static final String PURCHASES = "Purchases";
	static final String ORDERS = "Orders";
	static final String ORDER_LINES = "Order_Lines";
	
	// Text files for Mac:
//	static final String ITEMFILE = "./items.txt";
//	static final String SUPPLIERFILE = "./suppliers.txt";
//	static final String CUSTOMERFILE = "./customers.txt";
	// Text files for Windows:
	static final String ITEMFILE = "ENSF_607_608_Project/items.txt";
	static final String SUPPLIERFILE = "ENSF_607_608_Project/suppliers.txt";
	static final String CUSTOMERFILE = "ENSF_607_608_Project/customers.txt";
}
