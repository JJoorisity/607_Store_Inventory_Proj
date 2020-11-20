package server.serverModel;
import sharedModel.*;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Exercise 1 Code
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 9th, 2020
 * 
 *        Sources: Code requirements from assignment 
 *        
 *        Description: Manages file IO between shop app and DB init files. 
 */
public class FileManager {

	private String[][] data;
	private String filenameItems;
	private String filenameSuppliers;

	public FileManager(String Items, String Sups) {
		this.setFilenameItems(Items);
		this.setFilenameSuppliers(Sups);
	}

	/**
	 * loadsData from txt file into obj as arrays of strings
	 * 
	 * @param filename for file to load from.
	 * @throws IOException throws exception if file not found or unopenable
	 */
	private void loadData(String filename) throws IOException {

		LinkedList<String[]> rows = new LinkedList<String[]>(); // dynamic list size for csv input

		String dataRow;

		FileInputStream inputStream = null;
		Scanner sc = null;

		try {
			inputStream = new FileInputStream(filename);
			sc = new Scanner(inputStream, "UTF-8");

			while (sc.hasNextLine()) {
				dataRow = (sc.nextLine());
				rows.addLast(dataRow.split(";"));

			}
			// close input streams
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

		this.data = rows.toArray(new String[rows.size()][]);

	}

	/**
	 * Creates set Items based on txt input. Items are hashed based on Item ids.
	 * 
	 * @return LinkedHashSet of Item objects from array of strings created in
	 *         loadData.
	 * @throws IOException if load data fails
	 * @see Item Hashcode() and equals() Override
	 */
	public LinkedHashSet<Item> getItems() throws IOException {

		this.loadData(this.filenameItems);
		LinkedHashSet<Item> itemList = new LinkedHashSet<Item>();

		for (String[] item : this.data) {

			Item temp = new Item(Integer.parseInt(item[0]), item[1], Integer.parseInt(item[2]),
					Double.parseDouble(item[3]), item[4]);
			itemList.add(temp);
		}
		return itemList;
	}

	/**
	 * Creates Set of Suppliers from txt input. Suppliers are hashed based in supplier ID
	 * @return LinkedHashSet of Supplier objects from array of strings created in
	 *         loadData.
	 * @throws IOException  if load data fails
	 * @see Supplier hashcode() and equals() Override
	 */
	public LinkedHashSet<Supplier> getSupplierList() throws IOException {

		this.loadData(this.filenameSuppliers);
		LinkedHashSet<Supplier> SupplierList = new LinkedHashSet<Supplier>();

		for (String[] sup : this.data) {

			Supplier temp = new Supplier(sup[0], sup[1], sup[2], sup[3]);
			SupplierList.add(temp);
		}
		return SupplierList;
	}

	public void setFilenameItems(String filename) {
		this.filenameItems = filename;
	}

	public void setFilenameSuppliers(String filename) {
		this.filenameSuppliers = filename;
	}

}
