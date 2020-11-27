package sharedModel;

import java.util.Objects;

/**
 * Supplier class to store all relevant supplier information for the shop suppliers.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
/**
 * @author jorda
 *
 */
public class Supplier {

	private int supplierID;
	private char supplierType;
	private String companyName;
	private String address;
	private String salesContact;

	/**
	 * Constructor, requires Suppliers to be fully defined upon creation.
	 * @param supplierID (int) unique supplier ID.
	 * @param supplierType (char) the type of supplier: L or I
	 * @param companyName (String) name of company.
	 * @param address (String) company address.
	 * @param salesContact (String) name of company sales contact.
	 */
	public Supplier(int supplierID, char supplierType, String companyName, String address, String salesContact) {
		this.setSupplierID(supplierID);
		this.setSupplierType(supplierType);
		this.setCompanyName(companyName);
		this.setAddress(address);
		this.setSalesContact(salesContact);
	}

	/**
	 * @return (String) return address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the company address.
	 * @param address (String)
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return (String) return name of sales contact.
	 */
	public String getSalesContact() {
		return salesContact;
	}

	/**
	 * Set the name of the sales contact.
	 * @param salesContact (String)
	 */
	public void setSalesContact(String salesContact) {
		this.salesContact = salesContact;
	}

	/**
	 * @return (int) return supplier ID.
	 */
	public int getSupplierID() {
		return supplierID;
	}

	/**
	 * Set the supplier ID.
	 * @param supplierID2 (int)
	 */
	public void setSupplierID(int supplierID2) {
		this.supplierID = supplierID2;
	}

	/**
	 * @return (String) return company name.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Set the company name.
	 * @param companyName (String)
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return (char) return supplier type.
	 */
	public char getSupplierType() {
		return supplierType;
	}

	/**
	 * Set local or international.
	 * @param supplierType (char)
	 */
	public void setSupplierType(char supplierType) {
		this.supplierType = supplierType;
	}

	/**
	 * Formate the textual print out of the supplier information to the GUI.
	 */
	@Override
	public String toString() {

		String leftAlignFormat = "| %-15s | %-7d | %-8d | %-9.2f |%n";
		String res = String.format(leftAlignFormat, this.getSupplierID(), this.getCompanyName(), this.getAddress(),
				this.getSalesContact());
		return res;
	}
}
