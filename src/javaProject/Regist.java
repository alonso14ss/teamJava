package javaProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Regist extends JFrame {
	JPanel p_id;
	JLabel la_id;
	JTextField t_id;//id
	JButton bt_overlap;//중복확인 버튼
	JPanel p_pw;
	JLabel la_pw;
	JPasswordField t_pw;//비밀번호
	JPanel p_pwRe;
	JLabel la_pwRe;
	JPasswordField t_pwRe;//비밀번호 재입력
	JButton bt_reg;
	boolean isOverlap = true;
	
	Login login;

	public Regist(Login login) {
		this.login = login;
		p_id = new JPanel();
		p_id.setPreferredSize(new Dimension(350,70));
		//p_id.setBackground(Color.PINK);
		la_id = new JLabel("아이디              ");
		t_id = new JTextField(15);
		bt_overlap = new JButton("중복확인");
		
		p_pw = new JPanel();
		p_pw.setPreferredSize(new Dimension(350,50));
		//p_pw.setBackground(Color.LIGHT_GRAY);
		la_pw = new JLabel("비밀번호          ");
		t_pw = new JPasswordField(15);
		p_pwRe = new JPanel();
		p_pwRe.setPreferredSize(new Dimension(350,50));
		//p_pwRe.setBackground(Color.LIGHT_GRAY);
		la_pwRe = new JLabel("비밀번호 확인 ");
		t_pwRe = new JPasswordField(15);
		
		bt_reg = new JButton("회원가입");
		
		//조립
		p_id.add(la_id);
		p_id.add(t_id);
		p_id.add(bt_overlap);	
		p_pw.add(la_pw);
		p_pw.add(t_pw);
		p_pwRe.add(la_pwRe);
		p_pwRe.add(t_pwRe);
		
		add(p_id);
		add(p_pw);
		add(p_pwRe);
		add(bt_reg);
		
		
		setLayout(new FlowLayout());	
		setVisible(true);
		setSize(360,300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		bt_overlap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(t_id.getText().length()<3 || t_id.getText().length()>11) {
					JOptionPane.showMessageDialog(Regist.this, "아이디는 3~10자로 생성 가능합니다.");
				}else {
					overlapCheck(login);					
				}
			}
		});
		
		bt_reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isOverlap) {
					JOptionPane.showMessageDialog(Regist.this, "아이디 중복확인을 해주세요!");
				}else {
					okReg(login);
				}
			}
		});
	}
	
	public void overlapCheck(Login login) {
		String sql = "select * from gameuser where user_id=?";
		login.pstmt = null;
		login.rs = null;	
		try {
			login.pstmt = login.con.prepareStatement(sql);
			login.pstmt.setString(1, t_id.getText());			
			login.rs = login.pstmt.executeQuery();
			if (login.rs.next()) {// 일치하는 데이터 존재함
				JOptionPane.showMessageDialog(this, "이미 존재하는 아이디 입니다.");
				t_id.setText("");
				isOverlap = true;
				System.out.println("중복 flag 상태 : "+isOverlap);
			}else { //일치하는 데이터 없음
				JOptionPane.showMessageDialog(this, "사용 가능한 아이디 입니다.");
				isOverlap = false;
				System.out.println("중복 flag 상태 : "+isOverlap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void okReg(Login login) {
		String pw1 = new String(t_pw.getPassword());
		String pw2 = new String(t_pwRe.getPassword());
		if(pw1.equals(pw2)) {
			String sql = "insert into gameuser(user_num, user_id, user_pw, score, user_icon, login_status)"
					+ " values(seq_user.nextval, ?, ?, 0, 'user.png', 'n')";
			login.pstmt = null;
			login.rs = null;
			try {
				login.pstmt = login.con.prepareStatement(sql);
				login.pstmt.setString(1, t_id.getText());
				login.pstmt.setString(2, pw2);	
				login.rs = login.pstmt.executeQuery();
				
				JOptionPane.showMessageDialog(this, "회원가입 완료!");
				System.out.println("새 회원 가입완료 : "+t_id.getText());
				t_id.setText("");
				t_pw.setText("");
				t_pwRe.setText("");
				isOverlap = true;
				this.dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
			t_pw.setText("");
			t_pwRe.setText("");
		}
	}
}
