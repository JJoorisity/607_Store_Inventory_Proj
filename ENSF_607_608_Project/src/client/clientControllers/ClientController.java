package client.clientControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {

	private ShopClient shopClient;
	private CmsController customerController;
	private ImsController inventoryController;
	

	public ClientController(ShopClient shopClient) {
		this.shopClient = shopClient;
		customerController = new CmsController();
		inventoryController = new ImsController();
		this.shopClient.communicate();

		// construct gui. pass actionlisters as required.
	}
	private class Action implements ActionListener {

		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			e.getSource();
			// check which source triggered the event
			// call method from shop client for each potential send to server.
			// 
		}
		
	}
	
}
