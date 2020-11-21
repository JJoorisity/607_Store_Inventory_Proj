package sharedModel;

public class OrderLine {
	private int itemId;
	private int orderQty;

	public OrderLine(int itemId, int qty) {
		this.itemId = itemId;
		this.orderQty = qty;
	}
	
	public String printOrderLine(Item item) {
		String valueAlignFormat = "| %-20s  %-27d |%n";
		String stringAlignFormat = "| %-20s  %-27s |%n";
		String res = "";
		
		res += String.format(stringAlignFormat, "Item Description:", item.getItemDesc());
		res += String.format(valueAlignFormat, "Amount Ordered:", (this.orderQty));
		res += String.format(stringAlignFormat, "Supplier:", item.getSupplierID());
		res += "+*****************+*********+**********+***********+*";
		return res;
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
	
	
	
	
	
}
