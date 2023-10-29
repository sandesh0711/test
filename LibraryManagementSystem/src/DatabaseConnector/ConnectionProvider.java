package DatabaseConnector;

import java.sql.*;

public class ConnectionProvider {
	
	public static Connection getCon ()
	 {
	 
	   try
	 	  {
		   //jdbc:mysql://localhost:3306/?user=root
			   
			 Class.forName ("com.mysql.cj.jdbc.Driver");
			   final String JDBC_URL = "jdbc:mysql://localhost:3306/lms?characterEncoding=utf8";
				final String username="root";
				final String password="root";
			 Connection con=DriverManager.getConnection(JDBC_URL,username,password);
			 
			// return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			 return con;
	      }
	   
	    catch (Exception e)
			 {	
			 System.out.println (e);
			 return null;
			 }

     }
}