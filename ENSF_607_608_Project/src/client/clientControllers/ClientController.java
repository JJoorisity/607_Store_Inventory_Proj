package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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

		this.addRadioButtonActionListeners();
		shopApplication.startGui(shopApplication);

		this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}

	// testing constructor to be deleted
	public ClientController() {
		customerController = new CmsController();
		inventoryController = new ImsController();
		shopApplication = new ShopApplication(customerController.getApp(), inventoryController.getApp());

		this.addRadioButtonActionListeners();

		this.shopClient = new ShopClient("localhost", 8088);

	
		shopApplication.startGui(shopApplication);
		

		shopClient.communicate();

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
			System.out.println(SwingUtilities.isEventDispatchThread());

			Runnable runner = new Runnable() {
				public void run() {
					shopClient.triggerSearch(request);
				}
			};
			EventQueue.invokeLater(runner);

		}
	}

	public static void main(String[] args) {

		ClientController cc = new ClientController();

	}

}
