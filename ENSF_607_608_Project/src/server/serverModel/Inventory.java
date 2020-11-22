package server.serverModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import sharedModel.Item;
import sharedModel.Order;
import sharedModel.OrderLine;

/**
 * Exercise 1 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 9th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: ShopDB holds an inventory of items and allows for queries
 *        to be made to that list. Duplicate items are not allowed (based on
 *        item ID)
 */
public class Inventory {

	public LinkedHashSet<Item> itemList;
	private Order order;

	public Inventory() {
		this.itemList = new LinkedHashSet<Item>();
	}

	public void setItemList(LinkedHashSet<Item> i) {
		this.itemList = i;
	}

	public void listAllItems() {

	}

	public String checkQty(String input) {
		Item tool = returnItem(input);
		return String.format("Current Quantity of %s is %d \n\n", tool.getItemDesc(), tool.getQty());
	}

	/**
	 * Decrease qty of item in inventory by 1 or by user input value.
	 * 
	 * @param input Item name or ID.
	 * @param qty   Quantity to reduce by.
	 */
	public boolean decrement(Item item, int qty) {
		if (item == null) {
			return false;
		}

		if (item.getQty() - qty >= 0) {
			item.setQty(item.getQty() - qty);
			return true;
		} else {
			return false;
		}
	}

	// success message to GUI
	/**
	 * return String.format("Updated Qty of %s: %d -> %d \n\n", item.getItemDesc(),
	 * item.getQty(), item.getQty() - qty);
	 * 
	 * } else { return String.format("Cannot decrement, quantity in stock = %s. \n",
	 * item.getQty());
	 * 
	 * } }
	 */

	public void searchByName(String itemName) {

		Iterator<Item> itr = this.itemList.iterator();

		while (itr.hasNext()) {
			Item tool = itr.next();
			if (tool.getItemDesc().toLowerCase().strip().compareTo(itemName.toLowerCase().strip()) == 0) {
				System.out.format("+-----------------+---------+----------+-----------+%n");
				System.out.format("| Tool Name       | Tool ID | Quantity | Price ($) |%n");
				System.out.format("+-----------------+---------+----------+-----------+%n");
				System.out.println(tool);
				return;
			}
		}
		System.out.println("Tool not found. Returning to menu <<");
		return;

	}

	/**
	 * Prints item information of item in database based on passed item ID.
	 * 
	 * @param ID Tool ID of item to be found
	 */
	public void searchByID(String ID) {
		Iterator<Item> itr = this.itemList.iterator();
		int intID = Integer.parseInt(ID);
		while (itr.hasNext()) {
			Item tool = itr.next();
			if (tool.getItemID() == intID) {
				System.out.format("+-----------------+---------+----------+-----------+%n");
				System.out.format("| Tool Name       | Tool ID | Quantity | Price ($) |%n");
				System.out.format("+-----------------+---------+----------+-----------+%n");
				System.out.println(tool);
				return;
			}
		}
		System.out.println("Tool not found. Returning to menu <<");
		return;
	}

	/**
	 * Helper function. Returns Item object from inventory.
	 * 
	 * @param input Tool ID or Name to be returned.
	 * @return returns Item if found in inventory or null.
	 */
	public Item returnItem(String input) {
		Iterator<Item> itr = this.itemList.iterator();
		while (itr.hasNext()) {
			Item tool = itr.next();
			if (input.matches("-?\\d+") == true) {
				int intID = Integer.parseInt(input);
				if (tool.getItemID() == intID) {
					return tool;
				}
			} else if (tool.getItemDesc().toLowerCase().strip().compareTo(input.toLowerCase().strip()) == 0) {
				return tool;
			}
		}
		return null;
	}

	/**
	 * Generates unit hash ID based on todays date and tool ID. Hash ID is unique to
	 * the inputs. ie same inputs = same hashID
	 * 
	 * @param input Item name or ID to return Item from inventory
	 * @return returns 5 digit unique order ID
	 */
	public int generateOrderID() {
		LocalDate now = LocalDate.now();
		LocalDate epoch = LocalDate.ofEpochDay(0);
		long dateHash = ChronoUnit.DAYS.between(epoch, now);
		final int prime = 31;
		int result = 1;
		result = prime * result + ((int) dateHash);
		return result = result % 100000; // returns between 00000-99999
	}

}

// public void addItem(String itemName) {
//// not required by assignment
// }

// public void deleteItem(String itemName) {
//// not required by assignment
// }

// public void editItem(String itemName) {
//// not required by assignment
// }
