package com.bank.www.service;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class userJFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6477270330102433152L;

	public userJFrame() {
		super("�û�����������ʧ��������");
		this.setBounds(300, 240, 400, 150);// ���ڳ��������꼰��С
		this.setResizable(false);// ���ڴ�С���ܸı�
		this.setBackground(Color.lightGray);// ��ɫ����
		this.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER));// ��岼��Ϊ����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();// �½���һ�����
		this.getContentPane().add(panel1, "North");
		this.setVisible(true);// ���ڿɼ�
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		new userJFrame();
	}
}
