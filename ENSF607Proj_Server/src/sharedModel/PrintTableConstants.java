package sharedModel;

/**
 * PrintTableConstants interface. Global list of formatting artifacts and
 * strings for tables and strings to be printed to console.
 * 
 * @author NJack & JJoorisity
 * @version 1.0
 * @since 2020-11-06
 */
public interface PrintTableConstants {

	static final String TABLEBREAK = "========================================================\n";
	static final String ITEMTABLEFORMAT = "| %-7d | %-12d | %-13.2f | %-25s \n";
	static final String VALUEALIGNFORMAT = "| %-20s  %-30d |%n";
	static final String STRALIGNFORMAT = "| %-20s  %-30s |%n";
}
