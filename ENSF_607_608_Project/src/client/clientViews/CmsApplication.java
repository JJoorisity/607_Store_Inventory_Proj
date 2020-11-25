package client.clientViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.border.*;

// Need to restrict string lengths.

public class CmsApplication {

	private final JPanel CmsFrame = new JPanel();
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
	private ButtonGroup radioGroup = new ButtonGroup();
	private final JTextArea clientDisplay = new JTextArea();

	/**
	 * Create the application.
	 */
	public CmsApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField.setColumns(10);
		CmsFrame.setBackground(Color.WHITE);
		CmsFrame.setLayout(new BorderLayout(0, 0));
		CmsFrame.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		splitPane_right.setBorder(null);
		splitPane_right.setBackground(Color.WHITE);
		splitPane_right.setDividerSize(3);

		panel.add(splitPane_right, BorderLayout.CENTER);
		splitPane_left.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_left.setDividerSize(3);
		splitPane_left.setBorder(null);

		splitPane_right.setLeftComponent(splitPane_left);
		panel_SearchResults.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_left.setRightComponent(panel_SearchResults);
		panel_SearchResults.setLayout(new BorderLayout(0, 0));
		searchResultsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		searchResultsLbl.setOpaque(true);
		searchResultsLbl.setForeground(new Color(255, 255, 255));
		searchResultsLbl.setBackground(new Color(0, 51, 102));
		searchResultsLbl.setBorder(null);
		panel_SearchResults.add(searchResultsLbl, BorderLayout.NORTH);
		searchResultsLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		scrollPane.setBackground(Color.WHITE);

		panel_SearchResults.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(clientDisplay);
		panel_SearchCust.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_left.setLeftComponent(panel_SearchCust);
		panel_SearchCust.setLayout(new BorderLayout(0, 0));
		searchCustLbl.setOpaque(true);
		searchCustLbl.setForeground(new Color(255, 255, 255));
		searchCustLbl.setBackground(new Color(0, 51, 102));
		searchCustLbl.setBorder(null);
		searchCustLbl.setHorizontalAlignment(SwingConstants.CENTER);
		searchCustLbl.setFont(new Font("Tahoma", Font.BOLD, 15));

		panel_SearchCust.add(searchCustLbl, BorderLayout.NORTH);
		panel_2.setBorder(null);

