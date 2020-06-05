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
   
   // �����ͺ��̽� ����
   public Connection getConnection() {
      Connection con=null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         con=DriverManager.getConnection(url, user, password);
         System.out.println("���Ἲ��");
      }catch(ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return con;
   }
   
   //�����ͺ��̽� �ڿ� ����
   public void closeDB(Connection con) {
      if(con!=null) {
         try {
            con.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
   //DML(insert, update, delete)�� ������ ���
   //Connection�� PreparedStatement�� �ݳ�
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
   //select�� ���, PreparedStatement, ResultSet ����
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