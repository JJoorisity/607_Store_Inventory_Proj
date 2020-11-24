package client.clientControllers;

import client.clientViews.CmsApplication;

public class CmsController {

	private CmsApplication app;

	public CmsController() {
		app = new CmsApplication();
	}

	public CmsApplication getApp() {
		return app;
	}

	public void setApp(CmsApplication cms) {
		this.app = cms;
	}

}
