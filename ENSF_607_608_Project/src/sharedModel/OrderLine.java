package sharedModel;

public class OrderLine implements PrintTableConstants{
	private int itemId;
	private int orderQty;
	private String supplierName;

	public OrderLine(int itemId, int qty, String suppName) {
		this.itemId = itemId;
		this.orderQty = qty;
		this.supplierName = suppName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String printOrderLine(Item item) {
		String valueAlignFormat = "| %-20s  %-30d |%n";
		String stringAlignFormat = "| %-20s  %-30s |%n";
		String res = "";

		res += String.format(stringAlignFormat, "Item Description:", item.getItemDesc());
		res += String.format(valueAlignFormat, "Amount Ordered:", (this.orderQty));
		res += String.format(stringAlignFormat, "Supplier:", this.getSupplierName());
		res += TABLEBREAK;
		return res;
	}

}
