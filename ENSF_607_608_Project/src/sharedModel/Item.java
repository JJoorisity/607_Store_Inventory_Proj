package sharedModel;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Objects;

/**
 * Exercise 1 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 9th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: Item object that holds all information related to the
 *        individual tool.
 */
public class Item implements PrintTableConstants, Serializable{

	private static final long serialVersionUID = 2L;
	protected int itemID;
	protected char itemType;
	protected String itemDesc;
	protected int qty;
	protected double price;
	protected int supplierID;
	public final int ORDERQTYLIMIT = 40;

	/**
	 * Constructor, requires all inputs to be initialized
	 * 
	 * @param itemID     integer itemID from txt file
	 * @param itemName   String item name from txt file
	 * @param qty        integer qty from txt file
	 * @param price      double price from txt file
	 * @param supplierID String of numerical supplier id
	 */
	public Item(int itemID, char itemType, String itemDesc, int qty, double price, int supplierID) {
		this.setItemID(itemID);
		this.setItemType(itemType);
		this.setItemDesc(itemDesc);
		this.setQty(qty);
		this.setPrice(price);
		this.setSupplierID(supplierID);

	}
	
	public Item() {
		
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSupplierID() {
		return this.supplierID;
	}

	public void setSupplierID(int supplier) {
		this.supplierID = supplier;
	}

	public char getItemType() {
		return itemType;
	}

	public void setItemType(char itemType) {
		this.itemType = itemType;
	}

	/**
	 * {@inheritDoc} To string returns a line representation of the item buffered by
	 * "|"
	 */
	@Override
	public String toString() {
		String res = String.valueOf(getItemID()) + "," + this.getItemDesc() + "," + String.valueOf(this.getQty()) + "," + 
				String.valueOf(this.getPrice());
		return res;

	}

	/**
	 * {@inheritDoc} ensures item objects are compared using their item ID's only
	 * and not as a hash of all vars
	 * 
	 * @return boolean t/f based on ItemId integer comparison
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;

		if (!(obj instanceof Item))
			flag = false;
		if (obj instanceof Item)
			if (((Item) obj).getItemID() == this.getItemID()) {
				flag = true;
			} else {
				flag = false;
			}
		return flag;
	}

	/**
	 * {@inheritDoc} ensures item objects are compared using their item ID's only
	 * and not as a hash of the obj itselg
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getItemID());
	}

}
