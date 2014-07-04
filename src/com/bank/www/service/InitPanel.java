package com.bank.www.service;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class InitPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717870149600774536L;

	private JButton userButton, transferButton, bankButton;
	private JFrame jFrame;

	public InitPanel() {
		this.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER));
		Border title = BorderFactory.createTitledBorder("系统主窗口");
		this.setBorder(title);

		userButton = new JButton("用户账户管理");
		this.add(userButton);
		userButton.addActionListener(this);

		transferButton = new JButton("用户转账管理");
		this.add(transferButton);
		transferButton.addActionListener(this);

		bankButton = new JButton("银行利率管理");
		this.add(bankButton);
		bankButton.addActionListener(this);
	}

	public InitPanel(JFrame jFrame) {
		this();
		this.jFrame = jFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == userButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new UserPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == transferButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new TransferPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == bankButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new TransferPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
	}
}
