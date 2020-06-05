package javaProject;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class FriendModel extends AbstractTableModel{
	
	String[] column = {"아이디","접속여부"};
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public String getColumnName(int col) {
		return column[col];
	}
	@Override
	public Object getValueAt(int row, int col) {
		
		String data = null;
		if(col == 0)
		{
			data = "아이디";
		}
		else if(col == 1)
		{
			data = "접속중";			
		}
		return data;
	}

}
