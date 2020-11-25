package sharedModel;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable, PrintTableConstants {

	private static final long serialVersionUID = 1L;
	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phoneNum;
	private char customerType;

	public Customer(int id, String first, String last, String address, String pc, String pn, char type) {
		this.setCustomerId(id);
		this.setFirstName(first);
		this.setLastName(last);
		this.setAddress(address);
		this.setPostalCode(pc);
		this.setPhoneNum(pn);
		this.setCustomerType(type);
	}

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
		//res += TABLEBREAK;
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
	 * {@inheritDoc} ensures item objects are compared using their item ID's only
	 * and not as a hash of the obj itselg
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getCustomerId());
	}
}
