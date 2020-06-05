package javaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {
   String url="jdbc:oracle:thin:@localhost:1521:XE";
   String user="c##java";
   String password="1234";
   
   // 데이터베이스 접속
   public Connection getConnection() {
      Connection con=null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         con=DriverManager.getConnection(url, user, password);
         System.out.println("연결성공");
      }catch(ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return con;
   }
   
   //데이터베이스 자원 해제
   public void closeDB(Connection con) {
      if(con!=null) {
         try {
            con.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
   //DML(insert, update, delete)만 수행한 경우
   //Connection과 PreparedStatement만 반납
   public void closeDB(Connection con, PreparedStatement pstmt) {
      if(pstmt!=null) {
         try {
            pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }if(con!=null) {
          try {
              con.close();
           } catch (SQLException e) {
              e.printStackTrace();
           }
        }
   }
   //select문 경우, PreparedStatement, ResultSet 닫음
   public void closeDB(PreparedStatement pstmt, ResultSet rs) {
      if(pstmt!=null) {
         try {
            pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      if(rs!=null) {
         try {
            pstmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
}