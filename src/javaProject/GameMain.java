package javaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameMain extends JFrame implements ActionListener{
   JPanel p_quiz; // ��� ���� �г�
   JLabel la_quiz; //���� ��� ��
   JPanel p_contianer; //�ߴ� o,���� �������,x, ������ ����,������
   JPanel p_left; // o 
   JPanel p_waitRoom;//��� �� �̼��ý� ���� ������ ��ġ�Ұ�
   JPanel p_right; // x
   JPanel p_info;// ���� ��������, ����, ����, ������ ��ư
   JLabel la_myId;
   JLabel la_stage;
   JLabel la_score;
   JButton bt_start;
   JButton bt_exit;
   JPanel p_system; //�ϴ� ä��,�ý��� �г�
   JTextArea a_chat; //ä�� �� �ý��� ���
   JTextField a_input; // ä�� �Է�
   GameWatingRoom gameWatingRoom;
   
   boolean isMaster = false; //���� ���� = ��ư ��Ȱ��ȭ. Ȱ��ȭ ���� üũ
   public GameMain(GameWatingRoom gameWatingRoom) {
	  this.gameWatingRoom = gameWatingRoom;
      p_quiz = new JPanel();
      p_quiz.setPreferredSize(new Dimension(1600,150));
      a_chat = new JTextArea(10,10);
      a_chat.setPreferredSize(new Dimension(1500,200));
      gameWatingRoom.login.clientThread.changeArea(a_chat);
      p_quiz.setBackground(Color.GRAY);
      la_quiz = new JLabel("���� ������ ��µ� ���!");
      la_quiz.setFont(new Font("�ü�", Font.BOLD, 40));
      la_quiz.setHorizontalAlignment(JLabel.CENTER);
      
      p_left = new JPanel();
      p_left.setPreferredSize(new Dimension(450,450));
      p_left.setBackground(Color.RED);
      p_waitRoom = new JPanel();
      p_waitRoom.setPreferredSize(new Dimension(400,450));
      p_waitRoom.setBackground(Color.YELLOW);
      p_right = new JPanel();
      p_right.setPreferredSize(new Dimension(450,450));
      p_right.setBackground(Color.BLUE);
      p_info = new JPanel();
      p_info.setPreferredSize(new Dimension(260,450));
      p_info.setBackground(Color.WHITE);
      la_myId = new JLabel("user id");
      la_myId.setPreferredSize(new Dimension(260,30));
      la_stage = new JLabel("stage");
      la_stage.setPreferredSize(new Dimension(260,30));
      la_score = new JLabel("my score");
      la_score.setPreferredSize(new Dimension(260,30));
      bt_start = new JButton("���ӽ���");
      bt_start.setPreferredSize(new Dimension(260,100));
      bt_exit = new JButton("������");
      bt_exit.setPreferredSize(new Dimension(260,100));
      
      p_system = new JPanel();
      p_system.setPreferredSize(new Dimension(1600,250));
      p_system.setBackground(Color.LIGHT_GRAY);
      a_input = new JTextField(100);
      a_input.setPreferredSize(new Dimension(1600,40));
      a_input.setFont(new Font("����", Font.PLAIN, 16));
      
      p_quiz.setLayout(new BorderLayout());
      setLayout(new FlowLayout());
      
      p_quiz.add(la_quiz);
      
      p_info.add(la_myId);
      p_info.add(la_stage);
      p_info.add(la_score);
      p_info.add(bt_start);
      p_info.add(bt_exit);
      
      p_system.add(a_chat);
      p_system.add(a_input);
      
      add(p_quiz);
      add(p_left);
      add(p_waitRoom);
      add(p_right);
      add(p_info);
      add(p_system);
      
      bt_exit.addActionListener(this);
      
      setSize(1600,900);
      setVisible(true);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);

      addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameWatingRoom.login.disconnect();
			}
		});
      
      a_input.addKeyListener(new KeyAdapter() {
    	  public void keyReleased(KeyEvent e) {
    		  // ����ġ�� �޽��� ������
    		  int key = e.getKeyCode();
    		  if (key == KeyEvent.VK_ENTER) {
    			  // �ؽ�Ʈ�ʵ忡�� �ļ� ���� �޽��� ����
    			  String msg = a_input.getText();
    			  gameWatingRoom.login.clientThread.send(msg); // ������
//                login.clientThread.listen(); //�ް�
    		  }
    	  }
      });
   }

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_exit) {
			setVisible(false);
			gameWatingRoom.login.clientThread.changeArea(gameWatingRoom.area);
			gameWatingRoom.setVisible(true);
		}
		
	}
	
}