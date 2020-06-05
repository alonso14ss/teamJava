package javaProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

/*
����ĳ���ð��� �޸� �������� ���ƿ� �ٸ� ����� ���� �޽�����
���� ����ڰ� ��� �׼��� ���� �ʾƵ�(�� - Ű���� ����)
������ û��Ǿ�� �Ѵ�.
���� ���ѷ����� û������� �����ؾ� �ϴµ� �̶� ���ν���θ�
���ѷ����� �������ϸ� GUIó�� Eventó���� �� �� ���� �ǹǷ�
listen�� ���� ó���� ������ ������ ���� ������� ó������.
��? ������� �ϳ��� ���μ��� ������ ���������� ����� �� �ִ� �����̹Ƿ�
���ξ�����ʹ� ������ ���ѷ������� ó���� �����ϴ�.
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
   //�޽��� �ޱ�
   public void listen() {
      String msg = null;
      try {
         while(true) {
            msg = buffr.readLine(); //û��
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