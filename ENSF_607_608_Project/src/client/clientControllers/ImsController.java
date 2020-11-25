package client.clientControllers;

import client.clientViews.CmsApplication;
import client.clientViews.ImsApplication;
import server.serverModel.Inventory;

public class ImsController {

	private ImsApplication app;
	private ClientController cc;
	
	public ImsController(ClientController cc) {
		this.app = new ImsApplication();
		this.cc = cc;
	}

	public ImsApplication getApp() {
		return this.app;
	}
	
	public void setApp(ImsApplication ims) {
		this.app = ims;
	}
	
	public ClientController getCController() {
		return this.cc;
	}
	
}
