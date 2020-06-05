package javaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyRoom extends JFrame implements ActionListener{
   JPanel profile;
   JPanel myIcon;
   JPanel myStorage;
   JLabel user_id;
   JLabel user_point;
   JLabel user_winrate;
   JTextArea note;
   JPanel p_bt;
   JButton bt_exit;
   GameWatingRoom gameWatingRoom;
   
   public MyRoom(GameWatingRoom gameWatingRoom) {
	  this.gameWatingRoom = gameWatingRoom;
      profile = new JPanel();
      profile.setPreferredSize(new Dimension(400,900));
      profile.setBackground(Color.LIGHT_GRAY);
      profile.setBorder(BorderFactory.createEmptyBorder(30 ,0 , 0 , 0));
      myIcon = new JPanel();
      myIcon.setPreferredSize(new Dimension(300,300));
      myIcon.setBackground(Color.WHITE);
      user_id = new JLabel("유저 아이디");
      user_id.setPreferredSize(new Dimension(400,40));
      user_id.setHorizontalAlignment(JLabel.CENTER);
      user_point = new JLabel("유저 포인트");
      user_point.setPreferredSize(new Dimension(400,40));
      user_point.setHorizontalAlignment(JLabel.CENTER);
      user_winrate = new JLabel("유저 아이디");
      user_winrate.setPreferredSize(new Dimension(400,40));
      user_winrate.setHorizontalAlignment(JLabel.CENTER);
      myStorage = new JPanel();
      myStorage.setPreferredSize(new Dimension(360,360));
      myStorage.setBackground(Color.PINK);
      
      note = new JTextArea();
      note.setEditable(false);
      note.setText("오답노트가 들어갈 영역");
      
      p_bt = new JPanel();
      p_bt.setPreferredSize(new Dimension(200,900));
      p_bt.setBackground(Color.GRAY);
      p_bt.setLayout(new BorderLayout());
      bt_exit = new JButton("나가기");
      bt_exit.setPreferredSize(new Dimension(180,120));
      
      
      profile.add(myIcon);
      profile.add(user_id);
      profile.add(user_point);
      profile.add(user_winrate);
      profile.add(myStorage);
      
      p_bt.add(bt_exit,BorderLayout.SOUTH);
      
      add(profile, BorderLayout.WEST);
      add(p_bt, BorderLayout.EAST);
      
      bt_exit.addActionListener(this);
      
      setSize(1600,900);
      setVisible(true);
      setLocationRelativeTo(null);
      setResizable(false);
      
      addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameWatingRoom.login.disconnect();
			}
		});
      
      
   }
   
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_exit) {
			setVisible(false);
			gameWatingRoom.setVisible(true);
		}
	}
   
}