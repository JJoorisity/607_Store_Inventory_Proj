package server.serverControllers;

import server.serverModel.*;
import sharedModel.*;

public class ModelController implements Runnable, DatabaseTables {

	private DbController dbController;
	private ShopApp shop;

	// serlize item into object
	// need int qty for item order
	public boolean executePurchase(Item item, int qty) {
		// uses dbcontroller to query db
		// uses ShopApp to provide info to query with
		if (shop.getInventory().decrement(item, qty)) {

			int i = shop.getInventory().generateOrderID();
			Order temp = this.dbController.queryOrder(i);
			if (temp == null) {
				temp = new Order(i);
				dbController.insert(ORDERS, temp.orderSqlInsert());
			}
			OrderLine templine = this.dbController.queryOrderLine(item.getItemID(), temp.getOrderID());
			String sqlLine = temp.addOrderLine(item.getItemID(), qty);

			if (templine == null) {
				dbController.insert(ORDER_LINES, sqlLine);
				return true;
			} else {
				dbController.updateOrderLine(sqlLine); // sqlline = new data
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
