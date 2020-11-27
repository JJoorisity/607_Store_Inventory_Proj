package client.clientControllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.clientViews.CmsApplication;
import sharedModel.Customer;
import sharedModel.ObjectWrapper;

/**
 * Customer Controller handles interactions between the GUI application and the
 * client controller. Allows for the following operations: - add action
 * listeners to GUI controls - update Search results pane of customer tab -
 * update customer info text fields based on selection from GUI - clear search
 * results and customer info text from view - query database and return object
 * based on passed parameters and search type - create/edit/delete customers
 * based on info entered in customer info text boxes.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
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

	/**
	 * Creates and sets event listeners for all buttons and fields in the GUI as
	 * required.
	 */
	public void addActionListeners() {
		this.app.addSearchAction(new searchAction());
		this.app.addClearSearchAction(new clearSearchAction());
		this.app.addListSelectionAction(new selectListAction());
		this.app.addClearCustInfoAction(new clearCustInfoAction());
		this.app.addSaveCustInfoAction(new saveCustInfoAction());
		this.app.addDeleteCustAction(new deleteCustAction());
	}

	/**
	 * Updates GUI based on result from DB query
	 * 
	 * @param objectList (ArrayList<Object>) list of objects pulled based on query.
	 *                   Built to handle both single and multi-result queries.
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
	 * Updates each text field with customer info based on passed query result
	 * 
	 * @param objectList (ArrayList<Object>) list of objects pulled based on query.
	 *                   Built to handle both single and multi-result queries.
	 */
	public void updateCustInfoPane(ArrayList<Object> objectList) {
		for (Object o : objectList) {
			Runnable runner = new Runnable() {
				public void run() {
					app.updateCustInfoPane(o);
				}
			};
			EventQueue.invokeLater(runner);
		}
	}

	/**
	 * Internal class to handle search actions using Action Listener. Triggers an
	 * output back to the server side of the shop application based on radio button
	 * selections and search box contents.
	 */
	private class searchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = "";
			String searchText = app.getSearchFieldText();
			String type = app.getSelectedRadioButton();

			if (!searchText.trim().isEmpty()) {
				Customer c = new Customer();
				switch (type) {
				case "customerId": {
					try {
						c.setCustomerId(Integer.parseInt(searchText));
					} catch (NumberFormatException nfe) {
						c.setCustomerId(-1); // if user passes string instead of integer, set to sentinel value
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
						cc.getShopClient().triggerOutput(request); // sends wrapped object back to server.
					}
				};
				EventQueue.invokeLater(runner);
			}
		}
	}

	/**
	 * Internal class to handle clearing all results from GUI triggered by button
	 * press.
	 */
	private class clearSearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Runnable runner = new Runnable() {
				public void run() {
					app.setSearchFieldText("");
					app.resetSearchResultText();
					app.resetCustInfoPane();
				}
			};
			EventQueue.invokeLater(runner);
		}
	}

	/**
	 * Internal class to handle user selection from listed options. Changes customer
	 * info panel based on user selection from the search results pane.
	 */
	private class selectListAction implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				String selection = app.getResultList().getSelectedValue().toString();
				String temp = (selection.substring(selection.lastIndexOf("ID")));
				int customerID = Integer.parseInt(temp.replaceAll("[^0-9]", "")); // gets string from selection and
																					// pulls only the userID out.

				Customer c = new Customer();
				c.setCustomerId(customerID);

				ObjectWrapper request = new ObjectWrapper();
				request.addPassedObj(c);
				request.setMessage("SEARCHIDEDIT", "CUSTOMER");

				Runnable runner = new Runnable() {
					public void run() {
						cc.getShopClient().triggerOutput(request);
					}
				};
				EventQueue.invokeLater(runner);
			}
		}
	}

	/**
	 * Internal class to handle clearing info from customer information panel
	 *
	 */
	private class clearCustInfoAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Runnable runner = new Runnable() {
				public void run() {
					app.resetCustInfoPane();
				}
			};
			EventQueue.invokeLater(runner);
		}
	}

	/**
	 * Internal class to handle saving new or updated customer info. Requires a
	 * valid integer customerID input on the GUI to execute the save. Can and will
	 * save over existing customers without any warning.
	 * TODO add "are you sure you want to do this?" message before allowing save to continue.
	 */
	private class saveCustInfoAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Customer c = app.getEditedCustInfo();
			if (c != null) {
				ObjectWrapper request = new ObjectWrapper();
				request.addPassedObj(c);
				request.setMessage("SAVE", "CUSTOMER");

				Runnable runner = new Runnable() {
					public void run() {
						cc.getShopClient().triggerOutput(request);
					}
				};
				EventQueue.invokeLater(runner);
			}
		}
	}

	/**
	 * Internal class to handle deleting a customer from the database. Requires a
	 * valid integer customerID input on the GUI to execute the delete. 
	 * TODO add "are you sure you want to do this?" message before allowing delete to continue.
	 */
	private class deleteCustAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Customer c = app.getEditedCustInfo();
			if (c != null) {
				ObjectWrapper request = new ObjectWrapper();
				request.addPassedObj(c);
				request.setMessage("DELETE", "CUSTOMER");

				Runnable runner = new Runnable() {
					public void run() {
						cc.getShopClient().triggerOutput(request);
					}
				};
				EventQueue.invokeLater(runner);

				app.resetCustInfoPane();
				app.resetSearchResultText();
			}
		}

	}

}
