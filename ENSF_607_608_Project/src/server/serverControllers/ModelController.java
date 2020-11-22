package server.serverControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.serverModel.*;
import sharedModel.*;

public class ModelController implements Runnable, DatabaseTables {

	private DbController dbController;
	private ShopApp shop;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;

	// TODO
	// add/update/remove customers from customer list
	// search for customer based on name id or type.
	// add/update/remove suppliers from suppliers list
	// remove Items from Items list requires sql database to update referential
	// constraints
	// Query order by ID. print to reasonable format
	// Query order by most recent. print to reasonable format
	// Print all Items/Suppliers in list
	// print Item by ID, print Item byName
	//

	public ModelController(ObjectInputStream clientIn, ObjectOutputStream clientOut, DbController dbController,
			ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
		this.clientIn = clientIn;
		this.clientOut = clientOut;
	}

	public ModelController() {
		// TODO Auto-generated constructor stub
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

}
