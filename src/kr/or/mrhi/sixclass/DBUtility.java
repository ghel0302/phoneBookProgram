package kr.or.mrhi.sixclass;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtility {
	public static Connection getConnection() {
	      Connection con = null;

	      // Collection Framework의 Properties 사용해서 드라이버, URL, ID, Password 가져오기

	      try {
	         Properties properties = new Properties();
	         // URL 디코드하기
	         String filePath = DatabaseTest.class.getResource("db.properties").getPath();
	         filePath = URLDecoder.decode(filePath, "utf-8");
	         properties.load(new FileReader(filePath));
	         // 바인딩 properties. getProperty(가져오고 싶은 값의 key입력)
	         String driver = properties.getProperty("DRIVER");
	         String url = properties.getProperty("URL");
	         String userID = properties.getProperty("userID");
	         String userPassword = properties.getProperty("userPassword");
	         // jdbc driver를 load
	         Class.forName(driver);
	         // MySql Database connection
	         con = DriverManager.getConnection(url, userID, userPassword); // con = mysql과 연결된 키
	      } catch (Exception e) {
	         System.out.println("MySQL Database connection failed");
	         e.printStackTrace();
	      }

	      return con;
	   }
}