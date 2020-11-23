package server.serverControllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import server.serverModel.*;

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

	public ModelController(DbController dbController, ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
	}

	@Override
	public void run() {
		this.shop.runShop();
	}

	public DbController getDbController() {
		return dbController;
	}

	public ObjectInputStream getInputStream() {
		return clientIn;
	}

	public ObjectOutputStream getOutputStream() {
		return clientOut;
	}
}
