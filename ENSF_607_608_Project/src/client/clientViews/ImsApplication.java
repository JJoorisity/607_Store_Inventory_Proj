package client.clientViews;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class ImsApplication {
	private final JPanel ImsFrame = new JPanel();
	private final JSplitPane splitPane_rs = new JSplitPane();
	private final JSplitPane splitPane_ls = new JSplitPane();
	private final JPanel rightPanel = new JPanel();
	private final JLabel itemResultLbl = new JLabel("Search Results");
	private final JScrollPane itemSearchPane = new JScrollPane();
	private final JPanel purchasePanel = new JPanel();
	private final JLabel purchaseLbl = new JLabel("Purchase Item");
	private final JPanel purchaseInfoPanel = new JPanel();
	private final JLabel itemIdLbl = new JLabel("Item ID:");
	private final JTextField itemIdTxt = new JTextField();
	private final JLabel purchaseQtyLbl = new JLabel("Purchase Qty");
	private final JTextField purchaseQtyTxt = new JTextField();
	private final JTextField pMssgeTxt = new JTextField();
	private final JLabel pMssgLbl = new JLabel("Purchase Executed:");
	private final JPanel searchItemPane = new JPanel();
	private final JLabel searchItemLbl = new JLabel("Search Item");
	private final JPanel itemSearchPanel = new JPanel();
	private final JLabel itemCriteriaLbl = new JLabel("Select search criteria:");
	private final JRadioButton itemIdRBtn = new JRadioButton("Item ID");
	private final JRadioButton itemDRBtn = new JRadioButton("Item Description");
	private final JLabel lblNewLabel_1 = new JLabel("Enter search parameters:");
	private final JTextField searchItemTxt = new JTextField();
	private final JButton searchItemBtn = new JButton("Search");
	private final JButton clearItemBtn = new JButton("Clear");

	/**
	 * Create the application.
	 */
	public ImsApplication() {
		initialize();
		//fix
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ImsFrame.setLayout(new BorderLayout(0, 0));
		splitPane_rs.setBorder(null);

		ImsFrame.add(splitPane_rs);
		splitPane_ls.setBorder(null);
		splitPane_ls.setOrientation(JSplitPane.VERTICAL_SPLIT);

		splitPane_rs.setLeftComponent(splitPane_ls);
		purchasePanel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_ls.setRightComponent(purchasePanel);
		purchasePanel.setLayout(new BorderLayout(0, 0));
		purchaseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));

		purchasePanel.add(purchaseLbl, BorderLayout.NORTH);
		purchaseInfoPanel.setBorder(null);

		purchasePanel.add(purchaseInfoPanel, BorderLayout.CENTER);
		GridBagLayout gbl_purchaseInfoPanel = new GridBagLayout();
		gbl_purchaseInfoPanel.columnWidths = new int[] { 30, 0, 0, 30 };
		gbl_purchaseInfoPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_purchaseInfoPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		purchaseInfoPanel.setLayout(gbl_purchaseInfoPanel);

		GridBagConstraints gbc_itemIdLbl = new GridBagConstraints();
		gbc_itemIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_itemIdLbl.anchor = GridBagConstraints.EAST;
		gbc_itemIdLbl.gridx = 1;
		gbc_itemIdLbl.gridy = 0;
		itemIdLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		purchaseInfoPanel.add(itemIdLbl, gbc_itemIdLbl);
		itemIdTxt.setColumns(10);

		GridBagConstraints gbc_itemIdTxt = new GridBagConstraints();
		gbc_itemIdTxt.insets = new Insets(0, 0, 5, 0);
		gbc_itemIdTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemIdTxt.gridx = 2;
		gbc_itemIdTxt.gridy = 0;
		purchaseInfoPanel.add(itemIdTxt, gbc_itemIdTxt);

		GridBagConstraints gbc_purchaseQtyLbl = new GridBagConstraints();
		gbc_purchaseQtyLbl.anchor = GridBagConstraints.EAST;
		gbc_purchaseQtyLbl.insets = new Insets(0, 0, 5, 5);
		gbc_purchaseQtyLbl.gridx = 1;
		gbc_purchaseQtyLbl.gridy = 1;
		purchaseQtyLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		purchaseInfoPanel.add(purchaseQtyLbl, gbc_purchaseQtyLbl);
		purchaseQtyTxt.setColumns(10);

		GridBagConstraints gbc_purchaseQtyTxt = new GridBagConstraints();
		gbc_purchaseQtyTxt.insets = new Insets(0, 0, 5, 0);
		gbc_purchaseQtyTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_purchaseQtyTxt.gridx = 2;
		gbc_purchaseQtyTxt.gridy = 1;
		purchaseInfoPanel.add(purchaseQtyTxt, gbc_purchaseQtyTxt);

		GridBagConstraints gbc_pMssgLbl = new GridBagConstraints();
		gbc_pMssgLbl.insets = new Insets(0, 0, 5, 5);
		gbc_pMssgLbl.gridx = 1;
		gbc_pMssgLbl.gridy = 3;
		pMssgLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		purchaseInfoPanel.add(pMssgLbl, gbc_pMssgLbl);
		pMssgeTxt.setColumns(10);

		GridBagConstraints gbc_pMssgeTxt = new GridBagConstraints();
		gbc_pMssgeTxt.insets = new Insets(0, 0, 5, 0);
		gbc_pMssgeTxt.gridwidth = 2;
		gbc_pMssgeTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_pMssgeTxt.gridx = 1;
		gbc_pMssgeTxt.gridy = 4;
		purchaseInfoPanel.add(pMssgeTxt, gbc_pMssgeTxt);

		splitPane_ls.setLeftComponent(searchItemPane);
		searchItemPane.setLayout(new BorderLayout(0, 0));
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

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 5;
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		GridBagConstraints gbc_searchItemBtn = new GridBagConstraints();
		gbc_searchItemBtn.insets = new Insets(0, 0, 5, 0);
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
		gbc_clearItemBtn.insets = new Insets(0, 0, 5, 0);
		gbc_clearItemBtn.gridx = 3;
		gbc_clearItemBtn.gridy = 6;
		clearItemBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemSearchPanel.add(clearItemBtn, gbc_clearItemBtn);
		rightPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		splitPane_rs.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));
		itemResultLbl.setFont(new Font("Tahoma", Font.BOLD, 15));

		rightPanel.add(itemResultLbl, BorderLayout.NORTH);
		rightPanel.add(itemSearchPane, BorderLayout.CENTER);
	}

}
