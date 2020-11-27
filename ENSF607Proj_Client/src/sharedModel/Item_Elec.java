package sharedModel;

/**
 * Electrical Item, subclass of regular item/tool.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class Item_Elec extends Item {

	private static final long serialVersionUID = 2L;
	private String powerType;
	private int voltage;
	private int phase;

	/**
	 * Constructor
	 * 
	 * @param itemID     (Integer) itemID from txt file
	 * @param itemName   (String) item name from txt file
	 * @param qty        (Integer qty from txt file
	 * @param price      (Double) price from txt file
	 * @param supplierID (String) of numerical supplier id
	 * @param ptype		 (String) of powertype (AC/DC)
	 * @param volts		 (Integer required tool voltage (120/240 etc)
	 * @param phase		 (Integer required electrical phase (1/2/3) for tool operation
	 */
	public Item_Elec(int itemID, char itemType, String itemDesc, int qty, double price, int supplierID, String ptype,
			int volts, int phase) {
		super(itemID, itemType, itemDesc, qty, price, supplierID);
		this.setPowerType(ptype);
		this.setVoltage(volts);
		this.setPhase(phase);
	}
	
	/**
	 * Default constructor for cases where an ObjectWrapper needs to query by a single parameter.
	 */
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

}
