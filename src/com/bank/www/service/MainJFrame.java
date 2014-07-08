package com.bank.www.service;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8055401398269488327L;

	private String no;
	private Integer type;

	public MainJFrame() {
		super("模拟银行存取款管理系统");
		this.setBounds(300, 240, 400, 300);
		this.setResizable(false);
		this.setBackground(Color.lightGray);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("选项");
		final JMenuItem item1 = new JMenuItem("退出");
		item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == item1) {
					System.exit(0);
				}
			}
		});
		menu.add(item1);
		bar.add(menu);
		this.setJMenuBar(bar);

		LoginPanel loginPanel = new LoginPanel(this);
		this.getContentPane().add(loginPanel);
		this.setVisible(true);
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static void main(String[] args) {
		new MainJFrame();
	}

}
