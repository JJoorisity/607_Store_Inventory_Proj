package client.clientControllers;

import client.clientViews.CmsApplication;
import client.clientViews.ImsApplication;
import server.serverModel.Inventory;

public class ImsController {

	private ImsApplication app;
	private Inventory inventory = new Inventory();
	
	public ImsController() {
		this.app = new ImsApplication();
	}

	public ImsApplication getApp() {
		return this.app;
	}
	public void setApp(ImsApplication ims) {
		this.app = ims;
	}
	
	
}
