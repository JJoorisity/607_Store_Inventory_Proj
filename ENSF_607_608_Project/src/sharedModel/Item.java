package sharedModel;

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
public class Item {

	private int toolID;
	private String toolName;
	private int qty;
	private double price;
	private String supplier;
	private static final int ORDERQTYLIMIT = 40;

	/**
	 * Constructor, requires all inputs to be initialized
	 * 
	 * @param toolID integer tool ID from txt file
	 * @param toolName String tool name from txt file 
	 * @param qty integer qty from txt file
	 * @param price double price from txt file
	 * @param supplierID String of numerical supplier id
	 */
	public Item(int toolID, String toolName, int qty, double price, String supplierID) {
		this.setToolID(toolID);
		this.setToolName(toolName);
		this.setQty(qty);
		this.setPrice(price);
		this.setSupplierID(supplierID);
	}

	public int getToolID() {
		return toolID;
	}

	public void setToolID(int toolID) {
		this.toolID = toolID;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
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

	public String getSupplierID() {
		return this.supplier;
	}

	public void setSupplierID(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * {@inheritDoc} To string returns a line representation of the item buffered by
	 * "|"
	 */
	@Override
	public String toString() {

		String leftAlignFormat = "| %-15s | %-7d | %-8d | %-9.2f |";
		String res = String.format(leftAlignFormat, this.getToolName(), this.getToolID(), this.getQty(),
				this.getPrice());
		return res;

	}

	/**
	 * {@inheritDoc} ensures item objects are compared using their item ID's only
	 * and not as a hash of all vars
	 * 
	 * @return boolean t/f based on toolID integer comparison
	 */
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;

		if (!(obj instanceof Item))
			flag = false;
		if (obj instanceof Item)
			if (((Item) obj).getToolID() == this.getToolID()) {
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
		return Objects.hashCode(this.getToolID());
	}

	public static int getOrderqtylimit() {
		return ORDERQTYLIMIT;
	}

}
