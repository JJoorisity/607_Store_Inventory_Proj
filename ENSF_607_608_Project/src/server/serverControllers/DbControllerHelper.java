package server.serverControllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import sharedModel.*;

/**
 * Model style class to provide direct mySQL commands to the controller.
 * Controls the file read for the data initialization.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class DbControllerHelper implements DatabaseTables {

	public DbControllerHelper() {
	}


	/**
	 * Fills the data tables with all the information from the text file.
	 * @param filename (String) the text file import.
	 * @return (ArrayList<Object>) list of objects found in the text file.
	 */
	public ArrayList<Object> importFromTxt(String filename) {
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			Scanner sc = new Scanner(new FileReader(filename));
			while (sc.hasNext()) {
				String fileInfo[] = sc.nextLine().split(";");
				if (filename.equals(ITEMFILE))
					list.add(getItemFromTxt(fileInfo));
				else if (filename.equals(SUPPLIERFILE))
					list.add(getSupplierFromTxt(fileInfo));
				else if (filename.equals(CUSTOMERFILE))
					list.add(getCustomerFromTxt(fileInfo));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + filename + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Used to create Item objects from a text file.
	 * @param fileInfo (String) the information read in from the file.
	 * @return (Item) the item created from the string.
	 */
	public Item getItemFromTxt(String fileInfo[]) {
		return new Item_Elec(Integer.parseInt(fileInfo[0]), fileInfo[1].charAt(0), fileInfo[2],
				Integer.parseInt(fileInfo[3]), Double.parseDouble(fileInfo[4]), Integer.parseInt(fileInfo[5]),
				fileInfo[6], Integer.parseInt(fileInfo[7]), Integer.parseInt(fileInfo[8]));
	}

	/**
	 * Used to create Supplier objects from a text file.
	 * @param fileInfo (String) the information read in from the file.
	 * @return (Supplier) the supplier created from the string.
	 */
	public Supplier getSupplierFromTxt(String fileInfo[]) {
		return new Int_Supplier(Integer.parseInt(fileInfo[0]), fileInfo[1].charAt(0), fileInfo[2], fileInfo[3],
				fileInfo[4], Double.parseDouble(fileInfo[5]));
	}

	/**
	 * Used to create Customer objects from a text file.
	 * @param fileInfo (String) the information read in from the file.
	 * @return (Customer) the customer created from the string.
	 */
	public Customer getCustomerFromTxt(String fileInfo[]) {
		return new Customer(Integer.parseInt(fileInfo[0]), fileInfo[1], fileInfo[2], fileInfo[3], fileInfo[4],
				fileInfo[5], fileInfo[6].charAt(0));
	}

	/**
	 * Update an entry in the Customers table.
	 * @return (String) prepared statement.
	 */
	public String updateCustomer() {
		return ("UPDATE " + CUSTOMERS + " SET fName = ?, lName = ?, address = ?, postalCode = ?, phoneNumber = ?, "
				+ "customerType = ? WHERE customerId= ?");
	}

	/**
	 * Query Customers table by customer ID.
	 * @return (String) prepared statement.
	 */
	public String queryCustomer() {
		return ("SELECT * FROM " + CUSTOMERS + " WHERE customerId = ?");
	}

	/**
	 * Query Customers table by customer type.
	 * @return (String) prepared statement.
	 */
	public String queryCustomerType() {
		return ("SELECT * FROM " + CUSTOMERS + " WHERE customerType = ? ORDER BY lName, fName");
	}

	/**
	 * Query Customers table by customer last name.
	 * @return (String) prepared statement.
	 */
	public String queryCustomerName() {
		return ("SELECT * FROM " + CUSTOMERS + " WHERE lName = ? ORDER BY lName, fName");
	}

	/**
	 * Insert an entry in the Customers table.
	 * @return (String) prepared statement.
	 */
	public String insertCustomer() {
		return ("INSERT INTO " + CUSTOMERS + " VALUES (?,?,?,?,?,?,?)");
	}

	/**
	 * Delete an entry from the Customers table.
	 * @return (String) prepared statement.
	 */
	public String removeCustomer() {
		return ("DELETE FROM " + CUSTOMERS + " WHERE customerId = ?");
	}

	/**
	 * Insert an entry in the Items table.
	 * @return (String) prepared statement.
	 */
	public String insertItem() {
		return ("INSERT INTO " + ITEMS + " VALUES (?,?,?,?,?,?,?,?,?)");
	}

	/**
	 * Query Items table by item ID.
	 * @return (String) prepared statement.
	 */
	public String queryItemId() {
		return ("SELECT * FROM " + ITEMS + " WHERE itemId = ?");
	}

	/**
	 * Query Items table by item description.
	 * @return (String) prepared statement.
	 */
	public String queryItemDesc() {
		return ("SELECT * FROM " + ITEMS + " WHERE itemDesc LIKE ?");
	}

	/**
	 * Query all items in Items table.
	 * @return (String) prepared statement.
	 */
	public String queryItemAll() {
		return ("SELECT * FROM " + ITEMS);
	}

	/**
	 * Update an entry in the Items table.
	 * @return (String) prepared statement.
	 */
	public String updateItem() {
		return ("UPDATE " + ITEMS + " SET itemQty = ? WHERE itemId = ?");
	}

//	public String removeItem() {
//		return ("DELETE FROM " + ITEMS + " WHERE itemId = ?");
//	}

	/**
	 * Insert an entry in the Suppliers table.
	 * @return (String) prepared statement.
	 */
	public String insertSupplier() {
		return ("INSERT INTO " + SUPPLIERS + " VALUES (?,?,?,?,?,?)");
	}

	/**
	 * Query Suppliers table by supplier ID.
	 * @return (String) prepared statement.
	 */
	public String querySupplier() {
		return ("SELECT * FROM " + SUPPLIERS + " WHERE supplierId = ?");
	}
	
	/**
	 * Query Orders table by order ID.
	 * @return (String) prepared statement.
	 */
	public String queryOrder() {
		return ("SELECT * FROM " + ORDERS + " WHERE orderId = ?");
	}

	/**
	 * Insert an entry in the Orders table.
	 * @return (String) prepared statement.
	 */
	public String insertOrder() {
		return ("INSERT INTO " + ORDERS + " VALUES (?,?)");
	}
	
	/**
	 * Update an entry in the Order_Lines table.
	 * @return (String) prepared statement.
	 */
	public String updateOrderLine() {
		return ("UPDATE " + ORDER_LINES + " SET orderQty = ? WHERE itemId = ? AND orderId= ?");
	}

	/**
	 * Query Order_Lines table by item ID and order ID.
	 * @return (String) prepared statement.
	 */
	public String queryOrderLine() {
		return ("SELECT * FROM " + ORDER_LINES + " WHERE itemId = ? AND orderId = ?");
	}

	/**
	 * Query all order lines from Order_Lines table by order ID.
	 * @return (String) prepared statement.
	 */
	public String queryAllOrderLine() {
		return ("SELECT * FROM " + ORDER_LINES + " WHERE orderId = ?");
	}

	/**
	 * Insert an entry in the Order_Lines table.
	 * @return (String) prepared statement.
	 */
	public String insertOrderLine() {
		return ("INSERT INTO " + ORDER_LINES + " VALUES (?,?,?)");
	}

	/**
	 * Insert an entry in the Purchases table.
	 * @return (String) prepared statement.
	 */
	public String insertPurchases() {
		return ("INSERT INTO " + PURCHASES + " VALUES (?,?)");

	}

}