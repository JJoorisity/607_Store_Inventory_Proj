package sharedModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String[] message = new String[2];
	private ArrayList<Object> passedObj = new ArrayList<Object>();
	
	public ObjectWrapper() {
	}

	public String[] getMessage() {
		return message;
	}

	// commands: update, delete, purchase, search
	// objectType: Customer, Item_Elec, Item
	public void setMessage(String command, String objectType) {
		this.message[0] = command;
		this.message[1] = objectType;
	}

	public Object getPassedObj(int i) {
		return passedObj.get(i);
	}

	public void addPassedObj(ArrayList<Object> passedObj) {
		this.passedObj.addAll(passedObj);
	}
	
	public void addPassedObj(Object passedObj) {
		this.passedObj.add(passedObj);
	}
	
	public void resetWrapper() {
		this.message[0] = "";
		this.message[1] = "";
		passedObj.clear();
	}

}
