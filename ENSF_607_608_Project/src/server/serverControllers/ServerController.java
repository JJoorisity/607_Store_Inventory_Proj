package server.serverControllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class to run shop inventory server
 * Client classes.
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
	private ModelController modelController;
		
	/**
	 * Initialize the serverSocket and thread pool.
	 */
	public ServerController() {
		try {
			serverSocket = new ServerSocket(8088);
			pool = Executors.newFixedThreadPool(10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Run server and allow communication with client package.
	 * Initialize a modelController.
	 */
	public void runServer() {
		try {
			while (true) {
				clientSocket = serverSocket.accept();
				System.out.println("Server has accepted a connection.");
				clientIn = new ObjectInputStream(clientSocket.getInputStream());
				clientOut = new ObjectOutputStream(clientSocket.getOutputStream());

				ModelController newShop = new ModelController(clientIn, clientOut);

				pool.execute(newShop);
				System.out.println("Shop model active.");
			}
		} catch (IOException e) {
			System.err.println(e.getStackTrace() + " Server connection failed in runServer().");
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
	
	public static void main (String [] args) throws IOException{
		ServerController myServer = new ServerController ();
		myServer.runServer();
	}
}
