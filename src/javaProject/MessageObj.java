package javaProject;

//�� ��ü�� Socket, BufferedReader, BufferedWriter�� �����س��� �뵵
//�� �޽��� ��ü�� ���� ������ ���� �ʰ� ���������� �����ؾ� �Ѵ�
//�������? �ϳ��� ���μ��������� ���������� ����� �� �ִ� ���ν������
//�̶� �����ڴ� ���� ������ �ڵ带 run�޼��忡 �ۼ��Ѵ�
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageObj extends Thread {
   Socket socket;
   BufferedReader buffr;
   BufferedWriter buffw;
   GUIServer server;
   String ip;
   boolean flag = true;

   // �����ڰ� �߰������� ����� ������ ���� ����.
   public MessageObj(GUIServer server, Socket socket, String ip) {
      this.server = server;
      this.socket = socket;
      this.ip = ip;
      // �������κ��� ��Ʈ�� �̱�
      try {
         buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   // ���� �� ��Ʈ���� �� Ŭ������ ���������Ƿ� ���⼭ �޽��� ó��
   // �޽��� û��
   public void listen() {
      String msg = null;
      try {
         while (flag) {
            msg = buffr.readLine();
            if(msg.indexOf("disconnect") != -1)
            {
            	System.out.println(server.clienList.size());
            	//������ clienList���� �ش� ip ����Ʈ���� ����!!
            	int a = msg.indexOf('/');
            	String b = msg.substring(a+1);
            	System.out.println("ã�Ҵ�!!!" + b);
            	for(int i = 0 ; i < server.clienList.size(); i++)
            	{
            		System.out.println(b);
            		System.out.println(server.clienList.get(i).ip);
            		if(b.equals(server.clienList.get(i).ip))
            		{
            			System.out.println("����");
            			server.clienList.get(i).flag = false;
            			server.clienList.remove(i);
            			break;
            		}
            	}
            }
            // log
            System.out.println(server.clienList.size());
            server.area.append(msg + "\n");
            // ������

            // ������ �����ؼ� ���� ������ ������ �ٸ� MessageObj��
            // send() �� ȣ������
            // clientList��ŭ �ݺ��� �����鼭 send() ȣ���ϸ� ��
            // �̶� ����������� �޽����� ���ư��Ƿ� Multi Casting
            for (int i = 0; i < server.clienList.size(); i++) {
               MessageObj obj = server.clienList.get(i);
               obj.send(msg);
            }
         }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   // �޽��� ����
   public void send(String msg) {
      try {
         buffw.write(msg + "\n");
         buffw.flush();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   // ���������� �ڵ带 run�� �ۼ�
   public void run() {
      listen();
   }
}