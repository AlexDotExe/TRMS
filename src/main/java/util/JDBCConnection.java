  package util;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCConnection {
	private static Connection conn = null;
public static Connection getConnection() {
	
	try {
	if(conn == null) {
		
		/*Oracle added a hotfix to ensure ojdbc drivers 
		 * would load at the beginning of the application starting
		 */
		Class.forName("oracle.jdbc.driver.OracleDriver");
		/* To establish connection we need3 credentials: 
		 * url, username, password
		 
		String endpoint = "jdbc:oracle:thin:@project0.c7sziqqdxdqc.us-east-2.rds.amazonaws.com:1521:ORCL";
		String username = "";
		String password = "";
		*/
		Properties prop = new Properties();
		FileInputStream in = new FileInputStream(JDBCConnection.class.getClassLoader().getResource("connection.properties").getFile());
		prop.load(in);
		
		
		String endpoint = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		conn = DriverManager.getConnection(endpoint, username, password);
		return conn;
					}	
	else return conn;	
	
											}
	catch(Exception e) {e.printStackTrace();}

return null;
}
}
