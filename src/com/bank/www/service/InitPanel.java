package com.bank.www.service;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class InitPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717870149600774536L;

	private JButton userButton, transferButton, bankButton, accountButton, statisticsButton, depositButton, back;
	private JFrame jFrame;

	public InitPanel(JFrame jFrame) {
		this.jFrame = jFrame;
		Integer type = ((MainJFrame) this.jFrame).getType();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border title = BorderFactory.createTitledBorder("系统主窗口");
		this.setBorder(title);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		if (type == 1) {
			userButton = new JButton("用户账户管理");
			panel.add(userButton);
			userButton.addActionListener(this);
		}

		if (type == 0) {
			transferButton = new JButton("用户转账管理");
			panel.add(transferButton);
			transferButton.addActionListener(this);

			accountButton = new JButton("账户存取款管理");
			panel.add(accountButton);
			accountButton.addActionListener(this);
		}

		if (type == 2) {
			bankButton = new JButton("银行利率管理");
			panel.add(bankButton);
			bankButton.addActionListener(this);

			statisticsButton = new JButton("按期查询记录管理");
			panel.add(statisticsButton);
			statisticsButton.addActionListener(this);

			depositButton = new JButton("定期存款统计管理");
			panel.add(depositButton);
			depositButton.addActionListener(this);
		}
		this.add(panel);

		JPanel panel2 = new JPanel();
		back = new JButton("退出登录");
		back.addActionListener(this);
		panel2.add(back);
		this.add(panel2);
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
			this.jFrame.getContentPane().add(new BankPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == accountButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new AccountPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == statisticsButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new StatisticsPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == depositButton) {
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new depositPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
		if (e.getSource() == back) {
			((MainJFrame) this.jFrame).setNo(null);
			((MainJFrame) this.jFrame).setType(null);
			this.jFrame.remove(this);
			this.jFrame.getContentPane().add(new LoginPanel(this.jFrame));
			this.jFrame.setVisible(true);
			this.jFrame.repaint();
		}
	}
}
