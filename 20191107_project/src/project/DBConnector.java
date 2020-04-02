package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/myjava?useSSL=false";
	private final static String USERNAME = "myjava";
	private final static String PASSWORD = "12345";
	
	private static Connection conn = null;
	
	private DBConnector() {}
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			} catch (ClassNotFoundException e) {
				System.out.println("DRIVER를 찾을 수 없음 : " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("DB정보 오류 : " + e.getMessage());
			}
		}
		return conn;
	}
	
	public static void close(AutoCloseable closer) {
		try {
			if(closer != null) closer.close();
		} catch (Exception e) {}
		
	}
}
