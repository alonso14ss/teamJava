package javaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameWatingRoom extends JFrame implements ActionListener {
	JPanel p_west, p_east, p_table;
	JTextArea area;
	JTextField t_input;
	JButton bt_shop, bt_start, bt_logout, bt_myRoom;
	JScrollPane scroll, scroll2;
	JTable table;
	FriendModel friendModel;
	Login login;
	
	public GameWatingRoom(Login login) {
		this.login = login;
		System.out.println(login);
		area = new JTextArea();
		login.clientThread.changeArea(area);
		System.out.println(area);
		p_west = new JPanel();
		scroll = new JScrollPane(area);
		t_input = new JTextField("채팅 입력될 텍스트 필드", 30);

		p_east = new JPanel();
		bt_myRoom = new JButton("마이룸");
		bt_shop = new JButton("상점");
		p_table = new JPanel();
		table = new JTable(friendModel = new FriendModel()); // 친구목록 모델 들어가야됨.
		scroll2 = new JScrollPane(table);
		bt_start = new JButton("게임 시작");
		bt_logout = new JButton("로그 아웃");

		// 서쪽 패널 디자인
		p_west.setPreferredSize(new Dimension(450, 900));
		p_west.setBackground(Color.white);
		area.setPreferredSize(new Dimension(450, 800));
		area.setBackground(Color.RED);
		area.setFont(new Font("굴림", Font.BOLD, 16));

		// 동쪽 패널 디자인
		p_east.setPreferredSize(new Dimension(350, 900));
		p_east.setBackground(Color.yellow);
		bt_myRoom.setPreferredSize(new Dimension(150, 150));
		bt_shop.setPreferredSize(new Dimension(150, 150));
		p_table.setPreferredSize(new Dimension(350, 500));
		p_table.setBackground(Color.GRAY);
		scroll2.setPreferredSize(new Dimension(330, 480));
		bt_start.setPreferredSize(new Dimension(150, 150));
		bt_logout.setPreferredSize(new Dimension(150, 150));

		add(p_west, BorderLayout.WEST);
		
		//조립
		p_west.add(scroll);
		p_west.add(t_input, BorderLayout.SOUTH);

		p_east.add(bt_myRoom);
		p_east.add(bt_shop);

		p_table.add(scroll2);
		p_east.add(p_table);

		p_east.add(bt_start);
		p_east.add(bt_logout);
		add(p_east);

		setSize(800, 900);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

		// 버튼에 리스너 연결
		bt_start.addActionListener(this);
		bt_myRoom.addActionListener(this);
		bt_shop.addActionListener(this);
		
		//윈도우 리스너 연결
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				login.disconnect();
			}
		});

		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// 엔터치면 메시지 보내기
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					// 텍스트필드에서 쳐서 넣은 메시지 전달
					String msg = t_input.getText();
					login.clientThread.send(msg); // 보내고
				}
			}
		});
	}

	// 게임 시작 버튼 연결
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_start) {
			setVisible(false);
			new GameMain(GameWatingRoom.this);
		} else if (obj == bt_myRoom) {
			setVisible(false);
			new MyRoom(GameWatingRoom.this);
		} else if (obj == bt_shop) {
			setVisible(false);
			new Shop(GameWatingRoom.this);
		}

	}

}
