package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.clientViews.ShopApplication;

public class ClientController {

	private ShopClient shopClient;
	private CmsController customerController;
	private ImsController inventoryController;
	private ShopApplication shopApplication;

	public ClientController(ShopClient shopClient) {
		this.shopClient = shopClient;
		customerController = new CmsController();
		inventoryController = new ImsController();
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}

	// testing constructor to be deleted
	public ClientController() {
		customerController = new CmsController();
		inventoryController = new ImsController();
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		//this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}

	public void addRadioButtonActionListeners() {
		customerController.getApp().addSearchAction(new searchAction());
	}

	private class searchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String query = customerController.getApp().getSearchFieldText();
			String type = customerController.getApp().getSelectedRadioButton();
			this.shopClient.
			// execute query
			// print to results text field
		}

	}

	public static void main(String[] args) {

		ClientController cc = new ClientController();
		cc.addRadioButtonActionListeners();
		cc.shopApplication.startGui(cc.shopApplication);
	}


}
