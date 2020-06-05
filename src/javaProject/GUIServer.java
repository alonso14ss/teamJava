
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

//Runnable은 쓰레드 자체는 아닌 단지 run메서드를 보유한 인터페이스
public class GUIServer extends JFrame implements Runnable {
   JPanel p_north;
   JTextField t_port;
   JButton bt;
   JTextArea area;
   JScrollPane scroll;

   // 대화용 소켓이 아닌 접속자 감지용 소켓 선언
   // 전화기와 비유하자면 수화기가 아니라 벨
   ServerSocket server;
   Thread thread;
   

   Vector<MessageObj> clienList = new Vector<MessageObj>();

   public GUIServer() {
      p_north = new JPanel();
      t_port = new JTextField("7777", 10);
      bt = new JButton("서버가동");
      area = new JTextArea();
      scroll = new JScrollPane(area);

      p_north.add(t_port);
      p_north.add(bt);

      add(p_north, BorderLayout.NORTH);
      add(scroll);
      setBounds(400, 100, 300, 400);
      setVisible(true);

      // 추후 소켓 닫기 처리시 아래의 메서드는 사용안할 거임!!
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // 서버가동
      bt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 쓰레드 생성 및 runnable 상태로 진입
            // 쓰레드 생성 및 Runnable을 구현한 자를 매개변수로 넣을수 있다.
            thread = new Thread(GUIServer.this);
            thread.start();
         }
      });
      // 메세지가 누적될때, 커서 포커스가 스크롤의 제일 하단에 오게 처리
      DefaultCaret caret = (DefaultCaret) area.getCaret();
      caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
   }

   public void startServer() {
      int port = Integer.parseInt(t_port.getText());
      try {
         server = new ServerSocket(port);
         // 서버 가동
         area.append("서버 생성 및 접속자 기다리는 중..\n");
         while (true) {
            Socket socket = server.accept(); // 접속사가 발견될 때까지 무한대기!
            // 위험! : 만일 이 코드를 메인쓰레드인 실행부가 수행할 경우 모든 디자인,
            // 이벤트 감지등을 할수 없게 된다. 따라서 절대로 메인스레드가 지연상태나
            // 무한루프에 빠지게 해서는 안된다.안드로이드, 아이폰에서는 이런 방식을
            // 금기시 한다
            String ip = socket.getInetAddress().getHostAddress();
            area.append(ip + "님 접속함\n");
            
            //접속자 마다 MessageObj를 생성하여 소켓등을 보관
            //왜? 보관하지 않으면 while문의 지역변수로 사라지므로
            MessageObj messageObj = new MessageObj(this, socket,ip);
            //생성된 메시지 오브젝트를 벡터에 보관
            //벡터는 멤버변수이기 때문에 이 프로그램이 종료할대까지 그 값이 유지된다.
            //생명력이 인스턴스와 함께한다
            messageObj.start();
            clienList.add(messageObj);
            area.append("클라이언트 수는 " + clienList.size()+"\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // 개발자는 쓰레드로 독립 수행할 코드를 run에 재정의 하면 된다
   // 쓰레드를 구현하는 방법은 여러가지가 있는데 그중 Runnable인터페이스를 이용하여
   // 구현하는 방법을 이용해 본것임
   public void run() {
      startServer();
   }

   public static void main(String[] args) {
      new GUIServer();
   }
}