package javaProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBQuery {
	Connection con;
	boolean loginCheckFlag = false;
	
	
	public boolean loginCheck(String msg1,String msg2) {
		String sql = "select * from GAMEUSER where User_id =? and User_pw =?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, msg1);
			pstmt.setString(2, msg2);

			rs = pstmt.executeQuery();
			if (rs.next()) {// ��ġ�ϴ� ������ ������
				loginCheckFlag = true;
			} else {// ��ġ�ϴ� ������ ����
				loginCheckFlag = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return loginCheckFlag;
	}
}
