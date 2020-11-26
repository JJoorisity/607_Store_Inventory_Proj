package client.clientControllers;

import client.clientViews.ShopApplication;

/**
 * Client Controller handles all interactions between the shop client and the
 * shop application through the use of the inventory and customer controllers.
 * 
 * Contains main function to launch all client side operations.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ClientController {

	private ShopClient shopClient;
	private CmsController customerController;
	private ImsController inventoryController;
	private ShopApplication shopApplication;

	/**
	 * Client Controller constructor. Currently uses composition to create the client and inventory controllers. 
	 */
	public ClientController() {
		customerController = new CmsController(this);
		inventoryController = new ImsController(this);
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		// add all listeners required to gui
		customerController.addActionListeners();

		// init the shop client and set its controller
		this.shopClient = new ShopClient("localhost", 8088);
		shopClient.setController(this);

		// setup and run the gui
		shopApplication.startGui(shopApplication);

		// start the client communication loop to interact with the server.
		shopClient.communicate();

	}

	public CmsController getCmsController() {
		return this.customerController;
	}

	public ImsController getImsController() {
		return this.inventoryController;
	}

	public ShopClient getShopClient() {
		return this.shopClient;
	}

	public static void main(String[] args) {

		ClientController cc = new ClientController();

	}

}
