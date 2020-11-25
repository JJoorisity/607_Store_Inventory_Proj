package client.clientControllers;

import client.clientViews.ShopApplication;


public class ClientController {

	private ShopClient shopClient;
	private CmsController customerController;
	private ImsController inventoryController;
	private ShopApplication shopApplication;

	public ClientController(ShopClient shopClient) {
		this.shopClient = shopClient;
		customerController = new CmsController(this);
		inventoryController = new ImsController(this);
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		// replace with generic add all listenrs options
		customerController.addActionListeners();
		
		shopApplication.startGui(shopApplication);

		this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}

	// testing constructor to be deleted
	public ClientController() {
		customerController = new CmsController(this);
		inventoryController = new ImsController(this);
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		// replace with generic add all listenrs options
		customerController.addActionListeners();

		this.shopClient = new ShopClient("localhost", 8088);
		shopClient.setController(this);
	
		shopApplication.startGui(shopApplication);

		shopClient.communicate();

		// construct gui. pass actionlisters as required.
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
