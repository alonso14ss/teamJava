package javaProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

/*
유니캐스팅과는 달리 서버에서 날아온 다른 사람이 보낸 메시지는
현재 사용자가 어떠한 액션을 하지 않아도(예 - 키보드 엔터)
언제나 청취되어야 한다.
따라서 무한루프로 청취업무를 수행해야 하는데 이때 메인실행부를
무한루프레 빠지게하면 GUI처리 Event처리를 할 수 없게 되므로
listen에 대한 처리는 별도의 개발자 정의 스레드로 처리하자.
왜? 쓰레드란 하나의 프로세스 내에서 독립적으로 수행될 수 있는 단위이므로
메인쓰레드와는 별도로 무한루프등의 처리에 적절하다.
*/
public class MyClientThread extends Thread{
   Socket socket;
   BufferedReader buffr;
   BufferedWriter buffw;
   Login Login;
   JTextArea a;
   public MyClientThread(Login Login,Socket socket) {
      this.Login = Login;
      this.socket = socket;
      
      try {
         buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void send(String msg) {
	   
      try {
         buffw.write(Login.myId+" : "+msg+"\n");
         buffw.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   //메시지 받기
   public void listen() {
      String msg = null;
      try {
         while(true) {
            msg = buffr.readLine(); //청취
            a.append(msg+"\n");            
         }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void changeArea(JTextArea area)
   {
	   System.out.println("chaneArea : "+area);
	   this.a = area;
   }
   
   public static void main(String[] args) {
      new Login();
   }

   
   public void run() {
      listen();
   }
}