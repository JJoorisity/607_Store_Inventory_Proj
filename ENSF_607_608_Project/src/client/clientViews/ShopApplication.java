package client.clientViews;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import client.clientControllers.CmsController;
import client.clientControllers.ImsController;

public class ShopApplication {

	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Launch the application.
	 */
	public void startGui(ShopApplication shop) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					shop.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShopApplication(CmsApplication cms, ImsApplication ims) {
		initialize(cms, ims);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(CmsApplication cms, ImsApplication ims) {
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Inventory", null, ims.getImsFrame(), null);
		tabbedPane.addTab("Customers", null, cms.getCmsFrame(), null);
	}

}
