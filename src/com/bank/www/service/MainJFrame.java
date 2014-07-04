package com.bank.www.service;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8055401398269488327L;

	public MainJFrame() {
		super("模拟银行存取款管理系统");
		this.setBounds(300, 240, 400, 300);
		this.setResizable(false);
		this.setBackground(Color.lightGray);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("选项");
		JMenuItem item1 = new JMenuItem("退出");
		menu.add(item1);
		bar.add(menu);
		this.setJMenuBar(bar);

		InitPanel initPanel = new InitPanel(this);
		this.getContentPane().add(initPanel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainJFrame();
	}

}
