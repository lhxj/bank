package com.bank.www.service;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class transferAccountsJFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4943023271660033715L;

	public transferAccountsJFrame() {
		super("转账操作窗口");
		this.setBounds(300, 240, 400, 150);// 窗口初弹出坐标及大小
		this.setResizable(false);// 窗口大小不能改变
		this.setBackground(Color.lightGray);// 颜色设置
		this.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER));// 面板布局为居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();// 新建第一个面板
		this.getContentPane().add(panel1, "North");
		this.setVisible(true);// 窗口可见
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		new transferAccountsJFrame();
	}
}
