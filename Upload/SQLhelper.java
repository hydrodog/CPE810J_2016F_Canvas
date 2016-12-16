package update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLhelper {

	public Connection getSQL() {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Success loading Driver!");
		} catch (Exception e) {
			// handle the error
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sys?" + "user=root&password=GRwill123");
			System.out.println("Success connecting server!");
			// Do something with the Connection

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;

	}

}
