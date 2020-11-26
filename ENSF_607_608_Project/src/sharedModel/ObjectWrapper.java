package sharedModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object wrapper class to send objects between the client and server.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ObjectWrapper implements Serializable {

	private static final long serialVersionUID = 3L;
	private String[] message = new String[2];
	private ArrayList<Object> passedObj = new ArrayList<Object>();

	/**
	 * Default constructor.
	 */
	public ObjectWrapper() {
	}

	/**
	 * @return (String[]) message between client and server of object type and command.
	 */
	public String[] getMessage() {
		return message;
	}
	
	/**
	 * Set the message between the client and server.
	 * @param command (String) command to trigger receiving class's cases.
	 * (ex. update, delete, purchase, search)
	 * @param objectType (String) textual representation of class name passed.
	 */
	public void setMessage(String command, String objectType) {
		this.message[0] = command;
		this.message[1] = objectType;
	}

	/**
	 * Retrieve object in object wrapper arraylist.
	 * @param i (int) index of object being retrieved.
	 * @return (Object) the object being retrieved.
	 */
	public Object getPassedObj(int i) {
		return passedObj.get(i);
	}

	/**
	 * Retrieve all objects in object wrapper arraylist.
	 * @return (ArrayList<Object>) list of objects.
	 */
	public ArrayList<Object> getPassedObj() {
		return passedObj;
	}

	/**
	 * Add object collection to object wrapper arraylist.
	 * @param passedObj (ArrayList<Object>) collection of objects.
	 */
	public void addPassedObj(ArrayList<Object> passedObj) {
		this.passedObj.addAll(passedObj);
	}

	/**
	 * Add object to object wrapper arraylist.
	 * @param passedObj (Object) object to add.
	 */
	public void addPassedObj(Object passedObj) {
		this.passedObj.add(passedObj);
	}

	/**
	 * Clear contents of object wrapper.
	 */
	public void resetWrapper() {
		this.message[0] = "";
		this.message[1] = "";
		passedObj.clear();
	}

}
