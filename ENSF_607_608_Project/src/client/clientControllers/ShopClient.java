package client.clientControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import sharedModel.Commands;
import sharedModel.ObjectWrapper;

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
		ObjectWrapper request = new ObjectWrapper();
		ObjectWrapper answer = new ObjectWrapper();

		// client running
		while (true) {
			try {
				// need input from IMS or CMS from gui
				// write command to wrapper to send to server
				// returns wrapper with data requested or triggers print to screen

				// end of client action loop

				answer = (ObjectWrapper) clientIn.readObject(); // wait for server response
				String command = answer.getMessage()[0];
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
					case DISPLAY: // trigger gui response
						this.clientController.getCmsController().updateSearchResults(answer.getPassedObj());
						break;
					}
				} else if (command.contentEquals("QUIT")) { // to be actionlistener from gui
					break;
				}
				request.resetWrapper();
				answer.resetWrapper();

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.close();
	}

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
