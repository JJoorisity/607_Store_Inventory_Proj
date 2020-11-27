package server.serverControllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.serverModel.*;

/**
 * Master controller of shop business logic packages, initialized by the server
 * controller. Connects the database controller to the shop back end.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ModelController implements Runnable, DatabaseTables {
	private DbController dbController;
	private ShopApp shop;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;

	/**
	 * Constructor
	 * 
	 * @param clientIn     (ObjectInputStream) client input stream.
	 * @param clientOut    (ObjectOutputStream) client output stream.
	 * @param dbController (DbController) controller used to connect to mySQL
	 *                     database
	 * @param shop         (ShopApp) connection to shop back end.
	 */
	public ModelController(ObjectInputStream clientIn, ObjectOutputStream clientOut, DbController dbController,
			ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
		this.clientIn = clientIn;
		this.clientOut = clientOut;
	}

	/**
	 * Runnable interface method, initialize connection with the client through to
	 * the shop back end.
	 */
	@Override
	public void run() {
		this.shop.runShop();
	}
	
	/**
	 * Returns internal shop attribute for setup and use
	 * @return (ShopApp) returns shopApp being controllered but this controller
	 */
	public ShopApp getShop() {
		return this.shop;
	}
	
	/**
	 * Sets the inventory of shop to current Database Contents
	 */
	public void initShop() {
		this.shop.getInventory().setItemList(this.dbController.queryItem());
	}

	/**
	 * @return (DbController) current database controller.
	 */
	public DbController getDbController() {
		return dbController;
	}

	/**
	 * @return (ObjectInputStream) current client input stream.
	 */
	public ObjectInputStream getInputStream() {
		return clientIn;
	}

	/**
	 * @return (ObjectOutputStream) current client output stream.
	 */
	public ObjectOutputStream getOutputStream() {
		return clientOut;
	}

	public ModelController(DbController dbController, ShopApp shop) {
		this.dbController = dbController;
		this.shop = shop;
	}

	

}
