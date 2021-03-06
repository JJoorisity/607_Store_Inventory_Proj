package sharedModel;

/**
 * Commands interface. Global list of server/client commands.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-06
 */
public interface Commands {

	static final String CUSTOMER = "CUSTOMER";
	static final String COMPLETE = "COMPLETE";
	static final String FAILED = "FAILED";
	static final String ID = "ID"; 
	static final String NAME = "NAME";
	static final String ALL = "ALL";
	static final String TYPE = "TYPE";
	static final String DISPLAY = "DISPLAY";
	static final String ITEM_ELEC = "ITEM_ELEC";
	static final String ORDER = "ORDER";
	static final String SAVE = "SAVE";
	static final String SEARCH = "SEARCH";
	static final String DELETE = "DELETE";
	static final String PURCHASE = "PURCHASE";
	static final String QUIT = "QUIT";
	static final String DISPLAYEDIT = "DISPLAYEDIT";
	static final String DISPLAYITEM = "DISPLAYITEM";
	static final String PCOMPLETE = "Purchase Complete";
	static final String PFAILED = "Inventory Low";
}
