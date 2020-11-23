package server.serverModel;

import sharedModel.*;

import java.io.IOException;
import java.util.*;
import server.serverControllers.ModelController;

/**
 * Exercise 1 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 9th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: FrontEnd user interface. Handles all interactions with
 *        uses. Auto Generates an order if check quantity or decrease item reads
 *        a qty less than 40 as required by assignment details
 */
public class ShopApp {

	private ModelController modelController;
	private Inventory inventory;

	public ShopApp() throws IOException {
		this.inventory = new Inventory();
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Save new or modify existing customer.
	 * 
	 * @param customer
	 * @return
	 */
	public boolean saveCustomer(Customer customer) {
		// check if customer exists already.
		if (modelController.getDbController().queryCustomer(customer.getCustomerId()) == null) {
			// insert
			modelController.getDbController().insertCustomer(customer);
		} else {
			// modify
			modelController.getDbController().updateCustomer(customer);
		}
		return true;
	}

	public boolean removeCustomer(Customer customer) {
		if (modelController.getDbController().queryCustomer(customer.getCustomerId()) != null) {
			modelController.getDbController().removeCustomer(customer);
			return true;
		}
		return false;
	}

	public LinkedHashSet<Customer> queryCustomer(char type) {
		return modelController.getDbController().queryCustomer(type);
	}

	public LinkedHashSet<Customer> queryCustomer(String name) {
		return modelController.getDbController().queryCustomer(name);
	}

	public Customer queryCustomer(int id) {
		return modelController.getDbController().queryCustomer(id);
	}

	public boolean executePurchase(int itemID, int qty, int customerID) {
		// TODO update purchase Table
		Item_Elec item = modelController.getDbController().queryItem(itemID);
		if (modelController.getDbController().queryCustomer(customerID) == null) {
			return false;
		}
		if (this.getInventory().decrement(item, qty)) {
			modelController.getDbController().updateItem(itemID, item.getQty()); // updates Item in db with new qty
			this.updateOrders(item, qty);
			modelController.getDbController().insertPurchases(itemID, customerID);
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
		return modelController.getDbController().queryItem(itemDesc);
	}

	public LinkedHashSet<Item_Elec> queryItem() {
		return modelController.getDbController().queryItem();
	}

	public Item_Elec queryItem(int itemId) {
		return modelController.getDbController().queryItem(itemId);
	}

	private void updateOrders(Item_Elec item, int qty) {
		if (item.getQty() < item.ORDERQTYLIMIT) {
			int i = this.getInventory().generateOrderID();
			Order temp = this.modelController.getDbController().queryOrder(i);
			if (temp == null) {
				temp = new Order(i);
				modelController.getDbController().insertOrder(temp);
			}
			updateOrderLines(item, temp, qty);
		}
		return; // output to gui that order succeeded or failed
	}

	public void updateOrderLines(Item_Elec item, Order temp, int qty) {
		OrderLine templine = this.modelController.getDbController().queryOrderLine(item.getItemID(), temp.getOrderID());

		if (templine == null) {
			modelController.getDbController().insertOrderLine(
					new OrderLine(item.getItemID(), item.ORDERQTYLIMIT - item.getQty()), temp.getOrderID());
			return;
		} else {
			modelController.getDbController().updateOrderLine(templine, qty, temp.getOrderID());
			return;
		}
	}

	// Included items as an option for extensibility but implementation is not
	// required.
	private void deleteObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		ObjectWrapper ow = new ObjectWrapper();
		boolean success = false;
		switch (type) {
		case "CUSTOMER":
			success = this.removeCustomer((Customer) request.getPassedObj(0));

			// case "ITEM_ELEC": this.removeItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage("COMPLETE", null);
		else
			ow.setMessage("FAILED", null);
		try {
			this.modelController.getOutputStream().writeObject(ow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Included items as an option for extensibility but implementation is not
	// required.
	private void saveObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		ObjectWrapper ow = new ObjectWrapper();
		boolean success = false;
		switch (type) {
		case "CUSTOMER":
			success = this.saveCustomer((Customer) request.getPassedObj(0));

			// case "ITEM_ELEC": this.saveItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage("COMPLETE", null);
		else
			ow.setMessage("FAILED", null);
		try {
			this.modelController.getOutputStream().writeObject(ow);
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
			Customer c = (Customer) request.getPassedObj(0);
			if (command.equals("*ID"))
				searchObject.add(this.queryCustomer(c.getCustomerId()));
			else if (command.equals("*NAME"))
				searchObject.addAll(this.queryCustomer(c.getLastName()));
			else if (command.equals("*TYPE"))
				searchObject.addAll(this.queryCustomer(c.getCustomerType()));
			ow.setMessage("DISPLAY", "CUSTOMER");
		}

		case "ITEM_ELEC": {
			Item_Elec item = (Item_Elec) request.getPassedObj(0);
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
			int id = this.getInventory().generateOrderID();
			searchObject.add(modelController.getDbController().queryOrder(id));
			ow.setMessage("DISPLAY", "ORDER");
		}
			ow.addPassedObj(searchObject);
			try {
				this.modelController.getOutputStream().writeObject(ow);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runShop() {
		ObjectWrapper request;
		try {
			while (true) {
				request = (ObjectWrapper) this.modelController.getInputStream().readObject();
				String command = request.getMessage()[0];
				if (request != null && !command.equals("")) {
					System.out.println("command : " + command);

					switch (command) {
					case "SAVE":
						this.saveObject(request);

					case "SEARCH*":
						this.searchObject(request);

					case "DELETE":
						this.deleteObject(request);

					case "PURCHASE":
						this.executePurchase((Integer) request.getPassedObj(0), (Integer) request.getPassedObj(1),
								(Integer) request.getPassedObj(2));
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

}