		panel_SearchCust.add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 30, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panel_2.setLayout(gbl_panel_2);
		GridBagConstraints gbc_selectSearchLbl = new GridBagConstraints();
		gbc_selectSearchLbl.anchor = GridBagConstraints.WEST;
		gbc_selectSearchLbl.insets = new Insets(5, 5, 5, 5);
		gbc_selectSearchLbl.gridx = 0;
		gbc_selectSearchLbl.gridy = 0;
		selectSearchLbl.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(selectSearchLbl, gbc_selectSearchLbl);
		selectSearchLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_searchParamLbl = new GridBagConstraints();
		gbc_searchParamLbl.gridwidth = 2;
		gbc_searchParamLbl.insets = new Insets(5, 0, 5, 5);
		gbc_searchParamLbl.gridx = 2;
		gbc_searchParamLbl.gridy = 0;
		panel_2.add(searchParamLbl, gbc_searchParamLbl);
		searchParamLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_custtIdBttn = new GridBagConstraints();
		gbc_custtIdBttn.anchor = GridBagConstraints.WEST;
		gbc_custtIdBttn.insets = new Insets(0, 10, 5, 5);
		gbc_custtIdBttn.gridx = 0;
		gbc_custtIdBttn.gridy = 1;
		panel_2.add(custtIdBttn, gbc_custtIdBttn);
		custtIdBttn.setHorizontalAlignment(SwingConstants.LEFT);
		custtIdBttn.setFont(new Font("Tahoma", Font.BOLD, 13));

		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);
		GridBagConstraints gbc_custTypeBttn = new GridBagConstraints();
		gbc_custTypeBttn.anchor = GridBagConstraints.WEST;
		gbc_custTypeBttn.insets = new Insets(0, 10, 5, 5);
		gbc_custTypeBttn.gridx = 0;
		gbc_custTypeBttn.gridy = 2;
		panel_2.add(custTypeBttn, gbc_custTypeBttn);
		custTypeBttn.setFont(new Font("Tahoma", Font.BOLD, 13));

		GridBagConstraints gbc_searchCust = new GridBagConstraints();
		gbc_searchCust.insets = new Insets(0, 0, 5, 5);
		gbc_searchCust.gridx = 2;
		gbc_searchCust.gridy = 2;
		searchCust.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(searchCust, gbc_searchCust);

		GridBagConstraints gbc_clearSearchCust = new GridBagConstraints();
		gbc_clearSearchCust.insets = new Insets(0, 0, 5, 0);
		gbc_clearSearchCust.gridx = 3;
		gbc_clearSearchCust.gridy = 2;
		clearSearchCust.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(clearSearchCust, gbc_clearSearchCust);
		GridBagConstraints gbc_lastNameBttn = new GridBagConstraints();
		gbc_lastNameBttn.anchor = GridBagConstraints.WEST;
		gbc_lastNameBttn.insets = new Insets(0, 10, 0, 5);
		gbc_lastNameBttn.gridx = 0;
		gbc_lastNameBttn.gridy = 3;
		panel_2.add(lastNameBttn, gbc_lastNameBttn);
		lastNameBttn.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_CustInfo.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_right.setRightComponent(panel_CustInfo);
		panel_CustInfo.setLayout(new BorderLayout(0, 0));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 51, 102));
		lblNewLabel.setBorder(null);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		panel_CustInfo.add(lblNewLabel, BorderLayout.NORTH);
		panel_custInfo.setBorder(null);

		panel_CustInfo.add(panel_custInfo, BorderLayout.CENTER);
		GridBagLayout gbl_panel_custInfo = new GridBagLayout();
		gbl_panel_custInfo.rowHeights = new int[] { 0, 50, 50, 50, 50, 50, 50 };
		gbl_panel_custInfo.columnWidths = new int[] { 30, 0, 50, 50, 30 };
		gbl_panel_custInfo.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
		gbl_panel_custInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel_custInfo.setLayout(gbl_panel_custInfo);

		GridBagConstraints gbc_clientIdLbl = new GridBagConstraints();
		gbc_clientIdLbl.anchor = GridBagConstraints.EAST;
		gbc_clientIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_clientIdLbl.gridx = 1;
		gbc_clientIdLbl.gridy = 0;
		clientIdLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		clientIdLbl.setMaximumSize(new Dimension(15, 16));
		panel_custInfo.add(clientIdLbl, gbc_clientIdLbl);
		clientIdTxt.setColumns(10);

		GridBagConstraints gbc_clientIdTxt = new GridBagConstraints();
		gbc_clientIdTxt.insets = new Insets(0, 0, 5, 5);
		gbc_clientIdTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientIdTxt.gridx = 2;
		gbc_clientIdTxt.gridy = 0;
		panel_custInfo.add(clientIdTxt, gbc_clientIdTxt);

		GridBagConstraints gbc_fNameLbl = new GridBagConstraints();
		gbc_fNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fNameLbl.anchor = GridBagConstraints.EAST;
		gbc_fNameLbl.gridx = 1;
		gbc_fNameLbl.gridy = 1;
		fNameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(fNameLbl, gbc_fNameLbl);
		fNameTxt.setColumns(10);

		GridBagConstraints gbc_fNameTxt = new GridBagConstraints();
		gbc_fNameTxt.gridwidth = 2;
		gbc_fNameTxt.insets = new Insets(0, 0, 5, 5);
		gbc_fNameTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_fNameTxt.gridx = 2;
		gbc_fNameTxt.gridy = 1;
		panel_custInfo.add(fNameTxt, gbc_fNameTxt);

		GridBagConstraints gbc_lNameLbl = new GridBagConstraints();
		gbc_lNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_lNameLbl.anchor = GridBagConstraints.EAST;
		gbc_lNameLbl.gridx = 1;
		gbc_lNameLbl.gridy = 2;
		lNameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(lNameLbl, gbc_lNameLbl);
		lNameTxt.setColumns(10);

		GridBagConstraints gbc_lNameTxt = new GridBagConstraints();
		gbc_lNameTxt.gridwidth = 2;
		gbc_lNameTxt.insets = new Insets(0, 0, 5, 5);
		gbc_lNameTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_lNameTxt.gridx = 2;
		gbc_lNameTxt.gridy = 2;
		panel_custInfo.add(lNameTxt, gbc_lNameTxt);

		GridBagConstraints gbc_addressLbl = new GridBagConstraints();
		gbc_addressLbl.insets = new Insets(0, 0, 5, 5);
		gbc_addressLbl.anchor = GridBagConstraints.EAST;
		gbc_addressLbl.gridx = 1;
		gbc_addressLbl.gridy = 3;
		addressLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(addressLbl, gbc_addressLbl);
		addressTxt.setColumns(10);

		GridBagConstraints gbc_addressTxt = new GridBagConstraints();
		gbc_addressTxt.insets = new Insets(0, 0, 5, 5);
		gbc_addressTxt.gridwidth = 2;
		gbc_addressTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTxt.gridx = 2;
		gbc_addressTxt.gridy = 3;
		panel_custInfo.add(addressTxt, gbc_addressTxt);

		GridBagConstraints gbc_pcLbl = new GridBagConstraints();
		gbc_pcLbl.insets = new Insets(0, 0, 5, 5);
		gbc_pcLbl.anchor = GridBagConstraints.EAST;
		gbc_pcLbl.gridx = 1;
		gbc_pcLbl.gridy = 4;
		pcLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(pcLbl, gbc_pcLbl);
		pcTxt.setColumns(10);

		GridBagConstraints gbc_pcTxt = new GridBagConstraints();
		gbc_pcTxt.gridwidth = 2;
		gbc_pcTxt.insets = new Insets(0, 0, 5, 5);
		gbc_pcTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_pcTxt.gridx = 2;
		gbc_pcTxt.gridy = 4;
		panel_custInfo.add(pcTxt, gbc_pcTxt);

		GridBagConstraints gbc_pnLbl = new GridBagConstraints();
		gbc_pnLbl.insets = new Insets(0, 0, 5, 5);
		gbc_pnLbl.anchor = GridBagConstraints.EAST;
		gbc_pnLbl.gridx = 1;
		gbc_pnLbl.gridy = 5;
		pnLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(pnLbl, gbc_pnLbl);
		pnTxt.setColumns(10);

		GridBagConstraints gbc_pnTxt = new GridBagConstraints();
		gbc_pnTxt.gridwidth = 2;
		gbc_pnTxt.insets = new Insets(0, 0, 5, 5);
		gbc_pnTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnTxt.gridx = 2;
		gbc_pnTxt.gridy = 5;
		panel_custInfo.add(pnTxt, gbc_pnTxt);

		GridBagConstraints gbc_cTypeLbl = new GridBagConstraints();
		gbc_cTypeLbl.insets = new Insets(0, 0, 0, 5);
		gbc_cTypeLbl.anchor = GridBagConstraints.EAST;
		gbc_cTypeLbl.gridx = 1;
		gbc_cTypeLbl.gridy = 6;
		cTypeLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_custInfo.add(cTypeLbl, gbc_cTypeLbl);

		GridBagConstraints gbc_cTypeCBox = new GridBagConstraints();
		gbc_cTypeCBox.insets = new Insets(0, 0, 0, 5);
		gbc_cTypeCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_cTypeCBox.gridx = 2;
		gbc_cTypeCBox.gridy = 6;
		cTypeCBox.setBackground(new Color(255, 255, 255));
		cTypeCBox.setModel(new DefaultComboBoxModel(new String[] {"C", "R"}));
		panel_custInfo.add(cTypeCBox, gbc_cTypeCBox);
		panel_custInfoBtn.setBorder(null);

		panel_CustInfo.add(panel_custInfoBtn, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_custInfoBtn = new GridBagLayout();
		gbl_panel_custInfoBtn.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel_custInfoBtn.rowWeights = new double[] { 0.0 };
		panel_custInfoBtn.setLayout(gbl_panel_custInfoBtn);
		saveCustBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		saveCustBtn.setFont(new Font("Tahoma", Font.BOLD, 13));

		GridBagConstraints gbc_saveCustBtn = new GridBagConstraints();
		gbc_saveCustBtn.insets = new Insets(0, 0, 0, 5);
		gbc_saveCustBtn.gridx = 0;
		gbc_saveCustBtn.gridy = 0;
		panel_custInfoBtn.add(saveCustBtn, gbc_saveCustBtn);
		GridBagConstraints gbc_deleteCustBtn = new GridBagConstraints();
		gbc_deleteCustBtn.insets = new Insets(0, 0, 0, 5);
		gbc_deleteCustBtn.gridx = 1;
		gbc_deleteCustBtn.gridy = 0;
		panel_custInfoBtn.add(deleteCustBtn, gbc_deleteCustBtn);
		deleteCustBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_clearCustBtn = new GridBagConstraints();
		gbc_clearCustBtn.gridx = 2;
		gbc_clearCustBtn.gridy = 0;
		panel_custInfoBtn.add(clearCustBtn, gbc_clearCustBtn);
		clearCustBtn.setFont(new Font("Tahoma", Font.BOLD, 13));

		this.radioGroup.add(custtIdBttn);
		custtIdBttn.setActionCommand("customerId");
		custtIdBttn.setSelected(true); // first radio button should always be selected. No null searches allowed
		this.radioGroup.add(custTypeBttn);
		custTypeBttn.setActionCommand("customerType");
		this.radioGroup.add(lastNameBttn);
		lastNameBttn.setActionCommand("lName");
	}

	public JPanel getCmsFrame() {
		return this.CmsFrame;
	}

	public void addSearchAction(ActionListener action) {
		this.searchCust.addActionListener(action);
	}

	public String getSearchFieldText() {
		return textField.getText();
	}

	public String getSelectedRadioButton() {
		return this.radioGroup.getSelection().getActionCommand();
	}

	public void setSearchResultText(String output) {
		this.clientDisplay.append(output); // need a textarea here
		
	}

}
