import server.serverControllers.DbController;
import server.serverControllers.ModelController;
import server.serverModel.ShopApp;
import sharedModel.*;

public class TEST_PRINTING_TOSTRING {

	public static void main(String[] args) {
		DbController testDB = new DbController();
		testDB.initializeConnection();
//		testDB.resetDatabase();
//		testDB.createTable();
//		testDB.initializeSupplierTable();
//		testDB.initializeItemTable();
//		testDB.initializeCustomerTable();

		ModelController test = new ModelController(testDB, new ShopApp());
		test.getShop().setModelController(test);

		test.initShop();

		//test.getShop().executePurchase(1035, 1200, 5);
		//test.getShop().executePurchase(1002, 10, 5);

		System.out.println(test.getShop().printOrder());
		System.out.println(test.getShop().printItems());

	}

}
