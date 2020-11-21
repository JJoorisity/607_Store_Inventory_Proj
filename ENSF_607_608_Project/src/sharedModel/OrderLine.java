package sharedModel;

public class OrderLine {
	private Item item;
	private int orderQty;

	public OrderLine(Item item, int qty) {
		this.item = item;
		this.orderQty = qty;
	}
	
	public String printOrderLine() {
		String valueAlignFormat = "| %-20s  %-27d |%n";
		String stringAlignFormat = "| %-20s  %-27s |%n";
		String res = "";
		
		res += String.format(stringAlignFormat, "Item Description:", this.item.getItemDesc());
		res += String.format(valueAlignFormat, "Amount Ordered:", (this.orderQty));
		res += String.format(stringAlignFormat, "Supplier:", this.item.getSupplierID());
		res += "+*****************+*********+**********+***********+*";
		return res;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
	
	
	
	
	
}
