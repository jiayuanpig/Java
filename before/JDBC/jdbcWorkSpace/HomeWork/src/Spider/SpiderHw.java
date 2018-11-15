package Spider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SpiderHw {
	
	//连接网页
		public  Document getDocument (String url){
	        try {
	       	 //5000是设置连接超时时间，单位ms
	            return Jsoup.connect(url).timeout(5000).get();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
		
	//导入数据库
	// JDBC driver name and database URL
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/homework?useUnicode=true&amp;characterEncoding=UTF-8";
	// Database credentials -- 数据库名和密码自己修改
	 static final String USER = "root";
	 static final String PASS = "";
		 
		 

	public static void main(String[] args) {
		
		SpiderHw t = new SpiderHw();
        Document doc = t.getDocument("http://www.weather.com.cn/weather/101200101.shtml");
        // 获取目标HTML代码
        Elements elements1 = doc.select("[class='t clearfix']");
        //今天
        Elements elements2 = elements1.select("[class='sky skyid lv3 on']");
        String today = elements2.get(0).text();
        System.out.println("今日天气： "+today);

        //几号
        Elements elements3 = elements1.select("h1");
        String number = elements3.get(0).text();
        System.out.println("日期： "+number);

        // 天气
        Elements elements4 = elements1.select("[class=wea]");
        String rain = elements4.get(0).text();
        System.out.println("天气： "+rain);

        // 最高温度
        Elements elements5 = elements1.select("span");
        String highTemperature = elements5.get(0).text()+"°C";
        System.out.println("最高温："+highTemperature);

        // 最低温度
        Elements elements6 = elements1.select(".tem i");
        String lowTemperature = elements6.get(0).text();
        System.out.println("最低温："+lowTemperature);

        // 风力
        Elements elements7 = elements1.select(".win i");
        String wind = elements7.get(2).text();
        System.out.println("风力："+wind);
		
//        ------------------------------------------------------------------------------------------
        
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
		    sql = "INSERT INTO weather VALUES ('"+today+"','"+number+"','"+rain+"','"+highTemperature+"','"+lowTemperature+"','"+wind+"')";
		    System.out.println(sql);
		    stmt.executeUpdate(sql);
		
		    //STEP 6: Clean-up environment
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
