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
 *        Description: Supplier object generated for supplierlist. 
 */
public class Supplier {

	private String supplierID;
	private String companyName;
	private String address;
	private String salesContact;

	/**
	 * Constructor, requires Suppliers to be fully defined upon creation
	 * 
	 * @param supplierID
	 * @param companyName
	 * @param address
	 * @param salesContact
	 */
	public Supplier(String supplierID, String companyName, String address, String salesContact) {
		this.setSupplierID(supplierID);
		this.setCompanyName(companyName);
		this.setAddress(address);
		this.setSalesContact(salesContact);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSalesContact() {
		return salesContact;
	}

	public void setSalesContact(String salesContact) {
		this.salesContact = salesContact;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * {@inheritDoc} To string returns a line representation of the item buffered by
	 * "|"
	 */
	@Override
	public String toString() {

		String leftAlignFormat = "| %-15s | %-7d | %-8d | %-9.2f |%n";
		String res = String.format(leftAlignFormat, this.getSupplierID(), this.getCompanyName(), this.getAddress(),
				this.getSalesContact());
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

		if (!(obj instanceof Supplier))
			flag = false;
		if (obj instanceof Supplier)
			if (((Supplier) obj).getSupplierID() == this.getSupplierID()) {
				flag = true;
			} else {
				flag = false;
			}
		return flag;
	}

	/**
	 * {@inheritDoc} ensures item objects are compared using their item ID's only
	 * and not as a hash of the obj itself
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getSupplierID());

	}
}
