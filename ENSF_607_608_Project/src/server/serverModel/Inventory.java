package server.serverModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.LinkedHashSet;

import sharedModel.Item;

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

	public Inventory() {
		this.itemList = new LinkedHashSet<Item>();
	}

	public void setItemList(LinkedHashSet<Item> i) {
		this.itemList = i;
	}

	/**
	 * Lists all items currently in inventory
	 */
	public void listAllItems() {

		System.out.format("+-----------------+---------+----------+-----------+%n");
		System.out.format("| Tool Name       | Tool ID | Quantity | Price ($) |%n");
		System.out.format("+-----------------+---------+----------+-----------+%n");

		for (Item i : this.itemList) {
			System.out.println(i);
		}
		System.out.format("+-----------------+---------+----------+-----------+%n");
	}

	/**
	 * Returns current quantity of input item
	 * 
	 * @param input Item name or ID
	 */
	public void checkQty(String input) {
		Item tool = returnItem(input);
		System.out.format("Current Quantity of %s is %d \n\n", tool.getToolName(), tool.getQty());
	}

	/**
	 * Decrease qty of item in inventory by 1 or by user input value.
	 * 
	 * @param input Item name or ID.
	 * @param qty   Quantity to reduce by.
	 */
	public void decreaseItem(String input, String qty) {
		Item tool = returnItem(input);
		if (tool == null) {
			System.out.println("Item not in DB\n");
			return;
		}
		if (qty.compareTo("nullEnter") == 0) { // set in sanitize input.
			qty = "1";
		}
		if (tool.getQty() - Integer.parseInt(qty) >= 0) {
			System.out.format("Updated Qty of %s: %d -> %d \n\n", tool.getToolName(), tool.getQty(),
					tool.getQty() - Integer.parseInt(qty));
			tool.setQty(tool.getQty() - Integer.parseInt(qty));
		} else {
			System.out.format("Too few in stock to reduce by %s. \n", qty);
			this.checkQty(input);

		}
	}

	/**
	 * Prints item information of item in database based on passed item name.
	 * 
	 * @param itemName Tool name of item to be found.
	 */
	public void searchByName(String itemName) {

		Iterator<Item> itr = this.itemList.iterator();

		while (itr.hasNext()) {
			Item tool = itr.next();
			if (tool.getToolName().toLowerCase().strip().compareTo(itemName.toLowerCase().strip()) == 0) {
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
			if (tool.getToolID() == intID) {
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
				if (tool.getToolID() == intID) {
					return tool;
				}
			} else if (tool.getToolName().toLowerCase().strip().compareTo(input.toLowerCase().strip()) == 0) {
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
	public int generateOrderID(String input) {
		LocalDate now = LocalDate.now();
		LocalDate epoch = LocalDate.ofEpochDay(0);
		long dateHash = ChronoUnit.DAYS.between(epoch, now);
		final int prime = 31;
		int result = 1;
		result = prime * result + ((int) dateHash);
		result = prime * result + (int) (this.returnItem(input).getToolID() ^ 2);
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
