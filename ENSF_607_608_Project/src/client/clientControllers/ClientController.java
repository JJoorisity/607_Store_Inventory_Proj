package client.clientControllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import exercise5.GameView;

public class ClientController {
		
		private Socket aSocket;
		private ObjectInputStream clientIn;
		private ObjectOutputStream clientOut;
		
		public GameClient (String serverName, int portNumber) {
			try {
				aSocket = new Socket (serverName, portNumber);					// initialize client socket
				socketIn = new BufferedReader(
						new InputStreamReader(aSocket.getInputStream()));		// Server receiving stream
				socketOut = new PrintWriter(aSocket.getOutputStream(), true);	// Server sending stream
			} catch (UnknownHostException uhExc) {
				System.err.println("Server host was not found.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Open communication with the server sockets.
		 */
		public void communicate(GameView theView) {
			String response = "";
			// Game running
			while (true) {
				try {
					response = socketIn.readLine();
					if (response.contains("B:"))
						theView.updateButtons(response.substring(2));
					else if (response.contains("M:"))
						theView.setMarker(response.substring(2));
					else if (!response.isEmpty())
						theView.setMessage(response);
					
					// Game is over
					if (response.toString().contains("OVER"))
						break;
					response = "";
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			close();
		}
		
		/**
		 * Set the name of the client to send to the server.
		 * @param name (String) the name of the user.
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Set the row of the button selected and send to the server.
		 * @param row (int) the row value of the button selected.
		 */
		public void setRow_Col(String row_col) {
			this.row_col = row_col;
		}
		
		/**
		 * Send name to server, once name is entered.
		 */
		public void sendName() {
			socketOut.println(name);
		}
		
		/**
		 * Send row and column to server, once name is entered.
		 */
		public void sendButton() {
			socketOut.println(row_col);
		}
		
		/**
		 * Close connections to the command line and server.
		 */
		public void close() {
			try {
				socketIn.close();
				socketOut.close();
			} catch (IOException e) {
				System.out.println("Closing error: " + e.getMessage());
			}
		}
	}
	
}
