package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.cj.util.StringUtils;

import client.clientViews.ImsApplication;
import sharedModel.*;

/**
 * Controller of the interactions between reading information from the client
 * and displaying in the ImsApplication frame.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ImsController implements Commands, PrintTableConstants {

	private ImsApplication app;
	private ClientController cc;
	
	/**
	 * Default constructor.
	 * @param cc (ClientConroller) relation with the client controller to allow
	 * communcation to the server.
	 */
	public ImsController(ClientController cc) {
		this.app = new ImsApplication();
		this.addActionListeners();
		this.cc = cc;
	}

	/**
	 * @return (ImsApplication) instance of the inventory frame.
	 */
	public ImsApplication getApp() {
		return this.app;
	}
	
	/**
	 * Set a new inventory frame.
	 * @param ims (ImsApplication)
	 */
	public void setApp(ImsApplication ims) {
		this.app = ims;
	}
	
	/**
	 * @return (ClientConroller)
	 */
	public ClientController getCController() {
		return this.cc;
	}
	
	/**
	 * Add action listeners to the ImsApplication.
	 */
	public void addActionListeners() {
		this.app.addActionListeners(new ImsActions());
		this.app.addSelectionListeners(new ImsActions());
	}
	
	/**
	 * Execute a search command from the ImsApplication to the server database.
	 * Cases are by item ID or item Description.
	 */
	public void executeSearch() {
		String command = "";
		String searchText = this.app.getSearchItemTxt();
		String type = app.getSelectedRadioButton();
		// create temp customer object with searchable attribute set.
		Item_Elec item = new Item_Elec();
		switch (type) {
		case "itemId": {
			try {
				if (StringUtils.isStrictlyNumeric(searchText))
					item.setItemID(Integer.parseInt(searchText));
	        } catch (NumberFormatException nfe) {
	        	item.setItemID(-1); // if user passes string instead of int set to sentinal value
	        }
			command = "SEARCHID";
			break;
		}
		case "itemDesc": {
			if (!searchText.trim().isEmpty())
				item.setItemDesc(searchText);
			command = "SEARCHNAME";
			break;
		}
		}
		wrapMessage(command, ITEM_ELEC, item);
	}
	
	/**
	 * Update the search results into the ImsApplication result table.
	 * @param objectList (ArrayList<Object>) list of objects to display.
	 */
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
	
	/**
	 * Update the purchase field message depending on if purchase was successful.
	 * @param message (String) message from server.
	 */
	public void updatePurchaseField(String message) {
		Runnable runner = new Runnable() {
			public void run() {
				app.setPMssgeTxt(message);
			}
		};
		EventQueue.invokeLater(runner);
	}
	
	/**
	 * Clear all the search fields.
	 */
	public void executeClearSearch() {
		this.app.setSearchItemTxt("");
		this.app.resetSearchResultText();
	}
	
	/**
	 * Clear all the purchase fields.
	 */
	public void executeClearPurchase() {
		this.app.setcustIdTxt("");
		this.app.setItemIdTxt("");
		this.app.setPurchaseQtyTxt("");
		this.app.setPMssgeTxt("");
	}
	
	/**
	 * Execute a purchase from the ImsApplication entered information
	 * to the server database.
	 */
	public void executePurchase() {
		String command = PURCHASE;
		ArrayList<Object> purchases = new ArrayList<Object>();
		if (StringUtils.isStrictlyNumeric(app.getItemIdTxt()) && StringUtils.isStrictlyNumeric(app.getPurchaseQtyTxt())
				&& StringUtils.isStrictlyNumeric(app.getcustIdTxt())) {
			purchases.add(Integer.parseInt(app.getItemIdTxt()));
			purchases.add(Integer.parseInt(app.getPurchaseQtyTxt()));
			purchases.add(Integer.parseInt(app.getcustIdTxt()));
			wrapMessage(command, ITEM_ELEC, purchases);
		}
	}
	
	/**
	 * Execute a search of all items in the server database.
	 */
	public void executeSearchAll() {
		this.app.resetSearchResultText();
		String command = SEARCH + ALL;
		// create temp customer object with searchable attribute set.
		Item_Elec item = new Item_Elec();
		wrapMessage(command, ITEM_ELEC, item);
	}
	
	/**
	 * Generate a text file for the daily order.
	 */
	public void generateOrder() {
		String command = SEARCH + ORDER;
		wrapMessage(command, ORDER, null);
	}
	
	/**
	 * Create a ObjectWrapper to send to the server for processing.
	 * @param command (String) the command the server needs to execute.
	 * @param type (String) the type of object being sent.
	 * @param newObj (Object) the object being sent to the server.
	 */
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

	/**
	 * Inner class to create action listeners for the ImsApplication events.
	 * 
	 * @author NJack & JJoorisity
	 * @version 1.0
	 * @since 2020-11-26
	 */
	private class ImsActions implements ActionListener, ListSelectionListener {

		/**
		 * Method trigger for all action events by the ImsApplication button clicks.
		 */
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
		
		/**
		 * Method trigger for all selection events by the ImsApplication table selections.
		 */
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
