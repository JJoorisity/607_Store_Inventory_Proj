package client.clientViews;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class ShopApplication {

	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel CmsApplication = new JPanel();
	private final JPanel panel = new JPanel();
	private final JSplitPane splitPane_right = new JSplitPane();
	private final JSplitPane splitPane_left = new JSplitPane();
	private final JPanel panel_SearchResults = new JPanel();
	private final JPanel panel_SearchCust = new JPanel();
	private final JPanel panel_CustInfo = new JPanel();
	private final JLabel searchResultsLbl = new JLabel("Search Results:");
	private final JLabel selectSearchLbl = new JLabel("Select type of search to be performed:");
	private final JRadioButton custtIdBttn = new JRadioButton("Customer ID");
	private final JRadioButton custTypeBttn = new JRadioButton("Customer Type");
	private final JRadioButton lastNameBttn = new JRadioButton("Customer Last Name");
	private final JLabel searchParamLbl = new JLabel("Enter search parameters:");
	private final JLabel searchCustLbl = new JLabel("Search Clients");
	private final JPanel panel_2 = new JPanel();
	private final JTextField textField = new JTextField();
	private final JButton searchCust = new JButton("Search");
	private final JButton clearSearchCust = new JButton("Clear Search");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblNewLabel = new JLabel("Customer Information");
	private final JPanel panel_custInfo = new JPanel();
	private final JButton saveCustBtn = new JButton("Save");
	private final JButton deleteCustBtn = new JButton("Delete");
	private final JButton clearCustBtn = new JButton("Clear");
	private final JLabel clientIdLbl = new JLabel("Client ID:");
	private final JTextField clientIdTxt = new JTextField();
	private final JTextField fNameTxt = new JTextField();
	private final JTextField pcTxt = new JTextField();
	private final JTextField lNameTxt = new JTextField();
	private final JTextField addressTxt = new JTextField();
	private final JPanel panel_custInfoBtn = new JPanel();
	private final JTextField pnTxt = new JTextField();
	private final JLabel fNameLbl = new JLabel("First Name:");
	private final JLabel lNameLbl = new JLabel("Last Name:");
	private final JLabel addressLbl = new JLabel("Address:");
	private final JLabel pcLbl = new JLabel("Postal Code:");
	private final JLabel pnLbl = new JLabel("Phone Number:");
	private final JLabel cTypeLbl = new JLabel("Customer Type:");
	private final JComboBox<String> cTypeCBox = new JComboBox<String>();
	private final JPanel ImsApplication = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopApplication window = new ShopApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShopApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Inventory", null, ImsApplication, null);
		
		tabbedPane.addTab("Customers", null, new CmsApplication().getCmsApplication(), null);
		
		
	}

}
