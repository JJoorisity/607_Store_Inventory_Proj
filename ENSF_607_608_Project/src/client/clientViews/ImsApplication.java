package client.clientViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;

public class ImsApplication {
	private final JPanel ImsFrame = new JPanel();
	private final JSplitPane splitPane_rs = new JSplitPane();
	private final JSplitPane splitPane_ls = new JSplitPane();
	private final JPanel rightPanel = new JPanel();
	private final JLabel itemResultLbl = new JLabel("Search Results");
	private JScrollPane itemSearchPane;
	
	private final JPanel purchasePanel = new JPanel();
	private final JLabel purchaseLbl = new JLabel("Purchase Item");
	private final JPanel purchaseInfoPanel = new JPanel();
	private final JLabel custIdLbl = new JLabel("Customer ID:");
	private final JTextField custIdTxt = new JTextField();
	private final JLabel itemIdLbl = new JLabel("Item ID:");
	private final JTextField itemIdTxt = new JTextField();
	private final JLabel purchaseQtyLbl = new JLabel("Purchase Qty:");
	private final JTextField purchaseQtyTxt = new JTextField();
	private final JLabel pMssgLbl = new JLabel("Purchase Executed:");
	private final JTextField pMssgeTxt = new JTextField();
	private final JButton purchaseBtn = new JButton("Purchase");
	
	private final JPanel searchItemPane = new JPanel();
	private final JLabel searchItemLbl = new JLabel("Search Item");
	private final JPanel itemSearchPanel = new JPanel();
	private final JLabel itemCriteriaLbl = new JLabel("Select search criteria:");
	private ButtonGroup radioGroup = new ButtonGroup();
	private final JRadioButton itemIdRBtn = new JRadioButton("Item ID");
	private final JRadioButton itemDRBtn = new JRadioButton("Item Description");
	private final JLabel lblNewLabel_1 = new JLabel("Enter search parameters:");
	private final JTextField searchItemTxt = new JTextField();
	private final JButton searchItemBtn = new JButton("Search");
	private final JButton clearItemBtn = new JButton("Clear");
	private final JButton searchAllBtn = new JButton("Search All");
	private DefaultListModel listModel1 = new DefaultListModel();
	private final JList itemList = new JList();
	
	
	

	
	// Add customer Id to purchase section.
	
	/**
	 * Create the application.
	 */
	public ImsApplication() {
		initialize();
		//fix
	}
	
	public void addActionListeners(ActionListener listener) {
		searchItemBtn.addActionListener(listener);
		searchAllBtn.addActionListener(listener);
		clearItemBtn.addActionListener(listener);
		purchaseBtn.addActionListener(listener);
	}
	
	public void addSelectionListeners(ListSelectionListener listener) {
		itemList.addListSelectionListener(listener);
	}
	
	public JButton getClearItemBtn() {
		return this.clearItemBtn;
	}
	
	public JButton getSearchItemBtn() {
		return this.searchItemBtn;
	}
	
	public JButton getSearchAllBtn() {
		return this.searchAllBtn;
	}
	
	public JButton getPurchaseBtn() {
		return this.purchaseBtn;
	}
	
	public JList getItemList() {
		return this.itemList;
	}
	
	public String getSearchItemTxt() {
		return searchItemTxt.getText();
	}

	public void setSearchItemTxt(String set) {
		searchItemTxt.setText(set);
	}
	
	public String getItemIdTxt() {
		return itemIdTxt.getText();
	}

	public void setItemIdTxt(String set) {
		itemIdTxt.setText(set);
	}
	
	public String getPurchaseQtyTxt() {
		return purchaseQtyTxt.getText();
	}

	public void setPurchaseQtyTxt(String set) {
		purchaseQtyTxt.setText(set);
	}
	
	public String getcustIdTxt() {
		return custIdTxt.getText();
	}

	public void setcustIdTxt(String set) {
		custIdTxt.setText(set);
	}
	
	public String getPMssgeTxt() {
		return pMssgeTxt.getText();
	}

	public void setPMssgeTxt(String set) {
		pMssgeTxt.setText(set);
	}

	public String getSelectedRadioButton() {
		return this.radioGroup.getSelection().getActionCommand();
	}

	public void setSearchResultText(String output) {
		int i = this.listModel1.getSize();
		this.itemList.ensureIndexIsVisible(i - 1);
		this.listModel1.add(i, output);
	}

