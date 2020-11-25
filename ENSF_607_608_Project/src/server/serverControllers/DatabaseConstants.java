package server.serverControllers;

/**
 * Hold the connection information for the local mySQL database.
 * @author NJack & JJoorisity
 * @version	1.0
 * @since 2020-11-26
 */
public interface DatabaseConstants {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/607toolshop"; // change to our DB Name

	// Database credentials
	static final String USERNAME = "root";
	//static final String PASSWORD = "WornOutPlaces_1";
	static final String PASSWORD = "craven-stomp-husband-learn";
}
