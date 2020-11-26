package sharedModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Customer holds all customer information. Implements Serializable so it can be
 * sent between server and client. Implements PrintTableConstants to maintain
 * cohesion between class toString() methods. Overrides equals and hashcode to
 * ensure objects can be stored in a linkedhashset without error or duplication.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class Customer implements Serializable, PrintTableConstants {

	private static final long serialVersionUID = 1L;
	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phoneNum;
	private char customerType;

	/**
	 * Constructor
	 * 
	 * @param id      (Integer) unique customer ID
	 * @param first   (String) customer first name
	 * @param last    (String) customer last name
	 * @param address (String) customer address
	 * @param pc      (String) customer postal code
	 * @param pn      (String) customer phone number
	 * @param type    (char) customer Type (residential or commercial (R/C)
	 */
	public Customer(int id, String first, String last, String address, String pc, String pn, char type) {
		this.setCustomerId(id);
		this.setFirstName(first);
		this.setLastName(last);
		this.setAddress(address);
		this.setPostalCode(pc);
		this.setPhoneNum(pn);
		this.setCustomerType(type);
	}

	/**
	 * default constructor for instances where an object wrapper needs to send a
	 * search query with only one customer attribute. (e.g. search by ID requires a
	 * customer object with an ID but no other information to perform the query)
	 */
	public Customer() {
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public char getCustomerType() {
		return customerType;
	}

	public void setCustomerType(char customerType) {
		this.customerType = customerType;
	}

	@Override
	public String toString() {

		String res = "";
		res += "Name: " + this.getFirstName() + " " + this.getLastName() + " | Customer ID: " + this.getCustomerId()
				+ " | Customer Type: " + this.getCustomerType() + "\n";
		// res += TABLEBREAK;
		return res;

	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;

		if (!(obj instanceof Customer))
			flag = false;
		if (obj instanceof Customer)
			if (((Customer) obj).getCustomerId() == this.getCustomerId()) {
				flag = true;
			} else {
				flag = false;
			}
		return flag;
	}

	/**
	 * {@inheritDoc} ensures item objects are compared using their customer ID's
	 * only and not as a hash of the object itself
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getCustomerId());
	}
}
