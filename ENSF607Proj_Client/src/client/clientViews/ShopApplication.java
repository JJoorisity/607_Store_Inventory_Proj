package client.clientViews;

import javax.swing.*;
import java.awt.*;

/**
 * Generate the shop application by combining both the ImsApplication and 
 * CmsApplication class frames into separate tabs of the ShopApplication JFrame.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-26
 */
public class ShopApplication {

	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	/**
	 * Create the shop application.
	 */
	public ShopApplication(CmsApplication cms, ImsApplication ims) {
		initialize(cms, ims);
	}

	/**
	 * Launch the GUI application.
	 * @param shop (ShopApplication) passes itself to use the 
	 * eventQueue runnable method.
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
	 * @return (JFrame) returns the shop JFrame.
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Initialize the contents of the GUI tabbed frames.
	 * @param cms (CmsApplication) the customer frame.
	 * @param ims (ImsApplication) the inventory frame.
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
