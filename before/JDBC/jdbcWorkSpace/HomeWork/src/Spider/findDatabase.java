package Spider;


//STEP 1. Import required packages
import java.sql.*;

public class findDatabase {
	 // JDBC driver name and database URL
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/homework";
	
	 //  Database credentials -- 数据库名和密码自己修改
	 static final String USER = "root";
	 static final String PASS = "";
	
	 public static void main(String[] args) {
		 Connection conn = null;
		 Statement stmt = null;
		 try{
		    //STEP 2: Register JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");
		
		    //STEP 3: Open a connection
		    System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		
		    //STEP 4: Execute a query
		    System.out.println("Creating statement...");
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT id, name, summary FROM spiderhw";
		    ResultSet rs = stmt.executeQuery(sql);
		
		    //STEP 5: Extract data from result set
		    while(rs.next()){
		       //Retrieve by column name
		       int id  = rs.getInt("id");
		       String name = rs.getString("name");
		       String summary = rs.getString("summary");
		
		       //Display values
		       System.out.print("ID: " + id);
		       System.out.print(", Name: " + name);
		       System.out.print(", Summary: " + summary);
		    }
		    //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		    conn.close();
		 }catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		 }catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		 }finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }//end try
		 System.out.println("Goodbye!");
	}
}