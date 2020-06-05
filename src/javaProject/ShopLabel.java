
package javaProject;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import javax.swing.JLabel;

public class ShopLabel extends JLabel {
	int id;

	public ShopLabel(int id, ImageIcon icon) {
		super(icon);
		this.id = id;
		setPreferredSize(new Dimension(70, 60));
	}
}