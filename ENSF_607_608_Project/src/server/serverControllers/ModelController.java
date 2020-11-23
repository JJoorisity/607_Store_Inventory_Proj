package server.serverControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import server.serverModel.*;
import sharedModel.*;

public class ModelController implements Runnable, DatabaseTables {

	private DbController dbController;
	private ShopApp shop;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;

	// TODO
	// Query order by ID. print to reasonable format
	// Print Order to reasonable format
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
	public boolean saveCustomer(Customer customer) {
		// check if customer exists already.
		if (dbController.queryCustomer(customer.getCustomerId()) == null) {
			// insert
			dbController.insertCustomer(customer);
		} else {
			// modify
			dbController.updateCustomer(customer);
		}
		return true;
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
	
//	public boolean removeItem(Item item) {
//		if (dbController.queryItem(item.getItemID()) != null) {
//			dbController.removeItem(item);
//			return true;
//		}
//		return false;
//	}
	
	public LinkedHashSet<Item_Elec> queryItem(String itemDesc) {
		return dbController.queryItem(itemDesc);
	}
	
	public LinkedHashSet<Item_Elec> queryItem() {
		return dbController.queryItem();
	}
	
	public Item_Elec queryItem(int itemId) {
		return dbController.queryItem(itemId);
	}

	private void updateOrders(Item_Elec item, int qty) {
		if (item.getQty() < item.ORDERQTYLIMIT) {
			int i = shop.getInventory().generateOrderID();
			Order temp = this.dbController.queryOrder(i);
			if (temp == null) {
				temp = new Order(i);
				dbController.insertOrder(temp);
			}
			updateOrderLines(item, temp, qty);
		}
		return; // output to gui that order succeeded or failed
	}
	
	public void updateOrderLines(Item_Elec item, Order temp, int qty) {
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
	
	// Included items as an option for extensibility but implementation is not required.
	private void deleteObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		ObjectWrapper ow = new ObjectWrapper();
		boolean success = false;
		switch (type) {
		case "CUSTOMER": success = this.removeCustomer((Customer)request.getPassedObj(0));
			
		//case "ITEM_ELEC": this.removeItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage("COMPLETE", null);
		else
			ow.setMessage("FAILED", null);
		try {
			clientOut.writeObject(ow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Included items as an option for extensibility but implementation is not required.
	private void saveObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		ObjectWrapper ow = new ObjectWrapper();
		boolean success = false;
		switch (type) {
		case "CUSTOMER": success = this.saveCustomer((Customer)request.getPassedObj(0));
			
		//case "ITEM_ELEC": this.saveItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage("COMPLETE", null);
		else
			ow.setMessage("FAILED", null);
		try {
			clientOut.writeObject(ow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		String command = request.getMessage()[0];
		ArrayList<Object> searchObject = new ArrayList<Object>();
		ObjectWrapper ow = new ObjectWrapper();
		switch (type) {
		case "CUSTOMER": {
			Customer c = (Customer)request.getPassedObj(0);
			if (command.equals("*ID"))
				searchObject.add(this.queryCustomer(c.getCustomerId()));
			else if (command.equals("*NAME"))
				searchObject.addAll(this.queryCustomer(c.getLastName()));
			else if (command.equals("*TYPE"))
				searchObject.addAll(this.queryCustomer(c.getCustomerType()));
			ow.setMessage("DISPLAY", "CUSTOMER");
		}
			
		case "ITEM_ELEC": {
			Item_Elec item = (Item_Elec)request.getPassedObj(0);
			if (command.equals("*ID"))
				searchObject.add(this.queryItem(item.getItemID()));
			else if (command.equals("*NAME"))
				searchObject.addAll(this.queryItem(item.getItemDesc()));
			else if (command.equals("*ALL"))
				searchObject.addAll(this.queryItem());
			ow.setMessage("DISPLAY", "ITEM_ELEC");
		}
		case "ORDER": {
			// will only generate today's order for printing
			int id = shop.getInventory().generateOrderID();
			searchObject.add(dbController.queryOrder(id));
			ow.setMessage("DISPLAY", "ORDER");
		}
		ow.addPassedObj(searchObject);
			try {
				clientOut.writeObject(ow);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		ObjectWrapper request;
		try {
			while (true) {
				request = (ObjectWrapper) clientIn.readObject();
				String command = request.getMessage()[0];
				if (request != null && !command.equals("")) {
					System.out.println("command : " + command);
					
					switch (command) {
					case "SAVE": this.saveObject(request);
						
					case "SEARCH*": this.searchObject(request);
						
					case "DELETE": this.deleteObject(request);
						
					case "PURCHASE": this.executePurchase((Integer)request.getPassedObj(0), 
							(Integer)request.getPassedObj(1), 
							(Integer)request.getPassedObj(2));
					}
				} else if (command.contentEquals("QUIT")) {
					break;
				}
				request.resetWrapper();
			}
		} catch (ClassNotFoundException | IOException ex) {
			System.err.println(ex.getStackTrace());
		}

	}

	public static void main(String[] args) throws IOException {
		DbController testDB = new DbController();
		testDB.initializeConnection();
		ModelController test = new ModelController(testDB, new ShopApp());
		test.executePurchase(1002, 1, 15);
	}
}
