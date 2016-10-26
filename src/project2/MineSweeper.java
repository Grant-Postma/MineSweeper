package project2;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class MineSweeper extends JFrame {
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.setSize(800, 400);
		frame.setVisible(true);
	}
}