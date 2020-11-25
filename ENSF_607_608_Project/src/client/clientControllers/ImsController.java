package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.clientViews.ImsApplication;
import sharedModel.*;

public class ImsController {

	private ImsApplication app;
	private ClientController cc;
	
	public ImsController(ClientController cc) {
		this.app = new ImsApplication();
		this.addActionListeners();
		this.cc = cc;
	}

	public ImsApplication getApp() {
		return this.app;
	}
	
	public void setApp(ImsApplication ims) {
		this.app = ims;
	}
	
	public ClientController getCController() {
		return this.cc;
	}
	
	public void addActionListeners() {
		this.app.addActionListeners(new ImsActions());
		this.app.addSelectionListeners(new ImsActions());
	}
	
	public void executeSearch() {
		String command = "";
		String searchText = this.app.getSearchItemTxt();
		String type = app.getSelectedRadioButton();

		// create temp customer object with searchable attribute set.
		Item item = new Item();
		switch (type) {
		case "itemId": {
			try {
				item.setItemID(Integer.parseInt(searchText));
	        } catch (NumberFormatException nfe) {
	        	item.setItemID(-1); // if user passes string instead of int set to sentinal value
	        }
			command = "SEARCHID";
			break;
		}
		case "itemDesc": {
			item.setItemDesc(searchText);
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
	
	public void executeClear() {
		
		
	}
	
	public void executePurchase() {
		
	}

//	public void updateSearchResults(ArrayList<Object> objectList) {
//		for (Object o : objectList) {
//			Runnable runner = new Runnable() {
//				public void run() {
//					app.setSearchResultText(o.toString());
//				}
//			};
//			EventQueue.invokeLater(runner);
//		}
//	}

	private class ImsActions implements ActionListener, ListSelectionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == getApp().getSearchItemBtn())
				executeSearch();
			else if (e.getSource() == getApp().getClearItemBtn())
				executeClear();
			else if (e.getSource() == getApp().getPurchaseBtn())
				executePurchase();
			
			
//			String command = "";
//			String searchText = app.getSearchFieldText();
//			String type = app.getSelectedRadioButton();
//
//			// create temp customer object with searchable attribute set.
//			Customer c = new Customer();
//			switch (type) {
//			case "customerId": {
//				try {
//					c.setCustomerId(Integer.parseInt(searchText));
//		        } catch (NumberFormatException nfe) {
//		        	c.setCustomerId(-1); // if user passes string instead of int set to sentinal value
//		        }
//				command = "SEARCHID";
//				break;
//			}
//			case "customerType": {
//				c.setCustomerType(searchText.charAt(0));
//				command = "SEARCHTYPE";
//				break;
//			}
//			case "lName": {
//				c.setLastName(searchText);
//				command = "SEARCHNAME";
//				break;
//			}
//			}
//			ObjectWrapper request = new ObjectWrapper();
//			request.addPassedObj(c);
//			request.setMessage(command, "CUSTOMER");
//
//			Runnable runner = new Runnable() {
//				public void run() {
//					cc.getShopClient().triggerOutput(request);
//				}
//			};
//			EventQueue.invokeLater(runner);

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == getApp().getItemList()) {
				
			}
			
		}
	}
	
}
