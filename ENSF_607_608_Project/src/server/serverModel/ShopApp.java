package server.serverModel;

import java.io.IOException;
import java.util.*;

import sharedModel.Order;
import sharedModel.Supplier;


/**
 * Exercise 1 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 9th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: FrontEnd user interface. Handles all interactions with
 *        uses. Auto Generates an order if check quantity or decrease item
 *        reads a qty less than 40 as required by assignment details
 */
public class ShopApp {

	private Inventory inventory;
	private LinkedHashSet<Supplier> supplierList;
	private HashMap<Integer, Order> orderlist = new HashMap<Integer, Order>();
	private FileManager fileMgr;

	public ShopApp() throws IOException {
		this.fileMgr = new FileManager("items.txt", "suppliers.txt");
		this.inventory = new Inventory();
	}

	/**
	 * Prints menu to screen and handles user input and calling desired functions
	 */
	public void Menu() {

		Scanner scan = new Scanner(System.in);

		while (true) {

			this.printMenuChoices();

			String choice = null;
			while (choice == null) {
				choice = this.sanitizeInput(scan.nextLine(), "menu");
			}

			switch (choice) {
			case "1": { // list all items
				this.inventory.listAllItems();
				break;
			}
			case "2": { // Search by name
				System.out.println("Enter Tool Name >> ");
				this.inventory.searchByName((scan.nextLine()));
				break;
			}
			case "3": { // search by id
				System.out.println("Enter Tool ID >> ");
				String input = null;
				while (input == null) {
					input = this.sanitizeInput(scan.nextLine(), "");
				}
				this.inventory.searchByID(input);
				break;

			}
			case "4": { // check item qty (auto order if below defined limit)
				System.out.println("Check Quantity Item by ID or Name >> ");
				String input = (scan.nextLine());
				this.inventory.checkQty(input);
				if (this.inventory.returnItem(input).getQty() < Item.getOrderqtylimit()) {
					boolean flag = this.generateOrder(input);
					if (flag == true) {
						this.printOrderList();
					}
				}
				break;
			}
			case "5": { // decrease item quantity (auto order if below defined limit)
				System.out.println("Decrease stock of an item. Enter an ID or Name >> ");
				String input = scan.nextLine();
				System.out.println("Enter value to decrease by or hit enter to decrease by one >> ");
				String qty = this.sanitizeInput(scan.nextLine(), "");
				if (qty != null) {
					this.inventory.decreaseItem(input, qty);
					if (this.inventory.returnItem(input).getQty() < Item.getOrderqtylimit()) {
						boolean flag = this.generateOrder(input); // if this is the first time this item has been
																	// ordered today print the current order list.
						if (flag == true) {
							this.printOrderList();
						}
					}
				}
				break;

			}
			case "6": { // print current order list for the day.
				this.printOrderList();
				break;
			}
			case "7": {
				scan.close();
				return;
			}

			}

		}
	}

	/**
	 * Prints menu options list for user.
	 */
	private void printMenuChoices() {

		System.out.println("\nWelcome to theShopApp, please select one of the following options: ");
		System.out.format("+-----------------+---------+----------+-----------+%n");
		System.out.println("1. List all items in inventory");
		System.out.println("2. Search for tool by tool name");
		System.out.println("3. Search for tool by tool ID");
		System.out.println("4. Check item quantity");
		System.out.println("5. Decrease item quantity");
		System.out.println("6. Print Current Order List");
		System.out.println("7. Quit");
		System.out.println("\nInput Selection >>");
	}

	/**
	 * Sanitizes user inputs for type. Accepts a string input. If input is numerical
	 * ensures value is 0 or greater. If menu flag is selected ensures input falls
	 * between 1-7.
	 * 
	 * @param s    user input string
	 * @param menu flag for if input is for menu options.
	 * @return returns the string if input is a string. returns null if input does
	 *         not fall within integer requirements.
	 */
	private String sanitizeInput(String s, String menu) {

		String errMsg = s + " Was an invalid input. Please try again >>";
		if (s.isEmpty()) {
			s = "nullEnter";
			return s;
		}
		try {
			int r = Integer.valueOf(s);
			if (r < 0) { // no negative values allowed
				System.out.println(errMsg);
				return null;
			}
			if (menu.compareTo("menu") == 0) {
				if (r > 0 && r < 8) {
					return s;
				} else {
					System.out.println(errMsg);
					return null;
				}
			} else {
				return s;
			}

		} catch (NumberFormatException e) {
			System.out.println(errMsg);
			return null;
		}
	}

	/**
	 * Initializes supplier list
	 * 
	 * @param sList LinkedHashSet of Supplier items
	 */
	private void setSupplierList(LinkedHashSet<Supplier> sList) {
		this.supplierList = sList;
	}

	/**
	 * Returns supplierID from item object based on user input string.
	 * 
	 * @param supplierID
	 * @return
	 */
	private String getSupplierName(String supplierID) {
		Iterator<Supplier> itr = this.supplierList.iterator();
		while (itr.hasNext()) {
			Supplier sup = itr.next();
			if (sup.getSupplierID().strip().compareTo(supplierID.strip()) == 0) {
				return sup.getCompanyName();
			}
		}
		return null;

	}

	/**
	 * Generates Order object from input item name/ID if item has not been ordered
	 * today.
	 * 
	 * @param Input tool name or ID
	 * @return Returns true if item was added to list. Returns false if item has
	 *         already been ordered today.
	 */
	private boolean generateOrder(String input) {
		int Hash = this.inventory.generateOrderID(input);
		if (this.inOrderList(input) == false) { // requires supplier name from supplier list
			Order newOrder = new Order(this.inventory.returnItem(input),
					this.getSupplierName(this.inventory.returnItem(input).getSupplierID()), Hash);
			this.addToOrderList(Hash, newOrder);
			return true;
		} else {
			System.out.println("Item has already been ordered today\n");
			return false;
		}
	}

	/**
	 * Prints current order list.
	 */
	private void printOrderList() {
		System.out.println("Daily Order List:\n");
		System.out.println("+*****************+*********+**********+***********+*");
		this.orderlist.entrySet().forEach(entry -> {
			System.out.println(entry.getValue().getOrderString());
		});
	}

	/**
	 * Adds Order to shop orderlist.
	 * 
	 * @param orderID Unique hash ID Integer generated from tool ID
	 * @param order   Order object
	 */
	private void addToOrderList(int orderID, Order order) {
		this.orderlist.put(orderID, order);
	}

	/**
	 * Checks if tool has been ordered today and is in the list. Allows for tools to
	 * be ordered if days are different.
	 * 
	 * @param input input tool ID or name
	 * @return returns true if item is in list. False otherwise
	 */
	private boolean inOrderList(String input) {
		int Hash = this.inventory.generateOrderID(input);
		if (this.orderlist.containsKey(Hash)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws IOException {

		// Initialize app
		ShopApp s = new ShopApp();

		// set inventory and supplier list
		s.inventory.setItemList(s.fileMgr.getItems());
		s.setSupplierList(s.fileMgr.getSupplierList());

		// run user input menu loop
		s.Menu();

		System.out.println("Quitting");

		return;

	}

}
