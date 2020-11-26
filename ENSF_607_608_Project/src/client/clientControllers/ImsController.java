package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.clientViews.ImsApplication;
import sharedModel.*;

public class ImsController implements Commands, PrintTableConstants {

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
		Item_Elec item = new Item_Elec();
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
		wrapMessage(command, ITEM_ELEC, item);
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
	
	public void updatePurchaseField(String message) {
		Runnable runner = new Runnable() {
			public void run() {
				app.setPMssgeTxt(message);
			}
		};
		EventQueue.invokeLater(runner);
	}
	
	public void executeClearSearch() {
		this.app.setSearchItemTxt("");
		this.app.resetSearchResultText();
	}
	
	public void executeClearPurchase() {
		this.app.setcustIdTxt("");
		this.app.setItemIdTxt("");
		this.app.setPurchaseQtyTxt("");
		this.app.setPMssgeTxt("");
	}
	
	public void executePurchase() {
		String command = PURCHASE;
		ArrayList<Object> purchases = new ArrayList<Object>();
		purchases.add(Integer.parseInt(app.getItemIdTxt()));
		purchases.add(Integer.parseInt(app.getPurchaseQtyTxt()));
		purchases.add(Integer.parseInt(app.getcustIdTxt()));
		wrapMessage(command, ITEM_ELEC, purchases);
	}
	
	public void executeSearchAll() {
		this.app.resetSearchResultText();
		String command = SEARCH + ALL;
		// create temp customer object with searchable attribute set.
		Item_Elec item = new Item_Elec();
		wrapMessage(command, ITEM_ELEC, item);
	}
	
	public void generateOrder() {
		String command = SEARCH + ORDER;
		wrapMessage(command, ORDER, null);
	}
	
	public void wrapMessage(String command, String type, Object newObj) {
		ObjectWrapper request = new ObjectWrapper();
		request.addPassedObj(newObj);
		request.setMessage(command, type);

		Runnable runner = new Runnable() {
			public void run() {
				cc.getShopClient().triggerOutput(request);
			}
		};
		EventQueue.invokeLater(runner);
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
				executeClearSearch();
			else if (e.getSource() == getApp().getPurchaseBtn())
				executePurchase();
			else if (e.getSource() == getApp().getSearchAllBtn())
				executeSearchAll();
			else if (e.getSource() == getApp().getClearPurchBtn())
				executeClearPurchase();
			else if (e.getSource() == getApp().getOrderBtn())
				generateOrder();
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Runnable runner = new Runnable() {
				public void run() {
					// get current selected row
					int i = app.getItemTable().getSelectedRow();
					
					if (i >= 0) {
						String itemId = String.valueOf(app.getItemTable().getValueAt(i, 0));
						app.setItemIdTxt(itemId);
					}
				}
			};
			EventQueue.invokeLater(runner);
		}
	}
	
}
