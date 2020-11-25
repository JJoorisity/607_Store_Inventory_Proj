package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import client.clientViews.CmsApplication;
import sharedModel.Customer;
import sharedModel.ObjectWrapper;

public class CmsController {

	private CmsApplication app;
	private ClientController cc;

	public CmsController(ClientController cc) {
		app = new CmsApplication();
		this.cc = cc;
	}

	public CmsApplication getApp() {
		return app;
	}

	public void setApp(CmsApplication cms) {
		this.app = cms;
	}

	public void addActionListeners() {
		this.app.addSearchAction(new searchAction());
		this.app.addClearSearchAction(new clearAction());
	}

	public void updateSearchResults(ArrayList<Object> objectList) {
		for (Object o : objectList) {
			Runnable runner = new Runnable() {
				public void run() {
					app.setSearchResultText(o.toString());
				}
			};
			EventQueue.invokeLater(runner);
		}
	}

	private class searchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = "";
			String searchText = app.getSearchFieldText();
			String type = app.getSelectedRadioButton();

			// create temp customer object with searchable attribute set.
			Customer c = new Customer();
			switch (type) {
			case "customerId": {
				try {
					c.setCustomerId(Integer.parseInt(searchText));
		        } catch (NumberFormatException nfe) {
		        	c.setCustomerId(-1); // if user passes string instead of int set to sentinal value
		        }
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

			Runnable runner = new Runnable() {
				public void run() {
					cc.getShopClient().triggerOutput(request);
				}
			};
			EventQueue.invokeLater(runner);

		}
	}
	
	private class clearAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Runnable runner = new Runnable() {
				public void run() {
					app.setSearchFieldText("");
					app.resetSearchResultText("");
				}
			};
			EventQueue.invokeLater(runner);

		}
	}

}
