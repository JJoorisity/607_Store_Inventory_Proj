package sharedModel;

public class Item_Elec extends Item {

	private static final long serialVersionUID = 2L;
	private String powerType;
	private int voltage;
	private int phase;

	public Item_Elec(int itemID, char itemType, String itemDesc, int qty, double price, int supplierID, String ptype,
			int volts, int phase) {
		super(itemID, itemType, itemDesc, qty, price, supplierID);
		this.setPowerType(ptype);
		this.setVoltage(volts);
		this.setPhase(phase);
	}
	
	public Item_Elec() {
		
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public void setVoltage(int v) {
		this.voltage = v;
	}

	public int getVoltage() {
		return this.voltage;
	}

	public String sqlInsert() {
		return this.itemID + ", " + this.supplierID + ", " + this.itemType + ", " + this.itemDesc + ", " + this.price
				+ ", " + this.qty + ", " + this.powerType + ", " + this.voltage + ", " + this.phase;

	}

}
