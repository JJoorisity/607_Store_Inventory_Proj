package sharedModel;

/**
 * International supplier. Inherits from supplier class. Contains all the same
 * information as supplier as well as an import tax field.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class Int_Supplier extends Supplier {

	double importTax;

	public Int_Supplier(int supplierID, char supplierType, String companyName, String address, String salesContact,
			double importTax) {
		super(supplierID, supplierType, companyName, address, salesContact);
		this.setImportTax(importTax);
	}

	public double getImportTax() {
		return importTax;
	}

	public void setImportTax(double importTax) {
		this.importTax = importTax;
	}

	@Override
	public String toString() {

		String leftAlignFormat = "| %-15s | %-7d | %-8d | %-9.2f | %-9.2f |%n";
		String res = String.format(leftAlignFormat, this.getSupplierID(), this.getCompanyName(), this.getAddress(),
				this.getSalesContact(), this.getImportTax());
		return res;
	}

}
