package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.clientViews.ShopApplication;
import sharedModel.Customer;
import sharedModel.ObjectWrapper;

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
		
		this.addRadioButtonActionListeners();
		this.shopApplication.getFrame().setVisible(true);
		
		this.shopClient = new ShopClient("localhost",8088);
		this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}

	public void addRadioButtonActionListeners() {
		customerController.getApp().addSearchAction(new searchAction());
	}

	private class searchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = "";
			
			String searchText = customerController.getApp().getSearchFieldText();
			String type = customerController.getApp().getSelectedRadioButton();
			System.out.println(searchText + " " + type);
			
			// create temp customer object with searchable attribute set.
			Customer c = new Customer();
			switch (type) {
			case "customerId": {
				c.setCustomerId(Integer.parseInt(searchText));
				command = "SEARCHID";
				break;
			}
			case "customerType": {
				c.setCustomerType(searchText.charAt(0));
				command = "SEARCHTYPE";
				break;
			}
			case "lName": {
				c.setLastName(searchText);
				command = "SEARCHNAME";
				break;
			}
			}
			ObjectWrapper request = new ObjectWrapper();
			request.addPassedObj(c);
			request.setMessage(command, "CUSTOMER");

			shopClient.triggerSearch(request);
			// execute query
			// print to results text field
		}

	}

	public static void main(String[] args) {

		ClientController cc = new ClientController();
		
	}

}
