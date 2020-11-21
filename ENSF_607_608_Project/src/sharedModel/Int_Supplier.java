package sharedModel;

public class Int_Supplier extends Supplier{

	double importTax;
	
	public Int_Supplier(int supplierID, char supplierType, String companyName, String address, String salesContact, double importTax) {
		super(supplierID, supplierType, companyName, address,  salesContact);
		this.setImportTax(importTax);
	}

	public double getImportTax() {
		return importTax;
	}

	public void setImportTax(double importTax) {
		this.importTax = importTax;
	}

}
