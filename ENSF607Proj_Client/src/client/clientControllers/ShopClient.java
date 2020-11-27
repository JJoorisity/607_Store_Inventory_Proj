package client.clientControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import sharedModel.Commands;
import sharedModel.ObjectWrapper;

/**
 * Main interaction point between the client controller and the server sockets.
 * Contains main communication loop responsible for sending and receiving input
 * from the server. Send and receive operations use an object wrapper and a
 * distinct list of commands (through implemented interface) to tell the server
 * what command to execute and what object to execute that command with.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ShopClient implements Commands {

	private Socket aSocket;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;
	private ClientController clientController;

	public ShopClient(String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			// initialize client socket
			clientOut = new ObjectOutputStream(aSocket.getOutputStream()); // Server sending stream
			clientIn = new ObjectInputStream(aSocket.getInputStream()); // Server receiving stream

		} catch (UnknownHostException uhExc) {
			System.err.println("Server host was not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open communication with the server sockets.
	 */
	public void communicate() {
		ObjectWrapper answer = new ObjectWrapper();

		// client running
		while (true) {
			try {
				answer = (ObjectWrapper) clientIn.readObject(); // wait for server response
				String command = answer.getMessage()[0]; // get command from server
				if (answer != null && !command.equals("")) {
					System.out.println("command : " + command);

					switch (command) {
					case COMPLETE: {
						System.out.println("Action Completed");
						break;
					}
					case FAILED: {
						System.out.println("Action Failed");
						break;
					}
					case DISPLAY: {// trigger search display
						this.clientController.getCmsController().updateSearchResults(answer.getPassedObj());
						break;
					}
					case DISPLAYEDIT: {// trigger edit display
						this.clientController.getCmsController().updateCustInfoPane(answer.getPassedObj());
						break;
					}
					case DISPLAYITEM: {
						this.clientController.getImsController().updateSearchResults(answer.getPassedObj());
						break;
					}
					case PURCHASE: {
						this.clientController.getImsController().updatePurchaseField(answer.getMessage()[1]);
						this.clientController.getImsController().executeSearchAll();
						break;
					}
					}

				} else if (command.contentEquals("QUIT")) {
					break;
				}
				answer.resetWrapper();

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.close();
	}

	/**
	 * Sends a wrapped object to the server. Waits for response from server before continuing in the communication loop above.
	 * @param request (ObjectWrapper) object containing the request and object to be sent to the server.
	 */
	public void triggerOutput(ObjectWrapper request) {

		// send object wrapper with command
		try {
			clientOut.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			clientIn.close();
			clientOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}

	public void setController(ClientController clientController) {
		this.clientController = clientController;
	}

}
