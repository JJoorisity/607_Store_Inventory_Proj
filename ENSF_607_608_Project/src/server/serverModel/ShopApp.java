package server.serverModel;

import sharedModel.*;

import java.io.IOException;
import java.util.*;
import server.serverControllers.ModelController;

/**
 * Back end connection point to model controller. Handles all interactions with
 * the inventory and sharedModel package to the database controller and client.
 * - executes a client purchase. - customer modification commands. - provides
 * access to sharedModel classes for client queries. - runs communication loop
 * with client and ModelController
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ShopApp implements Commands {

	private ModelController modelController;
	private Inventory inventory;

	/**
	 * Constructor: initialize inventory class.
	 */
	public ShopApp() {
		this.inventory = new Inventory();
	}

	public void setModelController(ModelController m) {
		this.modelController = m;
	}

	/**
	 * @return (Inventory) access local inventory object.
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Save new or modify existing customer. If customer id is not found in
	 * database: create new else update.
	 * 
	 * @param customer (Customer) the customer being adjusted
	 * @return (boolean) returns true that change was successful.
	 */
	public boolean saveCustomer(Customer customer) {
		if (modelController.getDbController().queryCustomer(customer.getCustomerId()) == null) {
			modelController.getDbController().insertCustomer(customer);
		} else {
			modelController.getDbController().updateCustomer(customer);
		}
		return true;
	}

	/**
	 * Remove customer from the shop database.
	 * 
	 * @param customer (Customer) the customer being removed from the database.
	 * @return (boolean) true if removal was successful.
	 */
	public boolean removeCustomer(Customer customer) {
		if (modelController.getDbController().queryCustomer(customer.getCustomerId()) != null) {
			modelController.getDbController().removeCustomer(customer);
			return true;
		}
		return false;
	}

	/**
	 * Query a list of customers by the customer type.
	 * 
	 * @param type (char) type of customer being searched.
	 * @return (LinkedHashSet<Customer>) list of customers returned from mySQL
	 *         query.
	 */
	public LinkedHashSet<Customer> queryCustomer(char type) {
		return modelController.getDbController().queryCustomer(type);
	}

	/**
	 * Query a list of customers by customer last name.
	 * 
	 * @param name (String) last name of customers being searched.
	 * @return (LinkedHashSet<Customer>) list of customers returned from mySQL
	 *         query.
	 */
	public LinkedHashSet<Customer> queryCustomer(String name) {
		return modelController.getDbController().queryCustomer(name);
	}

	/**
	 * Query a customers by customer id.
	 * 
	 * @param id (int) id of customer being searched.
	 * @return (Customer) customer returned from query.
	 */
	public Customer queryCustomer(int id) {
		return modelController.getDbController().queryCustomer(id);
	}

	/**
	 * Execute a purchase by a customer for a specified quantity of an item.
	 * 
	 * @param itemID     (int) id of item that was purchased.
	 * @param qty        (int) quantity purchased of item.
	 * @param customerID (int) id of customer executing the purchase.
	 * @return (boolean) true if the purchase was successful.
	 */
	public boolean executePurchase(int itemID, int qty, int customerID) {
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

	/**
	 * Update the daily order with any new items that need to be ordered.
	 * 
	 * @param item (Item_Elec) item that needs to be ordered.
	 * @param qty  (int) quantity of item that needs to be ordered.
	 */
	private void updateOrders(Item_Elec item, int qty) {
		if (item.getQty() < item.ORDERQTYLIMIT) {
			int i = this.getInventory().generateOrderID();
			Order temp = this.modelController.getDbController().queryOrder(i);
			if (temp == null) {
				temp = new Order(i);
				modelController.getDbController().insertOrder(temp);
			}

			updateOrderLines(item, temp.getOrderID(), qty);
		}
		return; // output to gui that order succeeded or failed
	}

	/**
	 * Return a supplier from the database based on the passed ID
	 * 
	 * @param supplierID (int) 4 digit supplier ID to return
	 * @return (Supplier) retunrs single queried supplier or null if ID does not
	 *         match DB
	 */
	public Int_Supplier querySupplier(int supplierID) {
		return this.modelController.getDbController().querySupplier(supplierID);
	}

	/**
	 * Update the daily order's orderlines by adding a new line or updating existing
	 * line's quantity.
	 * 
	 * @param item    (Item_Elec) item that needs to be ordered.
	 * @param orderId (int) order id that the orderline is being added to.
	 * @param qty     (int) quantity of item needing to be ordered.
	 */
	public void updateOrderLines(Item_Elec item, int orderId, int qty) {
		OrderLine templine = this.modelController.getDbController().queryOrderLine(item.getItemID(), orderId);

		if (templine == null) {
			Item_Elec tempItem = this.queryItem(item.getItemID());
			Int_Supplier tempSupp = this.querySupplier(tempItem.getSupplierID());
			modelController.getDbController().insertOrderLine(
					new OrderLine(item.getItemID(), item.ORDERQTYLIMIT - item.getQty(), tempSupp.getCompanyName()),
					orderId);
			return;
		} else {
			modelController.getDbController().updateOrderLine(templine, qty, orderId);
			return;
		}
	}

	/**
	 * Query an item by the item name/description.
	 * 
	 * @param itemDesc (String) description of item being searched.
	 * @return (LinkedHashSet<Item_Elec>) list of items matching the item
	 *         description.
	 */
	public LinkedHashSet<Item_Elec> queryItem(String itemDesc) {
		return modelController.getDbController().queryItem(itemDesc);
	}

	/**
	 * Query all items listed in the database
	 * 
	 * @return (LinkedHashSet<Item_Elec>) list of all items.
	 */
	public LinkedHashSet<Item_Elec> queryItem() {
		return modelController.getDbController().queryItem();
	}

	/**
	 * Query an item from the database by its id.
	 * 
	 * @param itemId (int) id of item being searched.
	 * @return (Item_Elec) item matching the item id.
	 */
	public Item_Elec queryItem(int itemId) {
		return modelController.getDbController().queryItem(itemId);
	}

	// Included items as an option for extensibility but implementation is not
	// required.
	private void deleteObject(ObjectWrapper request) {
		String type = request.getMessage()[1];
		ObjectWrapper ow = new ObjectWrapper();
		boolean success = false;
		switch (type) {
		case CUSTOMER:
			success = this.removeCustomer((Customer) request.getPassedObj(0));
			break;
		// case "ITEM_ELEC": this.removeItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage(COMPLETE, null);
		else
			ow.setMessage(FAILED, null);
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
		case CUSTOMER:
			success = this.saveCustomer((Customer) request.getPassedObj(0));
			break;
		// case "ITEM_ELEC": this.saveItem((Item_Elec)request.getPassedObj(0));
		}
		if (success)
			ow.setMessage(COMPLETE, null);
		else
			ow.setMessage(FAILED, null);
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
		case CUSTOMER: {
			Customer c = (Customer) request.getPassedObj(0);
			if (command.equals(ID) || command.contains(ID)) {
				searchObject.add(this.queryCustomer(c.getCustomerId()));
				if (command.contains("EDIT")) {
					ow.setMessage(DISPLAYEDIT, CUSTOMER); // special case for customer edit pane
					break;
				}
			} else if (command.equals(NAME) || command.contains(NAME))
				searchObject.addAll(this.queryCustomer(c.getLastName()));
			else if (command.equals(TYPE) || command.contains(TYPE))
				searchObject.addAll(this.queryCustomer(c.getCustomerType()));
			ow.setMessage(DISPLAY, CUSTOMER);
			break;
		}

		case ITEM_ELEC: {
			Item_Elec item = (Item_Elec) request.getPassedObj(0);
			if (command.contains(ID))
				searchObject.add(this.queryItem(item.getItemID()));
			else if (command.contains(NAME))
				searchObject.addAll(this.queryItem(item.getItemDesc()));
			else if (command.contains(ALL))
				searchObject.addAll(this.queryItem());
			ow.setMessage(DISPLAY, ITEM_ELEC);
			break;
		}
		case ORDER: {
			// will only generate today's order for printing
			int id = this.getInventory().generateOrderID();
			searchObject.add(modelController.getDbController().queryOrder(id));
			ow.setMessage(DISPLAY, ORDER);
			break;
		}
		}
		try {
			if (searchObject.isEmpty() == true) {
				ow.setMessage(FAILED, CUSTOMER);
			} else if (searchObject.get(0) == null) {
				ow.setMessage(FAILED, CUSTOMER); // if query returns a null, set the outgoing message to failed
			}
			ow.addPassedObj(searchObject);
			this.modelController.getOutputStream().writeObject(ow);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String printOrder() {
		int i = this.getInventory().generateOrderID();
		Order temp = this.modelController.getDbController().queryOrder(i);
		return this.getInventory().printOrder(temp);
	}

	public String printItems() {
		return this.getInventory().toString();
	}

	public void runShop() {
		// ObjectWrapper request;
		try {
			while (true) {
				ObjectWrapper request = (ObjectWrapper) this.modelController.getInputStream().readObject();
				String command = request.getMessage()[0];
				if (command.contains(SEARCH)) {
					this.searchObject(request); // search not save
				} else if (command.contentEquals(QUIT)) {
					break;
				} else if (request != null && !command.equals("")) {
					System.out.println("command : " + command);

					switch (command) {
					case SAVE: {
						this.saveObject(request);
						break;
					}
					case DELETE: {
						this.deleteObject(request);
						break;
					}
					case PURCHASE: {
						this.executePurchase((Integer) request.getPassedObj(0), (Integer) request.getPassedObj(1),
								(Integer) request.getPassedObj(2));
						break;
					}

					}
				}
				request.resetWrapper();
			}
		} catch (ClassNotFoundException | IOException ex) {
			System.err.println(ex.getStackTrace());
		}

	}

}

//public boolean removeItem(Item item) {
//if (dbController.queryItem(item.getItemID()) != null) {
//	dbController.removeItem(item);
//	return true;
//}
//return false;
//}
