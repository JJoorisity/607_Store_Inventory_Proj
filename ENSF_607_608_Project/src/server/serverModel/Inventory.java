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

	public Inventory() {
		this.itemList = new LinkedHashSet<Item>();
	}

	public void setItemList(LinkedHashSet<Item> i) {
		this.itemList = i;
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
