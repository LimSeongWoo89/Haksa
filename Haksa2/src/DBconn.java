import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBconn {
	private Connection conn =null;
	private Statement stmt = null;
	public DBconn() {
		String url = "jdbc:oracle:thin:@localhost:1521:myoracle"; // Oracle JDBC 연결
		String url2 = "jdbc:mysql://localhost:3306/sampledb?useSSL=false&useUnicode=true&characterEncoding=UTF-8"; // MySQL 연결
		String uid = "ora_user";
		String pass = "hong";
		try { // DB 연결
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Oracle JDBC
			//Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, pass); // Oracle jdbc
			//conn = DriverManager.getConnection(url2,"hkd","1234"); // mySQL
			stmt = conn.createStatement();	
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void closeSet() {
		try {
			conn.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
