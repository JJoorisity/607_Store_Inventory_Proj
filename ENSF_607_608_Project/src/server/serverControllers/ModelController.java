package server.serverControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashSet;

import server.serverModel.*;
import sharedModel.*;

public class ModelController implements Runnable, DatabaseTables {

	private DbController dbController;
	private ShopApp shop;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;

	// TODO
	// add/update/remove suppliers from suppliers list
	// Query order by ID. print to reasonable format
	// Query order by most recent. print to reasonable format
	// Print all Items/Suppliers in list
	// print Item
	// serialize everything

	public ModelController(ObjectInputStream clientIn, ObjectOutputStream clientOut, DbController dbController,
			ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
		this.clientIn = clientIn;
		this.clientOut = clientOut;
	}
	
	public ModelController(DbController dbController,
			ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
	}
	
	/**
	 * Save new or modify existing customer.
	 * @param customer
	 * @return
	 */
	public void saveCustomer(Customer customer) {
		// check if customer exists already.
		if (dbController.queryCustomer(customer.getCustomerId()) == null) {
			// insert
			dbController.insertCustomer(customer);
		} else {
			// modify
			dbController.updateCustomer(customer);
		}
	}
	
	public boolean removeCustomer(Customer customer) {
		if (dbController.queryCustomer(customer.getCustomerId()) != null) {
			dbController.removeCustomer(customer);
			return true;
		}
		return false;
	}
	
	public LinkedHashSet<Customer> queryCustomer(char type) {
		return dbController.queryCustomer(type);
	}
	
	public LinkedHashSet<Customer> queryCustomer(String name) {
		return dbController.queryCustomer(name);
	}
	
	public Customer queryCustomer(int id) {
		return dbController.queryCustomer(id);
	}

	public boolean executePurchase(int itemID, int qty, int customerID) {
		// TODO update purchase Table
		Item_Elec item = dbController.queryItem(itemID);
		if (dbController.queryCustomer(customerID) == null) {
			return false;
		}
		if (shop.getInventory().decrement(item, qty)) {
			dbController.updateItem(itemID, item.getQty()); // updates Item in db with new qty
			this.updateOrders(item, qty);
			dbController.insertPurchases(itemID, customerID);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Item item) {
		if (dbController.queryItem(item.getItemID()) != null) {
			dbController.removeItem(item);
			return true;
		}
		return false;
	}
	
	public LinkedHashSet<Item_Elec> queryItem(String itemDesc) {
		return dbController.queryItem(itemDesc);
	}

	private void updateOrders(Item_Elec item, int qty) {
		if (item.getQty() < item.ORDERQTYLIMIT) {
			int i = shop.getInventory().generateOrderID();
			Order temp = this.dbController.queryOrder(i);
			if (temp == null) {
				temp = new Order(i);
				dbController.insertOrder(temp);
			}
			OrderLine templine = this.dbController.queryOrderLine(item.getItemID(), temp.getOrderID());

			if (templine == null) {
				dbController.insertOrderLine(new OrderLine(item.getItemID(), item.ORDERQTYLIMIT - item.getQty()),
						temp.getOrderID());
				return;
			} else {
				dbController.updateOrderLine(templine, qty, temp.getOrderID());
				return;
			}
		}
		return; // output to gui that order succeeded or failed
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws IOException {
		DbController testDB = new DbController();
		testDB.initializeConnection();
		ModelController test = new ModelController(testDB, new ShopApp());
		test.executePurchase(1002, 1, 15);
	}
}
