package server.serverControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.serverModel.ShopApp;

/**
 * Server class to run shop inventory server Client classes.
 * 
 * @author JJoorisity NJacl
 * @version 1.0
 * @since 2020-11-06
 */
public class ServerController {
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private ObjectInputStream clientIn;
	private ObjectOutputStream clientOut;
	private ExecutorService pool;

	/**
	 * Initialize the serverSocket and thread pool.
	 */
	public ServerController() {
		try {
			serverSocket = new ServerSocket(8088, 0, InetAddress.getByName("localhost"));
			pool = Executors.newFixedThreadPool(10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run server and allow communication with client package. Initialize a
	 * modelController.
	 */
	public void runServer() {

		while (true) {
			try {
				clientSocket = serverSocket.accept();

				System.out.println("Server has accepted a connection.");

				clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
				clientIn = new ObjectInputStream(clientSocket.getInputStream());

				DbController myDB = new DbController();
				myDB.initializeConnection();
				ShopApp myShop = new ShopApp();
				
				ModelController newShop = new ModelController(clientIn, clientOut, myDB, myShop);
				myShop.setModelController(newShop);
				
				pool.execute(newShop);
				System.out.println("Shop model active.");

			} catch (IOException e) {
				System.err.println(e.getStackTrace() + " Server connection failed in runServer().");
			}
		}
	}

	/**
	 * Close connections to the command line and clients.
	 */
	public void close() {
		try {
			clientSocket.close();
			pool.shutdown();
		} catch (IOException e) {
			System.err.println(e.getStackTrace() + " Server connection failed in close().");
		}
	}

	public static void main(String[] args) throws IOException {
		ServerController myServer = new ServerController();
		myServer.runServer();
		myServer.close();
	}
}
