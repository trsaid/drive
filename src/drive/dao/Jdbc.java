package drive.dao;

import java.sql.*;

public class Jdbc {
	public static void main(String[] args) throws Exception {
		db test = new db();
		String query = "SELECT * FROM utilisateurs";
		ResultSet rs = test.db_connect().executeQuery(query);
		
		while(rs.next()) {
			String name = rs.getString("name");
			System.out.println(name);
		}
	}
}


class db {
	public Statement db_connect() throws Exception {
			
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "site";
			String password = "said";
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,username,password);
			Statement st = con.createStatement();
	//		ResultSet r = st.executeQuery(Query);
			
	//		r.next();
	//		String name = r.getString("name");
	//		System.out.println(name);
			
	//		st.close();
	//		con.close();
			
			return st;
		}
	
}