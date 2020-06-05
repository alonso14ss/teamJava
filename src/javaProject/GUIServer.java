
package javaProject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

//Runnable�� ������ ��ü�� �ƴ� ���� run�޼��带 ������ �������̽�
public class GUIServer extends JFrame implements Runnable {
   JPanel p_north;
   JTextField t_port;
   JButton bt;
   JTextArea area;
   JScrollPane scroll;

   // ��ȭ�� ������ �ƴ� ������ ������ ���� ����
   // ��ȭ��� �������ڸ� ��ȭ�Ⱑ �ƴ϶� ��
   ServerSocket server;
   Thread thread;
   

   Vector<MessageObj> clienList = new Vector<MessageObj>();

   public GUIServer() {
      p_north = new JPanel();
      t_port = new JTextField("7777", 10);
      bt = new JButton("��������");
      area = new JTextArea();
      scroll = new JScrollPane(area);

      p_north.add(t_port);
      p_north.add(bt);

      add(p_north, BorderLayout.NORTH);
      add(scroll);
      setBounds(400, 100, 300, 400);
      setVisible(true);

      // ���� ���� �ݱ� ó���� �Ʒ��� �޼���� ������ ����!!
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // ��������
      bt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // ������ ���� �� runnable ���·� ����
            // ������ ���� �� Runnable�� ������ �ڸ� �Ű������� ������ �ִ�.
            thread = new Thread(GUIServer.this);
            thread.start();
         }
      });
      // �޼����� �����ɶ�, Ŀ�� ��Ŀ���� ��ũ���� ���� �ϴܿ� ���� ó��
      DefaultCaret caret = (DefaultCaret) area.getCaret();
      caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
   }

   public void startServer() {
      int port = Integer.parseInt(t_port.getText());
      try {
         server = new ServerSocket(port);
         // ���� ����
         area.append("���� ���� �� ������ ��ٸ��� ��..\n");
         while (true) {
            Socket socket = server.accept(); // ���ӻ簡 �߰ߵ� ������ ���Ѵ��!
            // ����! : ���� �� �ڵ带 ���ξ������� ����ΰ� ������ ��� ��� ������,
            // �̺�Ʈ �������� �Ҽ� ���� �ȴ�. ���� ����� ���ν����尡 �������³�
            // ���ѷ����� ������ �ؼ��� �ȵȴ�.�ȵ���̵�, ������������ �̷� �����
            // �ݱ�� �Ѵ�
            String ip = socket.getInetAddress().getHostAddress();
            area.append(ip + "�� ������\n");
            
            //������ ���� MessageObj�� �����Ͽ� ���ϵ��� ����
            //��? �������� ������ while���� ���������� ������Ƿ�
            MessageObj messageObj = new MessageObj(this, socket,ip);
            //������ �޽��� ������Ʈ�� ���Ϳ� ����
            //���ʹ� ��������̱� ������ �� ���α׷��� �����Ҵ���� �� ���� �����ȴ�.
            //������� �ν��Ͻ��� �Բ��Ѵ�
            messageObj.start();
            clienList.add(messageObj);
            area.append("Ŭ���̾�Ʈ ���� " + clienList.size()+"\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // �����ڴ� ������� ���� ������ �ڵ带 run�� ������ �ϸ� �ȴ�
   // �����带 �����ϴ� ����� ���������� �ִµ� ���� Runnable�������̽��� �̿��Ͽ�
   // �����ϴ� ����� �̿��� ������
   public void run() {
      startServer();
   }

   public static void main(String[] args) {
      new GUIServer();
   }
}