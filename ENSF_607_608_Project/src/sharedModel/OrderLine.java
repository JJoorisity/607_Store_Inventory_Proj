package sharedModel;

/**
 * OrderLine class to store the information for a supplier order line in
 * the daily Order.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class OrderLine implements PrintTableConstants{
	private int itemId;
	private int orderQty;
	private String supplierName;

	/**
	 * Constructor.
	 * @param itemId (int) the item ID on the order.
	 * @param qty (int) the quantity of the item being ordered.
	 * @param suppName (String) the name of the supplier providing the item.
	 */
	public OrderLine(int itemId, int qty, String suppName) {
		this.itemId = itemId;
		this.orderQty = qty;
		this.supplierName = suppName;
	}

	/**
	 * @return (int) return the item ID.
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Assign the item ID.
	 * @param itemId (int)
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return (int) the order quantity.
	 */
	public int getOrderQty() {
		return orderQty;
	}

	/**
	 * Set the order quantity for the item.
	 * @param orderQty (int)
	 */
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	/**
	 * @return (String) get the supplier name of the item.
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * Set the supplier of the item.
	 * @param supplierName (String) new supplier name.
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * Create a formatted string print out of the order line to text.
	 * @param item (Item) the item belonging to the orderline.
	 * @return (String) print out of the order line.
	 */
	public String printOrderLine(Item item) {
		String res = "";

		res += String.format(STRALIGNFORMAT, "Item Description:", item.getItemDesc());
		res += String.format(VALUEALIGNFORMAT, "Amount Ordered:", (this.orderQty));
		res += String.format(STRALIGNFORMAT, "Supplier:", this.getSupplierName());
		res += TABLEBREAK;
		return res;
	}

}
