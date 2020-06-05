package javaProject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Login extends JFrame {
	JPanel p_id;
	JLabel la_id;
	JTextField t_id;
	JPanel p_pw;
	JLabel la_pw;
	JPasswordField t_pw;
	JButton bt_login;
	JLabel la_reg;
	ConnectionManager connectionManager;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	Socket client;
	MyClientThread clientThread;
	boolean loginCheckFlag = false;
	String userId;

	JPanel background; // 배경이미지
	ImageIcon icon;

	GameMain gameMain;
	
	String myId = null;

	public Login() {
		connect();
		p_id = new JPanel();
		p_id.setPreferredSize(new Dimension(200, 30));
		la_id = new JLabel("   ID");
		t_id = new JTextField(10);
		p_pw = new JPanel();
		p_pw.setPreferredSize(new Dimension(200, 30));
		la_pw = new JLabel("PW");
		t_pw = new JPasswordField(10);
		bt_login = new JButton("로그인");
		bt_login.setPreferredSize(new Dimension(140, 30));
		bt_login.setFont(new Font("돋움", Font.BOLD, 20));
		la_reg = new JLabel("회원가입");

		setLayout(new FlowLayout());

		p_id.add(la_id);
		p_id.add(t_id);
		p_pw.add(la_pw);
		p_pw.add(t_pw);
		add(p_id);
		add(p_pw);
		add(bt_login);
		add(la_reg);

		setSize(200, 180);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});

		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
				if (loginCheckFlag)
				{
					setVisible(false);
					new GameWatingRoom(Login.this);
				}
				else
					JOptionPane.showMessageDialog(Login.this, "일치하는 데이터가 없습니다.");

			}
		});

		la_reg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Regist(Login.this);
			}
		});
	}

	public void loginCheck() {
		String sql = "select * from GAMEUSER where User_id =? and User_pw =?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, new String(t_pw.getPassword()));

			rs = pstmt.executeQuery();
			if (rs.next()) {// 일치하는 데이터 존재함
				JOptionPane.showMessageDialog(this, "로그인 성공");
				userId = t_id.getText();
				myId = userId;
				loginCheckFlag = true;
				t_id.setText("");
				t_pw.setText("");
			} else {// 일치하는 데이터 없음
				JOptionPane.showMessageDialog(this, "로그인 실패");
				t_id.setText("");
				t_pw.setText("");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			client = new Socket("192.168.0.25", 7777);
		      connectionManager = new ConnectionManager();
		      con = connectionManager.getConnection(); 
			// 접속을 성공했으니, 쓰레드인 메시지 객체를 생성
			clientThread = new MyClientThread(this, client);
			clientThread.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		String myip = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			myip = ip.toString();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		clientThread.send("disconnection"+myip);
		connectionManager.closeDB(con);
		System.exit(0);
	}
	

	public static void main(String[] args) {
		new Login();
	}
}