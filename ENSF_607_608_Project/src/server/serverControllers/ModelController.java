package server.serverControllers;

import server.serverModel.*;
import sharedModel.*;

public class ModelController implements Runnable {

	private DbController dbController;
	private ShopApp shop;

	// serlize item into object
	// need int qty for item order

	public boolean executePurchase() {

		// uses dbcontroller to query db
		// uses ShopApp to provide info to query with
		if (shop.getInventory().decrement(item, qty)) {

			int i = shop.getInventory().generateOrderID();
			Order temp = this.dbController.queryOrder(i);
			if (temp == null) {
				temp = shop.getInventory().createOrder(item, qty, i);
				dbController.insertOrder(temp.orderSqlInsert());
			}
			OrderLine templine = this.dbController.queryOrderLine(item.getItemID(), temp.getOrderID());
			String sqlLine = temp.addOrderLine(item, qty);

			if (templine == null) {
				dbController.insertOrderLine(sqlLine);
			} else {
				dbController.updateOrderLine(sqlLine, templine); // sqlline = new data, templine = old object
			}

		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
