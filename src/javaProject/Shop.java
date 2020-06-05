package javaProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Shop extends JFrame implements ActionListener{

   JPanel p_west;
   JPanel p_center;
   JPanel p_canvas;
   JPanel btBox, pointBox, blank, priceBox;
   Toolkit kit = Toolkit.getDefaultToolkit();
   JButton bt_buy, bt_reset, bt_back;
   JLabel la_shop, la_point, la_price;
   ImageIcon prev_icon, next_icon;
   Image bigImage;
   String dir = "D:/web_app/java_workspace/javaProject/src/data/";
   String[] path = { "monster.png", "monster1.png", "monster2.png", "monster3.png", "monster4.png", "monster5.png",
         "monster6.png", "monster7.png" };
   ShopLabel[] la_thumb;
   ImageIcon[] icon;
   JScrollPane scroll;
   JTextField t_id, t_havePoint;
   int cursor = 0;
   GameWatingRoom gameWatingRoom;

   public Shop(GameWatingRoom gameWatingRoom) {
	  this.gameWatingRoom = gameWatingRoom;
      p_west = new JPanel();
      p_center = new JPanel();
      btBox = new JPanel();
      pointBox = new JPanel();
      blank = new JPanel();
      priceBox = new JPanel();

      p_canvas = new JPanel() {
         @Override
         public void paint(Graphics g) {
            g.drawImage(bigImage, 0, 0, 250, 250, this);
         }
      };

      scroll = new JScrollPane(p_west);

      t_id = new JTextField("0", 8);
      t_havePoint = new JTextField("50,000", 6);

      bt_buy = new JButton("구매하기");
      bt_reset = new JButton("되돌리기");
      bt_back = new JButton("돌아가기");

      la_shop = new JLabel("상점");
      la_point = new JLabel("보유 포인트");
      la_price = new JLabel("가격");

      p_west.setPreferredSize(new Dimension(300, 250));

      p_canvas.setPreferredSize(new Dimension(250, 250));

      t_id.setFont(new Font("돋움", Font.BOLD, 20));
      t_havePoint.setFont(new Font("돋움", Font.BOLD, 20));

      btBox.setLayout(new GridLayout());
      btBox.setPreferredSize(new Dimension(280, 80));
      btBox.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

      pointBox.setLayout(new GridLayout());
      pointBox.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

      la_shop.setPreferredSize(new Dimension(300, 50));

      blank.setPreferredSize(new Dimension(300, 120));

      bt_back.setPreferredSize(new Dimension(270, 50));

      priceBox.setLayout(new GridLayout());
      priceBox.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

      p_west.add(la_shop);
      p_west.add(p_canvas);
      btBox.add(bt_buy);
      btBox.add(bt_reset);
      p_west.add(btBox);

      pointBox.add(la_point);
      pointBox.add(t_havePoint);
      p_west.add(pointBox);

      p_west.add(blank);
      p_west.add(bt_back);

      priceBox.add(la_price);
      priceBox.add(t_id);

      p_center.add(priceBox);

      add(scroll, BorderLayout.WEST);
      add(p_center);

      createImage();

      for (int i = 0; i < path.length; i++) {
         p_center.add(la_thumb[i]);
         la_thumb[i].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

               ShopLabel obj = (ShopLabel) e.getSource();

               t_id.setText(Integer.toString(obj.id * 1000 + 1000));
               cursor = obj.id;
               showImage(obj.id);
            }
         });
      }
      
      bt_back.addActionListener(this);

      setSize(600, 700);
      setVisible(true);
      setResizable(false);
      setLocationRelativeTo(null);
      
      addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameWatingRoom.login.disconnect();
			}
		});
      
   }

   public void createImage() {
      la_thumb = new ShopLabel[path.length];
      icon = new ImageIcon[path.length];

      for (int i = 0; i < path.length; i++) {
         la_thumb[i] = new ShopLabel(i, new ImageIcon(dir + path[i]));
      }
      bigImage = kit.getImage(dir + path[0]);
   }

   public void showImage(int id) {
      bigImage = kit.getImage(dir + path[id]);
      p_canvas.repaint();
   }
   
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_back) {
			setVisible(false);
			gameWatingRoom.setVisible(true);
		}
		
	}
}