	public void resetSearchResultText(String output) {
		this.listModel1.removeAllElements();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ImsFrame.setLayout(new BorderLayout(0, 0));
		splitPane_rs.setDividerSize(3);
		splitPane_rs.setBorder(null);

		ImsFrame.add(splitPane_rs);
		splitPane_ls.setDividerSize(3);
		splitPane_ls.setBorder(null);
		splitPane_ls.setOrientation(JSplitPane.VERTICAL_SPLIT);

		splitPane_rs.setLeftComponent(splitPane_ls);
		purchasePanel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_ls.setRightComponent(purchasePanel);
		purchasePanel.setLayout(new BorderLayout(0, 0));
		purchaseLbl.setOpaque(true);
		purchaseLbl.setBackground(new Color(0, 51, 102));
		purchaseLbl.setForeground(new Color(255, 255, 255));
		purchaseLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purchaseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));

		purchasePanel.add(purchaseLbl, BorderLayout.NORTH);
		purchaseInfoPanel.setBorder(null);

		custIdTxt.setColumns(10);
		purchasePanel.add(purchaseInfoPanel, BorderLayout.CENTER);
		GridBagLayout gbl_purchaseInfoPanel = new GridBagLayout();
		gbl_purchaseInfoPanel.columnWidths = new int[] { 30, 0, 0, 0, 30 };
		gbl_purchaseInfoPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_purchaseInfoPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		purchaseInfoPanel.setLayout(gbl_purchaseInfoPanel);
						
						GridBagConstraints gbc_custIdLbl = new GridBagConstraints();
						gbc_custIdLbl.anchor = GridBagConstraints.EAST;
						gbc_custIdLbl.insets = new Insets(0, 0, 5, 5);
						gbc_custIdLbl.gridx = 1;
						gbc_custIdLbl.gridy = 0;
						custIdLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
						purchaseInfoPanel.add(custIdLbl, gbc_custIdLbl);
								
								GridBagConstraints gbc_custIdTxt = new GridBagConstraints();
								gbc_custIdTxt.insets = new Insets(0, 0, 5, 5);
								gbc_custIdTxt.fill = GridBagConstraints.HORIZONTAL;
								gbc_custIdTxt.gridx = 2;
								gbc_custIdTxt.gridy = 0;
								purchaseInfoPanel.add(custIdTxt, gbc_custIdTxt);
						
								GridBagConstraints gbc_itemIdLbl = new GridBagConstraints();
								gbc_itemIdLbl.insets = new Insets(0, 0, 5, 5);
								gbc_itemIdLbl.anchor = GridBagConstraints.EAST;
								gbc_itemIdLbl.gridx = 1;
								gbc_itemIdLbl.gridy = 1;
								itemIdLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
								purchaseInfoPanel.add(itemIdLbl, gbc_itemIdLbl);
						itemIdTxt.setColumns(10);
						
								GridBagConstraints gbc_itemIdTxt = new GridBagConstraints();
								gbc_itemIdTxt.insets = new Insets(0, 0, 5, 5);
								gbc_itemIdTxt.fill = GridBagConstraints.HORIZONTAL;
								gbc_itemIdTxt.gridx = 2;
								gbc_itemIdTxt.gridy = 1;
								purchaseInfoPanel.add(itemIdTxt, gbc_itemIdTxt);
		
				GridBagConstraints gbc_purchaseQtyLbl = new GridBagConstraints();
				gbc_purchaseQtyLbl.anchor = GridBagConstraints.EAST;
				gbc_purchaseQtyLbl.insets = new Insets(0, 0, 5, 5);
				gbc_purchaseQtyLbl.gridx = 1;
				gbc_purchaseQtyLbl.gridy = 2;
				purchaseQtyLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
				purchaseInfoPanel.add(purchaseQtyLbl, gbc_purchaseQtyLbl);
		purchaseQtyTxt.setColumns(10);
		
				GridBagConstraints gbc_purchaseQtyTxt = new GridBagConstraints();
				gbc_purchaseQtyTxt.insets = new Insets(0, 0, 5, 5);
				gbc_purchaseQtyTxt.fill = GridBagConstraints.HORIZONTAL;
				gbc_purchaseQtyTxt.gridx = 2;
				gbc_purchaseQtyTxt.gridy = 2;
				purchaseInfoPanel.add(purchaseQtyTxt, gbc_purchaseQtyTxt);
		
				GridBagConstraints gbc_pMssgLbl = new GridBagConstraints();
				gbc_pMssgLbl.insets = new Insets(0, 0, 5, 5);
				gbc_pMssgLbl.gridx = 1;
				gbc_pMssgLbl.gridy = 4;
				pMssgLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
				purchaseInfoPanel.add(pMssgLbl, gbc_pMssgLbl);
						pMssgeTxt.setColumns(10);
						
								GridBagConstraints gbc_pMssgeTxt = new GridBagConstraints();
								gbc_pMssgeTxt.insets = new Insets(0, 0, 0, 5);
								gbc_pMssgeTxt.fill = GridBagConstraints.HORIZONTAL;
								gbc_pMssgeTxt.gridx = 1;
								gbc_pMssgeTxt.gridy = 5;
								purchaseInfoPanel.add(pMssgeTxt, gbc_pMssgeTxt);
								
								GridBagConstraints gbc_purchaseBtn = new GridBagConstraints();
								gbc_purchaseBtn.insets = new Insets(0, 0, 0, 5);
								gbc_purchaseBtn.gridx = 2;
								gbc_purchaseBtn.gridy = 5;
								purchaseBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
								purchaseInfoPanel.add(purchaseBtn, gbc_purchaseBtn);
		searchItemPane.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_ls.setLeftComponent(searchItemPane);
		searchItemPane.setLayout(new BorderLayout(0, 0));
		searchItemLbl.setHorizontalAlignment(SwingConstants.CENTER);
		searchItemLbl.setOpaque(true);
		searchItemLbl.setForeground(new Color(255, 255, 255));
		searchItemLbl.setBackground(new Color(0, 51, 102));
		searchItemLbl.setFont(new Font("Tahoma", Font.BOLD, 15));

		searchItemPane.add(searchItemLbl, BorderLayout.NORTH);

		searchItemPane.add(itemSearchPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_itemSearchPanel = new GridBagLayout();
		gbl_itemSearchPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 30 };
		gbl_itemSearchPanel.columnWidths = new int[] { 30, 30, 30, 30, 30 };
		gbl_itemSearchPanel.columnWeights = new double[] { 1.0 };
		gbl_itemSearchPanel.rowWeights = new double[] { Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		itemSearchPanel.setLayout(gbl_itemSearchPanel);

		GridBagConstraints gbc_itemCriteriaLbl = new GridBagConstraints();
		gbc_itemCriteriaLbl.anchor = GridBagConstraints.WEST;
		gbc_itemCriteriaLbl.insets = new Insets(10, 5, 5, 5);
		gbc_itemCriteriaLbl.gridx = 1;
		gbc_itemCriteriaLbl.gridy = 1;
		itemCriteriaLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(itemCriteriaLbl, gbc_itemCriteriaLbl);

		GridBagConstraints gbc_itemIdRBtn = new GridBagConstraints();
		gbc_itemIdRBtn.anchor = GridBagConstraints.WEST;
		gbc_itemIdRBtn.insets = new Insets(0, 10, 5, 5);
		gbc_itemIdRBtn.gridx = 1;
		gbc_itemIdRBtn.gridy = 2;
		itemIdRBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(itemIdRBtn, gbc_itemIdRBtn);

		GridBagConstraints gbc_itemDRBtn = new GridBagConstraints();
		gbc_itemDRBtn.anchor = GridBagConstraints.WEST;
		gbc_itemDRBtn.insets = new Insets(0, 10, 5, 5);
		gbc_itemDRBtn.gridx = 1;
		gbc_itemDRBtn.gridy = 3;
		itemDRBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(itemDRBtn, gbc_itemDRBtn);
		
		GridBagConstraints gbc_searchAllBtn = new GridBagConstraints();
		gbc_searchAllBtn.insets = new Insets(0, 0, 5, 5);
		gbc_searchAllBtn.gridx = 3;
		gbc_searchAllBtn.gridy = 4;
		searchAllBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(searchAllBtn, gbc_searchAllBtn);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 5;
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		GridBagConstraints gbc_searchItemBtn = new GridBagConstraints();
		gbc_searchItemBtn.insets = new Insets(0, 0, 5, 5);
		gbc_searchItemBtn.gridx = 3;
		gbc_searchItemBtn.gridy = 5;
		searchItemBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(searchItemBtn, gbc_searchItemBtn);
		searchItemTxt.setColumns(10);

		GridBagConstraints gbc_searchItemTxt = new GridBagConstraints();
		gbc_searchItemTxt.insets = new Insets(0, 0, 5, 5);
		gbc_searchItemTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchItemTxt.gridx = 1;
		gbc_searchItemTxt.gridy = 6;
		itemSearchPanel.add(searchItemTxt, gbc_searchItemTxt);

		GridBagConstraints gbc_clearItemBtn = new GridBagConstraints();
		gbc_clearItemBtn.insets = new Insets(0, 0, 5, 5);
		gbc_clearItemBtn.gridx = 3;
		gbc_clearItemBtn.gridy = 6;
		clearItemBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(clearItemBtn, gbc_clearItemBtn);
		rightPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_rs.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		itemResultLbl.setBackground(new Color(0, 51, 102));
		itemResultLbl.setForeground(new Color(255, 255, 255));
		itemResultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		itemResultLbl.setOpaque(true);
		itemResultLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		rightPanel.add(itemResultLbl, BorderLayout.NORTH);
		
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setVisibleRowCount(20);
		itemList.setLayoutOrientation(JList.VERTICAL);
		itemSearchPane = new JScrollPane(itemList);
		itemSearchPane.setBackground(Color.WHITE);
		rightPanel.add(itemSearchPane, BorderLayout.CENTER);
		
	
		this.radioGroup.add(itemIdRBtn);
		itemIdRBtn.setActionCommand("itemId");
		itemIdRBtn.setSelected(true); // first radio button should always be selected. No null searches allowed
		this.radioGroup.add(itemDRBtn);
		itemDRBtn.setActionCommand("itemDesc");
		
		
	}

	public JPanel getImsFrame() {
		return this.ImsFrame;
	}

}
